package denokela.com.projectfire;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CurrentExcos_List extends Fragment implements AdapterView.OnItemClickListener {

    String[] firstname;
    String[] middlename;
    String[] surname;
    String [] post;
    String[] imagepath;
    String[] phonenumber;
    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_current_excos__list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.excoprofilelist);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        if(result!=null){
        if(!result.contains("No Data Found") && !result.contains("null")) {
            Custom_Exco_Listview customListView = new Custom_Exco_Listview(getActivity(), firstname, middlename, surname, post, imagepath);
            listView.setAdapter(customListView);
            listView.setOnItemClickListener(this);
        }else{
            Toast.makeText(getContext(),"No Data Found", Toast.LENGTH_LONG).show();
        }}else{
            Toast.makeText(getContext(),"Sorry an Error Occured",Toast.LENGTH_LONG).show();
        }
}


    private void collectData() {
//Connection
        try {

            URL url = new URL("http://192.168.43.194/FB_DATA/viewcurrentexcos.php");
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("phonenumber", phonenumber[i]);
        Intent intent = new Intent(getContext(), Member_Profile.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}