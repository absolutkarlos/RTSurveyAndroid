package id_app.rt_survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id_app.rt_survey.Api.AppController;
import id_app.rt_survey.Api.JOR;
import id_app.rt_survey.Api.URL;

public class login_activity extends AppCompatActivity {


    private JOR mJOR;
    private EditText login_mail;
    private EditText login_password;
    private Button send;
    private String mail;
    private String password;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login_mail=(EditText)findViewById(R.id.mail);
        login_password=(EditText)findViewById(R.id.password);
        send=(Button)findViewById(R.id.login);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mail=login_mail.getText().toString();
                password=login_password.getText().toString();
                Survey();

            }
        });

    }

    private void Survey(){

        JSONObject LOGIN = new JSONObject();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        try {
            LOGIN.put("grant_type","password");
            LOGIN.put("UserName",mail);
            LOGIN.put("Password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mJOR=new JOR(URL.LOGIN.getRequestType(), URL.LOGIN.getURL(), LOGIN, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences SP=getSharedPreferences("USER", Context.MODE_PRIVATE);
                SharedPreferences.Editor ED=SP.edit();

                try {

                    ED.putBoolean("SESSION_ACTIVE",true);
                    ED.putString("TOKEN",response.getString("access_token"));
                    ED.putString("TOKEN_TYPE",response.getString("token_type"));
                    ED.putString("USER_ID", String.valueOf(response.getInt("userid")));
                    ED.putString("USERNAME",mail);
                    ED.putString("PASSWORD",password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ED.commit();
                Intent intent = new Intent(login_activity.this,RT_Survey_main.class);
                startActivity(intent);
                finish();
                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(login_activity.this,error.toString(),Toast.LENGTH_SHORT).show();
                pDialog.hide();

            }

        });



        AppController.getInstance().addToRequestQueue(mJOR,"LOGIN");


        /*

        Intent intent = new Intent(this,RT_Survey_main.class);

        SharedPreferences SP=getSharedPreferences("USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor ED=SP.edit();

        ED.putBoolean("SESSION_ACTIVE",true);
        ED.putString("TOKEN","FROM SERVER");
        ED.putString("MAIL",mail);
        ED.putString("PASSWORD",password);
        ED.commit();

        finish();
        startActivity(intent);

        */


    }


}
