package denokela.com.projectfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Member_Profile extends AppCompatActivity {
    BufferedInputStream is;
    String firstname,middlename,surname,phonenumber,bday,state,course,level,year,pic_url;
    CustomImageView profileimg;



    ProgressDialog progressDialog;
    Bitmap bitmap;

    TextView tvfname,tvmname,tvsname,tvpnumber,tvbirthday,tvstate,tvcourse,tvlevel,tvyear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member__profile);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final Bundle bundle = getIntent().getExtras();
        final String pnumber =bundle.getString("phonenumber");

        String retrieve = "RetrieveData";

        UrlConnectivity urlConnectivity = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {

                try{
                    JSONArray ja=new JSONArray(output);
                    JSONObject jo=ja.getJSONObject(0);

                    firstname=jo.getString("First Name");
                    middlename=jo.getString("Middle Name");
                    surname = jo.getString("Surname");
                    phonenumber=jo.getString("Phone Number");
                    bday =jo.getString("Birthday Day") + " "+ jo.getString("Birthday Month");
                    state= jo.getString("State of Origin");
                    course =jo.getString("Course");
                    level= jo.getString("Level");
                    year = jo.getString("Year Joined");
                    pic_url = jo.getString("Profile_pic url");
                    profileimg = (CustomImageView) findViewById(R.id.profileimage);
                    new GetImageFromURL(profileimg).execute(pic_url);
                    initialize();
                    tvfname.setText(firstname);
                    tvmname.setText(middlename);
                    tvsname.setText(surname);
                    tvpnumber.setText(phonenumber);
                    tvbirthday.setText(bday);
                    tvstate.setText(state);
                    tvcourse.setText(course);
                    tvlevel.setText(level);
                    tvyear.setText(year);

                    progressDialog.dismiss();

                    profileimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent sharedIntent = new Intent(getApplicationContext(), Image_FullScreen.class);
                            Bundle bundler= new Bundle();
                            bundler.putString("picurl",pic_url);
                            sharedIntent.putExtras(bundler);
                            startActivity(sharedIntent);
                        }
                    });


                }
                catch (Exception ex)
                {

                    ex.printStackTrace();
                }

            }
        },retrieve);

        urlConnectivity.execute(pnumber);



    }

    public void initialize(){
        tvfname = (TextView) findViewById(R.id.profile_first_name);
        tvmname = (TextView) findViewById(R.id.profile_middle_name);
        tvsname = (TextView) findViewById(R.id.profile_surname);
        tvpnumber = (TextView) findViewById(R.id.profile_phonenumber);
        tvstate = (TextView) findViewById(R.id.profile_state);
        tvcourse = (TextView) findViewById(R.id.profile_course);
        tvlevel = (TextView) findViewById(R.id.profile_level);
        tvyear = (TextView) findViewById(R.id.profile_year);
        tvbirthday= (TextView) findViewById(R.id.profile_birthday);
    }

}

class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
{
    Bitmap bitmap;

    CustomImageView imgView;
    public GetImageFromURL(CustomImageView imgv)
    {
        this.imgView=imgv;
    }
    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay=url[0];
        bitmap=null;

        try{

            InputStream ist=new java.net.URL(urldisplay).openStream();
            bitmap= BitmapFactory.decodeStream(ist);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){

        super.onPostExecute(bitmap);
        imgView.setImageBitmap(bitmap);

    }


}
