package com.axlecho.learn.doubletoolbarhideanimation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager FM = getFragmentManager();
        FragmentTransaction MfragmentTransaction = FM.beginTransaction();
        MfragmentTransaction.replace(R.id.content_layout, new ContentFragment());
        MfragmentTransaction.commit();
    }
}
