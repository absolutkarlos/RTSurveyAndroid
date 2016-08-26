package id_app.rt_survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id_app.rt_survey.Api.AppController;
import id_app.rt_survey.Api.JOR;
import id_app.rt_survey.Api.URL;
import id_app.rt_survey.Utilities.Item;
import id_app.rt_survey.Utilities.ItemAdapter;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class Survey_one extends Fragment{


    private RecyclerView recycle_view;
    private JOR mJOR;
    private View view;
    private ItemAdapter itemAdapter;
    private ProgressDialog pDialog;
    private String USERID;
    private String TOKEN;
    private SharedPreferences SP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.survey_one,container,false);
        recycle_view=(RecyclerView) view.findViewById(R.id.recycler_view);
        itemAdapter=new ItemAdapter(getData(),(AppCompatActivity)getActivity());
        recycle_view.setAdapter(itemAdapter);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);

        SharedPreferences SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        USERID=SP.getString("USERID",null);
        TOKEN=SP.getString("TOKEN",null);


        return view;
    }

    //CONSTRUCTOR DE VALORES DE PRUEBA ANDROID
    public static List<Item> getData(){

        List<Item> data= new ArrayList<>();
        for (int i=0;i<=30;i++){

            Item item= new Item("color"+String.valueOf(i),"name"+String.valueOf(i),"locate"+String.valueOf(i),"date"+String.valueOf(i));
            data.add(item);
        }
        return data;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Go:
                Toast.makeText(getActivity(),TOKEN,Toast.LENGTH_SHORT).show();
                /*
                //LOGICA PARA CAMBIAR A LA SEGUNDA VISTA...
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Survey_two one = new Survey_two();
                transaction.replace(R.id.sub_frame,one,"F2");
                transaction.addToBackStack(null);
                transaction.commit();
                */
                break;

            case R.id.Update:



                break;
            default:
                break;
        }

        return true;
    }
}
