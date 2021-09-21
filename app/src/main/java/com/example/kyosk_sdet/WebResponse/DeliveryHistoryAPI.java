package com.example.kyosk_sdet.WebResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryHistoryAPI{
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("Information")
        @Expose
        private List<Information> information = null;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<Information> getInformation() {
            return information;
        }

        public void setInformation(List<Information> information) {
            this.information = information;
        }

public class Information {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("shippingaddress")
    @Expose
    private String shippingaddress;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("delivery_status")
    @Expose
    private String delivery_status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShippingaddress() {
        return shippingaddress;
    }

    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getDelivery_status() {
        return delivery_status;
    }



}
}
