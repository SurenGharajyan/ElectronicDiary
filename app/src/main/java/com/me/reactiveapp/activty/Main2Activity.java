package com.me.reactiveapp.activty;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.me.reactiveapp.R;
import com.me.reactiveapp.fragmentsImageSlide.FragmentAdapter;
import com.me.reactiveapp.fragmentsImageSlide.FragmentModel;
import com.me.reactiveapp.model.RegistrationHumansModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    //Doesn't works

    //The program is too big (126 MB). I doesn't know what to do with this


    //Work
    //difficult xmls creating for difficult devices(big)(you can do it in ending the project)

    //3. in tablet it the size image is too small, and this is looking so ugly
    //stipvatc amen mi sarqi hamar petq a tarber xml-ner sarqel(Tablet,Metc Ekran....)
//tarber chaper amen mi sarqi hamar

    public static int REMEMBER_ACCOUNT = -1;
    public static int AUTOSLIDE = -1;
    TextView textViewUsername, textViewDaysAdded;
    ViewPager viewPager;
    ImageView imageViewLeft, imageViewRight, imageViewadd, imageViewUsernameChange;
    PulsatorLayout puls;
    ObjectAnimator animation1, animation2, translationX, translationY, menuCircleOpacity;
    FragmentAdapter fragmentAdapter;
    Timer timer;
    CircleMenu circleMenu;
    DisplayMetrics displayMetrics;
    RelativeLayout relativeLayout;
    Realm realm;
    LinearLayout viewAlert;
    RelativeLayout viewSetting, viewAboutInfo;
    CardView cardView;
    EditText usernameChanging;
    Switch mSwitchSlide;
    CheckBox mCheckBoxRememberAccount;
    Button DeleteAccount;
    Intent intent;
    SharedPreferences sharedPreferences;
    int screenHeight, screenWidth;
    float parentCenterX, parentCenterY;
    float positionImageAddX;
    float positionImageAddY;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        i = getIntent().getIntExtra(MainActivity.KEY_FOR_INFORMATION, 0);
        initForRealm();
        initdialogSettings();
        initSomeAlgorithms();
        init();
    }

    private void initSomeAlgorithms() {

        displayMetrics = new DisplayMetrics();
        getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        timer = new Timer();
        timer.scheduleAtFixedRate(new AutoSlide(), 6000, 6000);
        if (getIntent().getIntExtra(MainActivity.AUTOSLIDEMAINACTIVITY, 1) == -1) {
            timer.cancel();
        }
    }

    private void initForRealm() {
        realm = Realm.getDefaultInstance();
    }


    private void initdialogSettings() {
        viewSetting = (RelativeLayout) getLayoutInflater().inflate(R.layout.settings, null);
        viewAboutInfo = (RelativeLayout) getLayoutInflater().inflate(R.layout.alert_about_me, null);
        mSwitchSlide = (Switch) viewSetting.findViewById(R.id.switch_sliding_images);
        mCheckBoxRememberAccount = (CheckBox) viewSetting.findViewById(R.id.checkbox_remember_account);
        DeleteAccount = (Button) viewSetting.findViewById(R.id.button_delete_account);
        mSwitchSlide.setOnCheckedChangeListener(this);
        mCheckBoxRememberAccount.setOnCheckedChangeListener(this);
        DeleteAccount.setOnClickListener(this);

    }

    private void init() {
        puls = (PulsatorLayout) findViewById(R.id.pulse);
        puls.start();
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main2);
        parentCenterX = relativeLayout.getWidth() / 2;
        parentCenterY = relativeLayout.getHeight() / 2;
        viewPager = (ViewPager) findViewById(R.id.viewpaferSlider);
        viewPager.setOnClickListener(this);
        imageViewLeft = (ImageView) findViewById(R.id.left_arrow);
        imageViewRight = (ImageView) findViewById(R.id.right_arrow);
        imageViewadd = (ImageView) findViewById(R.id.diary_any_settings);
        imageViewadd.setOnClickListener(this);
        imageViewUsernameChange = (ImageView) findViewById(R.id.image_username_change);
        imageViewUsernameChange.setOnClickListener(this);
        textViewUsername = (TextView) findViewById(R.id.username_getting);
        textViewDaysAdded = (TextView) findViewById(R.id.textview_day_add);
        cardView = (CardView) findViewById(R.id.about_you_cardview);
        cardView.setBackgroundColor(Color.TRANSPARENT);

        //viewAlert=(LinearLayout) getLayoutInflater().inflate(R.layout.alert_change_username,null);

        realm.beginTransaction();
        RealmResults<RegistrationHumansModel> getResults = realm.where(RegistrationHumansModel.class).findAll();
        textViewUsername.setText("Welcome " + getResults.get(i).getUserNameUser());
        realm.commitTransaction();


        textViewDaysAdded.setText("Days You Added: Հլը որ ոչ մի բան(realm)");


        CircleMenuRealization();
        leftArrow();
        rightArrow();
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.setItems(getFragmentList());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageViewLeft.setVisibility(View.GONE);
                imageViewRight.setVisibility(View.GONE);
