package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.openclassrooms.entrevoisins.R;

public class NeighbourDetailActivity extends AppCompatActivity {

    private final Toolbar mToolbar = findViewById(R.id.toolbar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, NeighbourDetailActivity.class);
        intent.putExtra("TOOLBAR", (Parcelable) mToolbar);
        ActivityCompat.startActivity(activity, intent, null);
    }
}