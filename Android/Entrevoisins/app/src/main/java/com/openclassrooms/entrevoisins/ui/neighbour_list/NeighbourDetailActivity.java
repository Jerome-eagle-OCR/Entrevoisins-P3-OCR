package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;

public class NeighbourDetailActivity extends AppCompatActivity {

    private FloatingActionButton mFavNeighbourButton;
    private ImageView mNeighbourPic;
    private TextView mTBNeighbourName;
    private TextView mNeighbourName;
    private TextView mNeighbourLocation;
    private TextView mNeighbourPhone;
    private TextView mNeighbourFB;
    private TextView mNeighbourAboutMe;

    public static String SELECTED_NEIGHBOUR = "SELECTED_NEIGHBOUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);

        // Widgets wiring
        mFavNeighbourButton = findViewById(R.id.fav_neighbour_btn);
        mNeighbourPic = findViewById(R.id.neighbour_pic);
        mTBNeighbourName = findViewById(R.id.tb_neighbour_name);
        mNeighbourName = findViewById(R.id.neighbour_name);
        mNeighbourLocation = findViewById(R.id.neighbour_location);
        mNeighbourPhone = findViewById(R.id.neighbour_phone);
        mNeighbourFB = findViewById(R.id.neighbour_fb);
        mNeighbourAboutMe = findViewById(R.id.neighbour_aboutMe);

        // Have fun with Toto
        mNeighbourPic.setImageDrawable(getDrawable(R.drawable.toto));
        mTBNeighbourName.setText("Toto");
        mNeighbourName.setText("Toto");
        mNeighbourLocation.setText("Totoville à 0km");
        mNeighbourPhone.setText("+33 0 00 00 00 00");
        mNeighbourFB.setText("www.facebook.fr/Toto");
        mNeighbourAboutMe.setText("Hello ! J'adore faire des blagues à gogo. Alors, si toi aussi t'es un rigolo, ajoute-moi en amigo !\n");


        mFavNeighbourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mFavNeighbourButton.isActivated()) {
                    String toastThis =  "Ajout de " + mNeighbourName.getText() + " réussi !";
                    Toast.makeText(NeighbourDetailActivity.this, toastThis, Toast.LENGTH_SHORT).show();
                    mFavNeighbourButton.setActivated(true);
                } else {
                    String toastThis =  "Retrait de " + mNeighbourName.getText() + " effectué.";
                    Toast.makeText(NeighbourDetailActivity.this, toastThis, Toast.LENGTH_SHORT).show();
                    mFavNeighbourButton.setActivated(false);
                }
            }
        });
    }


    /**
     * Used to navigate to this activity
     *
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, NeighbourDetailActivity.class);
        //intent.putExtra(SELECTED_NEIGHBOUR, (Serializable) mNeighbour);
        ActivityCompat.startActivity(activity, intent, null);
    }
}