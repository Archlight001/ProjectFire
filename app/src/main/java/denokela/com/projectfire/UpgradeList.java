package denokela.com.projectfire;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
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

    AlertDialog.Builder alertDialog,sdialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);
        Bundle grab = getIntent().getExtras();
        post =grab.getString("valuepost");
        urladdress = "http://192.168.43.194/FB_DATA/viewallmembers.php";

        listView = (ListView) findViewById(R.id.profilelist);
        alertDialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        sdialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        progressDialog = new ProgressDialog(this,R.style.MyDialogTheme);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        if(result!=null) {
            CustomListView customListView = new CustomListView(this, firstname, middlename, surname, imagepath);
            listView.setAdapter(customListView);
            listView.setOnItemClickListener(this);
        }else{
            Toast.makeText(this,"Sorry an Error Occured",Toast.LENGTH_LONG).show();

        }
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
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        final String assign = "AssignExco";
        alertDialog.setMessage("You are about to Assign "+ firstname[position] + " " + surname[position] +
         " "+ "as the " +post + " of the Current House. Select Assign to continue the process " +
                "or Exit to Terminate the process");
        alertDialog.setPositiveButton("Assign", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                progressDialog.setMessage("Processing.......");
                progressDialog.show();
                progressDialog.setCancelable(false);
                MarshorExcoUrlConnectivity marshorExcoUrlConnectivity = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
                    @Override
                    public void processfinish(String output) {
                        progressDialog.dismiss();
                        if(output.contains("Success")){
                            sdialog.setTitle("Success");
                            sdialog.setMessage("The post of " + post + " has been successfully assigned " +
                                    "to "+ firstname[position] + " " + surname[position]);
                            sdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    finish();
                                }
                            });
                            sdialog.show();
                            sdialog.setCancelable(false);

                        }else if(output.contains("Sorry")){
                            sdialog.setMessage(output);
                            sdialog.show();
                        }
                    }
                },assign);
                marshorExcoUrlConnectivity.execute(post,phonenumber[position]);
            }
        });
        alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
        alertDialog.setCancelable(false);

    }
}
