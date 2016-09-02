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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
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
    private ItemAdapter itemAdapter=null;
    private ProgressDialog pDialog;
    private String USERID;
    private String TOKEN;
    private SharedPreferences SP;
    private List<Item> data_firts=null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.survey_one,container,false);
        recycle_view=(RecyclerView) view.findViewById(R.id.recycler_view);

        setHasOptionsMenu(true);

        SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        USERID=SP.getString("USER_ID",null);
        TOKEN=SP.getString("TOKEN",null);

        Toast.makeText(getActivity(),USERID,Toast.LENGTH_SHORT).show();

        FileInputStream fis = null;

        try {
            fis = getActivity().openFileInput("SURVEY_CACHE");
            ObjectInputStream is = new ObjectInputStream(fis);
            data_firts = (List<Item>) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(data_firts==null){
            listRequest();
        }else{
            itemAdapter=new ItemAdapter(data_firts,(AppCompatActivity)getActivity());
            recycle_view.setAdapter(itemAdapter);
            recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

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

            case R.id.Update:
                listRequest();
                break;

            default:
                break;
        }

        return true;
    }

    public void listRequest(){

        JSONObject LIST = new JSONObject();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando...");
        pDialog.show();

        try {
            LIST.put("TOKEN",SP.getString("TOKEN",null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mJOR= new JAR(URL.LIST.getURL() + USERID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pDialog.hide();

                String name="wrong";
                String colar="wrong";
                String locate="wrong";
                String date="wrong";
                String order_name="wrong";

                JSONObject object;
                Item item=null;
                List<Item> data_igni= new ArrayList<>();

                for(int i=0;i<=response.length(); i++){

                    try {

                        object= response.getJSONObject(i);

                        name=object.getJSONObject("site").getJSONObject("client").getString("legalName");
                        colar=object.getJSONObject("orderStatus").getString("color");
                        locate=object.getJSONObject("site").getString("address");

                        date=object.getJSONObject("site").getJSONObject("client").getString("createAt");
                        order_name=object.getString("orderNumber");

                        Log.e("VER OBJETO",object.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("VER",String.valueOf(i));
                    Log.e("VER",name);
                    Log.e("VER",colar);
                    Log.e("VER",locate);
                    Log.e("VER",date);
                    Log.e("VER",order_name);

                    item= new Item(colar,locate,date,name,order_name);
                    data_igni.add(item);
                }

                FileOutputStream fos = null;

                try {

                    fos = getActivity().openFileOutput("SURVEY_CACHE", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(data_igni);
                    os.close();
                    fos.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(data_firts==null){
                    itemAdapter=new ItemAdapter(data_igni,(AppCompatActivity)getActivity());
                    recycle_view.setAdapter(itemAdapter);
                    recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                }else{
                    itemAdapter.swapItems(data_igni);
                }

            }
        },new Response.ErrorListener() {
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

        AppController.getInstance().addToRequestQueue(mJOR,"LIST");

    }





}
