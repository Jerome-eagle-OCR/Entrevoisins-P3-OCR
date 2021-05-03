package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.openclassrooms.entrevoisins.R;

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

    private int totoJokeCount = 0;
    private boolean mTotoJoke = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_neighbour);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totoJokeCount();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTotoJoke = false;
        totoJokeCount = 0;
    }

    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }

    private void totoJokeCount() {
        switch (totoJokeCount) {
            case 6:
                mTotoJoke = true;
                Toast.makeText(ListNeighbourActivity.this, "0 + 0 = ?", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent neighbourDetailActivityIntent = new Intent(ListNeighbourActivity.this, NeighbourDetailsActivity.class); //Toto easter egg
                        startActivity(neighbourDetailActivityIntent);
                    }
                }, 2000);
                break;
            case 5:
            case 4:
            case 3:
            case 2:
                String totoToast;
                if (totoJokeCount == 5) {
                    totoToast = "Vous êtes maintenant à 1 clic de la surprise !";
                } else {
                    totoToast = "Vous êtes maintenant à " + (6 - totoJokeCount) + " clics de la surprise !";
                }
                Toast.makeText(ListNeighbourActivity.this, totoToast, Toast.LENGTH_SHORT).show();
            default:
                totoJokeCount++;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return (!mTotoJoke) && super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return (!mTotoJoke) && super.dispatchKeyEvent(event);
    }
}

