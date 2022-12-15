package id.ac.umn.uas_43802;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.adapter.ChatAdapter;
import id.ac.umn.uas_43802.model.ChatModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class Chat extends Fragment {

    RecyclerView rV;
    ChatAdapter chatAdapter;
    ArrayList<ChatModel> person = new ArrayList<ChatModel>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chat.
     */
    // TODO: Rename and change types and number of parameters
    public static Chat newInstance(String param1, String param2) {
        Chat fragment = new Chat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // FragmentChatBinding binding = FragmentChatBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        rV = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rV.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(person, getContext());
        rV.setAdapter(chatAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference chatRef = db.collection("userChats");

//        Task<DocumentSnapshot> query = chatRef.document(user.getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot data = task.getResult();
//
//                        Log.d("chat", result.toString());
//                    }
//                });

        DocumentReference docRef = chatRef.document(user.getUid());
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
                    person.clear();
                    for (Map.Entry<String, Object> entry : result.entrySet()) {
                        Map<String, Object> hasil = (Map<String, Object>) entry.getValue();
                        HashMap<String, Object> user = (HashMap<String, Object>) hasil.get("userInfo");
                        HashMap<String, Object> last = (HashMap<String, Object>) hasil.get("lastMessage");
                        person.add(new ChatModel(user.get("uid").toString(), user.get("photoURL").toString(), user.get("displayName").toString(), last.get("text").toString()));
                    }
                    chatAdapter = new ChatAdapter(person, getContext());
                    rV.setAdapter(chatAdapter);
                } else {
                    Log.d("test", source + " data: null");
                }
            }
        });



//        data = new ArrayList<>();
//        for (int i = 0; i <  ChatData.nama_toko.length; i++){
//            data.add(new ChatModel(
//                    ChatData.gambar_toko[i],
//                    ChatData.nama_toko[i],
//                    ChatData.latest_chat[i]
//            ));
//
//        }
        // Inflate the layout for this fragment
        return view;
    }


    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}