package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

public class NeighbourDetailActivity extends AppCompatActivity {

    private FloatingActionButton mFavNeighbourButton;
    private ImageView mNeighbourPic;
    private TextView mTBNeighbourName;
    private TextView mNeighbourName;
    private TextView mNeighbourLocation;
    private TextView mNeighbourPhone;
    private TextView mNeighbourFB;
    private TextView mNeighbourAboutMe;
    private Toolbar mToolbar;

    private Neighbour mNeighbour;
    private List<Neighbour> mFavoriteNeighbours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);

        mFavoriteNeighbours = new ArrayList<>();

        //Widgets wiring
        mFavNeighbourButton = findViewById(R.id.fav_neighbour_btn);
        mNeighbourPic = findViewById(R.id.neighbour_pic);
        mTBNeighbourName = findViewById(R.id.tb_neighbour_name);
        mNeighbourName = findViewById(R.id.neighbour_name);
        mNeighbourLocation = findViewById(R.id.neighbour_location);
        mNeighbourPhone = findViewById(R.id.neighbour_phone);
        mNeighbourFB = findViewById(R.id.neighbour_fb);
        mNeighbourAboutMe = findViewById(R.id.neighbour_aboutMe);
        mToolbar = findViewById(R.id.toolbar);

        //Configure action bar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get clicked neighbour from Extra
        Intent mIntent = getIntent();
        mNeighbour = (Neighbour) mIntent.getSerializableExtra("CLICKED_NEIGHBOUR");

        if (mNeighbour != null) {
            // Set details for clicked neighbour
            String clickedNeighbourName = mNeighbour.getName();
            String clickedNeighbourFB = "www.facebook.fr/" + clickedNeighbourName.toLowerCase(); //create mock FB address based on neighbour name
            String clickedNeighbourAM = mNeighbour.getAboutMe() + "\n"; //add extra line at the end of about me text
            String improvedAvatar = mNeighbour.getAvatarUrl().replace("cc/150", "cc/320"); //get image with higher resolution (320p instead of 150p)

            Glide.with(this).load(improvedAvatar).into(mNeighbourPic);
            mTBNeighbourName.setText(clickedNeighbourName);
            mNeighbourName.setText(clickedNeighbourName);
            mNeighbourLocation.setText(mNeighbour.getAddress());
            mNeighbourPhone.setText(mNeighbour.getPhoneNumber());
            mNeighbourFB.setText(clickedNeighbourFB);
            mNeighbourAboutMe.setText(clickedNeighbourAM);
        } else {
            //Have fun with Toto
            mNeighbourPic.setImageDrawable(getDrawable(R.drawable.toto));
            mTBNeighbourName.setText("Toto");
            mNeighbourName.setText("Toto");
            mNeighbourLocation.setText("Totoville à 0km");
            mNeighbourPhone.setText("+33 0 00 00 00 00");
            mNeighbourFB.setText("www.facebook.fr/toto");
            mNeighbourAboutMe.setText("Hello ! J'adore faire des blagues à gogo. Alors, si toi aussi t'es un rigolo, ajoute-moi en amigo !\n");
        }

        //Test if neighbour is already favorite and manage FAB consequently
        //TODO:delete "!" in if condition
        if (!mFavoriteNeighbours.contains(mNeighbour)) {
            mFavNeighbourButton.setActivated(true);
            //TODO:change FAB star color
            mFavNeighbourButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_yellow_star_24));
        } else {
            mFavNeighbourButton.setActivated(false);
        }

        //FAB listener to set or unset neighbour as favorite
        mFavNeighbourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mFavNeighbourButton.isActivated()) {
                    String toastThis = "Ajout de " + mNeighbourName.getText() + " aux favoris !";
                    Snackbar.make(view, toastThis, Snackbar.LENGTH_SHORT).show();
                    mFavNeighbourButton.setActivated(true);
                    //TODO:change FAB star color
                    mFavoriteNeighbours.add(mNeighbour);
                } else {
                    String toastThis = "Retrait de " + mNeighbourName.getText() + " des favoris.";
                    Snackbar.make(view, toastThis, Snackbar.LENGTH_SHORT).show();
                    mFavNeighbourButton.setActivated(false);
                    //TODO:change FAB star color
                    mFavoriteNeighbours.remove(mNeighbour);
                }
            }
        });
    }
}