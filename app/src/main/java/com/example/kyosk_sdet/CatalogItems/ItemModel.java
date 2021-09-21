package com.example.kyosk_sdet.CatalogItems;

public class ItemModel{

        private String prod_id, prod_name, img_ulr,price,stock ;

        public ItemModel( String prod_id, String prod_name, String img_url, String price,String stock){

            this.prod_id = prod_id;
            this.prod_name = prod_name;
            this.img_ulr = img_url;
            this.price=price;
            this.stock=stock;

        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProd_id(){ return prod_id;}
        public void setProd_id(String id){ this.prod_id = id;}

        public String getProd_name(){ return prod_name;}
        public void setProd_name(String name){ this.prod_name = name;}


        public String getImg_ulr(){ return img_ulr;}
        public void setImg_ulr(String img_ulr){ this.img_ulr = img_ulr;}


}
