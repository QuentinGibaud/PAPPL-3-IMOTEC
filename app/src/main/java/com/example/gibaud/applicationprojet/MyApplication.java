package com.example.gibaud.applicationprojet;

//Liste des imports
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;

/**
 * Activite principal : permet de lancer et de fermer l'application
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */


public class MyApplication extends Application {


    /**
     * Declaration des attributs
     */
    private List<Activity> activityList = new LinkedList<Activity>(); //liste des activités que l'on peut lancer
    private static MyApplication instance; //accès au R

    /**
     * Constructeur sans parametres
     */
    private MyApplication() {
    }


    /**
     * Getter de l'application en cours
     * @return une nouvelle application
     */
    public static MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }


    /**
     * Permet de gerer l'ensemble des activites
     * Permet de lancer les differentes activites
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    /**
     * Permet de quitter l'application en fermant l'activite principale
     */
    public void exitApp() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        System.exit(0);
    }


    /**
     * Permet de gerer un espace mémoire trop faible pour l'application
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
