package id.ac.umn.uas_43802;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreModel implements Parcelable {

	String name;

	String image;

	public StoreModel(String name, String image) {
		this.name = name;
		this.image = image;
	}


	protected StoreModel(Parcel in) {
		name = in.readString();
		image = in.readString();
	}

	public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
		@Override
		public StoreModel createFromParcel(Parcel in) {
			return new StoreModel(in);
		}

		@Override
		public StoreModel[] newArray(int size) {
			return new StoreModel[size];
		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(name);
		parcel.writeString(image);
	}
}
