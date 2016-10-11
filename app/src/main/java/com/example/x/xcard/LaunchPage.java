package com.example.x.xcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by X on 2016/10/11.
 */

public class LaunchPage extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(LaunchPage.this, MainActivity.class);
                LaunchPage.this.startActivity(mainIntent);
                LaunchPage.this.finish();
            }
        }, 3000);
    }

}
