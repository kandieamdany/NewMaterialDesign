package com.smartdevelopers.kandie.nicedrawer;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;

        String title=getString(R.string.app_name);

        switch (position) {
            case 0: //search//todo


                break;
            case 1: //stats

                fragment=new StatsFragment();

                fragment = getSupportFragmentManager().findFragmentByTag(StatsFragment.TAG);
                if (fragment == null) {
                    fragment = new StatsFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, StatsFragment.TAG).commit();
                break;
            case 2: //my account //todo
                fragment=new CountryActivity();
                fragment=getSupportFragmentManager().findFragmentByTag(CountryActivity.TAG);
                if(fragment==null){
                    fragment=new CountryActivity();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,CountryActivity.TAG).commit();
                break;
            case 3: //Tabs //todo
                fragment=new TabsFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(TabsFragment.TAG);
                if(fragment==null){
                    fragment=new TabsFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,TabsFragment.TAG).commit();
//                Intent intent=new Intent(getApplicationContext(),com.smartdevelopers.kandie.nicedrawer.TabsActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }

    }//end of fragments


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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


}