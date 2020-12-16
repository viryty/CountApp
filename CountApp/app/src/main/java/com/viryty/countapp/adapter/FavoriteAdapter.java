package com.viryty.countapp.adapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.viryty.countapp.R;
import com.viryty.countapp.model.Subject;
import com.viryty.countapp.utils.ItemTouchHelperAdapter;
import com.viryty.countapp.utils.ItemTouchHelperViewHolder;
import com.viryty.countapp.utils.OnStartDragLisntener;
import com.viryty.countapp.utils.OnViewHolderHelper;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteVH>
        implements ItemTouchHelperAdapter {

    private ArrayList<Subject> subjects;
    private OnViewHolderHelper onViewHolderHelper;
    private OnStartDragLisntener onStartDragLisntener;

    public FavoriteAdapter(ArrayList<Subject> subjects, OnViewHolderHelper onViewHolderHelper, OnStartDragLisntener onStartDragLisntener) {
        this.subjects = subjects;
        this.onViewHolderHelper = onViewHolderHelper;
        this.onStartDragLisntener = onStartDragLisntener;
    }

    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new FavoriteVH(v, onViewHolderHelper);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVH holder, int position) {
        holder.handler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    onStartDragLisntener.onStartDrag(holder);
                }
                return false;
            }
        });
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
        onViewHolderHelper.onRemoveFavorite(position);
    }

    public class FavoriteVH extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder, View.OnClickListener {

        private OnViewHolderHelper onViewHolderHelper;

        private ImageView handler;
        private TextView title;
        private TextView date;
        private TextView count;
        private ImageView icon;

        public FavoriteVH(@NonNull View itemView, OnViewHolderHelper onViewHolderHelper) {
            super(itemView);
            this.onViewHolderHelper = onViewHolderHelper;
            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            count = itemView.findViewById(R.id.count);
            icon = itemView.findViewById(R.id.icon);
            handler = itemView.findViewById(R.id.handler);
        }

        public void onBind(Subject subject) {
            title.setText(subject.getTitle());
            date.setText(subject.getDate());
            count.setText(String.valueOf(subject.getCount()));
            icon.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            onViewHolderHelper.onClick(getAdapterPosition());
        }

        @Override
        public void onItemSelected() {
            itemView.setAlpha(0.7f);
        }

        @Override
        public void onItemClear() {
            itemView.setAlpha(1f);
        }
    }
}
