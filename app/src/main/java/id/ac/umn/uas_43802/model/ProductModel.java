package id.ac.umn.uas_43802.model;

import java.util.Map;

public class ProductModel {
    private String name, description, price, category, photoUrl;
    private Map<String, String> toko;

    public ProductModel(String name, String description, String price, String category, Map<String, String> toko, String photoUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.toko = toko;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ProductModel(String name, String description, String price, String category, String photoUrl, Map<String,String> toko) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.photoUrl = photoUrl;
        this.toko = toko;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Map<String,String> getToko() {
        return toko;
    }
}
