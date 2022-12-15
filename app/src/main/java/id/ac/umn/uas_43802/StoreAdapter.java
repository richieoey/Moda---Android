package id.ac.umn.uas_43802;
import android.content.Context;
import android.content.Intent;
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
		import java.util.List;

		import id.ac.umn.uas_43802.model.ProductModel;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
	private Context context;
	private ArrayList<StoreModel> data = new ArrayList<>();;
	private Dialog dialog;

	public interface Dialog{
		void onClick(int pos);
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	public StoreAdapter (ArrayList<StoreModel> data, Context context) {
		this.data = data;
		this.context = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_list, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

		RequestOptions options = new RequestOptions();
		options.fitCenter();
		Glide.with(context).load(data.get(position).getImage()).apply(options).into(holder.image);
		holder.name.setText(data.get(position).getName());
		holder.itemView.setOnClickListener(view -> {
			Intent intent = new Intent(holder.itemView.getContext(), StoreDetail.class);
			intent.putExtra("store", data.get(position));
			holder.itemView.getContext().startActivity(intent);
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder{
		TextView name;
		ImageView image;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			name = itemView.findViewById(R.id.store_name);
			image = itemView.findViewById(R.id.image_store);
			itemView.setOnClickListener(view -> {
				if (dialog!=null){
					dialog.onClick(getLayoutPosition());
				}
			});
		}
	}
}
