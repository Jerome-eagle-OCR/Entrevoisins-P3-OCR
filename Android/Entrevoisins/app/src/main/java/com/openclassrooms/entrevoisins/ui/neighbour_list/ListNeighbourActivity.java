package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;

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

    public static final int NEIGHBOUR_DETAIL_ACTIVITY_REQUEST_CODE = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        setSupportActionBar(mToolbar);
        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }

    @Subscribe
    public void onClickNeighbour(NeighbourDetailEvent event) {
        //Essai pour vérifier le bon fonctionnement de l'event (affichage du détail du voisin Toto pour mettre un peu de fun...
        Intent neighbourDetailActivityIntent = new Intent(ListNeighbourActivity.this, NeighbourDetailActivity.class);
        startActivity(neighbourDetailActivityIntent);

        /* Intent qui ne fonctionne pas avec le cast du event.neighbour en objet Serializable
        Intent neighbourDetailActivityIntent = new Intent(ListNeighbourActivity.this, NeighbourDetailActivity.class);
        neighbourDetailActivityIntent.putExtra("NEIGHBOUR", (Serializable) event.neighbour);
        startActivityForResult(neighbourDetailActivityIntent, NEIGHBOUR_DETAIL_ACTIVITY_REQUEST_CODE);*/
    }
}
