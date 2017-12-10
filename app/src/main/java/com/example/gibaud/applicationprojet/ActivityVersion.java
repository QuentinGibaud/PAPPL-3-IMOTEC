package com.example.gibaud.applicationprojet;

//Liste des imports
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Affichage des coordonnées utiles
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */

public class ActivityVersion extends AppCompatActivity implements View.OnClickListener{

    /**
     * Déclaration des attributs
     */
    ImageView btn_version_rentrer ;

    /**
     * Affichage du layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_version);
        MyApplication.getInstance().addActivity(this);

        btn_version_rentrer = (ImageView) findViewById(R.id.btn_version_rentrer);
        btn_version_rentrer.setOnClickListener(this);
    }

    /**
     * Sortie de l'activité par appui sur le bouton Retour
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_version_rentrer:
                this.finish();
                break;
        }
    }

    /**
     * Initialisation de la ToolBar du menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Affichage du menu et redirection des activités
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_algorithme) {
            //Affichage de l'arbre de décision
            Intent it = new Intent(ActivityVersion.this, ActivityAlgo.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.action_a_propos) {
            //Affichage des coordonnées utiles
            return true;
        }
        if (id == R.id.action_quitter) {
            //Quitter l'application
            MyApplication.getInstance().exitApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
