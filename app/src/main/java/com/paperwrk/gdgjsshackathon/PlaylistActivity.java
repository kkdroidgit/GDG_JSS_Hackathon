package com.paperwrk.gdgjsshackathon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.paperwrk.gdgjsshackathon.chat.ChatActivity;
import com.paperwrk.gdgjsshackathon.fragments.PlaylistViewFragment;
import com.paperwrk.gdgjsshackathon.utils.CheckConnection;

public class PlaylistActivity extends AppCompatActivity {

    private String[] YOUTUBE_PLAYLISTS ;
    Bundle b;

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        if(!CheckConnection.isConnected(PlaylistActivity.this)){
            Toast.makeText(PlaylistActivity.this,"No Internet Connection Detected",Toast.LENGTH_LONG).show();
        }

        b = this.getIntent().getExtras();

        YOUTUBE_PLAYLISTS = b.getStringArray("videoID");



        if (savedInstanceState == null) {
            mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();
            if(YOUTUBE_PLAYLISTS.length == 1){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, PlaylistViewFragment
                                .newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                        .commit();
            }
            else{
            /*    getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, SingleViewFragment
                                .newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                        .commit();
            */}
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case R.id.action_book:
                chat_now();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chat_now() {
        new AlertDialog.Builder(this)
                .setTitle("Chat Now")
                .setMessage("Are You Sure You Want to Chat?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(PlaylistActivity.this,ChatActivity.class);
                        startActivity(intent);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
