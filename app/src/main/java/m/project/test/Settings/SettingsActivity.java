package m.project.test.Settings;

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import m.project.test.Network.TranslateServer.ListenerRequestTranslate;
import m.project.test.Network.TranslateServer.ResponseTranslate;
import m.project.test.R;

/**
 * Activité Setting
 */
public class SettingsActivity extends AppCompatActivity implements ListenerRequestTranslate {

    SharedPreferences preferences= null;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public void getResultCommand(ResponseTranslate response) {
        if(response.isError()) return;
       if(response.getCommand().equals("settings")){
            finish();
        }

    }



    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preference);
        }


    }
}
