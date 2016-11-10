package work.cropin.ming.cropinsunnnydatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CropinMainActivity extends AbstractRefreshList  {


    private TextView textView;

    //String json_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropin_main);

        textView = (TextView) findViewById(R.id.textView);
        Button bClick = (Button) findViewById(R.id.bClick);


        bClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonTask();

                textView.setText(json_string);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
        });
    }







    /*public class JsonTask extends AsyncTask<String, String, String> {


        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                //creating client object
                URL url = new URL(params[0]);
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
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json_string = result;
            textView.setText(json_string);
            textView.setMovementMethod(new ScrollingMovementMethod());


        }
    }*/

    /*public void jsonParse(View view) {

        if (json_string == null || json_string.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No JSON DATA", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ListViewDisplayActivity.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }

    }*/




}


