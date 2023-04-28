package com.example.i200444_assignment2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList titles;

    public MyAdapter(Context context, ArrayList title_id) {
        this.context = context;
        this.titles = title_id;
    }

    public void adapFilterList(ArrayList<String> filterList) {
        titles = filterList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_entry, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title_id.setText(titles.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_id = itemView.findViewById(R.id.note_title);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), NoteDetails.class);
                intent.putExtra("id", titles.get(getAdapterPosition()).toString());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
