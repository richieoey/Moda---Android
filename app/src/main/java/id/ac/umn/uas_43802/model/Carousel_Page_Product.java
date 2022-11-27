package id.ac.umn.uas_43802.model;

public class Carousel_Page_Product {
    private int featured_image;
    private String name_product, name_store, price;

    public Carousel_Page_Product(int hero, String name, String store, String price) {
        this.featured_image = hero;
        this.name_store = store;
        this.name_product = name;
        this.price = price;
    }

    public int getFeatured_image() {
        return featured_image;
    }

    public String getName_product() {
        return name_product;
    }

    public String getName_store() {
        return name_store;
    }

    public String getPrice() {
        return price;
    }
}
