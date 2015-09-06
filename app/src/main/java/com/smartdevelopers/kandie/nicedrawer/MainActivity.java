package com.smartdevelopers.kandie.nicedrawer;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    Intent intent;
    String user_name;
    String user_email;
    String user_photo_url;

    Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        //settings default
        PreferenceManager.setDefaultValues(this,R.xml.settings,false);

        intent=getIntent();
        user_name=intent.getStringExtra("username");
        user_email=intent.getStringExtra("mail");
        user_photo_url=intent.getStringExtra("picture");


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer

            mNavigationDrawerFragment.setUserData(user_name, user_email, user_photo_url);



//        mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;

        String title=getString(R.string.app_name);

        switch (position) {
            case 0: //Swipe Refresh Activity//todo

                fragment=new SwipeListFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(SwipeListFragment.TAG);
                if (fragment==null){
                    fragment=new SwipeListFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,SwipeListFragment.TAG).commit();

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
        switch (id){
            case R.id.action_refresh:Bundle data_Bundle=new Bundle();
                data_Bundle.putInt("id", 0);
                //Refresh content
                break;

            case R.id.action_settings:Bundle bundle=new Bundle();
                bundle.putInt("id", 1);
                Intent intent=new Intent(getApplicationContext(),com.smartdevelopers.kandie.nicedrawer.SettingsFragment.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //Settings
//                fragment=new SettingsFragment();
                break;
            case R.id.action_help:Bundle bundle1=new Bundle();
                bundle1.putInt("id", 2);
                //Help

                break;
            case R.id.action_feedback:Bundle bundle2=new Bundle();
                bundle2.putInt("id", 1);
                //Feedback

                break;
            default:
                break;

        }
        if(fragment!=null)
            this.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

        else
            Log.e("error", "cannot create fragment");

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }





}
