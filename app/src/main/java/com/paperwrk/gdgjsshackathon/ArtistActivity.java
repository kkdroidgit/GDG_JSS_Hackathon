package com.paperwrk.gdgjsshackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class ArtistActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    TextView et_name;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 18;
    TextView editText;
    private EditText mMessageEditText;
    private Button mSendButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();

        et_name = findViewById(R.id.artist_name);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //logged in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedCleanUp();
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
        };



    }

    private void  onSignedInInitialize(String username) {
        mUsername = username;
        et_name.setText(mUsername);
    }

    private void onSignedCleanUp(){
        mUsername = ANONYMOUS;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Toast.makeText(this,"Signed In!",Toast.LENGTH_LONG).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Sign In Canceled",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }



}
