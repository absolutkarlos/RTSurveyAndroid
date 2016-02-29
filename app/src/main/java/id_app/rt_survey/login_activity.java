package id_app.rt_survey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login_activity extends AppCompatActivity {


    private EditText login_mail;
    private EditText login_password;
    private Button send;
    private String mail;
    private String password;

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
                inicioSurvey();

            }
        });

    }

    private void inicioSurvey(){

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

    }

    private static boolean isEmailValid(String email) {

        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
