package com.inovex.bikroyik.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.data.repositories.OrderActivityRepo;

import java.util.List;

public class SingleProductActivityViewModel extends ViewModel {

    private static SingleProductActivityViewModel viewModel = null;
    private Context mContext;
    private OrderActivityRepo repo;
    private SharedPreference sharedPreference;
    public MutableLiveData<OrderJsonModel> orderJsonModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> totalItemLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Tax>> taxListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<DiscountDetails>> discountListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Integer>> allAvailableDiscountLiveDataList = new MutableLiveData<>();


    public static SingleProductActivityViewModel getInstance(){
        if (viewModel == null){
            viewModel = new SingleProductActivityViewModel();
            return viewModel;
        }
        return viewModel;
    }

    public void init(Context context){
        mContext = context;
        repo = OrderActivityRepo.getInstance(context);
        repo.init(context);

        if (orderJsonModelMutableLiveData != null){
            sharedPreference = SharedPreference.getInstance(context);
            String orderId = sharedPreference.getCurrentOrderId();
            OrderJsonModel orderJsonModel = null;
            if (orderId != null){
                orderJsonModel = repo.getOrderData(orderId);
                if (orderJsonModel != null){
                    orderJsonModelMutableLiveData.setValue(orderJsonModel);
                }
            }

        }else {
            sharedPreference = SharedPreference.getInstance(context);
            String orderId = sharedPreference.getCurrentOrderId();
            OrderJsonModel orderJsonModel = null;
            if (orderId != null){
                orderJsonModel = repo.getOrderData(orderId);
            }

            if (orderJsonModel != null){
                orderJsonModelMutableLiveData.setValue(orderJsonModel);
            }
        }
    }

    public OrderedProductModel getOrderProductModel(String orderId, String productId){
        return repo.getOrderProductModel(orderId, productId);
    }




    public void updateProductAndOrder(String productId, OrderedProductModel orderedProduct, int totalOrderQuantity){
        OrderJsonModel order = orderJsonModelMutableLiveData.getValue();
        ProductModel productModel = repo.getSingleProduct(productId);


        if (order != null && orderedProduct != null && productModel != null){

            //total price calculation

            orderedProduct.setQuantity(totalOrderQuantity);
            //orderedProduct.getProductName();

            int totalOrder = orderedProduct.getQuantity();
            double totalForProduct = productModel.getMrp()*totalOrder;
            double totalOrderPrice = order.getTotal() - orderedProduct.getTotalPrice();
            totalOrderPrice = totalOrderPrice + totalForProduct;

            orderedProduct.setTotalPrice(totalForProduct);
            order.setTotal(totalOrderPrice);

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

            boolean isUpdatedSuccessfully = repo.updateExistingOrder(order, orderedProduct);

            if (isUpdatedSuccessfully){
                OrderJsonModel orderJsonModel = repo.getOrderData(order.getOrderId());
                orderJsonModelMutableLiveData.setValue(orderJsonModel);

                if (totalItemLiveData != null && totalItemLiveData.getValue() != null){
                    totalItemLiveData.setValue(orderedProduct.getQuantity());
                }
            }
        }
    }





