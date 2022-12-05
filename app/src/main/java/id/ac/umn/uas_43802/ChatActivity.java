package id.ac.umn.uas_43802;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.ac.umn.uas_43802.adapter.ChatAdapter;
import id.ac.umn.uas_43802.adapter.ChatDetailAdapter;
import id.ac.umn.uas_43802.databinding.ActivityChatBinding;
import id.ac.umn.uas_43802.model.ChatDetailModel;
import id.ac.umn.uas_43802.model.ChatModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class ChatActivity extends AppCompatActivity {
    private String combineId;
    private ActivityChatBinding binding;
    ChatDetailAdapter detailAdapter;
    ArrayList<ChatDetailModel> chats = new ArrayList<ChatDetailModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        detailAdapter = new ChatDetailAdapter(chats, getApplicationContext());
        binding.chatRecyclerView.setAdapter(detailAdapter);

        ChatModel data = getIntent().getParcelableExtra("person");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference chatRef = db.collection("chats");

        if(user.getUid().compareTo(data.getUid()) > 0){
            combineId = user.getUid() + data.getUid();
        }else{
            combineId =  data.getUid() + user.getUid();
        }

        binding.textName.setText(data.getName());

        DocumentReference docRef = chatRef.document(combineId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("test", "Listen failed.", e);
                    return;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";

                if (snapshot != null && snapshot.exists()) {
                    Map<String, Object> result =  snapshot.getData();
                    ArrayList<HashMap<String, Object>> messages = (ArrayList<HashMap<String, Object>>) result.get("messages");
                    chats.clear();
                    for(HashMap<String, Object> message: messages){
                        Timestamp tanggal = (Timestamp) message.get("date");
                        long total_miliseconds= (long) ((tanggal.getSeconds()+(tanggal.getNanoseconds())*0.00000001)*1000);
                        Date hai = new Date(total_miliseconds);
                        String date = DateFormat.format("hh:mm", hai).toString();
                        chats.add(new ChatDetailModel(message.get("senderId").toString(),message.get("text").toString(), date));
                    }
                    Log.d("test", source + " data: " + messages);
                    binding.progressBar.setVisibility(View.GONE);
                    detailAdapter = new ChatDetailAdapter(chats, getApplicationContext());
                    binding.chatRecyclerView.setAdapter(detailAdapter);
                } else {
                    Log.d("test", source + " data: null");
                }
            }
        });

        binding.imageBack.setOnClickListener(view -> {
                ChatActivity.super.onBackPressed();
        });

        binding.btnSend.setOnClickListener(view -> {
            HashMap<String, Object> sendData = new HashMap<String, Object>();
            UUID uniqueKey = UUID.randomUUID();
            sendData.put("id", uniqueKey.getMostSignificantBits());
            sendData.put("text", binding.inputMessage.getText().toString());
            sendData.put("senderId", user.getUid());
            sendData.put("date", Timestamp.now());
            db.collection("chats").document(combineId).update("messages", FieldValue.arrayUnion(sendData));

            sendData.clear();

            sendData.put(combineId + ".lastMessage.text", binding.inputMessage.getText().toString());
            sendData.put(combineId + ".date", FieldValue.serverTimestamp());
            db.collection("userChats").document(user.getUid()).update(sendData);

            sendData.clear();
            sendData.put(combineId + ".lastMessage.text", binding.inputMessage.getText().toString());
            sendData.put(combineId + ".date", FieldValue.serverTimestamp());
            db.collection("userChats").document(data.getUid()).update(sendData);

            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.inputMessage.getWindowToken(), 0);
            binding.inputMessage.setText("");
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}