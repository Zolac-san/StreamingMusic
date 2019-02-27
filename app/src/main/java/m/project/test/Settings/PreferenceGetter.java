package m.project.test.Settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import m.project.test.MyApp;

public class PreferenceGetter {
    private static SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(MyApp.getAppContext());

    public static String getValue(String key){
        return preferences.getString(key,"");
    }
}
