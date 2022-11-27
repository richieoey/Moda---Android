package id.ac.umn.uas_43802;

import android.os.Parcel;
import android.os.Parcelable;

public class ProdukModel implements Parcelable {

	String name;
	String namaToko;
	String harga;
	int image;

	public ProdukModel(String name, String namaToko, String harga, int image) {
		this.name = name;
		this.namaToko = namaToko;
		this.harga = harga;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamaToko() {
		return namaToko;
	}

	public void setNamaToko(String namaToko) {
		this.namaToko = namaToko;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int i) {
		dest.writeString(this.name);
		dest.writeString(this.namaToko);
		dest.writeString(this.harga);
		dest.writeValue(this.image);
	}

	public void readFromParcel(Parcel source) {
		this.name = source.readString();
		this.namaToko = source.readString();
		this.harga = source.readString();
		this.image = (Integer) source.readValue(Integer.class.getClassLoader());
	}

	protected ProdukModel(Parcel in) {
		this.name = in.readString();
		this.namaToko = in.readString();
		this.harga = in.readString();
		this.image = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Creator<ProdukModel> CREATOR = new Creator<ProdukModel>() {
		@Override
		public ProdukModel createFromParcel(Parcel source) {
			return new ProdukModel(source);
		}

		@Override
		public ProdukModel[] newArray(int size) {
			return new ProdukModel[size];
		}
	};
}
