package com.example.kyosk_sdet.WebResponse;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class categoryRes {

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

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("stock")
            @Expose
            private Integer stock;
            @SerializedName("price")
            @Expose
            private Integer price;
            @SerializedName("img_url")
            @Expose
            private String imgUrl;
            @SerializedName("id")
            @Expose
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Integer getStock() {
                return stock;
            }

            public void setStock(Integer stock) {
                this.stock = stock;
            }

            public Integer getPrice() {
                return price;
            }

            public void setPrice(Integer price) {
                this.price = price;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

        }
    }

