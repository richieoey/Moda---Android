package id.ac.umn.uas_43802.model;

import java.util.HashMap;
import java.util.Map;

public class ProductModel {
    private String name, description, price, category, photoUrl;
    private HashMap<String, Object> toko;

    public ProductModel(String name, String description, String price, String category, HashMap<String, Object> toko, String photoUrl) {
        this.name = name;
        this.description = description;
        this.price = "Rp. " + price;
        this.category = category;
        this.toko = toko;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

    public HashMap<String, Object> getToko() {
        return toko;
    }
}
