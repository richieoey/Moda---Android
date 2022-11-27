package id.ac.umn.uas_43802;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import id.ac.umn.uas_43802.adapter.PageAdapter;
import id.ac.umn.uas_43802.adapter.ProductAdapter;
import id.ac.umn.uas_43802.databinding.ActivityMainBinding;
import id.ac.umn.uas_43802.model.Carousel_Page_Model;
import id.ac.umn.uas_43802.model.Carousel_Page_Product;

public class HomeUser extends AppCompatActivity {

	BottomNavigationView nav;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_user);
		nav = findViewById(R.id.bottomNavigation);
		replaceFragment(new Home());

		nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()){
					case R.id.home:
						replaceFragment(new Home());
						break;
					case R.id.store:
						replaceFragment(new Store());
						break;
					case R.id.chat:
						replaceFragment(new Chat());
						break;
					case R.id.profile:
						replaceFragment(new Profile());
						break;
				}
				return true;
			}
		});
	}

	private void replaceFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frame_layout, fragment);
		fragmentTransaction.commit();
	}



}






