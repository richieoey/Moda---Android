package id.ac.umn.uas_43802;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import id.ac.umn.uas_43802.databinding.FragmentProfileBinding;
import id.ac.umn.uas_43802.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageView;
    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference usersRef = db.collection("user");

        // Create a query against the collection.
        Task<QuerySnapshot> query = usersRef.whereEqualTo("uid", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String nama = document.getData().get("name").toString();
                                binding.namaUser.setText(nama);
                                binding.username.setText(nama);

                                String email = document.getData().get("email").toString();
                                binding.email.setText(email);
//                                imageView = (ImageView) findViewById(R.id.profileAtas);
//                                Glide.with(this).load(document.getData().get("photoUrl")).into(imageView);

                                String phonenumber = document.getData().get("phone").toString();
                                binding.phonenumber.setText(phonenumber);

                                String gender = document.getData().get("gender").toString();
                                binding.gender.setText(gender);


                                Log.d("nama", nama);
                                Log.d("Test", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("error", "Error getting documents: ", task.getException());
                        }
                    }
                });
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            Log.d("uidUser", " " + user.getUid());
        }

		binding.registerSeller.setOnClickListener(v -> goRegisterSeller());

        binding.logout.setOnClickListener(v-> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));

        });
		return binding.getRoot();
	}


	public void goRegisterSeller() {
		Intent intent = new Intent(getActivity(), register_seller.class);
		startActivity(intent);
	}

    public static BitmapDrawable getBitmapFromURL(Object src) {
        try {
            URL url = new URL((String) src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            BitmapDrawable dr = new BitmapDrawable(Resources.getSystem(), myBitmap);
            return dr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

