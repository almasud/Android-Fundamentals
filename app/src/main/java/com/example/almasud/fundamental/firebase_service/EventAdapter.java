package com.example.almasud.fundamental.firebase_service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private Context context;
    private ArrayList<Event> events;
    private OnClickListener listener;

    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
        this.listener = (OnClickListener) context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_event_row, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.eventName.setText(event.getEventName());
        holder.eventBudget.setText(String.valueOf(event.getBudget()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventBudget;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventBudget = itemView.findViewById(R.id.eventBudget);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemClick(events.get(getAdapterPosition()), view);
                    return false;
                }
            });
        }
    }

    // Update Recycler View after any add or remove
    public void updateView(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    // Custom listener to send data from adapter
    public interface OnClickListener {
        void onItemClick(Event event, View view);
    }
}
