package com.me.reactiveapp.activty;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.inputmethodservice.Keyboard;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.reactiveapp.R;
import com.me.reactiveapp.model.RegistrationHumansModel;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_FOR_INFORMATION = "KeyInformation";
    public static final String AUTOSLIDEMAINACTIVITY = "AutoSlide";
    public static final String LOGIN_USER="login user";
    SharedPreferences sharedPreferences;
    int l = 0;
    boolean t = false;
    int i = 0;

    SharedPreferences sharedPreferencesMain;
    Button BtnSignIn, BtnLogin, BtnCreateAccount, BtnCreate, BtnCloseApp;
    EditText EdtSignInLogin, EdtSignInPassword, EdtCreateAccountLogin, EdtCreateAccountPassword,
            EdtCreateAccountPasswordRepeat, EdtCreateAccountUsername;
    TextView TxtLoading, TxtSymbolChange, TxtInCorrectLogOrPass, TxtRepeatPassEqualsPassword, TxtLoginAlreadyExist, TxtCreateUsernameWith18Length;
    ProgressBar ProgressSignInLogIn, ProgressCreateAccountLoading;
    RelativeLayout RelativeSignIn, RelativeCreateAccount, RelativeLoadingNewAccount, RelativeButtons;
    Animation animationRelativeVisible, animationRelativeGone, animationLogInProgressBar;
    View line;
    ImageView imageview;
    RelativeLayout.LayoutParams params, paramsSignInAndCreateAccount;
    Realm realmEdittextsInfo;
    Intent intent;
    ObjectAnimator anim;
    ScrollView scrollview;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        realmEdittextsInfo = Realm.getDefaultInstance();

    }


    void init() {
        BtnLogin = (Button) findViewById(R.id.button_login);
        BtnSignIn = (Button) findViewById(R.id.button_signIn);
        BtnCreateAccount = (Button) findViewById(R.id.button_create_account);
        BtnCreate = (Button) findViewById(R.id.button_create);
        BtnCloseApp = (Button) findViewById(R.id.button_close_app);
        EdtSignInLogin = (EditText) findViewById(R.id.edit_login);
        EdtSignInPassword = (EditText) findViewById(R.id.edit_password);
        EdtCreateAccountLogin = (EditText) findViewById(R.id.edit_create_login);
        EdtCreateAccountPassword = (EditText) findViewById(R.id.edit_create_password);
        EdtCreateAccountPasswordRepeat = (EditText) findViewById(R.id.edit_create_password_repeat);
        EdtCreateAccountUsername = (EditText) findViewById(R.id.edit_create_username);
        TxtLoading = (TextView) findViewById(R.id.loading_text);
        TxtSymbolChange = (TextView) findViewById(R.id.textview_8_symbol);
        TxtInCorrectLogOrPass = (TextView) findViewById(R.id.textview_incorrect);
        TxtRepeatPassEqualsPassword = (TextView) findViewById(R.id.textview_password_and_repaet_equals);
        TxtLoginAlreadyExist = (TextView) findViewById(R.id.textview_login_already_exist);
        TxtCreateUsernameWith18Length = (TextView) findViewById(R.id.textview_for_username);
        ProgressSignInLogIn = (ProgressBar) findViewById(R.id.progressbar_login);
        ProgressCreateAccountLoading = (ProgressBar) findViewById(R.id.progressbar_create);
        RelativeSignIn = (RelativeLayout) findViewById(R.id.relative_SignIn);
        RelativeCreateAccount = (RelativeLayout) findViewById(R.id.relative_create_account);
        RelativeLoadingNewAccount = (RelativeLayout) findViewById(R.id.relative_loading_account);
        RelativeButtons = (RelativeLayout) findViewById(R.id.relative_buttons);
        imageview = (ImageView) findViewById(R.id.imageView);

        line = findViewById(R.id.view_line);
        scrollview = (ScrollView) findViewById(R.id.scrollView2);
        TxtLoginAlreadyExist.setText("");
        EdtCreateAccountLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getEdtCreateAccountLogin().isEmpty()) {
                    realmEdittextsInfo.beginTransaction();
                    RealmResults<RegistrationHumansModel> realmResuts = realmEdittextsInfo.where(RegistrationHumansModel.class).equalTo("loginUser", getEdtCreateAccountLogin()).findAll();
                    if (!realmResuts.isEmpty()) {
                        TxtLoginAlreadyExist.setText("Login are already exist");
                        TxtLoginAlreadyExist.setTextColor(RED);
                    } else {
                        TxtLoginAlreadyExist.setText("Everything is fine :)");
                        TxtLoginAlreadyExist.setTextColor(GREEN);
                    }
                    realmEdittextsInfo.commitTransaction();

                } else {
                    TxtLoginAlreadyExist.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdtCreateAccountPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getEdtCreateAccountPassword().isEmpty()) {
                    if (getEdtCreateAccountPassword().length() < 8) {
                        TxtSymbolChange.setText("Your Password symbol count to short then 8");
                        TxtSymbolChange.setTextColor(RED);
                    } else {
                        TxtSymbolChange.setText("Everything is fine :)");
                        TxtSymbolChange.setTextColor(GREEN);
                    }

                } else {
                    TxtSymbolChange.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EdtCreateAccountPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getEdtCreateAccountPasswordRepeat().isEmpty()) {
                    if (!getEdtCreateAccountPassword().equals(getEdtCreateAccountPasswordRepeat())) {
                        TxtRepeatPassEqualsPassword.setText("Your Password doesn't equal Repeat Password");
                        TxtRepeatPassEqualsPassword.setTextColor(RED);
                    } else {
                        TxtRepeatPassEqualsPassword.setText("Everything is fine :)");
                        TxtRepeatPassEqualsPassword.setTextColor(GREEN);
                    }
                } else {
                    TxtRepeatPassEqualsPassword.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EdtCreateAccountUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getEdtCreateAccountUsername().isEmpty()) {
                    if (getEdtCreateAccountUsername().length() > 18) {
                        TxtCreateUsernameWith18Length.setText("Your Username is too long");
                        TxtCreateUsernameWith18Length.setTextColor(RED);
                    } else {
                        TxtCreateUsernameWith18Length.setText("");
                    }

                } else {
                    TxtCreateUsernameWith18Length.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        ProgressSignInLogIn.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);
        ProgressCreateAccountLoading.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.MULTIPLY);
        BtnLogin.setOnClickListener(this);
        BtnCreate.setOnClickListener(this);
        BtnCreateAccount.setOnClickListener(this);
        BtnSignIn.setOnClickListener(this);
        BtnCloseApp.setOnClickListener(this);
        EdtSignInLogin.setOnClickListener(this);
        EdtSignInPassword.setOnClickListener(this);


        animationRelativeVisible = AnimationUtils.loadAnimation(this, R.anim.animation_for_relatives);
        animationRelativeGone = AnimationUtils.loadAnimation(this, R.anim.animation_for_relative_gone);
        animationLogInProgressBar = AnimationUtils.loadAnimation(this, R.anim.animation_for_progressbar);

        intent = new Intent(MainActivity.this, Main2Activity.class);

        paramsSignInAndCreateAccount = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsSignInAndCreateAccount.setMargins(0, 200, 0, 0);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_signIn:

                if (RelativeSignIn.getVisibility() != View.VISIBLE) {
                    imageview.setVisibility(View.GONE);

                    RelativeSignIn.setVisibility(View.VISIBLE);
                    RelativeSignIn.startAnimation(animationRelativeVisible);
                    RelativeCreateAccount.startAnimation(animationRelativeGone);
                    RelativeCreateAccount.setVisibility(View.GONE);
                    line.setVisibility(View.VISIBLE);
                    params = new RelativeLayout.LayoutParams(BtnSignIn.getWidth(), 10);
                    line.setLayoutParams(params);
                    RelativeButtons.setLayoutParams(paramsSignInAndCreateAccount);

                    anim = ObjectAnimator.ofFloat(line, "translationX", 0);
                    anim.setInterpolator(new AnticipateOvershootInterpolator());
                    anim.setDuration(1500);
                    AnimatorSet set3 = new AnimatorSet();
                    set3.play(anim);
                    set3.start();
                }

                break;

            case R.id.button_login:
                anim = ObjectAnimator.ofFloat(BtnLogin, "translationX", getScreenWidth());
                anim.setDuration(1000);
                AnimatorSet set2 = new AnimatorSet();
                set2.play(anim);
                set2.start();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                ProgressSignInLogIn.setVisibility(View.VISIBLE);
                ProgressSignInLogIn.startAnimation(animationLogInProgressBar);
                BtnSignIn.setEnabled(false);
                BtnCreateAccount.setEnabled(false);
                BtnSignIn.setTextColor(GRAY);
                BtnCreateAccount.setTextColor(GRAY);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim = ObjectAnimator.ofFloat(BtnLogin, "translationX", 0);
                        anim.setInterpolator(new FastOutSlowInInterpolator());
                        anim.setDuration(1000);
                        AnimatorSet set1 = new AnimatorSet();
                        set1.play(anim);
                        set1.start();
                        BtnSignIn.setTextColor(WHITE);
                        BtnCreateAccount.setTextColor(WHITE);
                        BtnCreateAccount.setEnabled(true);
                        BtnSignIn.setEnabled(true);

                        ProgressSignInLogIn.clearAnimation();
                        ProgressSignInLogIn.setVisibility(View.INVISIBLE);
                        LoginAccount();
                        EdtSignInLogin.setText("");
                        EdtSignInPassword.setText("");


