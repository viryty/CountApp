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
import com.viryty.countapp.utils.OnViewHolderHelper;
import com.viryty.countapp.utils.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment implements OnViewHolderHelper {

    public final static String NAME_FRAGMENT = "Главная";

    private static HomeFragment instance;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private ArrayList<Subject> subjects;
    private ItemTouchHelper itemTouchHelper;

    public static Fragment newInstance() {
        if(instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        subjects = new ArrayList<>();

        for(int i = 1; i <= 15; i++) {
            Subject subject = new Subject();
            subject.setTitle(String.valueOf(i));
            if (i % 2 == 0) {
                subject.setFavorite(true);
            } else {
                subject.setFavorite(false);
            }
            subjects.add(subject);
        }

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeAdapter = new HomeAdapter(subjects, this);
        recyclerView.setAdapter(homeAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(homeAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Subject subject = subjects.remove(fromPosition);
        subjects.add(toPosition > fromPosition + 1 ? toPosition - 1 : toPosition, subject);
        homeAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRemove(int position) {
        subjects.remove(position);
        homeAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onRemoveFavorite(int position) {
        Subject subject = subjects.remove(position);
        if (subject.isFavorite()) {
            subject.setFavorite(false);
        } else {
            subject.setFavorite(true);
        }
        subjects.add(position, subject);
        homeAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClick(int position) {
        Log.d("Test", position + " click");
    }
}
