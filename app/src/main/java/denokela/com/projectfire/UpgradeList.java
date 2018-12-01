package denokela.com.projectfire;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UpgradeList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urladdress;
    String[] firstname;
    String[] middlename;
    String[] surname;
    String[] imagepath;
    String[] phonenumber;
    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;
    String post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        Bundle grab = getIntent().getExtras();
        post =grab.getString("valuepost");
        urladdress = "http://192.168.43.194/FB_DATA/viewallmembers.php";

        listView = (ListView) findViewById(R.id.profilelist);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CustomListView customListView = new CustomListView(this, firstname, middlename, surname, imagepath);
            listView.setAdapter(customListView);
            listView.setOnItemClickListener(this);

    }

    private void collectData() {
//Connection
        try {

            URL url = new URL(urladdress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

//JSON
        try {

            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            firstname = new String[ja.length()];
            middlename = new String[ja.length()];
            surname = new String[ja.length()];
            phonenumber = new String[ja.length()];
            imagepath = new String[ja.length()];

            for (int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);
                firstname[i] = jo.getString("First Name");
                middlename[i] = jo.getString("Middle Name");
                surname[i] = jo.getString("Surname");
                phonenumber[i] = jo.getString("Phone Number");
                imagepath[i] = jo.getString("Profile_pic url");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
