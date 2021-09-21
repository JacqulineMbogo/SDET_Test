package com.example.kyosk_sdet.WebServices;

import androidx.viewbinding.BuildConfig;

import com.example.kyosk_sdet.Utility.Constant;
import com.example.kyosk_sdet.WebResponse.AddtoCart;
import com.example.kyosk_sdet.WebResponse.DeliveryHistoryAPI;
import com.example.kyosk_sdet.WebResponse.EditCartItem;
import com.example.kyosk_sdet.WebResponse.GetOrderProductDetails;
import com.example.kyosk_sdet.WebResponse.NewProductRes;
import com.example.kyosk_sdet.WebResponse.NewUserRegistration;
import com.example.kyosk_sdet.WebResponse.OrderHistoryAPI;
import com.example.kyosk_sdet.WebResponse.OrderSummary;
import com.example.kyosk_sdet.WebResponse.PlaceOrder;
import com.example.kyosk_sdet.WebResponse.ProductDetail_Res;
import com.example.kyosk_sdet.WebResponse.SearchProductRes;
import com.example.kyosk_sdet.WebResponse.StaffSignInRes;
import com.example.kyosk_sdet.WebResponse.UserSignInRes;
import com.example.kyosk_sdet.WebResponse.clearbalanceAPI;
import com.example.kyosk_sdet.WebResponse.getCartDetails;
import com.example.kyosk_sdet.WebResponse.payAPI;
import com.example.kyosk_sdet.WebResponse.receiveAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ServiceWrapper  {

    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader) {
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG)
//            builder.addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }


        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }


    ///  user signin
    public Call<UserSignInRes> UserSigninCall( String securecode){
        return mServiceInterface.UserSigninCall( convertPlainString(securecode));
    }


    ///  new product details
    public Call<NewProductRes> getNewProductRes(String securcode){
        return mServiceInterface.getNewPorduct(convertPlainString(securcode));
    }

    // add to cart
    public Call<AddtoCart> addtoCartCall(String securcode, String prod_id, String user_id){
        return mServiceInterface.addtocartcall(convertPlainString(securcode), convertPlainString(prod_id),convertPlainString(user_id) );
    }

    // get product detials
       public Call<ProductDetail_Res> getProductDetails(String securcode, String prod_id){
        return mServiceInterface.getProductDetials(convertPlainString(securcode), convertPlainString(prod_id));
    }




    // get user cart
    // add to cart
    public Call<getCartDetails> getCartDetailsCall(String securcode, String qoute_id, String user_id){
        return mServiceInterface.getusercartcall(convertPlainString(securcode), convertPlainString(qoute_id),convertPlainString(user_id) );
    }

    // delete cart item
    public Call<AddtoCart> deletecartprod(String securcode, String user_id, String prod_id){
        return mServiceInterface.deleteCartProd(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(prod_id) );
    }
    // edit cart item
    public Call<EditCartItem> editcartcartprodqty(String securcode, String user_id, String prod_id, String prod_qty){
        return mServiceInterface.editCartQty(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(prod_id),  convertPlainString(prod_qty) );
    }


    // get order summery
    public Call<OrderSummary> getOrderSummarycall(String securcode, String user_id, String qoute_id){
        return mServiceInterface.getOrderSummarycall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(qoute_id) );
    }


    // get place order api
    public Call<PlaceOrder> placceOrdercall(String securcode, String user_id, String address_id,
                                            String total_price, String qoute_id, String delivermode){
        return mServiceInterface.PlaceOrderCall(convertPlainString(securcode), convertPlainString(user_id),
                convertPlainString(address_id), convertPlainString(total_price), convertPlainString(qoute_id), convertPlainString( delivermode));
    }

    // get order history
    public Call<OrderHistoryAPI> getorderhistorycall(String securcode, String user_id){
        return mServiceInterface.getorderHistorycall(convertPlainString(securcode), convertPlainString(user_id) );
    }
    // get order prodcut detais history
    public Call<GetOrderProductDetails> getorderproductcall(String securcode, String user_id, String order_id){
        return mServiceInterface.getorderProductdetailscall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(order_id) );
    }

    // get order prodcut detais history
    public Call<payAPI> makepaymentcall(String securcode, String user_id, String order_id , String total_price, String payment_amount){
        return mServiceInterface. makepaymentcall(convertPlainString(securcode), convertPlainString(user_id), convertPlainString(order_id) , convertPlainString(total_price),  convertPlainString(payment_amount) );
    }

    public Call<clearbalanceAPI> clearbalancecall(String securcode, String order_id , String total_price, String code, String mode, String amount){
        return mServiceInterface. clearbalancecall(convertPlainString(securcode), convertPlainString(order_id) , convertPlainString(total_price),  convertPlainString(code),  convertPlainString(mode), convertPlainString(amount) );
    }


    // get order history
    public Call<DeliveryHistoryAPI> deliveryhistorycall(String securcode, String user_id){
        return mServiceInterface.deliveryhistorycall(convertPlainString(securcode), convertPlainString(user_id) );
    }

    public Call<receiveAPI> receivecall(String securcode, String order_id , String status){
        return mServiceInterface. receivecall(convertPlainString(securcode), convertPlainString(order_id) , convertPlainString(status) );
    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data){
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return  plainString;
    }

}


