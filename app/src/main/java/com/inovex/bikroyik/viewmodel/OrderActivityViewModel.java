package com.inovex.bikroyik.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.Client;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.data.repositories.OrderActivityRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivityViewModel extends ViewModel{
    private static OrderActivityViewModel viewModel = null;
    private Context mContext;
    private OrderActivityRepo repo;
    private SharedPreference sharedPreference;
    private MutableLiveData<Client> clientMutableLiveData = new MutableLiveData<>();
    private Map<String, OrderedProductModel> productModelMap = new HashMap<>();
    public MutableLiveData<OrderJsonModel> orderJsonModelMutableLiveData = new MutableLiveData<>();
    private List<ProductModel> totalSales_arrayList = new ArrayList<>(); //only can use total sales item functions
    private MutableLiveData<List<OrderedProductModel>> orderedProductListLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> totalItemLiveData = new MutableLiveData<>();
    public MutableLiveData<String> barcodeLiveData = new MutableLiveData<>();
    private MutableLiveData<ProductModel> productModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> onScannerButtonClick = new MutableLiveData<>();
    private MutableLiveData<Integer> currentSpinnerSelectedItemPosition = new MutableLiveData<>();
    private MutableLiveData<String> totalChargeMuLiveData = new MutableLiveData<>();


    public static OrderActivityViewModel getInstance(){
        if (viewModel == null){
            viewModel = new OrderActivityViewModel();
            return viewModel;
        }
        return viewModel;
    }

    public void init(Context context, String orderId){
        mContext = context;
        repo = OrderActivityRepo.getInstance(context);
        repo.init(context);
        totalItemLiveData.setValue(0);



        if (orderJsonModelMutableLiveData != null){
            sharedPreference = SharedPreference.getInstance(context);
            OrderJsonModel orderJsonModel = null;
            if (orderId != null){
                orderJsonModel = repo.getOrderData(orderId);
                if (orderJsonModel != null){
                    int totalOrderItem = 0;
                    for (int i=0; i<orderJsonModel.getOrderDetails().size(); i++){
                        OrderedProductModel orderedProductModel = orderJsonModel.getOrderDetails().get(i);
                        totalOrderItem += orderedProductModel.getQuantity();
                        productModelMap.put(orderJsonModel.getOrderDetails().get(i).getProductId(), orderedProductModel);
                    }
                    orderJsonModelMutableLiveData.setValue(orderJsonModel);
                    totalItemLiveData.setValue(totalOrderItem);
                }else {
                    orderJsonModelMutableLiveData.setValue(null);
                    orderedProductListLiveData.setValue(null);
                    totalChargeMuLiveData.setValue("0.0");
                }
            }else {
                orderJsonModelMutableLiveData.setValue(null);
                orderedProductListLiveData.setValue(null);
                totalChargeMuLiveData.setValue("0.0");
            }

//            if (orderJsonModel != null){
//                orderJsonModelMutableLiveData.setValue(orderJsonModel);
//            }

        }else {
            sharedPreference = SharedPreference.getInstance(context);
            OrderJsonModel orderJsonModel = null;
            if (orderId != null){
                orderJsonModel = repo.getOrderData(orderId);
            }else {
                orderJsonModelMutableLiveData.setValue(null);
                orderedProductListLiveData.setValue(null);
                totalChargeMuLiveData.setValue("0.0");
            }

            if (orderJsonModel != null){
                orderJsonModelMutableLiveData.setValue(orderJsonModel);
            }else {
                orderJsonModelMutableLiveData.setValue(null);
                orderedProductListLiveData.setValue(null);
                totalChargeMuLiveData.setValue("0.0");
            }
        }
    }

    public void makeMapEmpty(){
        productModelMap.clear();
    }

    public LiveData<OrderJsonModel> getOrderJsonModel(){
        return orderJsonModelMutableLiveData;
    }

    public LiveData<String> getBarcode(){
        return barcodeLiveData;
    }

    public void setBarcodeLiveData(String barCode){
        if (barcodeLiveData != null && !TextUtils.isEmpty(barCode)){
            barcodeLiveData.setValue(barCode);
        }
    }

    public LiveData<String> getTotalChargeLiveData(){
        return totalChargeMuLiveData;
    }



    public void makeOrder(Context context, ProductModel productModel, int newAddedQuantity){
        boolean isOrderedAtLeastOneProduct = productModelMap.containsKey(productModel.getProductId());
        String orderId = sharedPreference.getCurrentOrderId();

//        if (orderJsonModelMutableLiveData != null && orderJsonModelMutableLiveData.getValue() != null){
//            if (orderJsonModelMutableLiveData.getValue().getOrderId() != null){
//                orderId = orderJsonModelMutableLiveData.getValue().getOrderId();
//            }
//        }
        //update total order item

        boolean isOrderExist = false;
        if (!TextUtils.isEmpty(orderId)){
            isOrderExist = repo.isOrderExist(orderId);
        }

        if ( isOrderExist && isOrderedAtLeastOneProduct){
            //in same order id at least one product already added
            updateProductAndOrder(productModel, newAddedQuantity, false, false);

        }else {
            if (isOrderExist){
                //order exist but this product is new for ths order
                updateProductAndOrder(productModel, newAddedQuantity, false, true);

            }else {

                updateProductAndOrder(productModel, newAddedQuantity, true, false);
            }
        }

    }

    public void setOnScannerButtonClickListener(boolean click){
        onScannerButtonClick.setValue(click);
    }

    public LiveData<Boolean> geOnScannerButtonClickListener(){
       return onScannerButtonClick;
    }

    public LiveData<Integer> getTotalOrderItem(){
        return totalItemLiveData;
    }

    public void setTotalOrderItem(int totalItems){
        totalItemLiveData.setValue(totalItems);
    }


    public LiveData<OrderJsonModel> getOrderedProductList(){
        return orderJsonModelMutableLiveData;
    }

    public LiveData<Integer> getProductSelectedPosition(){
        return currentSpinnerSelectedItemPosition;
    }

    public void setProductCategorySelectedPosition(int position){
        currentSpinnerSelectedItemPosition.setValue(position);
    }

    private void updateProductAndOrder(ProductModel productModel, int newAddedQuantity,
                                       boolean isVeryNewOrder, boolean isExistingOrderButNewProduct){
        OrderJsonModel order = null;
        OrderedProductModel orderedProduct = null;

        if (isVeryNewOrder){
            //order doesn't exist so create new order
            SharedPreference sharedPreference = SharedPreference.getInstance(mContext);
            order = new OrderJsonModel();
            orderedProduct = new OrderedProductModel();

            order.setOrderId(String.valueOf(System.currentTimeMillis()));
            orderedProduct.setProductId(productModel.getProductId());
            orderedProduct.setProductName(productModel.getProductName());
            //update orderId with new OrderId in sharedPreference
            sharedPreference.setCurrentOrderId(order.getOrderId());

        }else if (isExistingOrderButNewProduct){
            order = orderJsonModelMutableLiveData.getValue();
            orderedProduct = new OrderedProductModel();
            orderedProduct.setProductId(productModel.getProductId());
            orderedProduct.setProductName(productModel.getProductName());
            productModelMap.put(productModel.getProductId(), orderedProduct);
        }else {
            order = orderJsonModelMutableLiveData.getValue();
            orderedProduct = productModelMap.get(productModel.getProductId());
        }



        if (order != null){


            //total price calculation
            orderedProduct.setQuantity(orderedProduct.getQuantity()+newAddedQuantity);

            int totalOrder = orderedProduct.getQuantity();
            double totalForProduct = productModel.getMrp()*totalOrder;
            double totalOrderPrice = order.getTotal() - orderedProduct.getTotalPrice();
            totalOrderPrice = totalOrderPrice + totalForProduct;

            orderedProduct.setTotalPrice(totalForProduct);
            order.setTotal(totalOrderPrice);

            //tax calculation
//            if (Boolean.parseBoolean(productModel.getIsExcludedTax())){
//                final double totalTax = (orderedProduct.getTotalPrice()*productModel.getTax())/100;
//                double orderTax = order.getTotalTax() - orderedProduct.getTotalTax();
//                order.setTotalTax(orderTax+totalTax);
//                orderedProduct.setTotalTax(totalTax);
//            }

            //tax calculation
            List<Tax> taxList = repo.getAllTaxInfo(order.getOrderId(), productModel.getProductId());
            double totalPercent = 0;
            double totalTax_ = 0;

            if (Boolean.parseBoolean(productModel.getIsExcludedTax())){
                totalPercent = productModel.getTax();
            }

            if (taxList != null){
                for (int i=0; i< taxList.size(); i++){
                    if (taxList.get(i).getIsTaxApplied()){
                        totalPercent += taxList.get(i).getTaxAmount();
                    }
                }
            }

            double orderTax = order.getTotalTax() - orderedProduct.getTotalTax();
            totalTax_ = (orderedProduct.getTotalPrice()*totalPercent)/100;
            order.setTotalTax(orderTax+totalTax_);
            orderedProduct.setTotalTax(totalTax_);




//            //discount calculation
//            if (Boolean.parseBoolean(productModel.getAvailableDiscount())){
//                double totalDiscount = 0;
//                if (productModel.getDiscountType().equals("BDT") ||
//                        productModel.getDiscountType().equals("bdt")){
//                    totalDiscount = productModel.getDiscount()*newAddedQuantity;
//                    totalDiscount += orderedProduct.getTotalDiscount();
//                }else {
//                    totalDiscount = (totalPriceForNewQuantity*productModel.getDiscount())/100;
//                }
//
//                double orderDiscount = order.getTotalDiscount() - orderedProduct.getTotalDiscount();
//                orderDiscount += totalDiscount;
//                order.setTotalDiscount(orderDiscount);
//                orderedProduct.setTotalDiscount(totalDiscount);
//            }

            //discount calculation
            double totalDiscount = 0;
            if (Boolean.parseBoolean(productModel.getAvailableDiscount())){
                if (productModel.getDiscountType().equals("BDT") ||
                        productModel.getDiscountType().equals("bdt")){
                    totalDiscount = productModel.getDiscount()*totalOrder;
                }else {
                    totalDiscount = (orderedProduct.getTotalPrice()*productModel.getDiscount())/100;
                }
            }


            List<DiscountDetails> discountDetailsList = repo.getAllDiscountInfo(order.getOrderId(), productModel.getProductId());
            if (discountDetailsList != null){
                totalPercent = 0;
                double discountBdt = 0;
                String discountTypeBDT = "BDT";

                for (int i=0; i< discountDetailsList.size(); i++){
                    if (discountDetailsList.get(i).isDiscountApplied()){
                        if (discountDetailsList.get(i).getDiscountType().equals(discountTypeBDT) ||
                                discountDetailsList.get(i).getDiscountType().equals("bdt")){
                            discountBdt += discountDetailsList.get(i).getDiscount()*orderedProduct.getQuantity();
                        }else {
                            totalPercent += discountDetailsList.get(i).getDiscount();
                        }
                    }
                }
                double totalDiscountInPercentage =(orderedProduct.getTotalPrice()*totalPercent)/100;
                totalDiscount = totalDiscount + totalDiscountInPercentage + discountBdt;

            }

            double orderDiscount = order.getTotalDiscount() - orderedProduct.getTotalDiscount();
            orderDiscount += totalDiscount;
            order.setTotalDiscount(orderDiscount);
            orderedProduct.setTotalDiscount(totalDiscount);




//
//            //tax calculation
//            List<Tax> taxList = orderedProduct.getTax();
//            double totalPercent = 0;
//            double totalTax = 0;
//
//            double productPrice = orderedProduct.getTotalPrice();
//            if (taxList != null){
//
//                for (int i=0; i< taxList.size(); i++){
//                    totalPercent += taxList.get(i).getTaxAmount();
//                }
//                double mainOldTotalTax = order.getTotalTax() - orderedProduct.getTotalTax();
//                totalTax = (productPrice*totalPercent)/100;
//                orderedProduct.setTotalTax(totalTax);
//                double mainNewTotalTax = mainOldTotalTax + totalTax;
//                order.setTotalTax(mainNewTotalTax);
//            }
//
//
//
//
//
//            //discount calculation
//            List<DiscountDetails> discountDetailsList = orderedProduct.getDiscountDetails();
//            if (discountDetailsList != null){
//                double totalDiscount = 0;
//                productPrice = orderedProduct.getTotalPrice();
//                double mainOldTotalDiscount = order.getTotalDiscount();
//                totalPercent = 0;
//                double discountBdt = 0;
//                String discountTypeBDT = "BDT";
//
//
//
//
//                for (int i=0; i< discountDetailsList.size(); i++){
//                        if (discountDetailsList.get(i).getDiscountType().equals(discountTypeBDT) ||
//                                discountDetailsList.get(i).getDiscountType().equals("bdt")){
//                            discountBdt += discountDetailsList.get(i).getDiscount();
//                        }else {
//                            totalPercent += discountDetailsList.get(i).getDiscount();
//                        }
//                }
//                double totalDiscountInPercentage =(productPrice*totalPercent)/100;
//                totalDiscount = totalDiscountInPercentage + discountBdt;
//                double mainNewDiscount = mainOldTotalDiscount + totalDiscount;
//
//                orderedProduct.setTotalDiscount(totalDiscount);
//                order.setTotalDiscount(mainNewDiscount);
//            }




            //update grand total
            orderedProduct.setGrandTotal((orderedProduct.getTotalPrice() +
                    orderedProduct.getTotalTax()) - orderedProduct.getTotalDiscount());
            order.setGrandTotal((order.getTotal()+order.getTotalTax())-order.getTotalDiscount());




            //product and offer quantity

            boolean isOfferAvailable = Boolean.parseBoolean(productModel.getAvailableOffer());
            if (isOfferAvailable){
                int totalOffer = (orderedProduct.getQuantity()/productModel.getRequiredQuantity())*
                        productModel.getFreeQuantity();
                orderedProduct.setOfferItemId(productModel.getOfferItemId());
                orderedProduct.setOfferName(productModel.getFreeItemName());
                orderedProduct.setOfferQuantity(totalOffer);
            }else {
                orderedProduct.setOfferQuantity(0);
            }
            boolean isUpdatedSuccessfully = false;
            if (isVeryNewOrder){
                isUpdatedSuccessfully = repo.insertNewOrder(order, orderedProduct);
            }else if (isExistingOrderButNewProduct){
                isUpdatedSuccessfully = repo.addNewProductInExistingOrder(order, orderedProduct);
            }else {
                isUpdatedSuccessfully = repo.updateExistingOrder(order, orderedProduct);
            }



            if (isUpdatedSuccessfully){
                OrderJsonModel orderJsonModel = repo.getOrderData(order.getOrderId());
                orderJsonModelMutableLiveData.setValue(orderJsonModel);
                productModelMap.put(orderedProduct.getProductId(), orderedProduct);

                if (totalItemLiveData != null){
//                    int newItem = totalItemLiveData.getValue() + newAddedQuantity;

                    int totalProduct = totalItemLiveData.getValue()+newAddedQuantity;
                    totalItemLiveData.setValue(totalProduct);
                }
            }
        }
    }

    public ProductModel getProductByBarcode(String barcode){
        ProductModel productModel = repo.getSingleProductByBarcode(barcode);

        return productModel;
    }


    private void newProductWithExistingOrder(ProductModel productModel, int newAddedQuantity){

        OrderJsonModel order = orderJsonModelMutableLiveData.getValue();
        OrderedProductModel orderedProductModel = new OrderedProductModel();
        orderedProductModel.setQuantity(newAddedQuantity);


        if (order != null){
            //total price calculation
            String orderId = order.getOrderId();

            double totalPriceForNewQuantity = productModel.getMrp()*newAddedQuantity;
            double totalForCoreOrder = order.getTotal()+ totalPriceForNewQuantity;

            orderedProductModel.setTotalPrice(totalPriceForNewQuantity);
            order.setTotal(totalForCoreOrder);

            //tax calculation
            if (Boolean.parseBoolean(productModel.getIsExcludedTax())){
                final double totalTax = (orderedProductModel.getTotalPrice()*productModel.getTax())/100;
                double orderTax = order.getTotalTax() - orderedProductModel.getTotalTax();
                order.setTotalTax(orderTax+totalTax);
                orderedProductModel.setTotalTax(totalTax);
            }

            //discount calculation
            if (Boolean.parseBoolean(productModel.getAvailableDiscount())){
                double totalDiscount = 0;
                if (productModel.getDiscountType().equals("BDT") ||
                        productModel.getDiscountType().equals("bdt")){
                    totalDiscount = productModel.getDiscount()*newAddedQuantity;
                    totalDiscount += orderedProductModel.getTotalDiscount();
                }else {
                    totalDiscount = (totalPriceForNewQuantity*productModel.getDiscount())/100;
                }

                double orderDiscount = order.getTotalDiscount() - orderedProductModel.getTotalDiscount();
                orderDiscount += totalDiscount;
                order.setTotalDiscount(orderDiscount);
                orderedProductModel.setTotalDiscount(totalDiscount);
            }


//            //add tax in tax database table
//            Tax tax = new Tax(productModel.getTaxId(), productModel.getTaxName(), productModel.getTax());
//            repo.insertTax(tax);
//            boolean isExcluded = Boolean.parseBoolean(productModel.getIsExcludedTax());
//            if (isExcluded){
//                repo.addTaxForAProduct(orderId, productModel.getProductId(), productModel.getTaxId());
//            }
//
//
//            //tax calculation
//            List<Tax> taxList =  repo.getAllTaxInfo(orderId, productModel.getProductId());
//            orderedProductModel.setTax(taxList);
//
//            double productPrice = orderedProductModel.getTotalPrice();
//            double mainOldTotalTax = order.getTotalTax();
//
//            double totalPercent = 0;
//            for (int i=0; i< taxList.size(); i++){
//                totalPercent += taxList.get(i).getTaxAmount();
//            }
//            final double totalTax = (productPrice*totalPercent)/100;
//            orderedProductModel.setTotalTax(totalTax);
//            double mainNewTotalTax = mainOldTotalTax + totalTax;
//            order.setTotalTax(mainNewTotalTax);
//
//
//
//
//            //add discount
//            DiscountDetails discountDetails = new DiscountDetails(productModel.getDiscountId(), "Product Discount",
//                    productModel.getDiscountType(), productModel.getDiscount());
//            repo.insertCoreDiscount(discountDetails);
//            List<DiscountDetails> discountDetailsList = null;
//            if (Boolean.parseBoolean(productModel.getAvailableDiscount())){
//                repo.insertProductDiscount(orderId, productModel.getProductId(), productModel.getDiscountId());
//                discountDetailsList = repo.getAllDiscountInfo(orderId, productModel.getProductId());
//                orderedProductModel.setDiscountDetails(discountDetailsList);
//            }
//
//
//
//
//            //discount calculation
//            productPrice = orderedProductModel.getTotalPrice();
//            double mainOldTotalDiscount = order.getTotalDiscount() - orderedProductModel.getTotalDiscount();
//            totalPercent = 0;
//            double discountBdt = 0;
//            String discountTypeBDT = "BDT";
//
//            if (discountDetailsList != null){
//                for (int i=0; i< discountDetailsList.size(); i++){
//                    if (discountDetailsList.get(i).getDiscountType().equals(discountTypeBDT) ||
//                            discountDetailsList.get(i).getDiscountType().equals("bdt")){
//                        discountBdt += discountDetailsList.get(i).getDiscount();
//                    }else {
//                        totalPercent += discountDetailsList.get(i).getDiscount();
//                    }
//                }
//            }
//
//            double totalDiscountInPercentage = (productPrice*totalPercent)/100;
//            final double totalDiscount = totalDiscountInPercentage + discountBdt;
//            double mainNewDiscount = mainOldTotalDiscount + totalDiscount;
//
//            orderedProductModel.setTotalDiscount(totalDiscount);
//            order.setTotalDiscount(mainNewDiscount);
//

            //grand total
            double orderGrandTotal = orderedProductModel.getTotalPrice() + orderedProductModel.getTotalTax();
            orderGrandTotal = orderGrandTotal - orderedProductModel.getTotalDiscount();

            order.setGrandTotal(order.getGrandTotal() + orderGrandTotal);
            orderedProductModel.setGrandTotal(orderGrandTotal);



            //product and offer quantity
            orderedProductModel.setQuantity(newAddedQuantity);
            boolean isOfferAvailable = Boolean.parseBoolean(productModel.getAvailableOffer());
            if (isOfferAvailable && productModel.getRequiredQuantity() > 0){
                orderedProductModel.setOfferItemId(productModel.getFreeItemName());
                orderedProductModel.setOfferName(productModel.getFreeItemName());
                int totalOffer = (orderedProductModel.getQuantity()/productModel.getRequiredQuantity())*
                        productModel.getFreeQuantity();
                orderedProductModel.setOfferItemId(productModel.getOfferItemId());
                orderedProductModel.setOfferName(productModel.getFreeItemName());
                orderedProductModel.setOfferQuantity(totalOffer);
            }else {
                orderedProductModel.setOfferQuantity(0);
            }

            orderedProductModel.setProductId(productModel.getProductId());
            orderedProductModel.setProductName(productModel.getProductName());


            boolean isUpdatedSuccessfully = repo.addNewProductInExistingOrder(order, orderedProductModel);
            if (isUpdatedSuccessfully){
                //new code
//                productModelMap.put(productModel.getProductId(), orderedProductModel);
                OrderJsonModel orderJsonModelList = repo.getOrderData(orderId);
                orderJsonModelMutableLiveData.setValue(orderJsonModelList);
                productModelMap.put(orderedProductModel.getProductId(), orderedProductModel);

                if (totalItemLiveData != null && totalItemLiveData.getValue() != null){
                    int newItem = totalItemLiveData.getValue() + newAddedQuantity;
                    totalItemLiveData.setValue(newItem);
                }
            }
        }
    }

    private void veryNewOrder(Context context, ProductModel productModel, int newAddedQuantity){
        //order doesn't exist so create new order
        SharedPreference sharedPreference = SharedPreference.getInstance(mContext);
        OrderJsonModel order = new OrderJsonModel();
        OrderedProductModel orderedProduct = new OrderedProductModel();

        order.setOrderId(String.valueOf(System.currentTimeMillis()));
        orderedProduct.setProductId(productModel.getProductId());
        orderedProduct.setProductName(productModel.getProductName());
        //update orderId with new OrderId in sharedPreference
        sharedPreference.setCurrentOrderId(order.getOrderId());



        //total price calculation
        final double totalPriceForNewQuantity = productModel.getMrp()*newAddedQuantity;
        orderedProduct.setTotalPrice(totalPriceForNewQuantity);
        order.setTotal(totalPriceForNewQuantity);



        //tax calculation
        if (Boolean.parseBoolean(productModel.getIsExcludedTax())){
            final double totalTax = (orderedProduct.getTotalPrice()*productModel.getTax())/100;
            order.setTotalTax(totalTax);
            orderedProduct.setTotalTax(totalTax);
        }

        //discount calculation
        if (Boolean.parseBoolean(productModel.getAvailableDiscount())){
            if (productModel.getDiscountType().equals("BDT") ||
                    productModel.getDiscountType().equals("bdt")){
                double totalDiscount = productModel.getDiscount()*newAddedQuantity;
                order.setTotalDiscount(totalDiscount);
                orderedProduct.setTotalDiscount(totalDiscount);
            }else {
                double totalDiscount = (totalPriceForNewQuantity*productModel.getDiscount())/100;
                order.setTotalDiscount(totalDiscount);
                orderedProduct.setTotalDiscount(totalDiscount);
            }
        }


//        //add tax in tax database table
//        Tax tax = new Tax(productModel.getTaxId(), productModel.getTaxName(), productModel.getTax());
//        repo.insertTax(tax);
//        boolean isExcluded = Boolean.parseBoolean(productModel.getIsExcludedTax());
//        if (isExcluded){
//            repo.addTaxForAProduct(order.getOrderId(), productModel.getProductId(), productModel.getTaxId());
//        }
//
//
//        //tax calculation
//        List<Tax> taxList =  repo.getAllTaxInfo(order.getOrderId(), productModel.getProductId());
//        orderedProduct.setTax(taxList);
//
//        double productPrice = orderedProduct.getTotalPrice();
//
//        double totalPercent = 0;
//        if (taxList != null){
//            for (int i=0; i< taxList.size(); i++){
//                totalPercent += taxList.get(i).getTaxAmount();
//            }
//        }
//        final double totalTax = (productPrice*totalPercent)/100;
//        orderedProduct.setTotalTax(totalTax);
//        order.setTotalTax(totalTax);
//


//
//        //add discount
//        List<DiscountDetails> discountDetailsList = null;
//        DiscountDetails discountDetails = new DiscountDetails(productModel.getDiscountId(), "Product Discount",
//                productModel.getDiscountType(), productModel.getDiscount());
//        repo.insertCoreDiscount(discountDetails);
//        if (Boolean.parseBoolean(productModel.getAvailableDiscount())){
//            repo.insertProductDiscount(order.getOrderId(), productModel.getProductId(), productModel.getDiscountId());
//            discountDetailsList = repo.getAllDiscountInfo(order.getOrderId(), productModel.getProductId());
//            orderedProduct.setDiscountDetails(discountDetailsList);
//        }




//        //discount calculation
//        productPrice = orderedProduct.getTotalPrice();
//        totalPercent = 0;
//        double discountBdt = 0;
//        String discountTypeBDT = "BDT";
//
//        if (discountDetailsList != null){
//            for (int i=0; i< discountDetailsList.size(); i++){
//                if (discountDetailsList.get(i).getDiscountType().equals(discountTypeBDT) ||
//                        discountDetailsList.get(i).getDiscountType().equals("bdt")){
//                    discountBdt += discountDetailsList.get(i).getDiscount();
//                }else {
//                    totalPercent += discountDetailsList.get(i).getDiscount();
//                }
//            }
//        }
//
//        double discountInPercentage = (productPrice*totalPercent)/100;
//        final double totalDiscount = discountInPercentage + discountBdt;
//        orderedProduct.setTotalDiscount(totalDiscount);
//        order.setTotalDiscount(totalDiscount);




        //grand total
        double grandTotal = orderedProduct.getTotalPrice() + orderedProduct.getTotalTax();
        grandTotal = grandTotal - orderedProduct.getTotalDiscount();

        order.setGrandTotal(grandTotal);
        orderedProduct.setGrandTotal(grandTotal);



        //product and offer quantity
        orderedProduct.setQuantity(newAddedQuantity);
        boolean isOfferAvailable = Boolean.parseBoolean(productModel.getAvailableOffer());
        if (isOfferAvailable){
            orderedProduct.setOfferItemId(productModel.getOfferItemId());
            orderedProduct.setOfferName(productModel.getFreeItemName());

            int totalOffer = (orderedProduct.getQuantity()/productModel.getRequiredQuantity())*
                    productModel.getFreeQuantity();
            orderedProduct.setOfferItemId(productModel.getOfferItemId());
            orderedProduct.setOfferName(productModel.getFreeItemName());
            orderedProduct.setOfferQuantity(totalOffer);

        }else {
            orderedProduct.setOfferQuantity(0);
        }


        List<OrderedProductModel> orderedProductModelList = new ArrayList<>();
        orderedProductModelList.add(orderedProduct);
        order.setOrderDetails(orderedProductModelList);

        productModelMap.put(productModel.getProductId(), orderedProduct);
        orderJsonModelMutableLiveData.setValue(order);
        repo.insertNewOrder(order, orderedProduct);

        if (totalItemLiveData != null && totalItemLiveData.getValue() != null){
            int newItem = totalItemLiveData.getValue() + newAddedQuantity;
            totalItemLiveData.setValue(newItem);
        }
    }

    public void setClient(Client client){
        clientMutableLiveData.setValue(client);
    }
    public LiveData<Client> getClientLiveData(){
        return clientMutableLiveData;
    }

    public void addDiscount(DiscountDetails discountDetails){
        repo.insertProductDiscount(discountDetails);
    }

    public void deleteDiscount(String orderId, String productId, String discountId){
        repo.deleteProductDiscount(orderId,productId,discountId);
    }

    public void addTax(Tax tax){
        repo.addTaxForAProduct(tax);
    }

    public void deleteTax(String orderId, String productId, String taxId){
        repo.deleteProductTax(orderId, productId, taxId);
    }

}
