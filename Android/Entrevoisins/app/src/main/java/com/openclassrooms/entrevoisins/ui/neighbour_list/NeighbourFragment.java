package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private int mFragmentPosition;

    public static final int ALL_NEIGHBOURS_PAGE = 0;
    public static final int FAVORITE_NEIGHBOURS_PAGE = 1;

    public static final String BUNDLE_STATE_FRAGMENT_POSITION = "currentFragment";

    /**
     * Create and return a new instance
     *
     * @param fragmentPosition page actually displayed
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(int fragmentPosition) {
        NeighbourFragment fragment = new NeighbourFragment();
        fragment.mFragmentPosition = fragmentPosition;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFragmentPosition = savedInstanceState.getInt(BUNDLE_STATE_FRAGMENT_POSITION);//avoid list issue on screen orientation change
        }
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     * Test is done on fragment position to get the right list to pass to recyclerViewAdapter and set proper content description
     */
    private void initList() {
        switch (mFragmentPosition) {
            case ALL_NEIGHBOURS_PAGE:
                mNeighbours = mApiService.getNeighbours();
                getView().setContentDescription(String.valueOf(ALL_NEIGHBOURS_PAGE));
                break;
            case FAVORITE_NEIGHBOURS_PAGE:
                mNeighbours = mApiService.getFavoriteNeighbours();
                getView().setContentDescription(String.valueOf(FAVORITE_NEIGHBOURS_PAGE));
                break;
        }
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_STATE_FRAGMENT_POSITION, mFragmentPosition);//avoid list issue on screen orientation change
        super.onSaveInstanceState(outState);
    }
}
