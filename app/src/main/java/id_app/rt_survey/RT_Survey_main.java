package id_app.rt_survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import id_app.rt_survey.Api.AppController;
import id_app.rt_survey.Api.JOR;
import id_app.rt_survey.Api.URL;

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

    JSONResponse mJsonResponse;


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        try
        {
            mJsonResponse = (JSONResponse) getApplicationContext();
        }
        catch (ClassCastException e)
        {
            //throw new ClassCastException(getApplicationContext().toString()+" must implement ");
        }



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

            //LINEA DE TESTEO
            Toast.makeText(this,SP.getString("USERID",DEFAULT)+" "+SP.getString("TOKEN_TYPE",DEFAULT),Toast.LENGTH_SHORT).show();

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

                Toast.makeText(this,TOKEN,Toast.LENGTH_SHORT).show();
                /*
                //LOGICA PARA CAMBIAR A LA SEGUNDA VISTA...
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Survey_two one = new Survey_two();
                transaction.replace(R.id.sub_frame,one,"F2");
                transaction.addToBackStack(null);
                transaction.commit();
                */

                break;
            case R.id.Update:
                //LOGICA PARA ACTUALIZAR DATOS

                JSONObject LIST = new JSONObject();

                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                try {
                    LIST.put("TOKEN",SP.getString("TOKEN",null));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //ADVERTENCIA
                RetryPolicy policy = new DefaultRetryPolicy(0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                mJOR=new JOR(URL.LIST.getRequestType(), URL.LIST.getURL()+USERID, LIST, new Response.Listener<JSONObject>()  {

                    @Override
                    public void onResponse(JSONObject response) {

                        responseAUX=response;
                        pDialog.hide();
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pDialog.hide();
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

                    }

                } );

                mJOR.setRetryPolicy(policy);
                AppController.getInstance().addToRequestQueue(mJOR,"LIST");

                break;

            case R.id.Search:

                //LOGICA PARA ACTUALIZAR DATOS
                /*
                FragmentManager fm = getSupportFragmentManager();
                Dialog_Fragment DF = new Dialog_Fragment();
                DF.show(fm, "DF");
                */

                if(responseAUX!=null){
                    mJsonResponse.JSONList(responseAUX);
                }else{
                    Toast.makeText(this,"nulo JSON", Toast.LENGTH_SHORT).show();
                }



                break;
            default:
                break;

        }

        return false;
    }

    public interface JSONResponse{
        void JSONList(JSONObject jsonObject);
    }

}
