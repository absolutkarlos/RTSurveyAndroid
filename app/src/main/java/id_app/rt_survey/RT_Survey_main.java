package id_app.rt_survey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import id_app.rt_survey.Utilities.Item;

public class RT_Survey_main extends AppCompatActivity {

    private Toolbar app_bar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Boolean SESSION_ACTIVE;
    private String MAIL;
    private String PASSWORD;
    private String TOKEN;
    private String USERID;
    private static final String DEFAULT="N/A";
    private List<Item> data_firts=null;

    private SharedPreferences SP;
    private SharedPreferences.Editor ED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SP=getSharedPreferences("USER", Context.MODE_PRIVATE);
        SESSION_ACTIVE=SP.getBoolean("SESSION_ACTIVE",false);


        if(!SESSION_ACTIVE){

            deleteCache(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(),login_activity.class);
            startActivity(intent);
            finish();

        }else{


            USERID=SP.getString("USERID",null);
            Toast.makeText(RT_Survey_main.this,USERID, Toast.LENGTH_SHORT).show();

            MAIL=SP.getString("USERNAME",DEFAULT);
            PASSWORD=SP.getString("PASSWORD",DEFAULT);
            TOKEN=SP.getString("TOKEN",DEFAULT);;


            try {

                File file= new File(getCacheDir(),"SURVEY_CACHE");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fis);
                data_firts = (List<Item>) is.readObject();

                is.close();
                fis.close();

            } catch (FileNotFoundException e) {
                data_firts=null;
                e.printStackTrace();
            } catch (OptionalDataException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

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


        Bundle data = new Bundle();
        data.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) data_firts);
        one.setArguments(data);

        transaction.add(R.id.sub_frame,one,"main fragment");
        transaction.commit();

        /*
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this,"buscando intento",Toast.LENGTH_SHORT).show();
            //doMySearch(query);
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setupDrawer(NavigationView mNv){

        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                switch (item.getItemId()){

                    case R.id.close_user:

                        File file= new File(getCacheDir(),"SURVEY_CACHE");
                        file.delete();

                        deleteCache(getApplicationContext());

                        ED=SP.edit();
                        ED.clear();
                        ED.commit();

                        finish();

                        Intent intent = new Intent(getApplicationContext(),login_activity.class);
                        startActivity(intent);

                        break;

                    case R.id.order_create:

                        //Intent to create a new activity;

                        break;


                }
                return true;
            }
        });

        View headerLayout = navigationView.getHeaderView(0);
        TextView user_type= (TextView) headerLayout.findViewById(R.id.user_type);
        user_type.setText("Tipo de usuario "+USERID);

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


}
