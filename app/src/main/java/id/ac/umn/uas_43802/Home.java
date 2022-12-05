package id.ac.umn.uas_43802;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import id.ac.umn.uas_43802.adapter.CartAdapter;
import id.ac.umn.uas_43802.adapter.PageAdapter;
import id.ac.umn.uas_43802.adapter.ProductAdapter;
import id.ac.umn.uas_43802.model.Carousel_Page_Model;
import id.ac.umn.uas_43802.model.Carousel_Page_Product;
import id.ac.umn.uas_43802.model.ProductModel;

public class Home extends Fragment {
    private List<Carousel_Page_Model> listItems;

    private ViewPager page;
    private TabLayout tabLayout;
    private RecyclerView rv;
	private ImageButton btnCart;
    private ArrayList<ProductModel> product = new ArrayList<ProductModel>();
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter productAdapter;
	Button btnSee;
	private ImageButton btnBaju, btnCelana, btnTas, btnSepatu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        page = view.findViewById(R.id.my_pager) ;
        tabLayout = view.findViewById(R.id.my_tablayout);
        rv = view.findViewById(R.id.slider_product);
		btnBaju = view.findViewById(R.id.btn_baju);
        btnCelana = view.findViewById(R.id.btn_celana);
        btnSepatu = view.findViewById(R.id.btn_sepatu);
        btnTas = view.findViewById(R.id.btn_tas);
		btnSee = view.findViewById(R.id.seeAll);
		btnCart = view.findViewById(R.id.cart);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference productRef =  db.collection("product");

        Task<QuerySnapshot> query = productRef.limit(5)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> hasil = (Map<String, Object>) document.getData();
                            HashMap<String, Object> toko = (HashMap<String, Object>) hasil.get("toko");
                            product.add(new ProductModel(hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() ,  toko,  hasil.get("image").toString() ));
                        }
                        productAdapter = new ProductAdapter(product, getActivity());
                        rv.setAdapter(productAdapter);
                    } else {
                        Log.d("error", "Error getting documents: ", task.getException());
                    }
                });

		btnBaju.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent katBaju = new Intent(getActivity(), listProduk.class);
                katBaju.putExtra("type", "top");
				startActivity(katBaju);
			}
		});

        btnCelana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent katBaju = new Intent(getActivity(), listProduk.class);
                katBaju.putExtra("type", "pants");
                startActivity(katBaju);
            }
        });

        btnSepatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent katBaju = new Intent(getActivity(), listProduk.class);
                katBaju.putExtra("type", "shoes");
                startActivity(katBaju);
            }
        });

        btnTas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent katBaju = new Intent(getActivity(), listProduk.class);
                katBaju.putExtra("type", "bag");
                startActivity(katBaju);
            }
        });


		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent listCart = new Intent(getActivity(), list_cart.class);
				startActivity(listCart);
			}
		});

		btnSee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent seeAll = new Intent(getActivity(), listProduk.class);
				startActivity(seeAll);
			}
		});

        listItems = new ArrayList<>() ;
        listItems.add(new Carousel_Page_Model(R.drawable.image_1,"H&M Shirt"));
        listItems.add(new Carousel_Page_Model(R.drawable.image_2,"Pull & Bear"));

        PageAdapter itemsPager_adapter = new PageAdapter(getActivity(), listItems);
        page.setAdapter(itemsPager_adapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(product, getActivity());
        rv.setAdapter(productAdapter);

        //auto-slide
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),2000,5000);
        tabLayout.setupWithViewPager(page,true);

        return view;
    }



    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {
            // here you check the value of getActivity() and break up if needed
            if(getActivity() == null)
                return;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem()< listItems.size()-1) {
                        page.setCurrentItem(page.getCurrentItem()+1);
                    }
                    else
                        page.setCurrentItem(0);
                }
            });
        }
    }

}
