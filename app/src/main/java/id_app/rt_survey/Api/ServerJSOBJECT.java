package id_app.rt_survey.Api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class ServerJSOBJECT extends AsyncTask<Void,Void,JSONObject>{

    private static final String ENDPOINT = "DIRECCION DEL SERVIDOR";
    private JSONObject input=null;
    private String taskAddress;
    private TaskListener taskListener;
    private String authToken="VACIO";

    public void Network(JSONObject input ,String taskAddress,TaskListener tasklistener){
        this.taskAddress=taskAddress;
        this.input=input;
        this.taskListener=tasklistener;
    }

    public interface TaskListener {
        void onFinished(JSONObject RESULT);
    }

    public String getAuthToken() {
        return authToken;
    }

    public void authToken(String token){
        authToken=token;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        return makeHttpRequest();
    }

    @Override
    protected void onPostExecute(JSONObject RESULT) {
        super.onPostExecute(RESULT);

        JSONObject AUX= new JSONObject();
        if (this.taskListener != null) {
            this.taskListener.onFinished(RESULT);
        }
    }

    private JSONObject makeHttpRequest() {

        InputStream inputStream = null;
        String result = null;
        JSONObject jsonResult = null;

        try {

            URL Url = new URL(ENDPOINT+taskAddress);
            HttpURLConnection  conn = (HttpURLConnection) Url.openConnection();

            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new NullX509TrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

            conn.setRequestMethod("POST");
            conn.setRequestProperty("token",getAuthToken());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Enconding", "gzip,deflate");

            conn.setReadTimeout(12000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);


            OutputStreamWriter oswr = new OutputStreamWriter(conn.getOutputStream());
            oswr.write(input.toString());
            oswr.flush();

            inputStream = conn.getInputStream();
            result = streamToString(inputStream);
            jsonResult = new JSONObject(result);

            return jsonResult;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private static String streamToString(InputStream is) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    private class NullHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            System.out.println("Approving  null certificate for " + hostname);
            return true;
        }

    }

    private class NullX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
