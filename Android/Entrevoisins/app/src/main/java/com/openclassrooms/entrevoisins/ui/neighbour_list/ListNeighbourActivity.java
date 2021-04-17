package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.NeighbourDetailEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListNeighbourActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    androidx.viewpager.widget.ViewPager mViewPager;

    ListNeighbourPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this); // register activity in EventBus to subscribe events

        setSupportActionBar(mToolbar);
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neighbourDetailActivityIntent = new Intent(ListNeighbourActivity.this, NeighbourDetailActivity.class); //Toto easter egg
                startActivity(neighbourDetailActivityIntent);
            }
        });
    }

    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }

    /**
     * Fired if the user clicks on a neighbour item
     *
     * @param event
     */
    @Subscribe
    public void onClickNeighbour(NeighbourDetailEvent event) {
        Intent neighbourDetailActivityIntent = new Intent(ListNeighbourActivity.this, NeighbourDetailActivity.class);
        neighbourDetailActivityIntent.putExtra("NEIGHBOUR", event.neighbour); //neighbour put as Serialized in Extra to display details of actual selected neighbour
        startActivity(neighbourDetailActivityIntent);
    }
}
