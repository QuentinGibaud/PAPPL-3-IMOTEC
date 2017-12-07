//Utilisation du même package que pour le premier protocole
package com.example.gibaud.applicationprojet;

//Liste des imports
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 *
 *
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */

//Lance l'application et la ferme
public class MyApplication extends Application {


    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;

    private MyApplication() {
    }


    //Nouvelle application
    public static MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }


    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    //Sortir de l'application
    public void exitApp() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        System.exit(0);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
