package id.ac.umn.uas_43802.model;

public class ChatModel {
    private int featured_image;
    private String  name_store, latest_chat;

    public ChatModel(int hero, String store, String latest_chat) {
        this.featured_image = hero;
        this.name_store = store;
        this.latest_chat = latest_chat;
    }


    public int getFeatured_image() {
        return featured_image;
    }

    public String getName_store() {
        return name_store;
    }

    public String getName_latest_chat() {
        return latest_chat;
    }

}
