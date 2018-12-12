package denokela.com.projectfire;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.Arrays;


public class Birthdays_Of_The_Month extends Fragment implements AdapterView.OnItemClickListener {
    String[] firstname;
    String[] middlename;
    String[] surname;
    String [] bday;
    String[] bmonth;
    String[] phonenumber;
    String[] unitstatus;
    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;
    private static final String TAG = "LogData";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_current_excos__list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.excoprofilelist);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        for(int y=0;y<phonenumber.length;y++){
            getstatus(phonenumber[y],y);

        }
        sortData();

        if(!result.contains("No Data Found") && result!=null && !result.contains("null")) {
            Custom_Birthdays_Listview customListView = new Custom_Birthdays_Listview(getActivity(), firstname, middlename, surname, bday, bmonth,unitstatus);
            listView.setAdapter(customListView);
            listView.setOnItemClickListener(this);
        }else{
            Toast.makeText(getContext(),"No Birthdays for this Month", Toast.LENGTH_LONG).show();
        }
    }

    private void sortData(){
        int[] tbday = new int[bday.length];
        String[] tempbday = new String[bday.length];
        for(int w=0;w<tbday.length;w++){
            tbday[w]=Integer.parseInt(bday[w]);

        }
        Arrays.sort(tbday);
        for(int w=0;w<tempbday.length;w++){
            tempbday[w] = Integer.toString(tbday[w]);
        }
        for(int w=0;w<tempbday.length;w++){
            Toast.makeText(getContext(),tempbday[w],Toast.LENGTH_SHORT).show();
        }

        for(int w=0;w<tempbday.length;w++){
            if(!tempbday[w].equals(bday[w])){
                String searchval = tempbday[w];
                int oldindex=0;
                int newindex=0;
                for(int u=w;u<tempbday.length;u++){
                    if(bday[u].equals(searchval)){
                        oldindex=u;
                        newindex=w;
                    }
                }
               swap(firstname,oldindex,newindex);
                swap(middlename,oldindex,newindex);
                swap(surname,oldindex,newindex);
                swap(phonenumber,oldindex,newindex);
                swap(bday,oldindex,newindex);
                swap(bmonth,oldindex,newindex);
                swap(unitstatus,oldindex,newindex);

            }
        }


    }

    private void swap(String[] arr,int oldind, int newind){
        String temp= arr[oldind];
        arr[oldind]= arr[newind];
        arr[newind]= temp;
    }

    private void getstatus(String number,Integer count){
        try {
            URL url = new URL("http://192.168.43.194/FB_DATA/getunitstatus.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("PhoneNumber", "UTF-8") + "=" +
                    URLEncoder.encode(number, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            is = new BufferedInputStream(con.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            unitstatus[count]=result.trim();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }
    private void collectData() {
//Connection
        try {

            URL url = new URL("http://192.168.43.194/FB_DATA/grab_bday_bmonth.php");
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
            bday = new String[ja.length()];
            bmonth = new String[ja.length()];
            unitstatus = new String[ja.length()];

            for ( int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);
                firstname[i] = jo.getString("First Name");
                middlename[i] = jo.getString("Middle Name");
                surname[i] = jo.getString("Surname");
                phonenumber[i] = jo.getString("Phone Number");
                bday[i] = jo.getString("Birthday Day");
                bmonth[i] = jo.getString("Birthday Month");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }try{

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        MarshorExcoUrlConnectivity getstatus = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {
                if(output.contains("Member")){
                    Bundle bundle = new Bundle();
                    bundle.putString("phonenumber", phonenumber[i]);
                    Intent intent = new Intent(getContext(), Member_Profile.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(output.contains("Marshall")){
                    Bundle bundle = new Bundle();
                    bundle.putString("phonenumber", phonenumber[i]);
                    Intent intent = new Intent(getContext(), Marshall_Profile.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        },"GetUnitStatus");
        getstatus.execute(phonenumber[i]);
    }
}
