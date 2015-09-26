package org.test.boris.testapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.test.boris.testapp.R;
import org.test.boris.testapp.TestApp;
import org.test.boris.testapp.fragment.MainMenuFragment;

import javax.inject.Inject;

import dagger.ObjectGraph;


public class MainActivity extends AppCompatActivity {
//public class MainActivity extends ActionBarActivity {

    private final String TAG = MainActivity.class.getName();

    @Inject TestApp app;

    FragmentManager fragmentManager;
    static Fragment fragment;
    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TestApp)getApplicationContext()).getObjectGraph().inject(this);

        if (savedInstanceState == null) {
            Fragment f = new MainMenuFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ObjectGraph getObjectGraph() {
        return app.getObjectGraph();
    }
}
