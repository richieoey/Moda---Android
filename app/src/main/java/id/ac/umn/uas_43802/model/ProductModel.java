package id.ac.umn.uas_43802.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class ProductModel implements Parcelable {
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

    protected ProductModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readString();
        category = in.readString();
        photoUrl = in.readString();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(price);
        parcel.writeString(category);
        parcel.writeString(photoUrl);
    }
}
