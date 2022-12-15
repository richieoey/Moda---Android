package id.ac.umn.uas_43802.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchStoreModel  implements Parcelable  {
    private String address, image, ktpName, ktpNumber, name, phoneNumber, province, uid;

    protected SearchStoreModel(Parcel in) {
        address = in.readString();
        image = in.readString();
        ktpName = in.readString();
        ktpNumber = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        province = in.readString();
        uid = in.readString();
    }

    public static final Creator<SearchStoreModel> CREATOR = new Creator<SearchStoreModel>() {
        @Override
        public SearchStoreModel createFromParcel(Parcel in) {
            return new SearchStoreModel(in);
        }

        @Override
        public SearchStoreModel[] newArray(int size) {
            return new SearchStoreModel[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getKtpName() {
        return ktpName;
    }

    public String getKtpNumber() {
        return ktpNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public String getUid() {
        return uid;
    }

    public SearchStoreModel(String address, String image, String ktpName, String ktpNumber, String name, String phoneNumber, String province, String uid) {
        this.address = address;
        this.image = image;
        this.ktpName = ktpName;
        this.ktpNumber = ktpNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(image);
        parcel.writeString(ktpName);
        parcel.writeString(ktpNumber);
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeString(province);
        parcel.writeString(uid);
    }
}
