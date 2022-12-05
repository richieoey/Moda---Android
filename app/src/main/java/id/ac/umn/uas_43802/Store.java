package id.ac.umn.uas_43802;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import id.ac.umn.uas_43802.adapter.CartAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Store#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Store extends Fragment {
    RecyclerView rV;
    StoreAdapter storeAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<StoreModel> data = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Store() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Store.
     */
    // TODO: Rename and change types and number of parameters
    public static Store newInstance(String param1, String param2) {
        Store fragment = new Store();
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
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData(){
        db.collection("toko")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            StoreModel store = new StoreModel(document.getData().get("name").toString(), document.getData().get("image").toString());
                            data.add(store);
                        }
                        storeAdapter = new StoreAdapter(data, getContext());
                        rV.setAdapter(storeAdapter);
                    }else {
                        Log.d("error", "Error getting documents: ", task.getException());
                    };
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        rV = view.findViewById(R.id.recycler_view1);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rV.setLayoutManager(layoutManager);
        storeAdapter = new StoreAdapter(data, getContext());
        rV.setAdapter(storeAdapter);

        SearchView svStore = view.findViewById(R.id.search_store);

//        svStore.setOnClickListener(view1 -> {
//            svStore.setIconified(false);
//            Intent search = new Intent(getActivity(), SearchStore.class);
//            startActivity(search);
//        });


        getData();
        // Inflate the layout for this fragment
        return view;
    }

    public void setId(String id) {
    }
}
