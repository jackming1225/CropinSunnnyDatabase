package work.cropin.ming.cropinsunnnydatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListViewDisplayActivity extends AbstractRefreshList {

    private static final String UserId = "userId";
    private static final String Name = "name";
    private static final String Email = "emailId";
    private static final String Mobile = "contactNumber";



    String json_String = "";
    JSONObject jsonObject;

    JSONArray jsonArray;

    UserDetailsAdapter userDetailsAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_display_layout);


        listView = (ListView) findViewById(R.id.listView);


        json_String = getIntent().getExtras().getString("json_data");

        userDetailsAdapter = new UserDetailsAdapter(this, R.layout.row_layout);

        createAdapter();

        userDetailsAdapter.notifyDataSetChanged();







    }


    public void createAdapter(){
        try {
            jsonArray = new JSONArray(json_String);
            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);
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
    }


}