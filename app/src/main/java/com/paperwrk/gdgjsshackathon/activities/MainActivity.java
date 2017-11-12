package com.paperwrk.gdgjsshackathon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.paperwrk.gdgjsshackathon.R;
import com.paperwrk.gdgjsshackathon.utils.CheckConnection;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Drawer result = null;
    private AccountHeader headerResult = null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 123;
    private String name;
    private IProfile profile;
    private IProfile profile2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        IProfile profile = new ProfileDrawerItem().withName("Kartik Shandilya")
                .withEmail("kartikdroid17@gmail.com").withIcon(R.mipmap.ic_launcher)
                .withIdentifier(100);
        IProfile profile2 = new ProfileDrawerItem()
                .withName("Demo User").withEmail("demo@github.com")
                .withIcon(R.mipmap.ic_launcher).withIdentifier(101);


        //account header
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withSavedInstance(savedInstanceState)
                .withTranslucentStatusBar(true)
                .addProfiles(profile,profile2
                )
                .build();

        createNavDrawer(toolbar);


        mFirebaseAuth = FirebaseAuth.getInstance();




        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(!CheckConnection.isConnected(MainActivity.this)){
                    Toast.makeText(MainActivity.this,"Not Connected",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(user != null){
                        //logged in
                        name = user.getDisplayName();
                    } else {
                        //user not logged in
                        List<AuthUI.IdpConfig> providers = Arrays.asList(
                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setIsSmartLockEnabled(false)
                                        .setAvailableProviders(providers)
                                        .build(),
                                RC_SIGN_IN);
                    }

                }
            }
        };

    }

    private void createNavDrawer(Toolbar toolbar) {
        //Creating the drawer
        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("First Menu")
                                .withIdentifier(0),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Second Menu")
                )
                .build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Toast.makeText(this,"Welcome "+user.getDisplayName(),Toast.LENGTH_LONG).show();
                }
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Sign In Canceled",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
