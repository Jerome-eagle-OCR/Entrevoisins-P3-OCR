package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    private boolean mTotoJoke;

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

        //Get clicked neighbour index in list from Extra
        Intent mIntent = getIntent();
        int mClickedNeighbourIndexInList = mIntent.getIntExtra("CLICKED_NEIGHBOUR_INDEX_IN_LIST", -1);
        if (mClickedNeighbourIndexInList == -1) {
            mNeighbour = null;
        } else {
            mNeighbour = mApiService.getNeighbours().get(mClickedNeighbourIndexInList);
        }

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
        if (mNeighbour.getFavori()) {
            mFavNeighbourButton.setActivated(true);
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
                        Snackbar.make(view, "Toto est un farceur !\nTu ne peux pas l'ajouter en amigo...", Snackbar.LENGTH_INDEFINITE).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).setBackgroundTint(getResources().getColor(R.color.colorAccent)).show();
                        mFavNeighbourButton.setVisibility(view.GONE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        mTotoJoke = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NeighbourDetailActivity.this);

                                builder.setTitle("Encore une blague de Toto...")
                                        .setCancelable(false)
                                        .setMessage("Appuie sur OK pour revenir à la liste de voisins")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Ends game activity
                                                Intent intent = new Intent();
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                                mTotoJoke = false;
                            }
                        }, 7000);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return (!mTotoJoke) && super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return (!mTotoJoke) && super.dispatchKeyEvent(event);
    }
}