package com.me.reactiveapp.activty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.me.reactiveapp.R;

import static com.me.reactiveapp.activty.MainActivity.AUTOSLIDEMAINACTIVITY;
import static com.me.reactiveapp.activty.MainActivity.KEY_FOR_INFORMATION;

public class LogoActivity extends AppCompatActivity {
    Intent intent;
    SharedPreferences spref;
    int t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    if (loadAccountNumber() != -1) {
                        intent = new Intent(LogoActivity.this, Main2Activity.class);
                        intent.putExtra(KEY_FOR_INFORMATION, loadAccountNumber());
                        intent.putExtra(AUTOSLIDEMAINACTIVITY, loadAccountAutoSlide());
                    } else {
                        intent = new Intent(LogoActivity.this, MainActivity.class);
                    }
                startActivity(intent);
                finish();
            }
        },3000);
    }
    int loadAccountNumber() {
        spref=getSharedPreferences("your_pref",MODE_PRIVATE);
        int loadingIndex=spref.getInt("INDEXREALM",-1);
        return loadingIndex;
    }
    public int loadAccountAutoSlide() {
        spref=getSharedPreferences("auto_slide",MODE_PRIVATE);
        int autoSlide=spref.getInt("AUTOSLIDE"+loadAccountNumber(),-1);
        return autoSlide;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            t=getIntent().getIntExtra("IFDELETING",1);
        }
    }
}
