package id_app.rt_survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import id_app.rt_survey.Api.JOR;

public class RT_Survey_main extends AppCompatActivity {

    private Toolbar app_bar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Boolean Aux=false;
    private JSONObject responseAUX;
    private Boolean SESSION_ACTIVE;
    private String MAIL;
    private String PASSWORD;
    private String TOKEN;
    private String USERID;
    private static final String DEFAULT="N/A";

    private SharedPreferences SP;
    private SharedPreferences.Editor ED;

    private ProgressDialog pDialog;
    private JOR mJOR;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SP=getSharedPreferences("USER", Context.MODE_PRIVATE);
        SESSION_ACTIVE=SP.getBoolean("SESSION_ACTIVE",false);


        if(!SESSION_ACTIVE){

            Intent intent = new Intent(this,login_activity.class);
            startActivity(intent);
            finish();

        }else{

            USERID=SP.getString("USERID",null);
            MAIL=SP.getString("USERNAME",DEFAULT);
            PASSWORD=SP.getString("PASSWORD",DEFAULT);
            TOKEN=SP.getString("TOKEN",DEFAULT);;

        }

        setContentView(R.layout.rt_survey);

        app_bar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        setupDrawer(navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                app_bar,
                R.string.open,
                R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Survey_one one = new Survey_one();
        transaction.add(R.id.sub_frame,one,"main fragment");
        transaction.commit();

    }

    private void setupDrawer(NavigationView mNv){

        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                switch (item.getItemId()){

                    case R.id.close_user:

                        getBaseContext().deleteFile("SURVEY_CACHE");
                        ED=SP.edit();
                        ED.clear();
                        ED.commit();
                        finish();

                        break;
                    case R.id.Update_Basic:

                        break;

                }
                return true;
            }
        });

    }



}
