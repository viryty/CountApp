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
import com.viryty.countapp.adapter.HomeAdapter;
import com.viryty.countapp.model.Subject;
import com.viryty.countapp.utils.OnStartDragLisntener;
import com.viryty.countapp.utils.OnViewHolderHelper;
import com.viryty.countapp.utils.SimpleItemTouchHelperCallback;
import com.viryty.countapp.utils.SubjectItems;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment implements OnViewHolderHelper, OnStartDragLisntener {

    public final static String NAME_FRAGMENT = "Главная";

    private static HomeFragment instance;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private ItemTouchHelper itemTouchHelper;

    private SubjectItems subjectItems;

    public static Fragment newInstance() {
        if(instance == null) {
            instance = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new HomeAdapter(subjectItems.getSubjects(), this, this);
        recyclerView.setAdapter(homeAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(homeAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        //Subject subject = subjects.remove(fromPosition);
        //subjects.add(toPosition > fromPosition + 1 ? toPosition - 1 : toPosition, subject);

        subjectItems.updateSubjectMove(fromPosition, toPosition);
        homeAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRemove(int position) {
        //Subject subject = subjects.remove(position);
        subjectItems.removeSubject(position);
        homeAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onRemoveFavorite(int position) {
        Subject subject = subjectItems.getSubject(position);
        if (subject.isFavorite()) {
            subject.setFavorite(false);
        } else {
            subject.setFavorite(true);
        }

        subjectItems.updateSubject(subject);
        homeAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClick(int position) {
        Log.d("Test", position + " click");
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
