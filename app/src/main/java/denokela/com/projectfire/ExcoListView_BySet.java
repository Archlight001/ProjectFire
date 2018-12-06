package denokela.com.projectfire;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ExcoListView_BySet extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;
    String grabbeddata;
    String[] firstname;
    String[] middlename;
    String[] surname;
    String[] post;
    String[] imagepath;
    String[] phonenumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_excos__list);
        Bundle grab = getIntent().getExtras();
        grabbeddata = grab.get("year").toString();
        listView = (ListView) findViewById(R.id.excoprofilelist);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        if (result.toLowerCase().contains(grabbeddata.toLowerCase())) {
            Custom_Exco_Listview customListView = new Custom_Exco_Listview(this, firstname, middlename, surname, post, imagepath);
            listView.setAdapter(customListView);
        }else{
            Toast.makeText(this, "No Exco found for this year", Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(this);

    }
    private void collectData() {
//Connection
        try {

            URL url = new URL("http://192.168.43.194/FB_DATA/listexcobyset.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("Value", "UTF-8") + "=" +
                    URLEncoder.encode(grabbeddata, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            is = new BufferedInputStream(con.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //content
        try {
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
            post = new String[ja.length()];
            imagepath = new String[ja.length()];

            for (int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);
                firstname[i] = jo.getString("First Name");
                middlename[i] = jo.getString("Middle Name");
                surname[i] = jo.getString("Surname");
                phonenumber[i] = jo.getString("Phone Number");
                post[i] = jo.getString("Post");
                imagepath[i] = jo.getString("Profile_pic url");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

        MarshorExcoUrlConnectivity checkphonenum = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {
                 if(output.contains("Marshall")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("phonenumber", phonenumber[i]);
                    Intent intent = new Intent(getApplicationContext(), Marshall_Profile.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(output.contains("Member")){
                    Bundle bundle = new Bundle();
                    bundle.putString("phonenumber", phonenumber[i]);
                    Intent intent = new Intent(getApplicationContext(), Member_Profile.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                     Toast.makeText(getApplicationContext(),"Can't open now. Try again later", Toast.LENGTH_LONG).show();
                 }
            }
        }, "CheckPhoneNumber");
        checkphonenum.execute(phonenumber[i]);


    }
}
