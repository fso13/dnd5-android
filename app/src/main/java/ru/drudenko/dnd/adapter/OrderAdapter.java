package ru.drudenko.dnd.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.di.App;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final App app;

    public OrderAdapter(App app) {
        this.app = app;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_list_view_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(app.orders.get(position).getName());

        holder.button.setOnClickListener(v -> {
            app.deleteOrder(app.orders.get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return app.orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView2);
            textView.setTextColor(Color.WHITE);

            button = itemView.findViewById(R.id.delete_profile);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), app.orders.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
        }
    }
}

//in your Activity or Fragment where of Adapter is instantiated :
