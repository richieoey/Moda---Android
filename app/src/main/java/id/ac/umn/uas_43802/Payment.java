package id.ac.umn.uas_43802;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.model.CartModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class Payment extends AppCompatActivity {

    CheckBox gopay, cash;
    Button payBtn;
    String checked;
    int totalPrice;
    TextView price;
    ArrayList<CartModel> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);

        gopay = findViewById(R.id.checkboxgopay);
        cash = findViewById(R.id.checkboxcash);
        payBtn = findViewById(R.id.payButton);
        price = findViewById(R.id.totaltxt);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String uid = getIntent().getStringExtra("uid");

        db.collection("cart").document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> result =  documentSnapshot.getData();
                        if(!result.entrySet().isEmpty()){
                            for (Map.Entry<String, Object> entry : result.entrySet()) {
                                Map<String, Object> hasil = (Map<String, Object>) entry.getValue();
                                HashMap<String, Object> produk = (HashMap<String, Object>) hasil.get("product");
                                HashMap<String, Object> toko = (HashMap<String, Object>) produk.get("toko");

                                cart.add(new CartModel(new ProductModel(produk.get("uid").toString(),produk.get("name").toString(), produk.get("description").toString(), produk.get("price").toString(),produk.get("category").toString() , produk.get("image").toString()  ,toko), (int) (long) hasil.get("quantity")));
                            }
                            for(int i = 0; i < cart.size(); i++){
                                totalPrice += Integer.parseInt(cart.get(i).getProduct().getPrice()) * cart.get(i).getQuantity();
                            }

                            price.setText(String.valueOf(totalPrice));
                            payBtn.setOnClickListener(view -> {
                                ArrayList<HashMap<String, Object>> params = new ArrayList<>();

                                for(int i = 0; i < cart.size(); i++){
                                    HashMap<String, Object> product = new HashMap<String, Object>();
                                    HashMap<String, Object> sendData = new HashMap<String, Object>();
                                    HashMap<String, Object> finalData = new HashMap<String, Object>();
                                    product.put("uid", cart.get(i).getProduct().getUid());
                                    product.put("name", cart.get(i).getProduct().getName());
                                    product.put("description", cart.get(i).getProduct().getDescription());
                                    product.put("category", cart.get(i).getProduct().getCategory());
                                    product.put("price", cart.get(i).getProduct().getPrice());
                                    product.put("image", cart.get(i).getProduct().getPhotoUrl());
                                    product.put("toko", cart.get(i).getProduct().getToko());
                                    sendData.put("product", product);
                                    sendData.put("quantity", cart.get(i).getQuantity());
                                    finalData.put(user.getUid() + cart.get(i).getProduct().getUid(), sendData);
                                    params.add(finalData);
                                }

                                for(int i = 0; i < cart.size(); i++){
                                    int finalI = i;
                                    db.collection("history").document(user.getUid()).update("products",FieldValue.arrayUnion(params.get(i)))
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    if(finalI == cart.size() - 1){
                                                        Toast toast=Toast.makeText(getApplicationContext(),"Pembayaran Berhasil",Toast.LENGTH_SHORT);
                                                        toast.setMargin(50,50);
                                                        toast.show();
                                                        new java.util.Timer().schedule(
                                                                new java.util.TimerTask() {
                                                                    @Override
                                                                    public void run() {
                                                                        Intent intent = new Intent(Payment.this, HomeUser.class);
                                                                        startActivity(intent);
                                                                    }
                                                                },
                                                                toast.getDuration()
                                                        );
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });



        gopay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cash.setChecked(false);
                    checked = "gopay";
                }
            }
        });

        cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    gopay.setChecked(false);
                    checked = "cash";
                }
            }
        });

    }

}
