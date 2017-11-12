package com.paperwrk.gdgjsshackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.paperwrk.gdgjsshackathon.activities.HomeBookActivity;
import com.paperwrk.gdgjsshackathon.utils.PrefManager;

public class IdentityActivity extends AppCompatActivity {

    Button button_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button_book = findViewById(R.id.button_book);

        final PrefManager prefManager = new PrefManager(getApplicationContext());

        button_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IdentityActivity.this, HomeBookActivity.class);
                startActivity(intent);
                prefManager.setBook(true);
            }
        });


    }

}
