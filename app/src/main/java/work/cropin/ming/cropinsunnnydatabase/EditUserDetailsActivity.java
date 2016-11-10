package work.cropin.ming.cropinsunnnydatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class EditUserDetailsActivity extends AppCompatActivity {


    String id, name, email, mobile, editN, editM, editE, jsString, jsonNewString, jsonString1;
    TextView tId, jsonNew, tvNew;
    EditText editName, editEmail, editMobile;
    Button bUpdate, bPostJson, bDelete, bDelThisUser;
    JSONObject jsonObject;
    String http = "http://10.0.0.97:8080/SmartUtils/updateJSONFromAndroid";

    String httpdel = "http://10.0.0.97:8080/SmartUtils/deleteUserFromAndroid";

    HttpURLConnection urlConnection = null;

    UserDetailsAdapter userDetailsAdapter;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_details_activity);


        jsString = getIntent().getExtras().getString("jsonString");

        id = getIntent().getExtras().getString("user_id");
        name = getIntent().getExtras().getString("name");
        email = getIntent().getExtras().getString("email");
        mobile = getIntent().getExtras().getString("mobile");

        tvNew = (TextView) findViewById(R.id.tVNew);
        jsonNew = (TextView) findViewById(R.id.showNewJSON);
        tId = (TextView) findViewById(R.id.textID);
        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editMobile = (EditText) findViewById(R.id.editMobile);
        bUpdate = (Button) findViewById(R.id.bUpdate);
        bPostJson = (Button) findViewById(R.id.bPostJson);
        bDelete = (Button) findViewById(R.id.bDelUser);
        bDelThisUser = (Button) findViewById(R.id.bDelThisUser);

        tId.setText("User-ID: " + id);

        editName.setText(name);
        editEmail.setText(email);
        editMobile.setText(mobile);


        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonNew.setText(changeJsonData());
            }
        });


        bPostJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendJsontoServer();
            }

        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDeletRequestToServer();
            }
        });





    }


    private String changeJsonData() {
        String newName = editName.getText().toString();
        String newEmail = editEmail.getText().toString();
        String newMobile = editMobile.getText().toString();

        JSONObject jsonObject1 = new JSONObject();


        try {
            JSONArray jsonArray = new JSONArray(jsString);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("userId").equals(id)) { // compare for the key-value
                    ((JSONObject) jsonArray.get(i)).put("name", newName);
                    ((JSONObject) jsonArray.get(i)).put("emailId", newEmail);
                    ((JSONObject) jsonArray.get(i)).put("contactNumber", newMobile);
                    // put the new value for the key

                    jsonObject1.put("userId",id);
                    jsonObject1.put("name",newName);
                    jsonObject1.put("emailId",newEmail);
                    jsonObject1.put("contactNumber",newMobile);

                }
            }

            jsonNewString = jsonArray.toString();
            jsonString1 = jsonObject1.toString();
            return jsonString1;


        } catch (JSONException e) {
            e.printStackTrace();
            return "Json not formated" + e.getMessage();
        }





    }

    private void sendJsontoServer() {


        new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... params) {

                try {
                    return getServerResponse();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                TextView texView = (TextView) findViewById(R.id.tVNew);
                texView.setText(s);


            }
        }.execute();

    }


    public String getServerResponse() throws IOException {

        String json1 = changeJsonData();


        String newName = editName.getText().toString();
        String newEmail = editEmail.getText().toString();
        String newMobile = editMobile.getText().toString();


        URL url = new URL(http);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(false);

        List<Map<String, String>> params = new ArrayList<Map<String, String>>();
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("jsonString", json1);

        params.add(temp);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();
        os.close();

        conn.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line ="";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }

    private String getQuery(List<Map<String, String>> params) throws UnsupportedEncodingException {


        String json1 = changeJsonData();
        String newName = editName.getText().toString();
        String newEmail = editEmail.getText().toString();
        String newMobile = editMobile.getText().toString();


        StringBuilder result = new StringBuilder();

        boolean first = true;

        for (Map<String, String> pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode("jsonString", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(json1, "UTF-8"));
        }

        return result.toString();
    }








    private void sendDeletRequestToServer() {


        new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... params) {

                try {
                    return toDeleteUser();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                TextView texView = (TextView) findViewById(R.id.tVNew);
                texView.setText(s);


            }
        }.execute();

    }


    public String toDeleteUser() throws IOException {

        String user_Id = id;


        URL url = new URL(httpdel);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(false);

        List<Map<String, String>> params = new ArrayList<Map<String, String>>();
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("userId", user_Id);

        params.add(temp);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(sendIDtoDelete(params));
        writer.flush();
        writer.close();
        os.close();

        conn.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line ="";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }

    private String sendIDtoDelete(List<Map<String, String>> params) throws UnsupportedEncodingException {


        String user_Id = id;


        StringBuilder result = new StringBuilder();

        boolean first = true;

        for (Map<String, String> pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode("userId", "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(user_Id, "UTF-8"));
        }

        return result.toString();
    }
}
