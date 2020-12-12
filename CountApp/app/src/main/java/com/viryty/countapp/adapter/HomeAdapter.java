package com.viryty.countapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.viryty.countapp.R;
import com.viryty.countapp.model.Subject;
import com.viryty.countapp.utils.ItemTouchHelperAdapter;
import com.viryty.countapp.utils.ItemTouchHelperViewHolder;
import com.viryty.countapp.utils.OnViewHolderHelper;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.SubjectVH>
        implements ItemTouchHelperAdapter {

    private ArrayList<Subject> subjects;
    private OnViewHolderHelper onViewHolderHelper;

    public HomeAdapter(ArrayList<Subject> subjects, OnViewHolderHelper onViewHolderHelper) {
        this.subjects = subjects;
        this.onViewHolderHelper = onViewHolderHelper;
    }

    @NonNull
    @Override
    public SubjectVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectVH(v, onViewHolderHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectVH holder, int position) {
        holder.onBind(subjects.get(position));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        onViewHolderHelper.onMove(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position, int direction) {
        if(direction == ItemTouchHelper.END) {
            onViewHolderHelper.onRemove(position);
        } else {
           onViewHolderHelper.onRemoveFavorite(position);
        }

    }

    public class SubjectVH extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener {

        private OnViewHolderHelper onViewHolderHelper;

        private TextView title;
        private TextView date;
        private TextView count;
        private ImageView icon;

        public SubjectVH(@NonNull View itemView, OnViewHolderHelper onViewHolderHelper) {
            super(itemView);
            this.onViewHolderHelper = onViewHolderHelper;
            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            count = itemView.findViewById(R.id.count);
            icon = itemView.findViewById(R.id.icon);
        }

        public void onBind(Subject subject) {
            title.setText(subject.getTitle());
            date.setText(subject.getDate());
            count.setText(String.valueOf(subject.getCount()));
            if (subject.isFavorite()) {
                icon.setVisibility(View.VISIBLE);
            } else {
                icon.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setAlpha(0.7f);
        }

        @Override
        public void onItemClear() {
            itemView.setAlpha(1f);
        }

        @Override
        public void onClick(View v) {
            onViewHolderHelper.onClick(getAdapterPosition());
        }
    }

}
