package com.viryty.countapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viryty.countapp.R;
import com.viryty.countapp.adapter.FavoriteAdapter;
import com.viryty.countapp.model.Subject;
import com.viryty.countapp.utils.OnStartDragLisntener;
import com.viryty.countapp.utils.OnViewHolderHelper;
import com.viryty.countapp.utils.SimpleItemTouchHelperCallback;
import com.viryty.countapp.utils.SubjectItems;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements OnViewHolderHelper, OnStartDragLisntener {

    public final static String NAME_FRAGMENT = "Избранное";

    private static FavoriteFragment instance;

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private ItemTouchHelper itemTouchHelper;

    private SubjectItems subjectItems;

    public static Fragment newInstance() {
        if(instance == null) {
            instance = new FavoriteFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subjectItems = SubjectItems.get(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteAdapter = new FavoriteAdapter(subjectItems.getSubjectsFavorite(), this, this);
        recyclerView.setAdapter(favoriteAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(favoriteAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        //Subject subject = subjects.remove(fromPosition);
        //subjects.add(toPosition > fromPosition + 1 ? toPosition - 1 : toPosition, subject);

        subjectItems.updateSubjectMove(fromPosition, toPosition);
        favoriteAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRemove(int position) {
        //subjects.remove(position);
        subjectItems.removeSubject(position);
        favoriteAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onRemoveFavorite(int position) {
        Subject subject = subjectItems.getSubjectFavorite(position);
        if (subject.isFavorite()) {
            subject.setFavorite(false);
        }
        //subjects.remove(subject);
        subjectItems.updateSubjectFavorite(subject);
        favoriteAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onClick(int position) {
        Log.d("Test", position + " click");
    }
}