//
//                        realmEdittextsInfo.beginTransaction();
//                        realmEdittextsInfo.clear(RegistrationHumansModel.class);
//                        realmEdittextsInfo.commitTransaction();
                    }
                }, 4000);
                break;
            case R.id.button_create_account:

                if (RelativeCreateAccount.getVisibility() != View.VISIBLE) {
                    imageview.setVisibility(View.GONE);
                    RelativeCreateAccount.setVisibility(View.VISIBLE);
                    RelativeCreateAccount.startAnimation(animationRelativeVisible);
                    RelativeSignIn.startAnimation(animationRelativeGone);
                    RelativeSignIn.setVisibility(View.GONE);
                    line.setVisibility(View.VISIBLE);
                    RelativeButtons.setLayoutParams(paramsSignInAndCreateAccount);

                    params = new RelativeLayout.LayoutParams(BtnCreateAccount.getWidth(), 10);
                    line.setLayoutParams(params);

                    anim = ObjectAnimator.ofFloat(line, "translationX", getScreenWidth() - BtnCreateAccount.getWidth());
                    anim.setInterpolator(new AnticipateOvershootInterpolator());
                    anim.setDuration(1500);
                    //animation=ObjectAnimator.ofFloat(BtnCreateAccount,"alpha",0.25f,1).ofFloat(BtnCreateAccount,"scaleX",0,1).ofFloat(BtnCreateAccount,"scaleY",0,1).setDuration(1500);

                    AnimatorSet set4 = new AnimatorSet();
                    set4.play(anim);
                    set4.start();
                }

                break;

            case R.id.button_create:

                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                RelativeLoadingNewAccount.setVisibility(View.VISIBLE);
                BtnCreate.setVisibility(View.GONE);
                BtnSignIn.setEnabled(false);
                BtnCreateAccount.setEnabled(false);
                BtnSignIn.setTextColor(GRAY);
                BtnCreateAccount.setTextColor(GRAY);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BtnSignIn.setTextColor(WHITE);
                        BtnCreateAccount.setTextColor(WHITE);
                        RelativeLoadingNewAccount.setVisibility(View.GONE);
                        BtnCreate.setVisibility(View.VISIBLE);
                        BtnSignIn.setEnabled(true);
                        BtnCreateAccount.setEnabled(true);
                        TxtInCorrectLogOrPass.setText("");
                        if (TxtSymbolChange.getText().equals(TxtLoginAlreadyExist.getText()) && TxtSymbolChange.getText().equals(TxtRepeatPassEqualsPassword.getText())
                                && !TxtSymbolChange.getText().equals("") && TxtCreateUsernameWith18Length.length() == 0) {
                            //if Password length Long then 8 //if Password and Repeat Password equals  //if  Login doesn't exist
                            CreateAccount();
                            // intent.putExtra(USERNAME, getEdtCreateAccountUsername());

                            Toast.makeText(MainActivity.this, "Go and Sign In", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Please repeat Again", Toast.LENGTH_LONG).show();
                        }
                        EdtCreateAccountLogin.setText("");
                        EdtCreateAccountUsername.setText("");
                        EdtCreateAccountPassword.setText("");
                        EdtCreateAccountPasswordRepeat.setText("");

                    }
                }, 8000);
                break;
            case R.id.button_close_app:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void CreateAccount() {
        realmEdittextsInfo.beginTransaction();
        RegistrationHumansModel model = realmEdittextsInfo.createObject(RegistrationHumansModel.class);
        model.setLoginUser(getEdtCreateAccountLogin());
        model.setPasswordUser(getEdtCreateAccountPassword());
        model.setUserNameUser(getEdtCreateAccountUsername());
        RealmResults<RegistrationHumansModel> realmResult = realmEdittextsInfo.where(RegistrationHumansModel.class).findAll();
        for (int j = 0; j < realmResult.size(); j++) {
            Log.d("Create", "Login=" + realmResult.get(j).getLoginUser() + " Password=" + realmResult.get(j).getPasswordUser());
        }
        realmEdittextsInfo.commitTransaction();
    }

    private void LoginAccount() {

        realmEdittextsInfo.beginTransaction();
//        RegistrationHumansModel rhModel = realmEdittextsInfo.where(RegistrationHumansModel.class)
//                .equalTo("loginUser", getEdtSignInLogin()).equalTo("passwordUser", getEdtSignInPassword()).findFirst();
//        if (rhModel != null) {
//            t = true;
//            TxtInCorrectLogOrPass.setText("");
//
//        } else {
//            Toast.makeText(MainActivity.this, "Please Create Account", Toast.LENGTH_SHORT).show();
//        }


//        for (int j = 0; j <realmResultLoginPass.size() ; j++) {
//            Log.d("Login","Login="+realmResultLoginPass.get(j).getLoginUser()+" Password="+realmResultLoginPass.get(j).getPasswordUser());
//        }
//
        RealmResults<RegistrationHumansModel> realmResultLoginPass=realmEdittextsInfo
                .where(RegistrationHumansModel.class).findAll();


//if result is equal with login and password
        for (i = 0; i <realmResultLoginPass.size(); i++) {
            if(realmResultLoginPass.get(i).getLoginUser().equals(getEdtSignInLogin()) &&
                    realmResultLoginPass.get(i).getPasswordUser().equals(getEdtSignInPassword())) {
                t=true;
                TxtInCorrectLogOrPass.setText("");
                break;
            }
        }

        if (realmResultLoginPass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please Create Account", Toast.LENGTH_SHORT).show();
        }
        if (!t) {
            TxtInCorrectLogOrPass.setText("Your Login or Password is incorrect");
            TxtInCorrectLogOrPass.setTextColor(RED);
            scrollview.setScrollY(0);
        } else {

            intent.putExtra(KEY_FOR_INFORMATION, i);
            intent.putExtra(AUTOSLIDEMAINACTIVITY, loadAccountAutoSlide(i));
            //TODO save signinlogin to Shared Preferences.
            saveUserLogin(getEdtSignInLogin());
            startActivity(intent);
            t = false;
            finish();
        }

        realmEdittextsInfo.commitTransaction();
    }

    private void saveUserLogin(String userlogin) {
        sharedPreferencesMain=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencesMain.edit();
        editor.putString(LOGIN_USER,userlogin);
        editor.apply();
    }


    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @NonNull
    private String getEdtSignInLogin() {
        return EdtSignInLogin.getText().toString();
    }

    @NonNull
    private String getEdtSignInPassword() {
        return EdtSignInPassword.getText().toString();
    }

    @NonNull
    private String getEdtCreateAccountLogin() {
        return EdtCreateAccountLogin.getText().toString();
    }

    @NonNull
    private String getEdtCreateAccountPassword() {
        return EdtCreateAccountPassword.getText().toString();
    }

    @NonNull
    private String getEdtCreateAccountPasswordRepeat() {
        return EdtCreateAccountPasswordRepeat.getText().toString();
    }

    @NonNull
    private String getEdtCreateAccountUsername() {
        return EdtCreateAccountUsername.getText().toString();
    }


    public int loadAccountAutoSlide(int index) {
        sharedPreferencesMain = getSharedPreferences("auto_slide", MODE_PRIVATE);
        int autoSlide = sharedPreferencesMain.getInt("AUTOSLIDE" + index, -1);
        return autoSlide;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realmEdittextsInfo!=null) {
            realmEdittextsInfo.close();
        }
    }

}