    public void updateProductNameData(String productId, OrderedProductModel orderedProduct,int totalOrderQuantity){
        OrderJsonModel order = orderJsonModelMutableLiveData.getValue();
        ProductModel productModel = repo.getSingleProduct(productId);


        if (order != null && orderedProduct != null && productModel != null){

            //total price calculation
            orderedProduct.setQuantity(0);
            //orderedProduct.getProductName();

            int totalOrder = orderedProduct.getQuantity();
            double totalForProduct = productModel.getMrp()*totalOrder;
            double totalOrderPrice = order.getTotal() - orderedProduct.getTotalPrice();
            totalOrderPrice = totalOrderPrice + totalForProduct;

            orderedProduct.setTotalPrice(totalForProduct);
            order.setTotal(totalOrderPrice);

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

            boolean isUpdatedSuccessfully = repo.updateExistingOrder(order, orderedProduct);

            if (isUpdatedSuccessfully){
                OrderJsonModel orderJsonModel = repo.getOrderData(order.getOrderId());
                orderJsonModelMutableLiveData.setValue(orderJsonModel);

                if (totalItemLiveData != null && totalItemLiveData.getValue() != null){
                    totalItemLiveData.setValue(orderedProduct.getQuantity());
                }
            }
        }
    }






//    public void updateProductAndOrder(String productId , OrderedProductModel orderedProduct, int totalProduct){
//
//        OrderJsonModel order = orderJsonModelMutableLiveData.getValue();
//        ProductModel productModel = repo.getSingleProduct(productId);
//
//        if (order != null){
//            //total price calculation
//            double totalForProduct = productModel.getPrice()*totalProduct;
//            double oldTotalForOrder = order.getTotal() - orderedProduct.getTotalPrice() ;
//            double totalForCoreOrder = oldTotalForOrder +totalForProduct;
//
//            orderedProduct.setTotalPrice(totalForProduct);
//            order.setTotal(totalForCoreOrder);
//
//
//            //tax calculation
//            List<Tax> taxList = repo.getAllTaxInfo(order.getOrderId(), productModel.getProductId());
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
//            //discount calculation
//            List<DiscountDetails> discountDetailsList = repo.getAllDiscountInfo(order.getOrderId(), productModel.getProductId());
//            if (discountDetailsList != null){
//                double totalDiscount = 0;
//                productPrice = orderedProduct.getTotalPrice();
//                double mainOldTotalDiscount = order.getTotalDiscount();
//                totalPercent = 0;
//                double discountBdt = 0;
//                String discountTypeBDT = "BDT";
//
//                for (int i=0; i< discountDetailsList.size(); i++){
//                    if (discountDetailsList.get(i).getDiscountType().equals(discountTypeBDT) ||
//                            discountDetailsList.get(i).getDiscountType().equals("bdt")){
//                        discountBdt += discountDetailsList.get(i).getDiscount();
//                    }else {
//                        totalPercent += discountDetailsList.get(i).getDiscount();
//                    }
//                }
//                double totalDiscountInPercentage =(productPrice*totalPercent)/100;
//                totalDiscount = totalDiscountInPercentage + discountBdt;
//                double mainNewDiscount = mainOldTotalDiscount + totalDiscount;
//
//                orderedProduct.setTotalDiscount(totalDiscount);
//                order.setTotalDiscount(mainNewDiscount);
//            }
//
//            //update grand total
//            orderedProduct.setGrandTotal((orderedProduct.getTotalPrice() +
//                    orderedProduct.getTotalTax()) - orderedProduct.getTotalDiscount());
//            order.setGrandTotal((order.getTotal()+order.getTotalTax())-order.getTotalDiscount());
//
//
//            //product and offer quantity
//            orderedProduct.setQuantity(totalProduct);
//            boolean isOfferAvailable = Boolean.parseBoolean(productModel.getAvailableOffer());
//            if (isOfferAvailable){
//                int totalOffer = (orderedProduct.getQuantity()/productModel.getRequiredQuantity())*
//                        productModel.getFreeQuantity();
//                orderedProduct.setOfferItemId(productModel.getOfferItemId());
//                orderedProduct.setOfferName(productModel.getFreeItemName());
//                orderedProduct.setOfferQuantity(totalOffer);
//            }else {
//                orderedProduct.setOfferQuantity(0);
//            }
//
//            boolean isUpdatedSuccessfully = repo.updateExistingOrder(order, orderedProduct);
//
//            if (isUpdatedSuccessfully){
//                OrderJsonModel orderJsonModel = repo.getOrderData(order.getOrderId());
//                orderJsonModelMutableLiveData.setValue(orderJsonModel);
//
//                if (totalItemLiveData != null && totalItemLiveData.getValue() != null){
//                    totalItemLiveData.setValue(totalProduct);
//                }
//            }
//
//            updateProductQuantity(productModel.getProductId(), totalProduct);
//        }
//
//
//    }

    private void addDiscount(DiscountDetails discountDetails){
        repo.insertProductDiscount(discountDetails);
    }

    private void deleteDiscount(String orderId, String productId, String discountId){
        repo.deleteProductDiscount(orderId,productId,discountId);
    }

    private void addTax(Tax tax){
        repo.addTaxForAProduct(tax);
    }

    private void deleteTax(String orderId, String productId, String taxId){
        repo.deleteProductTax(orderId, productId, taxId);
    }

    private void updateProductQuantity(String productId, int totalQuantity){
        repo.updateTotalProduct(productId, totalQuantity);
    }

    public void queryAllTaxLiveData(String orderId, String productId){
        List<Tax> taxList = repo.getAllTaxForSingleProduct(orderId, productId);
        taxListMutableLiveData.setValue(taxList);
    }

    public void queryForExistingTaxLiveDataList(String orderId, String productId){
        List<Tax> taxList = repo.getAllTaxInfo(orderId, productId);
        taxListMutableLiveData.setValue(taxList);
    }

    public LiveData<List<Tax>> getLiveTaxList(){
        return taxListMutableLiveData;
    }

    public void queryForAllDiscountLiveData(String orderId, String productId){
        List<DiscountDetails> discountDetailsList = repo.getAllDiscount(orderId, productId);
        discountListMutableLiveData.setValue(discountDetailsList);

    }

    public void queryForExistingDiscountLiveDataList(String orderId, String productId){
        List<DiscountDetails> discountDetailsList = repo.getAllDiscountInfo(orderId, productId);
        discountListMutableLiveData.setValue(discountDetailsList);
    }

    public LiveData<List<DiscountDetails>> getLiveDiscountList(){
        return discountListMutableLiveData;
    }

    public void queryForAllDiscountList(String orderId, String productId){
        allAvailableDiscountLiveDataList.setValue(repo.getSingleProductDiscount(orderId, productId));
    }

    public LiveData<List<Integer>> getAllAvailableDiscount(){
        return allAvailableDiscountLiveDataList;
    }

    public void updateDiscountAvailability(List<DiscountDetails>
            discountIdList){

        if (discountIdList != null){
            for (int i=0; i<discountIdList.size(); i++){
                repo.updateDiscountAvailability(discountIdList.get(i));
            }
        }
    }


   /* public void updateProductAvailability(List<OrderedProductModel> orderedProductModel){

        if (orderedProductModel != null){
            for (int i=0; i<orderedProductModel.size(); i++){
                repo.updateDiscountAvailability(orderedProductModel.get(i));
            }
        }
    }*/

    public void updateTaxAvailability(List<Tax> taxIdList){
        if (taxIdList != null){
            for (int i=0; i<taxIdList.size(); i++){
                repo.updateTaxAvailability(taxIdList.get(i));
            }
        }
    }
}
