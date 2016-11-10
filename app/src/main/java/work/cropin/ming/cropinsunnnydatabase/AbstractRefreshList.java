package work.cropin.ming.cropinsunnnydatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ming on 27/9/16.
 */

public abstract class AbstractRefreshList extends Activity{


    String json_string = "";


    public String JsonTask() {

        new AsyncTask<String, String, String> (){


            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;

            @Override
            protected String doInBackground (String...params){
                try {
                    //creating client object
                    URL url = new URL("http://10.0.0.97:8080/SmartUtils/sunny");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();


                    //using httppost to use parameter url
                    InputStream inputStream = urlConnection.getInputStream();

                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    //read contect and display
                    StringBuilder stringBuilder = new StringBuilder();
                    String newLine = "";

                    while ((newLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(newLine);
                        //stringBuffer.append("\n");
                    }
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //Close content
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    try {
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate (String...values){
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute (String result){
                super.onPostExecute(result);
                json_string = result;
                /*TextView textView ;
                textView = (TextView) findViewById(R.id.textView);
                textView.setText(json_string);
                textView.setMovementMethod(new ScrollingMovementMethod());*/


            }
        }.execute();
        return json_string;
    }





    public void jsonParse(View view) {

        /*if (json_string == null || json_string.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No JSON DATA", Toast.LENGTH_SHORT).show();
        } else {*/
            Intent intent = new Intent(this, ListViewDisplayActivity.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        //}

    }

    /*public void createAdapter(){



        String UserId = "userId";
        String Name = "name";
        String Email = "emailId";
        String Mobile = "contactNumber";
        UserDetailsAdapter userDetailsAdapter;
        ListView listView;

        listView = (ListView) findViewById(R.id.listView);
        userDetailsAdapter = new UserDetailsAdapter(this, R.layout.row_layout);
        String json_String ;

        json_String = getIntent().getExtras().getString("json_data");
        try {
            JSONArray jsonArray = new JSONArray(json_String);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userId = jsonObject.getString(UserId);
                String name = jsonObject.getString(Name);
                String email = jsonObject.getString(Email);
                String mobile = jsonObject.getString(Mobile);
                String json = json_String;

                UserDetails userDetails = new UserDetails(userId, name, email, mobile, json);
                userDetailsAdapter.add(userDetails);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(userDetailsAdapter);
    }*/


}
