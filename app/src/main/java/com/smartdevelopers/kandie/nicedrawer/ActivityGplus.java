package com.smartdevelopers.kandie.nicedrawer;


import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.smartdevelopers.kandie.nicedrawer.Gcm.Util;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;




/**
 * Created by 4331 on 14/07/2015.
 */
public class ActivityGplus extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "ActivityGplus";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mSignInClicked;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;
    private Button btnEnter;

    String personPhotoUrl;
    String personName;
    String email;

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;
    String regid;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gplus_activity);
        context = getApplicationContext();
        btnEnter=(Button) findViewById(R.id.btn_main);
        //Cloud Messaging
        if(isUserRegistered(context)){
            startActivity(new Intent(ActivityGplus.this,MainActivity.class));
            finish();
        }else {
            // Initializing google plus api client
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN).build();
                /*
        Adding KenBurns View
        */
            KenBurnsView kbv = (KenBurnsView) findViewById(R.id.imageBurn);
            kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {

                }
            });
            btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);



            // Button click listeners
            btnSignIn.setOnClickListener(this);
            // Check device for Play Services APK. If check succeeds, proceed with
            //  GCM registration.

            if (checkPlayServices()) {
                gcm = GoogleCloudMessaging.getInstance(this);
                regid = getRegistrationId(context);

                if (regid.isEmpty()) {
                    registerInBackground();
                }


            } else {
                Log.i("pavan", "No valid Google Play Services APK found.");
            }
        }

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_main:
                //enter to the main activity
                enterMain();
                break;

        }
    }


    private void enterMain() {
        Intent intent=new Intent(getApplicationContext(),com.smartdevelopers.kandie.nicedrawer.MainActivity.class);
        startActivity(intent);
    }

    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }

    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    private void signInWithGplus() {

        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
//        }else {
//            //SnackBack
//            SnackbarManager.show(
//                    Snackbar.with(this)
//                            .text(R.string.checkNetwork)
//                            .textColor(Color.BLACK)
//                            .color(Color.CYAN)
//                            .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
//            Log.v("ActivityGplus","Not online");
//        }
        
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        updateUI(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        updateUI(false);

    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnEnter.setVisibility(View.VISIBLE);

        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnEnter.setVisibility(View.GONE);
        }
    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                sendRegistrationIdToBackend();
                //putting extra information in the Intent
//                Intent intent=new Intent(ActivityGplus.this,MainActivity.class);
//                intent.putExtra("username",personName);
//                intent.putExtra("mail",email);
//                intent.putExtra("picture", personPhotoUrl);
//                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Google Cloud Messaging
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        Util.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(Util.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(Util.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }


    private boolean isUserRegistered(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String User_name = prefs.getString(Util.USER_NAME, "");
        if (User_name.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return false;
        }

        return true;
    }
    private void registerInBackground() {
        new AsyncTask() {
            @Override
            protected String doInBackground(Object[] params) {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(ActivityGplus.this);
                    }
                    regid = gcm.register(Util.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    // You should send the registration ID to your server over HTTP,
                    //GoogleCloudMessaging gcm;/ so it can use GCM/HTTP or CCS to send messages to your app.
                    // The request to your server should be authenticated if your app
                    // is using accounts.
                    // sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the registration ID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
        }.execute();

    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Util.PROPERTY_REG_ID, regId);
        editor.putInt(Util.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private void storeUserDetails(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Util.EMAIL,email);
        editor.putString(Util.USER_NAME, personName);
//        editor.putString(Util.USER_PHOTO,personPhotoUrl);
        editor.commit();
    }
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(ActivityGplus.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    //  private RequestQueue mRequestQueue;
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
        new SendGcmToServer().execute();
// Access the RequestQueue through your singleton class.
        // AppController.getInstance().addToRequestQueue(jsObjRequest, "jsonRequest");
    }

    private class SendGcmToServer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String url = Util.register_url+"?name="+personName+"&email="+email+"&regId="+regid;
            Log.i("pavan", "url" + url);

            OkHttpClient client_for_getMyFriends = new OkHttpClient();

            String response = null;
            // String response=Utility.callhttpRequest(url);

            try {
                url = url.replace(" ", "%20");
                response = callOkHttpRequest(new URL(url),
                        client_for_getMyFriends);
                for (String subString : response.split("<script", 2)) {
                    response = subString;
                    break;
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //Toast.makeText(context,"response "+result,Toast.LENGTH_LONG).show();

            if (result != null) {
                if (result.equals("success")) {
                    storeUserDetails(context);
                    Intent intent=new Intent(ActivityGplus.this,MainActivity.class);
                    prefs=getApplicationContext().getSharedPreferences("DETAILS",MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putString("gUsername",personName);
                    editor.putString("gMail",email);
                    editor.putString("gPicture",personPhotoUrl);
                    editor.commit();
//                    intent.putExtra("username",personName);
//                    intent.putExtra("mail", email);
//                    intent.putExtra("picture", personPhotoUrl);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, "Try Again" + result, Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context, "Check net connection ", Toast.LENGTH_LONG).show();
            }
        }
    }
    // Http request using OkHttpClient
    String callOkHttpRequest(URL url, OkHttpClient tempClient)
            throws IOException {

        HttpURLConnection connection = tempClient.open(url);

        connection.setConnectTimeout(40000);
        InputStream in = null;
        try {
            // Read the response.
            in = connection.getInputStream();
            byte[] response = readFully(in);
            return new String(response, "UTF-8");
        } finally {
            if (in != null)
                in.close();
        }
    }

    byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int count; (count = in.read(buffer)) != -1;) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }


}
