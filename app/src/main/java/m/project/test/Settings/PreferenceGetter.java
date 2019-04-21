package m.project.test.Settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import m.project.test.MyApp;

/**
 * Classe permettant de recuperer les preferences
 */
public class PreferenceGetter {
    private static SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());

    /**
     * Recupère la valeur de la key donnée
     * @param key
     * @return valeur
     */
    public static String getValue(String key){
        return preferences.getString(key,"");
    }
}
