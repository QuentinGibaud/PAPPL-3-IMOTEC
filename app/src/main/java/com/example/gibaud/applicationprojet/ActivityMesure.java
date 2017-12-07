package com.example.gibaud.applicationprojet;

//Liste des imports
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 *
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */

public class ActivityMesure extends AppCompatActivity implements TextWatcher, Button.OnClickListener//implements View.OnClickListener
{
    //Définition des attributs
    private ImageView btn_back;

    private TextView tv_vider;

    private TextView tv_tests_normaux;
    private TextView tv_tests_normaux_detail;
    private TextView tv_nb_proposition;

    private TextView tv_ckr;
    private TextView tv_crtma;
    private TextView tv_crtly30;
    private TextView tv_ckhepr;
    private TextView tv_cffma;

    private CheckBox cb_protamine;
    private CheckBox cb_tp;
    private CheckBox cb_fibrinogene;
    private CheckBox cb_plasma_ccp;
    private CheckBox cb_acide;

    private EditText et_ckr;
    private EditText et_ck_hep_r;
    private EditText et_crt_ma;
    private EditText et_cff_ma;
    private EditText et_crt_ly30;

    private double ckr;
    private double ck_hep_r;
    private double crt_ma;
    private double cff_ma;
    private double crt_ly30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesure);

        MyApplication.getInstance().addActivity(this);

        //Initialisation des vues
        btn_back = (ImageView) findViewById(R.id.btn_back);

        tv_vider = (TextView) findViewById(R.id.tv_vider);

        tv_tests_normaux = (TextView) findViewById(R.id.tv_tests_normaux);
        tv_tests_normaux_detail = (TextView) findViewById(R.id.tv_tests_normaux_detail);
        tv_nb_proposition = (TextView) findViewById(R.id.tv_nb_proposition);

        tv_ckr = (TextView) findViewById(R.id.tv_ckr);
        tv_crtma = (TextView) findViewById(R.id.tv_crtma);
        tv_crtly30 = (TextView) findViewById(R.id.tv_crtly30);
        tv_ckhepr = (TextView) findViewById(R.id.tv_ckhepr);
        tv_cffma = (TextView) findViewById(R.id.tv_cffma);

        cb_protamine = (CheckBox) findViewById(R.id.tv_protamine);
        cb_tp = (CheckBox) findViewById(R.id.tv_tp);
        cb_fibrinogene = (CheckBox) findViewById(R.id.tv_fibrinogene);
        cb_plasma_ccp = (CheckBox) findViewById(R.id.tv_plasma_ccp);
        cb_acide = (CheckBox) findViewById(R.id.tv_acide);

        tv_tests_normaux.setVisibility(View.VISIBLE);
        tv_tests_normaux_detail.setVisibility(View.VISIBLE);
        tv_nb_proposition.setVisibility(View.VISIBLE);

        tv_ckr.setVisibility(View.GONE);
        tv_crtma.setVisibility(View.GONE);
        tv_crtly30.setVisibility(View.GONE);
        tv_ckhepr.setVisibility(View.GONE);
        tv_cffma.setVisibility(View.GONE);

        cb_protamine.setVisibility(View.GONE);
        cb_tp.setVisibility(View.GONE);
        cb_fibrinogene.setVisibility(View.GONE);
        cb_plasma_ccp.setVisibility(View.GONE);
        cb_acide.setVisibility(View.GONE);

        et_ckr = (EditText) findViewById(R.id.et_ckr);
        et_ck_hep_r = (EditText) findViewById(R.id.et_ck_hep_r);
        et_crt_ma = (EditText) findViewById(R.id.et_crt_ma);
        et_cff_ma = (EditText) findViewById(R.id.et_cff_ma);
        et_crt_ly30 = (EditText) findViewById(R.id.et_crt_ly30);

        //Initialisation des events
        btn_back.setOnClickListener(this);
        tv_vider.setOnClickListener(this);

        et_ckr.addTextChangedListener(this);
        et_ck_hep_r.addTextChangedListener(this);
        et_crt_ma.addTextChangedListener(this);
        et_cff_ma.addTextChangedListener(this);
        et_crt_ly30.addTextChangedListener(this);

        int nb_proposition = 0;
        tv_nb_proposition.setText(Integer.toString(nb_proposition));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                Intent it = new Intent(ActivityMesure.this, ActivityRappel.class);
                startActivity(it);
                break;
            case R.id.tv_vider:
                et_ckr.setText("");
                et_ck_hep_r.setText("");
                et_crt_ma.setText("");
                et_cff_ma.setText("");
                et_crt_ly30.setText("");
                break;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    /**
     * Implémentation de l'algorithme
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        int nb_proposition = 0;
        //Protamine
        if ((et_ckr.getText().length() > 0) && (et_ck_hep_r.getText().length() > 0)) {
            ckr = Double.valueOf(et_ckr.getText().toString());
            ck_hep_r = Double.valueOf(et_ck_hep_r.getText().toString());
            if (ck_hep_r <= ckr * 2) {
                cb_protamine.setVisibility(View.VISIBLE);
                nb_proposition++;
            } else {
                cb_protamine.setChecked(false);
                cb_protamine.setVisibility(View.GONE);
            }
        } else {
            cb_protamine.setChecked(false);
            cb_protamine.setVisibility(View.GONE);
        }

        //Transfusion plaquettaire
        if ((et_crt_ma.getText().length() > 0) && (et_cff_ma.getText().length() > 0)) {
            crt_ma = Double.valueOf(et_crt_ma.getText().toString());
            cff_ma = Double.valueOf(et_cff_ma.getText().toString());
            if (crt_ma < 48 && cff_ma > 16) {
                cb_tp.setVisibility(View.VISIBLE);
                nb_proposition++;
            } else {
                cb_tp.setChecked(false);
                cb_tp.setVisibility(View.GONE);
            }
        } else {
            cb_tp.setChecked(false);
            cb_tp.setVisibility(View.GONE);
        }

        //Fibrinogène
        if ((et_crt_ma.getText().length() > 0) && (et_cff_ma.getText().length() > 0)) {
            crt_ma = Double.valueOf(et_crt_ma.getText().toString());
            cff_ma = Double.valueOf(et_cff_ma.getText().toString());
            if (crt_ma > 47 && cff_ma < 17) {
                cb_fibrinogene.setVisibility(View.VISIBLE);
                nb_proposition++;
            } else {
                cb_fibrinogene.setChecked(false);
                cb_fibrinogene.setVisibility(View.GONE);
            }
        } else {
            cb_fibrinogene.setChecked(false);
            cb_fibrinogene.setVisibility(View.GONE);
        }


        //Plasma et CCP
        if ((et_ckr.getText().length() > 0) && (et_ck_hep_r.getText().length() > 0)) {
            ckr = Double.valueOf(et_ckr.getText().toString());
            ck_hep_r = Double.valueOf(et_ck_hep_r.getText().toString());
            if (ckr > 10 && ck_hep_r > 10) {
                cb_plasma_ccp.setVisibility(View.VISIBLE);
                nb_proposition++;
            } else {
                cb_plasma_ccp.setChecked(false);
                cb_plasma_ccp.setVisibility(View.GONE);
            }
        } else {
            cb_plasma_ccp.setChecked(false);
            cb_plasma_ccp.setVisibility(View.GONE);
        }

        //Acide Tranexamique
        if (et_crt_ly30.getText().length() > 0) {
            crt_ly30 = Double.valueOf(et_crt_ly30.getText().toString());
            if (crt_ly30 > 3) {
                cb_acide.setVisibility(View.VISIBLE);
                nb_proposition++;
            } else {
                cb_acide.setChecked(false);
                cb_acide.setVisibility(View.GONE);
            }
        } else {
            cb_acide.setChecked(false);
            cb_acide.setVisibility(View.GONE);
        }

        //Tests normaux
        if (nb_proposition == 0){
            tv_tests_normaux.setVisibility(View.VISIBLE);
            tv_tests_normaux_detail.setVisibility(View.VISIBLE);
        }
        else {
            tv_tests_normaux.setVisibility(View.GONE);
            tv_tests_normaux_detail.setVisibility(View.GONE);
        }

        //Affichage hors des valeurs normales

        //CKR
        if (et_ckr.getText().length() >0){
            ckr = Double.valueOf(et_ckr.getText().toString());
            if (ckr <= 4.6 || ckr >= 9.1){
                tv_ckr.setVisibility(View.VISIBLE);
            } else {
                tv_ckr.setVisibility(View.GONE);
            }
        } else {
            tv_ckr.setVisibility(View.GONE);
        }

        //CRT-MA
        if (et_crt_ma.getText().length() >0){
            crt_ma = Double.valueOf(et_crt_ma.getText().toString());
            if (crt_ma <= 52 || crt_ma >= 70){
                tv_crtma.setVisibility(View.VISIBLE);
            } else {
                tv_crtma.setVisibility(View.GONE);
            }
        } else {
            tv_crtma.setVisibility(View.GONE);
        }

        //CRT-LY30
        if (et_crt_ly30.getText().length() >0){
            crt_ly30 = Double.valueOf(et_crt_ly30.getText().toString());
            if ( crt_ly30 >= 2.2){
                tv_crtly30.setVisibility(View.VISIBLE);
            } else {
                tv_crtly30.setVisibility(View.GONE);
            }
        } else {
            tv_crtly30.setVisibility(View.GONE);
        }

        //CK-Hep-R
        if (et_ck_hep_r.getText().length() >0){
            ck_hep_r = Double.valueOf(et_ck_hep_r.getText().toString());
            if (ck_hep_r <= 4.3 || ck_hep_r >= 8.3){
                tv_ckhepr.setVisibility(View.VISIBLE);
            } else {
                tv_ckhepr.setVisibility(View.GONE);
            }
        } else {
            tv_ckhepr.setVisibility(View.GONE);
        }

        //CFF-MA
        if (et_cff_ma.getText().length() >0){
            cff_ma = Double.valueOf(et_cff_ma.getText().toString());
            if (cff_ma <= 15 || cff_ma >= 32){
                tv_cffma.setVisibility(View.VISIBLE);
            } else {
                tv_cffma.setVisibility(View.GONE);
            }
        } else {
            tv_cffma.setVisibility(View.GONE);
        }

        tv_nb_proposition.setText(Integer.toString(nb_proposition));
    }

    //ToolBar Menu initialisation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //ToolBar Items initialisation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_algorithme) {
            Intent it = new Intent(ActivityMesure.this, ActivityAlgo.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.action_a_propos) {
            Intent it = new Intent(ActivityMesure.this, ActivityVersion.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.action_quitter) {
            MyApplication.getInstance().exitApp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

