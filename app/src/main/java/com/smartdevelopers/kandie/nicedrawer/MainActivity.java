package com.smartdevelopers.kandie.nicedrawer;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.smartdevelopers.kandie.nicedrawer.news.AgricultureFragment;
import com.smartdevelopers.kandie.nicedrawer.news.ArtandCultureFragment;
import com.smartdevelopers.kandie.nicedrawer.news.BusinessFragment;
import com.smartdevelopers.kandie.nicedrawer.news.CountiesFragment;
import com.smartdevelopers.kandie.nicedrawer.news.FavoriteListFragment;
import com.smartdevelopers.kandie.nicedrawer.news.MusicFragment;
import com.smartdevelopers.kandie.nicedrawer.news.NewsFragment;
import com.smartdevelopers.kandie.nicedrawer.news.NotificationFragment;
import com.smartdevelopers.kandie.nicedrawer.news.PoliticsFragment;
import com.smartdevelopers.kandie.nicedrawer.news.SportsFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private static long back_pressed;
    Intent intent;
    String user_name;
    String user_email;
    String user_photo_url;

    Fragment fragment;
    FavoriteListFragment  favListFragment;
    SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        assert getSupportActionBar()!=null;
        //getSupportActionBar().setHomeAsEnabled(true); for appAppCompactActivity
        getSupportActionBar().setElevation(0);

        //Shared preference for Gmail


        //settings default
        PreferenceManager.setDefaultValues(this,R.xml.settings,false);

//        intent=getIntent();
//        user_name=intent.getStringExtra("username");
//        user_email=intent.getStringExtra("mail");
//        user_photo_url=intent.getStringExtra("picture");
        preferences=getApplicationContext().getSharedPreferences("DETAILS",MODE_PRIVATE);
        user_name=preferences.getString("gUsername", "");
        user_email=preferences.getString("gMail", "");
        user_photo_url=preferences.getString("gPicture","");


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer

            mNavigationDrawerFragment.setUserData(user_name, user_email,  user_photo_url);



//        mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;

        String title=getString(R.string.app_name);

        switch (position) {
            case 0: //News Fragment//todo

                fragment=new NewsFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(NewsFragment.TAG);
                if (fragment==null){
                    fragment=new NewsFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,NewsFragment.TAG).commit();

                break;
            case 1: //Politics Fragment//todo

                fragment=new PoliticsFragment();

                fragment = getSupportFragmentManager().findFragmentByTag(PoliticsFragment.TAG);
                if (fragment == null) {
                    fragment = new PoliticsFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, PoliticsFragment.TAG).commit();
                break;
            case 2: //Sports Fragment//todo
                fragment=new SportsFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(SportsFragment.TAG);
                if(fragment==null){
                    fragment=new SportsFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,SportsFragment.TAG).commit();
                break;
            case 3: //Music Fragment//todo
                fragment=new MusicFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(MusicFragment.TAG);
                if(fragment==null){
                    fragment=new MusicFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,MusicFragment.TAG).commit();
//                Intent intent=new Intent(getApplicationContext(),com.smartdevelopers.kandie.nicedrawer.TabsActivity.class);
//                startActivity(intent);
                break;
            case 4: //Art and Culture  Fragment//todo
                fragment=new ArtandCultureFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(ArtandCultureFragment.TAG);
                if(fragment==null){
                    fragment=new ArtandCultureFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,ArtandCultureFragment.TAG).commit();
                break;
            case 5: //Agriculture Fragment//todo
                fragment=new AgricultureFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(AgricultureFragment.TAG);
                if(fragment==null){
                    fragment=new AgricultureFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,AgricultureFragment.TAG).commit();
                break;
            case 6: //Counties Fragment//todo
                fragment=new CountiesFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(CountiesFragment.TAG);
                if(fragment==null){
                    fragment=new CountiesFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,CountiesFragment.TAG).commit();
                break;
            case 7: //Notification Fragment//todo
                fragment=new NotificationFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
                if(fragment==null){
                    fragment=new NotificationFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,NotificationFragment.TAG).commit();
                break;
            case 8: //Notification Fragment//todo
                fragment=new BusinessFragment();
                fragment=getSupportFragmentManager().findFragmentByTag(BusinessFragment.TAG);
                if(fragment==null){
                    fragment=new BusinessFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,BusinessFragment.TAG).commit();
                break;
            default:
                break;
        }

    }//end of fragments


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else if(back_pressed+2000>System.currentTimeMillis())
            super.onBackPressed();
        else Toast.makeText(getBaseContext(),"Press again to exit",Toast.LENGTH_SHORT).show();
        back_pressed=System.currentTimeMillis();

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
            case R.id.menu_favorites:Bundle data_Bundle=new Bundle();
                data_Bundle.putInt("id", 0);
                //Refresh content
                favListFragment = new FavoriteListFragment();
                this.getSupportFragmentManager().beginTransaction().replace(R.id.container,favListFragment).commit();
                break;

            case R.id.action_settings:Bundle bundle=new Bundle();
                bundle.putInt("id", 1);
                Intent i=new Intent(getApplicationContext(),com.smartdevelopers.kandie.nicedrawer.SettingsFragment.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
            default:
                break;

        }
        if(fragment!=null)
            this.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

        else
            Log.e("error", "cannot create fragment");

        return super.onOptionsItemSelected(item);
    }





}
