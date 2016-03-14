package id_app.rt_survey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class RT_Survey_main extends AppCompatActivity {

    private Toolbar app_bar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Boolean SESSION_ACTIVE;
    private String MAIL;
    private String PASSWORD;
    private String TOKEN;
    private static final String DEFAULT="N/A";

    private SharedPreferences SP;
    private SharedPreferences.Editor ED;

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

            //LINEA DE TESTEO
            Toast.makeText(this,SP.getString("USERID",DEFAULT)+" "+SP.getString("TOKEN_TYPE",DEFAULT),Toast.LENGTH_SHORT).show();

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

        //INICIALIZANDO EL PRIMER FRAGMENTO DE PRUEBA
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
                    case R.id.texto_1:
                        Toast.makeText(RT_Survey_main.this,"UNO",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.texto_2:
                        Toast.makeText(RT_Survey_main.this,"DOS",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.texto_3:
                        Toast.makeText(RT_Survey_main.this,"TRES",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.texto_4:
                        Toast.makeText(RT_Survey_main.this,"CUATRO",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.close_user:

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Go:
                //LOGICA PARA CAMBIAR A LA SEGUNDA VISTA...
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Survey_two one = new Survey_two();
                transaction.replace(R.id.sub_frame,one,"F2");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.Update:
                //LOGICA PARA ACTUALIZAR DATOS
                break;
            case R.id.Search:
                //LOGICA PARA ACTUALIZAR DATOS
                FragmentManager fm = getSupportFragmentManager();
                Dialog_Fragment DF = new Dialog_Fragment();
                DF.show(fm, "DF");


                break;
            default:
                break;

        }

        return false;
    }

}
