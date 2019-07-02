package com.example.almasud.fundamental.http_api;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almasud.fundamental.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FlowerResponseAdapter extends RecyclerView.Adapter<FlowerResponseAdapter.ViewHolder> {
    private Context context;
    private List<FlowerResponse> flowers;
    private ClickListener clickListener;

    public FlowerResponseAdapter(Context context, List<FlowerResponse> flowers) {
        this.context = context;
        this.flowers = flowers;
        this.clickListener = (ClickListener) context;
    }

    public interface ClickListener {
        void onClick(FlowerResponse flower);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flowerImg;
        TextView flowerName, flowerPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            flowerImg = itemView.findViewById(R.id.flowerImage);
            flowerName = itemView.findViewById(R.id.flowerName);
            flowerPrice = itemView.findViewById(R.id.flowerPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(flowers.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public FlowerResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_flower_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerResponseAdapter.ViewHolder holder, int position) {
        FlowerResponse flower = flowers.get(position);
        Uri uri = Uri.parse(RetrofitGetActivity.BASE_URL + "photos/" + flower.getPhoto());
        Picasso.get().load(uri).into(holder.flowerImg);
        holder.flowerName.setText(flower.getName());
        holder.flowerPrice.setText(String.valueOf(flower.getPrice()));
    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    // Update list after any add or remove
    public void updateData(List<FlowerResponse> flowers) {
        this.flowers = flowers;
        notifyDataSetChanged();
    }

}
