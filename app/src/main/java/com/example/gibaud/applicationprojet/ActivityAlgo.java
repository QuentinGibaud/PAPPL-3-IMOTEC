package com.example.gibaud.applicationprojet;

//Liste des imports
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Activité permettant l'affichage de l'algorithme
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */

public class ActivityAlgo extends AppCompatActivity implements Button.OnClickListener {

    /**
     * Déclaration des attributs
     */
    private ImageView btn_algo_rentrer;

    /**
     * Affichage du layout : arbre de décision
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_algorithme);

        MyApplication.getInstance().addActivity(this);

        btn_algo_rentrer = (ImageView) findViewById(R.id.btn_algorithme_rentrer);
        btn_algo_rentrer.setOnClickListener(this);
    }

    /**
     * Permet de sortir de l'activité
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_algorithme_rentrer:
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
        //Affichage de l'algorithme
        if (id == R.id.action_algorithme) {
            return true;
        }
        //Affichage des coordonnées utiles
        if (id == R.id.action_a_propos) {
            Intent it = new Intent(ActivityAlgo.this, ActivityVersion.class);
            startActivity(it);
            return true;
        }
        //Quitter l'application
        if (id == R.id.action_quitter) {
            MyApplication.getInstance().exitApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
