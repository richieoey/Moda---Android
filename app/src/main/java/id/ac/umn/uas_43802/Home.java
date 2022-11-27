package id.ac.umn.uas_43802;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import id.ac.umn.uas_43802.adapter.PageAdapter;
import id.ac.umn.uas_43802.adapter.ProductAdapter;
import id.ac.umn.uas_43802.model.Carousel_Page_Model;
import id.ac.umn.uas_43802.model.Carousel_Page_Product;

public class Home extends Fragment {
    private List<Carousel_Page_Model> listItems;

    private ViewPager page;
    private TabLayout tabLayout;
    private RecyclerView rv;
	private ImageButton btnCart;
    private ArrayList<Carousel_Page_Product> data;
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
		btnSee = view.findViewById(R.id.seeAll);
		btnCart = view.findViewById(R.id.cart);

		btnBaju.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent katBaju = new Intent(getActivity(), listProduk.class);
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


        data = new ArrayList<>();
        data.add(new Carousel_Page_Product(R.drawable.image_1, "H&M Shirt", "Rina Store", "Rp. 100.000"));
        data.add(new Carousel_Page_Product(R.drawable.image_2, "Pull&Bear Shirt", "Bunda Store", "Rp. 150.000"));
        data.add(new Carousel_Page_Product(R.drawable.image_1, "H&M Shirt", "Rina Store", "Rp. 100.000"));
        data.add(new Carousel_Page_Product(R.drawable.image_2, "Pull&Bear Shirt", "Bunda Store", "Rp. 150.000"));

        listItems = new ArrayList<>() ;
        listItems.add(new Carousel_Page_Model(R.drawable.image_1,"H&M Shirt"));
        listItems.add(new Carousel_Page_Model(R.drawable.image_2,"Pull & Bear"));

        PageAdapter itemsPager_adapter = new PageAdapter(getActivity(), listItems);
        page.setAdapter(itemsPager_adapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        productAdapter = new ProductAdapter(data, getActivity());
        rv.setLayoutManager(linearLayoutManager);
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
