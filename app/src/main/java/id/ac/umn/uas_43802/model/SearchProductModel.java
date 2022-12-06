package id.ac.umn.uas_43802.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class SearchProductModel implements Parcelable {
    private String uid,name, description, price, category, photoUrl;
    private HashMap<String, Object> toko;

    public SearchProductModel(String uid, String name, String description, String price, String category, String photoUrl, HashMap<String, Object> toko) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.price = "Rp. " + price;
        this.category = category;
        this.photoUrl = photoUrl;
        this.toko = toko;
    }

    protected SearchProductModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readString();
        category = in.readString();
        photoUrl = in.readString();
    }

    public String getUid() {
        return uid;
    }

    public static final Creator<SearchProductModel> CREATOR = new Creator<SearchProductModel>() {
        @Override
        public SearchProductModel createFromParcel(Parcel in) {
            return new SearchProductModel(in);
        }

        @Override
        public SearchProductModel[] newArray(int size) {
            return new SearchProductModel[size];
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
