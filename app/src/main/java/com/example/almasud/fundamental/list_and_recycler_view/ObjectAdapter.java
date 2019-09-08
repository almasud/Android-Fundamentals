package com.example.almasud.fundamental.list_and_recycler_view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class ObjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Object> objects;
    private static final int CAR_TYPE = 1;
    private static final int MOVIE_TYPE = 2;

    public ObjectAdapter(Context context, ArrayList<Object> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof Car)
            return CAR_TYPE;
        else if (objects.get(position) instanceof Movie)
            return MOVIE_TYPE;
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case CAR_TYPE:
                View v1 = inflater.inflate(R.layout.single_car_row, parent, false);
                viewHolder = new CarViewHolder(v1);
                break;
            case MOVIE_TYPE:
                View v2 = inflater.inflate(R.layout.single_movie_row, parent, false);
                viewHolder = new MovieViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CAR_TYPE:
                CarViewHolder cvh = (CarViewHolder) holder;
                Car car = (Car) objects.get(position);
                cvh.carIV.setImageResource(car.getCarImage());
                cvh.carNameTV.setText(car.getCarName());
                cvh.carCompanyTV.setText(car.getCarCompany());
                break;
            case MOVIE_TYPE:
                MovieViewHolder mvh = (MovieViewHolder) holder;
                Movie movie = (Movie) objects.get(position);
                mvh.movieNameTV.setText(movie.getMovieName());
                mvh.movieCategoryTV.setText(movie.getCategory());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView carIV;
        TextView carNameTV;
        TextView carCompanyTV;

        public CarViewHolder(View itemView) {
            super(itemView);
            carIV = itemView.findViewById(R.id.flowerImage);
            carNameTV = itemView.findViewById(R.id.flowerName);
            carCompanyTV = itemView.findViewById(R.id.carCompanyName);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView movieNameTV;
        TextView movieCategoryTV;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieNameTV = itemView.findViewById(R.id.movieName);
            movieCategoryTV = itemView.findViewById(R.id.movieCategory);
        }
    }
}