//            Log.d("Position","position x="+imageViewadd.getX()+" position y="+imageViewadd.getY());
                return false;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageViewLeft.setVisibility((position == 0) ? View.GONE : View.VISIBLE);
                imageViewRight.setVisibility((position == fragmentAdapter.getCount() - 1) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private List<FragmentModel> getFragmentList() {
        List<FragmentModel> mfragmentModels = new ArrayList<>();
        mfragmentModels.add(new FragmentModel(R.drawable.create_moment));
        mfragmentModels.add(new FragmentModel(R.drawable.extrim_moment));
        mfragmentModels.add(new FragmentModel(R.drawable.friendship));
        mfragmentModels.add(new FragmentModel(R.drawable.place_creative));
        mfragmentModels.add(new FragmentModel(R.drawable.love));
        mfragmentModels.add(new FragmentModel(R.drawable.celebrite_man));
        mfragmentModels.add(new FragmentModel(R.drawable.alone_photo));
        mfragmentModels.add(new FragmentModel(R.drawable.share_somethingp));
        mfragmentModels.add(new FragmentModel(R.drawable.read_it));

        return mfragmentModels;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diary_any_settings:
                openMenuCircle();

                break;
            case R.id.image_username_change:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                viewAlert = (LinearLayout) getLayoutInflater().inflate(R.layout.alert_change_username, null);
                usernameChanging = (EditText) viewAlert.findViewById(R.id.edittext_username_change);
                alertDialog.setView(viewAlert);

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        RealmResults<RegistrationHumansModel> realmForUsername = realm.where(RegistrationHumansModel.class).findAll();
                        //realmForUsername.get(getIntent().getIntExtra(MemoriesDayActivity.KEY_FOR_INFORMATION,0)).getUserNameUser()
                        if (!usernameChanging.getText().toString().isEmpty()) {

                            realmForUsername.get(i).setUserNameUser(usernameChanging.getText().toString());
                        }
                        textViewUsername.setText("Welcome " + realmForUsername.get(i).getUserNameUser());
                        realm.commitTransaction();

                    }
                });
                alertDialog.show();
                break;
        }
    }

    private void leftArrow() {
//        animation1=ObjectAnimator.ofFloat(imageViewLeft,"translationX",-50);
        animation1 = ObjectAnimator.ofFloat(imageViewLeft, "alpha", 0, 1);
        animation1.setRepeatCount(ValueAnimator.INFINITE);
        animation1.setDuration(3000);
        AnimatorSet set1 = new AnimatorSet();
        set1.play(animation1);
        set1.start();
    }

    private void rightArrow() {
//        animation2=ObjectAnimator.ofFloat(imageViewRight,"translationX",50);
        animation2 = ObjectAnimator.ofFloat(imageViewRight, "alpha", 0, 1);
        animation2.setInterpolator(new FastOutLinearInInterpolator());
        animation2.setRepeatCount(ValueAnimator.INFINITE);
        animation2.setDuration(3000);
        AnimatorSet set2 = new AnimatorSet();
        set2.playSequentially(animation2);

        set2.start();

    }

    private void CircleMenuRealization() {
        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#664cff"), R.drawable.ic_add, R.drawable.cancel_icon);
        circleMenu.addSubMenu(Color.parseColor("#1bffdc"), R.drawable.add_diary_day)
                //.addSubMenu(Color.BLUE,R.drawable.facebook_icon)
                .addSubMenu(Color.LTGRAY, R.drawable.setting_icon)
                .addSubMenu(Color.RED, R.drawable.ic_close)
                .addSubMenu(Color.parseColor("#ffbb99"), R.drawable.icon_about_me)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(final int index) {
                        switch (index) {
                            case 0:
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Write your new Activity Intent
                                        intent = new Intent(Main2Activity.this, MemoriesDayActivity.class);
                                        intent.putExtra(MainActivity.KEY_FOR_INFORMATION, i);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 600);
                                break;
                            case 1:
                                alertDialogForSettings();
                                break;
                            case 2:
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent = new Intent(Main2Activity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 600);
                                break;
                            case 3:
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog alertAbout = new AlertDialog.Builder(Main2Activity.this).create();
                                        viewAboutInfo = (RelativeLayout) getLayoutInflater().inflate(R.layout.alert_about_me, null);
                                        alertAbout.setView(viewAboutInfo);
                                        alertAbout.show();
                                    }
                                }, 600);
                                break;
                        }
                    }
                });
        circleMenu.setVisibility(View.GONE);


        circleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {
            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
                closeMenuCircle();
            }
        });
    }


    private void openMenuCircle() {
        puls.setVisibility(View.GONE);
        positionImageAddY = imageViewadd.getY();
        positionImageAddX = imageViewadd.getX();
        translationY = ObjectAnimator.ofFloat(imageViewadd, "y", screenHeight / 2 - imageViewadd.getHeight() / 2 - 10);
        translationX = ObjectAnimator.ofFloat(imageViewadd, "X", screenWidth / 2 - imageViewadd.getWidth() / 2);
        Log.d("xy", "y=" + (screenHeight / 2 - imageViewadd.getHeight() / 2 - 10) + " x=" + (screenWidth / 2 - imageViewadd.getWidth() / 2));
        AnimatorSet setAnim = new AnimatorSet();
        setAnim.playTogether(translationX, translationY);
        setAnim.setDuration(700);
        setAnim.start();


        circleMenu.setVisibility(View.VISIBLE);
        menuCircleOpacity = ObjectAnimator.ofFloat(circleMenu, "alpha", 0, 1);
        AnimatorSet setOpacity = new AnimatorSet();
        setOpacity.play(menuCircleOpacity);
        setOpacity.setDuration(900);
        setOpacity.start();
        setOpacity.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circleMenu.openMenu();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    private void closeMenuCircle() {
        translationY = ObjectAnimator.ofFloat(imageViewadd, "y", positionImageAddY);
        translationX = ObjectAnimator.ofFloat(imageViewadd, "X", positionImageAddX);
        AnimatorSet setAnim = new AnimatorSet();
        setAnim.playTogether(translationX, translationY);
        setAnim.setDuration(700);
        setAnim.start();

        menuCircleOpacity = ObjectAnimator.ofFloat(circleMenu, "alpha", 1, 0);
        AnimatorSet setOpacity = new AnimatorSet();
        setOpacity.play(menuCircleOpacity);
        setOpacity.setDuration(700);
        setOpacity.start();
        setOpacity.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circleMenu.setVisibility(View.GONE);
                puls.setVisibility(View.VISIBLE);
                imageViewadd.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_sliding_images:
                if (mSwitchSlide.isChecked()) {
                    timer.cancel();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new AutoSlide(), 6000, 6000);
                } else {
                    timer.cancel();
                }
                break;
            case R.id.checkbox_remember_account:
                if (isChecked) {
                    Toast.makeText(this, "You can remove password remembering" +
                            " by entering here and uncheck", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    void saveAccountNumber(int index) {
        sharedPreferences = getSharedPreferences("your_pref", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("INDEXREALM", index);
        edit.apply();
    }

    void saveSlideAccountNumber(int Autoslide) {
        sharedPreferences = getSharedPreferences("auto_slide", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("AUTOSLIDE" + i, Autoslide);
        edit.apply();
    }

    void alertDialogForSettings() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                AlertDialog alertDialForSettings = new AlertDialog.Builder(Main2Activity.this).create();
                viewSetting = (RelativeLayout) getLayoutInflater().inflate(R.layout.settings, null);

                mSwitchSlide = (Switch) viewSetting.findViewById(R.id.switch_sliding_images);
                mCheckBoxRememberAccount = (CheckBox) viewSetting.findViewById(R.id.checkbox_remember_account);
                DeleteAccount = (Button) viewSetting.findViewById(R.id.button_delete_account);
                mSwitchSlide.setOnCheckedChangeListener(Main2Activity.this);
                mCheckBoxRememberAccount.setOnCheckedChangeListener(Main2Activity.this);
                DeleteAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                realm.beginTransaction();
//                                RealmResults<RegistrationHumansModel> realmResults = realm.where(RegistrationHumansModel.class).findAll();
//                                realmResults.get(i).removeFromRealm();
//                                intent = new Intent(Main2Activity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                                realm.commitTransaction();
                            }
                        }, 1000);
                    }
                });


                alertDialForSettings.setView(viewSetting);
                alertDialForSettings.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mCheckBoxRememberAccount.isChecked()) {
                            REMEMBER_ACCOUNT = i;
                        } else {
                            REMEMBER_ACCOUNT = -1;
                        }
                        if (mSwitchSlide.isChecked()) {
                            AUTOSLIDE = i;
                        } else {
                            AUTOSLIDE = -1;
                        }

                        saveAccountNumber(REMEMBER_ACCOUNT);
                        saveSlideAccountNumber(AUTOSLIDE);
                    }
                });
                alertDialForSettings.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mSwitchSlide.setChecked(true);
                        mCheckBoxRememberAccount.setChecked(false);
                    }
                });
                alertDialForSettings.show();
            }
        }, 600);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public class AutoSlide extends TimerTask {

        @Override
        public void run() {
            Main2Activity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(ViewPagerCurrentTime(viewPager.getCurrentItem()));


                }
            });
        }

        int ViewPagerCurrentTime(int getCur) {
            if (getCur != fragmentAdapter.getCount() - 1) {
                return getCur + 1;
            } else {
                return 0;
            }
        }
    }


}
