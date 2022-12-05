package id.ac.umn.uas_43802.model;

public class ChatDetailModel {
    private String senderId,message, time;

    public ChatDetailModel(String senderId, String message, String time) {
        this.message = message;
        this.senderId = senderId;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getTime() {
        return time;
    }
}
