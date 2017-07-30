package com.me.reactiveapp.activty;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuboid.cuboidcirclebutton.CuboidButton;
import com.me.reactiveapp.R;
import com.me.reactiveapp.adapter.DaysRecyclerViewAdapter;
import com.me.reactiveapp.model.DaysRememberModel;
import com.me.reactiveapp.model.RealmDaysRememberModel;
import com.me.reactiveapp.model.RegistrationHumansModel;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;


public class MemoriesDayActivity extends AppCompatActivity implements View.OnClickListener
{ //implements View.OnClickListener {
    //Work

    //Add Adapter ArrayList replace RealmResult
    //Work with Filter to Replace ArrayList

    //New Model Create with equal Fields in RememberDayModel
    //If You have some Questions Open with Android studio ExampleRealm


    public static final String IFSEARCHING = "FALSE";
    RecyclerView recyclerView;
    private DaysRecyclerViewAdapter mAdapter;
    CuboidButton BtnAddDay;
    View view;
    SharedPreferences sharedPreferencesLogin;
    ArrayList<DaysRememberModel> mArrayList;
    Calendar calendar;
    EditText editAddTitle;
    CheckBox checkBox;
    Realm realm;
    int year, month, dayMonth;
    int j;
    private TextView mTextMessage;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbara);
        setSupportActionBar(toolbar);


        BtnAddDay = (CuboidButton) findViewById(R.id.button_add_day);
        BtnAddDay.setOnClickListener(this);
        view = getLayoutInflater().inflate(R.layout.alert_add_day, null);


        initRealm();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayMonth = calendar.get(Calendar.DAY_OF_MONTH);
        initViews();
//
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.naviga);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        j=getIntent().getIntExtra(MainActivity.KEY_FOR_INFORMATION, 0);
    }

    private void initRealm() {

        realm = Realm.getDefaultInstance();
    }

    private void initViews() {
        recyclerView=(RecyclerView) findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mArrayList=new ArrayList<>();

        realm.beginTransaction();
        String userLogin = getSharedPrefLogin();//TODO get form shared preferences
        RealmResults<RealmDaysRememberModel> realmResultsRecyclerView=realm.where(RealmDaysRememberModel.class)
                .equalTo("user",userLogin).findAll();
        // .findAll();
        if(!realmResultsRecyclerView.isEmpty()) {
            for (int i = 0; i <realmResultsRecyclerView.size() ; i++) {
                mArrayList.add(new DaysRememberModel(realmResultsRecyclerView.get(i).getTitle()
                        ,realmResultsRecyclerView.get(i).getDay(),realmResultsRecyclerView.get(i).getWithImage()));
            }

        }
        mAdapter = new DaysRecyclerViewAdapter(mArrayList);
        recyclerView.setAdapter(mAdapter);
        realm.commitTransaction();
        for (int i = 0; i <mArrayList.size() ; i++) {
            Log.d("mArrayList",mArrayList.get(i).getTitle()+"");
        }

    }

//    ArrayList<DaysRememberModel> listarra() {
//
//        mArrayList.add(new DaysRememberModel("Suren","Karen","Raxmud"));
//        mArrayList.add(new DaysRememberModel("Tigran","Meline","Raxmud"));
//        mArrayList.add(new DaysRememberModel("1","1","12"));
//        mArrayList.add(new DaysRememberModel("1","1","13"));
//        mArrayList.add(new DaysRememberModel("Meline","8","9"));
//        mArrayList.add(new DaysRememberModel("Arsen","levon","narek"));
//        return mArrayList;
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (R.id.button_add_day == v.getId()) {
            final AlertDialog alertDialogTitle = new AlertDialog.Builder(this).create();
            editAddTitle = new EditText(MemoriesDayActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            editAddTitle.setLayoutParams(lp);
            editAddTitle.setHint("Add your title.");
            alertDialogTitle.setTitle("Set Your Day Title");
            alertDialogTitle.setView(editAddTitle);
            alertDialogTitle.setButton(DialogInterface.BUTTON_POSITIVE, "Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(MemoriesDayActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                            AlertDialog alertCheckBox = new AlertDialog.Builder(MemoriesDayActivity.this).create();
                            alertCheckBox.setTitle("Add image on your day");
                            checkBox = new CheckBox(MemoriesDayActivity.this);
                            LinearLayout.LayoutParams LPcheckbox = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            checkBox.setLayoutParams(LPcheckbox);
                            checkBox.setText("Add the image in your day?");
                            alertCheckBox.setView(checkBox);
                            alertCheckBox.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String WithImage;
                                    if (checkBox.isChecked()) {
                                        WithImage="This day with image";
                                    } else {
                                        WithImage="This day without image";
                                    }
                                    mArrayList.add(new DaysRememberModel(getEditAddTitle(),dayOfMonth+"."+month+"."+year,WithImage));
                                    mAdapter.notifyDataSetChanged();
                                    realm.beginTransaction();
                                    //RealmDaysRememberModel addInformationRealm = realm.createObject(RealmDaysRememberModel.class);
                                    RealmDaysRememberModel addInformationRealm = realm.createObject(RealmDaysRememberModel.class);
                                    String calend=dayOfMonth + "." + month + "." + year;


                                    addInformationRealm.setTitle(getEditAddTitle());
                                    addInformationRealm.setDay(calend);
                                    addInformationRealm.setWithImage(WithImage);
                                    //String userLogin = getSharedPrefLogin();//todo GET FROM shared preferences
                                    //addInformationRealm.setUser(userLogin);

                                    RealmResults<RealmDaysRememberModel> realmDaysRememberModels = realm.where(RealmDaysRememberModel.class).findAll();
                                    for (int i = 0; i < realmDaysRememberModels.size(); i++) {
                                        Log.d("realmResult", "Title(" + i + ")=" + realmDaysRememberModels.get(i).getTitle() + "\n"
                                                + "Day(" + i + ")=" + realmDaysRememberModels.get(i).getDay() + "\n"
                                                + "WithImage(" + i + ")=" + realmDaysRememberModels.get(i).getWithImage());
                                    }
                                    realm.commitTransaction();
                                }
                            });
                            alertCheckBox.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    realm.beginTransaction();
                                    realm.clear(RealmDaysRememberModel.class);
                                    realm.commitTransaction();
                                }
                            });
                            alertCheckBox.show();

                        }
                    }, year, month, dayMonth);
                    datePickerDialog.show();
                }
            });
            alertDialogTitle.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogTitle.show();

        }
    }

    private String getEditAddTitle() {
        return editAddTitle.getText().toString();
    }

    private String getSharedPrefLogin() {
        sharedPreferencesLogin=getPreferences(MODE_PRIVATE);
        return sharedPreferencesLogin.getString(MainActivity.LOGIN_USER,"");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }
}




//    private void loadJSON() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RequestInterface request = retrofit.create(RequestInterface.class);
//        Call<JSONResponse> call = request.getJSON();
//        call.enqueue(new Callback<JSONResponse>() {
//            @Override
//            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
//
//                JSONResponse jsonResponse = response.body();
//                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
//                //mAdapter = new RecyclerAdapter(mArrayList);
//                for (int i = 0; i < mArrayList.size(); i++) {
//                    Log.d("mArrayList", mArrayList.get(i).getTitle() + "");
//                }
////                recyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse> call, Throwable t) {
//                Log.d("Error", t.getMessage());
//            }
//        });
//    }