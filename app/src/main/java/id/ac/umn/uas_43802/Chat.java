package id.ac.umn.uas_43802;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.umn.uas_43802.adapter.ChatAdapter;
import id.ac.umn.uas_43802.model.ChatModel;

public class Chat extends Fragment {

    RecyclerView rV;
    ChatAdapter chatAdapter;
    ArrayList<ChatModel> data;

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

        data = new ArrayList<>();
        for (int i = 0; i <  ChatData.nama_toko.length; i++){
            data.add(new ChatModel(
                    ChatData.gambar_toko[i],
                    ChatData.nama_toko[i],
                    ChatData.latest_chat[i]
            ));

            chatAdapter = new ChatAdapter(data, getContext());
            rV.setAdapter(chatAdapter);
        }
        // Inflate the layout for this fragment
        return view;
    }


    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}