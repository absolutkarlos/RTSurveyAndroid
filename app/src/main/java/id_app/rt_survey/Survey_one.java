package id_app.rt_survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id_app.rt_survey.Api.AppController;
import id_app.rt_survey.Api.JAR;
import id_app.rt_survey.Api.URL;
import id_app.rt_survey.Utilities.Item;
import id_app.rt_survey.Utilities.ItemAdapter;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class Survey_one extends Fragment{


    private RecyclerView recycle_view;
    private JAR mJOR;
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

        SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        USERID=SP.getString("USERID",null);
        TOKEN=SP.getString("TOKEN",null);


        return view;
    }

    //CONSTRUCTOR DE VALORES DE PRUEBA ANDROID
    public static List<Item> getData(){

        List<Item> data= new ArrayList<>();

        /*

        for (int i=0;i<=30;i++){

            Item item= new Item("color"+String.valueOf(i),"name"+String.valueOf(i),"locate"+String.valueOf(i),"date"+String.valueOf(i));
            data.add(item);
        }
        */

        return data;

    }


    public static List<Item> getData2(){

        List<Item> data= new ArrayList<>();
        for (int i=0;i<=3;i++){

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

                listRequest();

                break;

            case R.id.Search:


                break;
            default:
                break;
        }

        return true;
    }


    public void listRequest(){

        //Toast.makeText(getActivity(),"Survey_ONE",Toast.LENGTH_SHORT).show();

        JSONObject LIST = new JSONObject();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        try {
            LIST.put("TOKEN",SP.getString("TOKEN",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ADVERTENCIA
        RetryPolicy policy = new DefaultRetryPolicy(0,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);



        mJOR= new JAR(URL.LIST.getURL() + USERID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pDialog.hide();
                List<Item> data= new ArrayList<>();

                String name="wrong";
                String colar="wrong";
                String locate="wrong";
                String date="wrong";

                JSONObject object;

                for(int i=0;i<=response.length(); i++){

                    try {
                        object= response.getJSONObject(i);
                        name=object.getJSONObject("site").getJSONObject("client").getString("legalName");
                        colar=object.getJSONObject("orderStatus").getString("color");
                        locate=object.getString("address");
                        date=object.getJSONObject("client").getString("createAt");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Item item= new Item(colar,"prueba","prueba",name);
                    data.add(item);
                }

                itemAdapter.swapItems(data);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();

                String token = null;
                token = TOKEN;

                if(token!=null){
                    Log.e("epalex",token);
                    headers.put("Authorization", "bearer"+" "+token);
                    headers.put("Content-Type","application/json");
                }

                return headers;
            }
        };


        try {
            mJOR.getHeaders();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        mJOR.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(mJOR,"LIST");

    }

}
