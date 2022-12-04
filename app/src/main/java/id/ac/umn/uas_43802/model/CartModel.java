package id.ac.umn.uas_43802.model;

import java.util.Map;

public class CartModel {
//    private String quantity;
//    private Map<String, String> product;
//
//    public CartModel(String quantity, Map<String, String> product) {
//        this.quantity = quantity;
//        this.product = product;
//    }
//
//    public String getQuantity() {
//        return quantity;
//    }
//
//    public Map<String, String> getProduct() {
//        return product;
//    }

    private int featured_image, quantity;
    private String  name_store, nama_produk, harga ;

    public CartModel(int featured_image, int quantity, String name_store, String nama_produk, String harga) {
        this.featured_image = featured_image;
        this.quantity = quantity;
        this.name_store = name_store;
        this.nama_produk = nama_produk;
        this.harga = harga;
    }

    public int getFeatured_image() {
        return featured_image;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName_store() {
        return name_store;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getHarga() {
        return harga;
    }
}
