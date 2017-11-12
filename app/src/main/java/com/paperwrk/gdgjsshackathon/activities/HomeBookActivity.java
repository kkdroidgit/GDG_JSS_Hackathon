package com.paperwrk.gdgjsshackathon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.paperwrk.gdgjsshackathon.ArtistActivity;
import com.paperwrk.gdgjsshackathon.R;
import com.paperwrk.gdgjsshackathon.adapter.TvSeriesAdapter;
import com.paperwrk.gdgjsshackathon.utils.model.TvSeriesModel;

import java.util.ArrayList;

public class HomeBookActivity extends AppCompatActivity {

    Drawer result = null;
    private static final int RC_SIGN_IN = 123;
    private String name;
    Bundle bundle = new Bundle();
    private String user_Email;
    private IProfile profile;

    private RecyclerView mRecyclerView;
    private ArrayList<TvSeriesModel> mArrayList;
    private TvSeriesAdapter mAdapter;
    private Boolean exit = false;



    String[] links = new String[]{
            "PLjwBf9QEIO96SwjFDoxcCbF6getsFI_0u","PLW_Fgl5hSvd3j5OIYDcFS9UE9wfZYlGkf",
            "PLhYdRzceVuzZjJgVTnSv-x1iwn84KMiR2",
            "LLkS7Vxu4PjM99w0Is6idjcg",
            "PLJDxYrNzr_fKy1iqPmPg037HJCUR0_zPL", "PLzufeTFnhupzRq691NGVoAP0ub3ZZkBWM",
            "PLF05eR2VJLBkmUtRT15g73AYApfHZiNGH",
    };

    String[] titles = new String[]{"Bhuvam Bam","Vipul Goyal","Shirley Shetia","Zakir Khan",
            "Raju Srivastava","Kapil Sharma","Tanmay Bhatt"

    };

    int[] covers = new int[]{
            R.mipmap.bhuman_bum,
            R.mipmap.vipul,
            R.mipmap.sherley,
            R.mipmap.zakir_khan,
            R.mipmap.raju_srivastav,
            R.mipmap.kapil,
            R.mipmap.tanmay,
    };



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_book_layout);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        loadData();
        createNavDrawer(toolbar);
    }


    private void initViews(){
        mRecyclerView = findViewById(R.id.tv_series_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(HomeBookActivity.this,2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    private void loadData(){
        mArrayList = new ArrayList<>();
        for(int i=0;i<7;i++) {
            mArrayList.add(new TvSeriesModel(titles[i], covers[i],links[i]));
        }
        mAdapter = new TvSeriesAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void createNavDrawer(Toolbar toolbar) {
        //Creating the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        new ExpandableDrawerItem().withName("Instrument Players")
                                .withIdentifier(1)
                                .withSubItems(
                                        new SecondaryDrawerItem().withName("Fluet")
                                                .withIdentifier(100),
                                        new SecondaryDrawerItem().withName("Violin")
                                                .withIdentifier(101),
                                        new SecondaryDrawerItem().withName("Guitar")
                                                .withIdentifier(102),
                                        new SecondaryDrawerItem().withName("Tabla")
                                                .withIdentifier(105),
                                        new SecondaryDrawerItem().withName("Keyboard")
                                                .withIdentifier(105)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Lyricists"),
                        new PrimaryDrawerItem().withName("Solo Singers"),
                        new PrimaryDrawerItem().withName("Composers"),
                        new PrimaryDrawerItem().withName("Band Leaders")
                )
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_book,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_login:
                //login
                Intent intent = new Intent(HomeBookActivity.this, ArtistActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
