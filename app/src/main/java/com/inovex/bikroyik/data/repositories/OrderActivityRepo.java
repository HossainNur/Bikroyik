package com.inovex.bikroyik.data.repositories;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.model.Tax;

import java.util.List;

public class OrderActivityRepo {
    private static OrderActivityRepo orderActivityRepo = null;
    private static Context mContext;
    private  DatabaseSQLite databaseSQLite;

    private MutableLiveData<OrderJsonModel> orderJsonModelMutableLiveData = new MutableLiveData<>();

    public static OrderActivityRepo getInstance(Context context){
        if (orderActivityRepo == null){
            orderActivityRepo = new OrderActivityRepo();
            return orderActivityRepo;
        }

        return orderActivityRepo;
    }

    public void init(Context context){
        this.mContext = context;
        this.databaseSQLite = new DatabaseSQLite(context);
    }

    public OrderJsonModel getOrderData(String orderId){
        OrderJsonModel orderJsonModel = null;
        if (!TextUtils.isEmpty(orderId)){
            orderJsonModel = databaseSQLite.getOrderDataWithAllOrderedProducts(orderId);

        }


        return  orderJsonModel;
    }

    public void updateAProductData(){

    }

    public boolean addNewProductInExistingOrder(OrderJsonModel order, OrderedProductModel orderedProduct){
        //update order_table and create product into  product_table
        boolean success = databaseSQLite.updateCoreOrderData(order);
        boolean success2 = databaseSQLite.insertOrderProduct(order.getOrderId(), orderedProduct);

        return success && success2;
    }

    public boolean insertNewOrder(OrderJsonModel order, OrderedProductModel orderedProduct){
        //create new order with new product(s)
        databaseSQLite.insertOrder(order);
        databaseSQLite.insertOrderProduct(order.getOrderId(), orderedProduct);
        return true;
    }

    public boolean updateExistingCoreOrderData(OrderJsonModel orderJsonModel){
        return databaseSQLite.updateCoreOrderData(orderJsonModel);
    }

    public boolean updateExistingOrder(OrderJsonModel orderJsonModel, OrderedProductModel orderedProductModel){
        boolean updateOrder = updateExistingCoreOrderData(orderJsonModel);
        boolean updateProduct = databaseSQLite.updateOrderedProduct(orderedProductModel, orderJsonModel.getOrderId());

        return updateOrder && updateProduct;
    }

    public List<DiscountDetails> getAllDiscount(String orderId, String productId){
        return databaseSQLite.getAllDiscount(orderId, productId);
    }

    public List<Tax> getAllTaxForSingleProduct(String orderId, String productId){
        return databaseSQLite.getAllTaxInfoForSingleProduct(orderId, productId);
    }

    public List<Tax> getAllTax(){
        return databaseSQLite.getAllTaxInfo();
    }

    public boolean isOrderExist(String orderId){
        return databaseSQLite.isItExistingOrder(orderId);
    }

    public boolean isTaxIdExist(String orderId, String productId, String taxId){
        return databaseSQLite.shouldTaxApply(orderId, productId, taxId);
    }

    public void insertTax(Tax tax){
        databaseSQLite.insertTaxInfo(tax);
    }

    public void addTaxForAProduct(Tax tax){
        databaseSQLite.insertTaxInfo(tax);
    }

    public List<Tax> getAllTaxInfo(String orderId, String productId){
        return databaseSQLite.getAllOnGoingTaxForASingleProduct(orderId, productId);
    }

    public void insertCoreDiscount(DiscountDetails discountDetails){
        databaseSQLite.insertDiscountData(discountDetails);
    }
    public void insertProductDiscount(DiscountDetails discountDetails){
        databaseSQLite.insertDiscountData(discountDetails);
    }

    public List<DiscountDetails> getAllDiscountInfo(String orderId, String productId){
        return databaseSQLite.getAllOrderedProductDiscount(orderId, productId);
    }

    public void deleteProductDiscount(String orderId, String productId, String discountId){
        databaseSQLite.deleteSingleDiscountFrom_OnGoingDiscount(orderId, productId, discountId);
    }
    public void deleteProductTax(String orderId, String productId, String taxId){
        databaseSQLite.deleteAOnGoingOrderTax(orderId, productId, taxId);
    }



    public ProductModel getSingleProduct(String productId){

        return databaseSQLite.getSpecificProductData(productId);
    }

    public ProductModel getSingleProductByBarcode(String barcode){
        return databaseSQLite.getSpecificProductDataByBarcode(barcode);
    }

    public OrderedProductModel getOrderProductModel(String orderId, String productId){
        return databaseSQLite.getOrderProduct(orderId, productId);
    }

    public void updateTotalProduct(String productId, int newTotalProduct){
        ProductModel productModel = getSingleProduct(productId);

        if (productModel != null){
            int currentTotal;
            if (productModel.getOnHand() > newTotalProduct){
                currentTotal = productModel.getOnHand() - newTotalProduct;
                databaseSQLite.updateProductQuantity(productId, currentTotal);
            }else if(productModel.getOnHand() < newTotalProduct) {
                currentTotal = newTotalProduct - productModel.getOnHand();
                databaseSQLite.updateProductQuantity(productId, currentTotal);
            }

        }
    }
    public void updateOrderCompletedStatus(OrderJsonModel orderJsonModel, boolean isCompleted){
        databaseSQLite.updateOrderCompleted(orderJsonModel, isCompleted);
    }

    public List<Integer> getSingleProductDiscount(String orderId, String productId){
        return databaseSQLite.getAllDiscountIdForAProduct(orderId, productId);
    }

    public boolean updateDiscountAvailability(DiscountDetails discountDetails){

        return databaseSQLite.updateDiscountAvailability(discountDetails);
    }

  /*  public boolean updateProductAvailability(OrderedProductModel orderedProductModel){

        return databaseSQLite.updateDiscountAvailability(OrderedProductModel);
    }*/


    public boolean updateTaxAvailability(Tax tax){
        return databaseSQLite.updateTaxAvailability(tax);
    }



}
