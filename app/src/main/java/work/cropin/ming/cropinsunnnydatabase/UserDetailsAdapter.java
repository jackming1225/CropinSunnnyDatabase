package work.cropin.ming.cropinsunnnydatabase;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ming on 19/9/16.
 */
public class UserDetailsAdapter extends ArrayAdapter {


    AbstractRefreshList abstractRefreshList;
    Boolean result = false;


    List userDetailsArrayList = new ArrayList();

    Button bDeleteThisUser;

    String delID;

    private AbstractRefreshList context;

    //final UserDetails userDetails;
    public UserDetailsAdapter(AbstractRefreshList context, int resource) {
        super(context, resource);

        this.context = context;
    }


    public void add(UserDetails object) {
        super.add(object);
        userDetailsArrayList.add(object);

    }

    @Override
    public void remove(Object object) {
        super.remove(object);
    }

    @Override
    public int getCount() {
        return userDetailsArrayList.size();//list.size();
    }

    @Override
    public Object getItem(int position) {
        return userDetailsArrayList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        UserDetailsHolder userDetailsHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            userDetailsHolder = new UserDetailsHolder();
            userDetailsHolder.textUId = (TextView) row.findViewById(R.id.textUId);
            userDetailsHolder.textName = (TextView) row.findViewById(R.id.textName);
            userDetailsHolder.textEmail = (TextView) row.findViewById(R.id.textEmail);
            userDetailsHolder.textMobile = (TextView) row.findViewById(R.id.textMobile);
            row.setTag(userDetailsHolder);
        } else {
            userDetailsHolder = (UserDetailsHolder) row.getTag();

        }

        final UserDetails userDetails = (UserDetails) this.getItem(position);
        userDetailsHolder.textUId.setText(userDetails.getUserId());
        userDetailsHolder.textName.setText(userDetails.getName());
        userDetailsHolder.textEmail.setText(userDetails.getEmail());
        userDetailsHolder.textMobile.setText(userDetails.getMobile());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEdit(userDetails.getUserId(), userDetails.getName(), userDetails.getEmail(), userDetails.getMobile(), userDetails.getJsonStr());


            }
        });
        bDeleteThisUser = (Button) row.findViewById(R.id.bDelThisUser);

        bDeleteThisUser.setTag(position);

        bDeleteThisUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                delID = userDetails.getUserId();
                sendDeletRequestToServer();
                userDetailsArrayList.remove(position);
                notifyDataSetChanged();

            }
        });

        return row;
    }

    public class UserDetailsHolder {

        TextView textUId, textName, textEmail, textMobile;
    }

    public void openEdit(String Id, String name, String email, String mobile, String jsonString) {
        Intent intent = new Intent(getContext(), EditUserDetailsActivity.class);

        intent.putExtra("jsonString", jsonString);
        intent.putExtra("user_id", Id);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("mobile", mobile);

        getContext().startActivity(intent);


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
                //TextView texView = (TextView) findViewById(R.id.tVNew);
                //texView.setText(s);
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                //result =true;
                context.JsonTask();


            }

        }.execute();


    }


    public String toDeleteUser() throws IOException {

        String user_Id = delID;


        URL url = new URL("http://192.168.43.21:8080/SmartUtils/deleteUserFromAndroid");
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

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();


        return stringBuilder.toString();
    }

    private String sendIDtoDelete(List<Map<String, String>> params) throws UnsupportedEncodingException {


        String user_Id = delID;


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
