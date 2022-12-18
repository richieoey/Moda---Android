package id.ac.umn.uas_43802.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class CartModel implements Parcelable {
    private ProductModel product;
    private int quantity;

    public CartModel(ProductModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    protected CartModel(Parcel in) {
        product = in.readParcelable(ProductModel.class.getClassLoader());
        quantity = in.readInt();
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public ProductModel getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeInt(quantity);
    }
}
