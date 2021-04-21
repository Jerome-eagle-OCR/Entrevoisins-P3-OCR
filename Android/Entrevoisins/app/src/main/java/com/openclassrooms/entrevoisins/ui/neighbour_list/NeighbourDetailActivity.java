package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailActivity extends AppCompatActivity {

    @BindView(R.id.fav_neighbour_btn)
    FloatingActionButton mFavNeighbourButton;
    @BindView(R.id.neighbour_pic)
    ImageView mNeighbourPic;
    @BindView(R.id.tb_neighbour_name)
    TextView mTBNeighbourName;
    @BindView(R.id.neighbour_name)
    TextView mNeighbourName;
    @BindView(R.id.neighbour_location)
    TextView mNeighbourLocation;
    @BindView(R.id.neighbour_phone)
    TextView mNeighbourPhone;
    @BindView(R.id.neighbour_fb)
    TextView mNeighbourFB;
    @BindView(R.id.neighbour_aboutMe)
    TextView mNeighbourAboutMe;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Neighbour mNeighbour;

    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

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
        if (mApiService.getFavoriteNeighbours().contains(mNeighbour)) {
            mFavNeighbourButton.setActivated(true);
            //TODO:change FAB star color
            mFavNeighbourButton.setImageDrawable(getDrawable(R.drawable.ic_yellow_star_24));
        } else {
            mFavNeighbourButton.setActivated(false);
            mFavNeighbourButton.setImageDrawable(getDrawable(R.drawable.ic_grey_star_24));
        }

        //FAB listener to set or unset neighbour as favorite
        mFavNeighbourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mFavNeighbourButton.isActivated() && !mNeighbourName.getText().equals("Toto")) {
                    String toastThis = "Ajout de " + mNeighbourName.getText() + " aux favoris !";
                    Snackbar.make(view, toastThis, Snackbar.LENGTH_SHORT).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark)).show();
                    mFavNeighbourButton.setActivated(true);
                    mFavNeighbourButton.setImageDrawable(getDrawable(R.drawable.ic_yellow_star_24));
                    mApiService.addFavoriteNeighbour(mNeighbour);
                } else {
                    if (mNeighbourName.getText().equals("Toto")) {
                        Snackbar.make(view, "Toto est un farceur, tu ne peux pas l'ajouter en amigo...", Snackbar.LENGTH_SHORT).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).setBackgroundTint(getResources().getColor(R.color.colorAccent)).show();
                    } else {
                        String toastThis = "Retrait de " + mNeighbourName.getText() + " des favoris.";
                        Snackbar.make(view, toastThis, Snackbar.LENGTH_SHORT).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark)).show();
                        mFavNeighbourButton.setActivated(false);
                        mFavNeighbourButton.setImageDrawable(getDrawable(R.drawable.ic_grey_star_24));
                        mApiService.removeFavoriteNeighbour(mNeighbour);
                    }
                }
            }
        });
    }
}