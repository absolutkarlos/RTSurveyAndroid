package id_app.rt_survey.Api;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Carlos_Lopez on 3/12/16.
 */
public class JOR extends JsonObjectRequest {

    private JSONObject mJSONR;
    private String URL;

    public JOR(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.mJSONR=jsonRequest;
        this.URL=url;
    }

    public JOR(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        this.mJSONR=jsonRequest;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();

        String token = null;
        try {
            token = mJSONR.getString("TOKEN");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(token!=null){
            Log.e("epalex",token);
            headers.put("Authorization", "bearer"+" "+token);
            headers.put("Content-Type","application/json");
        }

        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return super.getRetryPolicy();
    }

    @Override
    public byte[] getBody() {

        HashMap<String,String> params=new HashMap<>();

        try {
            params = new ObjectMapper().readValue(mJSONR.toString(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String paramsEncoding = "UTF-8";
        StringBuilder encodedParams = new StringBuilder();

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);

        }
    }

}
