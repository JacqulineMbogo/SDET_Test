package com.example.kyosk_sdet.WebServices;

import com.example.kyosk_sdet.WebResponse.AddtoCart;
import com.example.kyosk_sdet.WebResponse.DeliveryHistoryAPI;
import com.example.kyosk_sdet.WebResponse.EditCartItem;
import com.example.kyosk_sdet.WebResponse.GetOrderProductDetails;
import com.example.kyosk_sdet.WebResponse.NewProductRes;
import com.example.kyosk_sdet.WebResponse.OrderHistoryAPI;
import com.example.kyosk_sdet.WebResponse.OrderSummary;
import com.example.kyosk_sdet.WebResponse.PlaceOrder;
import com.example.kyosk_sdet.WebResponse.ProductDetail_Res;
import com.example.kyosk_sdet.WebResponse.SearchProductRes;
import com.example.kyosk_sdet.WebResponse.UserSignInRes;
import com.example.kyosk_sdet.WebResponse.clearbalanceAPI;
import com.example.kyosk_sdet.WebResponse.getCartDetails;
import com.example.kyosk_sdet.WebResponse.payAPI;
import com.example.kyosk_sdet.WebResponse.receiveAPI;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {

    // method,, return type ,, secondary url


    ///  user signin request
    @Multipart
    @POST("user_signin.php")
    Call<UserSignInRes> UserSigninCall(
            @Part("securecode") RequestBody securecode

    );

    // get products
    @Multipart
    @POST("getnewproduct.php")
    Call<NewProductRes> getNewPorduct(
            @Part("securecode") RequestBody securecode
    );


    // add to cart
    @Multipart
    @POST("add_prod_into_cart.php")
    Call<AddtoCart> addtocartcall(
            @Part("securecode") RequestBody securecode,
            @Part("prod_id") RequestBody prod_id,
            @Part("user_id") RequestBody user_id

    );

    // get product details
    @Multipart
    @POST("getproductdetails.php")
    Call<ProductDetail_Res> getProductDetials(
            @Part("securecode") RequestBody securecode,
            @Part("prod_id") RequestBody prod_id
    );

    // get user cart
    @Multipart
    @POST("getusercartdetails.php")
    Call<getCartDetails> getusercartcall(
            @Part("securecode") RequestBody securecode,
            @Part("qoute_id") RequestBody qoute_id,
            @Part("user_id") RequestBody user_id
    );
    // delete cart item
    @Multipart
    @POST("deletecartitem.php")
    Call<AddtoCart> deleteCartProd(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("prod_id") RequestBody prod_id
    );

    // edit cart qty
    @Multipart
    @POST("editcartitem.php")
    Call<EditCartItem> editCartQty(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("prod_id") RequestBody prod_id,
            @Part("prod_qty") RequestBody prod_qty
    );


 // get order summery
 @Multipart
 @POST("getordersummary.php")
 Call<OrderSummary> getOrderSummarycall(
         @Part("securecode") RequestBody securecode,
         @Part("user_id") RequestBody user_id,
         @Part("qoute_id") RequestBody qoute_id
 );

    // place order api
    @Multipart
    @POST("placeorderapi.php")
    Call<PlaceOrder> PlaceOrderCall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("address_id") RequestBody address_id,
            @Part("total_price") RequestBody total_price,
            @Part("qoute_id") RequestBody qoute_id,
            @Part("deliverymode") RequestBody deliverymode
    );



    // get order history
    @Multipart
    @POST("getorderhistory.php")
    Call<OrderHistoryAPI> getorderHistorycall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id
    );


    // get order prodct details history
    @Multipart
    @POST("getorderhistoryproddetails.php")
    Call<GetOrderProductDetails> getorderProductdetailscall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("order_id") RequestBody order_id
    );

    // make payment
    @Multipart
    @POST("makepayment.php")
    Call<payAPI> makepaymentcall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("order_id") RequestBody order_id,
            @Part("total_price") RequestBody total_price,
             @Part("payment_amount") RequestBody payment_amount

    );
    // CLEAR BALANCE
    @Multipart
    @POST("clearbalance.php")
    Call<clearbalanceAPI> clearbalancecall(
            @Part("securecode") RequestBody securecode,
            @Part("order_id") RequestBody order_id,
            @Part("total_price") RequestBody total_price,
            @Part("code") RequestBody code,
            @Part("mode") RequestBody mode,
            @Part("amount") RequestBody amount

    );

    // RECEIVE
    @Multipart
    @POST("receive.php")
    Call<receiveAPI> receivecall(
            @Part("securecode") RequestBody securecode,
            @Part("order_id") RequestBody order_id,
            @Part("status") RequestBody status

    );

    // get delivery summery
    @Multipart
    @POST("getdelivery.php")
    Call<DeliveryHistoryAPI> deliveryhistorycall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id

    );


}




