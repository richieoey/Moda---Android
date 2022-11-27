package id.ac.umn.uas_43802;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

	ArrayList<StoreModel> dataStore;

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvNama;
		ImageView imgStore;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			tvNama = itemView.findViewById(R.id.store_name);
			imgStore = itemView.findViewById(R.id.image_store);
		}
	}

	StoreAdapter(ArrayList<StoreModel> data){
		this.dataStore = data;
	}

	@NonNull
	@Override
	public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_list, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
		TextView txtNama = holder.tvNama;
		ImageView ivStore = holder.imgStore;

		txtNama.setText(dataStore.get(position).getName());
		ivStore.setImageResource(dataStore.get(position).getImage());
	}

	@Override
	public int getItemCount() {
		return dataStore.size();
	}

}
