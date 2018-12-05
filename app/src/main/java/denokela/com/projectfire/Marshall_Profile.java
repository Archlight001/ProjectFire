package denokela.com.projectfire;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class Marshall_Profile extends AppCompatActivity {
    BufferedInputStream is;
    String firstname,middlename,surname,phonenumber,bday,state,course,servedas,year,pic_url;
    CustomImageView profileimg;

    Bitmap bitmap;

    TextView tvfname,tvmname,tvsname,tvpnumber,tvbirthday,tvstate,tvcourse,tvservedas,tvyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marshall__profile);
        final Bundle bundle = getIntent().getExtras();
        final String pnumber =bundle.getString("phonenumber");

        String retrieve = "RetrieveData";

        MarshorExcoUrlConnectivity urlConnectivity = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
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
                    servedas= jo.getString("Served As");
                    year = jo.getString("Year Graduated");
                    pic_url = jo.getString("Profile_pic url");
                    profileimg = (CustomImageView) findViewById(R.id.marshallprofileimage);
                    new GrabImageFromURL(profileimg).execute(pic_url);
                    initialize();
                    tvfname.setText(firstname);
                    tvmname.setText(middlename);
                    tvsname.setText(surname);
                    tvpnumber.setText(phonenumber);
                    tvbirthday.setText(bday);
                    tvstate.setText(state);
                    tvcourse.setText(course);
                    tvservedas.setText(servedas);
                    tvyear.setText(year);

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
        tvfname = (TextView) findViewById(R.id.marshallprofile_first_name);
        tvmname = (TextView) findViewById(R.id.marshallprofile_middle_name);
        tvsname = (TextView) findViewById(R.id.marshallprofile_surname);
        tvpnumber = (TextView) findViewById(R.id.marshallprofile_phonenumber);
        tvstate = (TextView) findViewById(R.id.marshallprofile_state);
        tvcourse = (TextView) findViewById(R.id.marshallprofile_course);
        tvservedas = (TextView) findViewById(R.id.marshallprofile_served);
        tvyear = (TextView) findViewById(R.id.marshallprofile_year);
        tvbirthday= (TextView) findViewById(R.id.marshallprofile_birthday);
    }


    }

class GrabImageFromURL extends AsyncTask<String,Void,Bitmap>
{
    Bitmap bitmap;

    CustomImageView imgView;
    public GrabImageFromURL(CustomImageView imgv)
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

