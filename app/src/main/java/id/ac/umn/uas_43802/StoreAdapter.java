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
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

	private Context context;
	private List<StoreModel> list;

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvNama;
		ImageView imgStore;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			tvNama = itemView.findViewById(R.id.store_name);
			imgStore = itemView.findViewById(R.id.image_store);
		}
	}

	public StoreAdapter(Context context, List<StoreModel> list){
		this.context = context;
		this.list = list;
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

		txtNama.setText(list.get(position).getName());
//		ivStore.setImageResource(list.get(position).getImage());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

}
