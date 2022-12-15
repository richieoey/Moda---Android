package id.ac.umn.uas_43802;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.ac.umn.uas_43802.model.ProductModel;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {

	ArrayList<ProductModel> dataStore;

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvNama;
		TextView tvToko;
		TextView tvHarga;
		ImageView imgStore;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvNama = itemView.findViewById(R.id.produk_name);
			tvToko = itemView.findViewById(R.id.store_name);
			tvHarga = itemView.findViewById(R.id.harga);
			imgStore = itemView.findViewById(R.id.image_store);
		}
	}

	ProdukAdapter(ArrayList<ProductModel> data){
		this.dataStore = data;
	}

	@NonNull
	@Override
	public ProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_list_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ProdukAdapter.ViewHolder holder, int position) {
		TextView txtNama = holder.tvNama;
		TextView txtToko = holder.tvToko;
		TextView txtHarga = holder.tvHarga;
		ImageView ivProduk = holder.imgStore;
		ProductModel produk = dataStore.get(position);

		txtNama.setText(dataStore.get(position).getName());
		txtHarga.setText(dataStore.get(position).getPrice());
		txtToko.setText(dataStore.get(position).getToko().get("name").toString());

		RequestOptions options = new RequestOptions();
		options.fitCenter();

		Glide.with(holder.itemView.getContext()).load(dataStore.get(position).getPhotoUrl()).apply(options).into(ivProduk);

		holder.itemView.setOnClickListener(view -> {
			Intent intent = new Intent(holder.itemView.getContext(), ProdukDetail.class);
			intent.putExtra("produk", produk);
			intent.putExtra("detail", "categoryView");
			holder.itemView.getContext().startActivity(intent);
		});
	}

	@Override
	public int getItemCount() {
		return dataStore.size();
	}

}
