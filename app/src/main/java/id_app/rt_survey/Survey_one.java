package id_app.rt_survey;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    private JAR mJAR;
    private View view;
    private ItemAdapter itemAdapter=null;
    private ProgressDialog pDialog;
    private String USERID;
    private String TOKEN;
    private SharedPreferences SP;
    private List<Item> data_firts=null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        //Bundle extras = getActivity().getIntent().getExtras();
        data_firts = extras.getParcelableArrayList("list");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.survey_one,container,false);
        recycle_view=(RecyclerView) view.findViewById(R.id.recycler_view);

        setHasOptionsMenu(true);

        SP = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        USERID=SP.getString("USERID",null);
        TOKEN=SP.getString("TOKEN",null);

        //TOAST MALDITO
        Toast.makeText(getActivity(),USERID,Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Toast.makeText(getActivity(),data_firts.get(1).siteName,Toast.LENGTH_SHORT).show();

        if(data_firts==null){
            listRequest();
        }else{
            itemAdapter=new ItemAdapter(data_firts,(AppCompatActivity)getActivity());
            recycle_view.setAdapter(itemAdapter);
            recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }


    public void searchIdNumbre(String query_text){

        List<Item> data_second=new ArrayList<>();
        String search_uppercarse=query_text.toUpperCase();

        if(query_text.length()==0){

            itemAdapter.swapItems(data_firts);

        }else{

            for(int i=0;i<=data_firts.size()-1; i++){

                if(data_firts.get(i).orderNumber.contains(search_uppercarse)){
                    Item mItem=data_firts.get(i);
                    data_second.add(mItem);
                }

            }
            itemAdapter.swapItems(data_second);
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the options menu from XML
        //MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.Search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemAdapter.swapItems(data_firts);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchIdNumbre(newText);
                return false;
            }
        });

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getActivity().getApplicationContext(),RT_Survey_main.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        //Text Color Custom AutoCompleteEditText
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHint("Ingrese numero de orden");
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);

        /*
        SearchView searchView = (SearchView) menu.findItem(R.id.Search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        */

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

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

        File file= new File(getActivity().getCacheDir(),"SURVEY_CACHE");
        file.delete();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(false);
        pDialog.show();

        mJAR= new JAR(URL.LIST.getURL() + USERID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pDialog.hide();

                String id="wrong";
                String orderNumber="wrong";
                String createAt="wrong";
                String idOrderStatus="wrong";
                String nameOrderStatus="wrong";
                String siteName="wrong";
                String clientName="wrong";

                JSONObject object;
                Item item=null;
                List<Item> data_igni= new ArrayList<>();

                for(int i=0;i<=response.length(); i++){

                    try {

                        object= response.getJSONObject(i);

                        id=object.getString("id");
                        orderNumber=object.getString("orderNumber");
                        createAt=object.getString("createAt");
                        idOrderStatus=object.getString("idOrderStatus");
                        nameOrderStatus=object.getString("nameOrderStatus");
                        siteName=object.getString("siteName");
                        clientName=object.getString("clientName");

                        Log.e("VER OBJETO",object.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    item= new Item(id,orderNumber,createAt,idOrderStatus,nameOrderStatus,siteName,clientName);
                    data_igni.add(item);
                }



                try {

                    File file= new File(getActivity().getCacheDir(),"SURVEY_CACHE");
                    FileOutputStream fos = new FileOutputStream(file);
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

        AppController.getInstance().addToRequestQueue(mJAR,"LIST");

    }

}
