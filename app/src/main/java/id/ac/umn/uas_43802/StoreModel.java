package id.ac.umn.uas_43802;

public class StoreModel {

	String name;
	String id;
	String image;

	public StoreModel(String name, String image, String id) {
		this.name = name;
		this.image = image;
		this.id = id;
	}


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

	public void setId(String id) {
		this.id = id;
	}
}
