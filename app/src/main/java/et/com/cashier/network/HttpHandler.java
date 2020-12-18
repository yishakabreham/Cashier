package et.com.cashier.network;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import et.com.cashier.model.TripDetail;

public class HttpHandler
{
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler()
    {

    }

    public String[] makeServiceCall(String reqUrl)
    {
        String[] result = new String[2];
        String response = null;

        try
        {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int status = conn.getResponseCode();
            result[0] = String.valueOf(status);
            // read the response
            InputStream inputStream = conn.getErrorStream();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            result[1] = response;
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        }
        catch (ProtocolException e)
        {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(TAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            Log.e(TAG, "HTTP Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public JSONArray makeServiceCallPost(RequestPackage requestPackage, Context mContext)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final JSONArray[] jsonArray = {new JSONArray()};
        try
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.POST, requestPackage.getUri(), requestPackage.getJsonObject(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    try
                    {
                        jsonArray[0] = response;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    error.printStackTrace();
                }
            }
            );

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsObjRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonArray[0];
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
