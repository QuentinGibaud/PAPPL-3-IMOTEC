package com.example.gibaud.applicationprojet;

//Liste des imports
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Affiche le layout principal avec le choix de periode et les informations de rappel avant traitement
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */

public class ActivityRappel extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    /**
     * Declaration des attributs
     */
    private ImageView btn_enregistre1;
    private RadioGroup radioGroup_periode;
    private TextView tv_periode;
    private TextView tv_imotec;

    /**
     * Affichage du layout de l'interface d'accueil et choix de periode
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rappel);

        MyApplication.getInstance().addActivity(this);

        btn_enregistre1 = (ImageView) findViewById(R.id.btn_enregistrer1);
        radioGroup_periode = (RadioGroup) findViewById(R.id.radioGroup_periode);
        tv_periode = (TextView) findViewById(R.id.tv_periode);
        tv_imotec = (TextView) findViewById(R.id.tv_imotec);

        btn_enregistre1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ActivityRappel.this, ActivityMesure.class);
                startActivity(it);
            }
        });
        radioGroup_periode.setOnCheckedChangeListener(this);

    }

    /**
     * Affichage selon la periode choisie
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioBtn_pre:
                tv_periode.setText(getResources().getString(R.string.tv_pre_operatoire));
                break;
            case R.id.radioBtn_post:
                tv_periode.setText(getResources().getString(R.string.tv_post_operatoire));
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
     * Affichage du menu et redirection des activites
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_algorithme) {
            //Affichage de l'arbre de décision
            Intent it = new Intent(ActivityRappel.this, ActivityAlgo.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.action_a_propos) {
            //Affichage des coordonnées utiles
            Intent it = new Intent(ActivityRappel.this, ActivityVersion.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.action_quitter) {
            //Sortie de l'application
            //this.finish();
            MyApplication.getInstance().exitApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
