package id.ac.umn.uas_43802.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatModel implements Parcelable {
    private String  photoUrl, name, lastMessage, uid;

    public ChatModel(String uid,String photoUrl, String name, String lastMessage) {
        this.photoUrl = photoUrl;
        this.uid = uid;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    protected ChatModel(Parcel in) {
        photoUrl = in.readString();
        name = in.readString();
        lastMessage = in.readString();
        uid = in.readString();
    }

    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel in) {
            return new ChatModel(in);
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photoUrl);
        parcel.writeString(name);
        parcel.writeString(lastMessage);
        parcel.writeString(uid);
    }
}
