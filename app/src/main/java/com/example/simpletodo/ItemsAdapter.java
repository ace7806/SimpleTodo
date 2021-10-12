package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//responsible to wrapping data to a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    public interface onLongClickListener{
        void onItemLongClicked(int pos);
    }
    public interface onClickListener{
        void onItemClicked(int pos);
    }
    List<String> list;
    onLongClickListener longClickListener;
    onClickListener onClickListener;

    public ItemsAdapter(List<String> list, onLongClickListener longClickListener,onClickListener onClickListener) {
        this.list = list;
        this.longClickListener = longClickListener;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflater to inflate the view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent, false);

        //wrap it inside a view holder and return it
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //grab item at position
        String item = list.get(position);
        //Bind the item into the specified ViewHolder 
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvItem = itemView.findViewById(android.R.id.text1);
        }
        //update the view inside of the view holder with the data
        public void bind(String item) {
            this.tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify the listener which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }

            });
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
