package denokela.com.projectfire;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Member_Profile extends AppCompatActivity {
    BufferedInputStream is;
    String firstname, middlename, surname, phonenumber, bday, state, course, level, year, pic_url;
    CustomImageView profileimg;
    ImageButton contentcopy;
    AlertDialog.Builder bdialog, adialog, cdialog;

    ProgressDialog progressDialog;
    Bitmap bitmap;
    Bundle bundle;

    TextView tvfname, tvmname, tvsname, tvpnumber, tvbirthday, tvstate, tvcourse, tvlevel, tvyear;
    SharedPreferences getpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member__profile);
        progressDialog = new ProgressDialog(this,R.style.MyDialogTheme);
        bdialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        adialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        cdialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        progressDialog.setMessage("Processing.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        bundle = getIntent().getExtras();
        final String pnumber = bundle.getString("phonenumber");
        getpref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String retrieve = "RetrieveData";

        UrlConnectivity urlConnectivity = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {
                if (output != null) {
                    try {
                        JSONArray ja = new JSONArray(output);
                        JSONObject jo = ja.getJSONObject(0);

                        firstname = jo.getString("First Name");
                        middlename = jo.getString("Middle Name");
                        surname = jo.getString("Surname");
                        phonenumber = jo.getString("Phone Number");
                        bday = jo.getString("Birthday Day") + " " + jo.getString("Birthday Month");
                        state = jo.getString("State of Origin");
                        course = jo.getString("Course");
                        level = jo.getString("Level");
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
                                Bundle bundler = new Bundle();
                                bundler.putString("fname", firstname);
                                bundler.putString("sname", surname);
                                bundler.putString("picurl", pic_url);
                                sharedIntent.putExtras(bundler);
                                startActivity(sharedIntent);
                            }
                        });
                        contentcopy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Phone Number Copied", phonenumber);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getApplicationContext(), "Phone Number Copied", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Sorry an Error Occured", Toast.LENGTH_LONG).show();

                }
            }
        }, retrieve);

        urlConnectivity.execute(pnumber);


    }

    public void initialize() {
        tvfname = (TextView) findViewById(R.id.profile_first_name);
        tvmname = (TextView) findViewById(R.id.profile_middle_name);
        tvsname = (TextView) findViewById(R.id.profile_surname);
        tvpnumber = (TextView) findViewById(R.id.profile_phonenumber);
        tvstate = (TextView) findViewById(R.id.profile_state);
        tvcourse = (TextView) findViewById(R.id.profile_course);
        tvlevel = (TextView) findViewById(R.id.profile_level);
        tvyear = (TextView) findViewById(R.id.profile_year);
        tvbirthday = (TextView) findViewById(R.id.profile_birthday);
        contentcopy = (ImageButton) findViewById(R.id.copycontent);
    }

    //Edit the Members Details
    public void Edit(View view) {

        adialog.setTitle("Select the Field you wish to Modify");
        String[] fields = {"First Name", "Middle Name", "Surname", "Phone Number", "Course", "State of Origin", "Level", "Year Joined"};
        final Spinner fieldspinners = new Spinner(getApplicationContext());
        fieldspinners.setPopupBackgroundResource(R.drawable.spinner);
        ArrayAdapter<String> spinadapter = new ArrayAdapter<String>(this, R.layout.spinnerview, fields);
        fieldspinners.setAdapter(spinadapter);
        adialog.setView(fieldspinners);

        adialog.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                final String getselectdeddata = fieldspinners.getSelectedItem().toString();
                bdialog.setTitle("You are Editing the " + getselectdeddata + " field");
                final EditText inputtext = new EditText(getApplicationContext());
                if (getselectdeddata.equals("Phone Number") || getselectdeddata.equals("Level") || getselectdeddata.equals("Year Joined")) {
                    inputtext.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                {
                    inputtext.setInputType(InputType.TYPE_CLASS_TEXT);

                }
                inputtext.setHint("Enter the new data for the field");
                inputtext.setTextColor(Color.WHITE);
                bdialog.setView(inputtext);
                bdialog.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        cdialog.setTitle("Input your Administrative ID");
                        final EditText adminid = new EditText(getApplicationContext());
                        adminid.setInputType(InputType.TYPE_CLASS_TEXT);
                        adminid.setTextColor(Color.WHITE);
                        cdialog.setView(adminid);

                        final String Username = getpref.getString("Username", "");
                        final String Password = getpref.getString("Password", "");
                        cdialog.setPositiveButton("Edit Field", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.show();
                                String getid = adminid.getText().toString().toUpperCase();
                                if (!getid.equals("")) {
                                    UrlConnectivity admincheck = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                                        @Override
                                        public void processfinish(String output) {
                                            if (output.contains("Correct admin ID")) {
                                                String newdata = inputtext.getText().toString().toUpperCase();
                                                UrlConnectivity editdetail = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                                                    @Override
                                                    public void processfinish(String output) {
                                                        if (output.contains("Field Updated Successfully") && output != null) {
                                                            DateFormat month = new SimpleDateFormat("MMMM");
                                                            DateFormat day = new SimpleDateFormat("d");
                                                            DateFormat year = new SimpleDateFormat("YYYY");
                                                            Date date = new Date();
                                                            String strmonth = month.format(date);
                                                            String strday = day.format(date);
                                                            String stryear = year.format(date);

                                                            String logmsg = "App User " + Username + " Changed the " + getselectdeddata + " Field of " + firstname + " " + middlename + " " + surname
                                                                    + " on the " + strday + " of " + strmonth + " " + stryear;
                                                            UrlConnectivity logmessage = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                                                                @Override
                                                                public void processfinish(String output) {
                                                                    if (output.contains("Log Message Inserted Successfully")) {
                                                                        progressDialog.dismiss();
                                                                        recreate();
                                                                        Toast.makeText(getApplicationContext(), "Field Updated Successfully", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                            }, "Log");
                                                            logmessage.execute(logmsg);

                                                        } else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(getApplicationContext(), "Sorry An error Occured. Please try again later", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }, "EditDetails");
                                                editdetail.execute(phonenumber, getselectdeddata, newdata);
                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Incorrect Administrative ID", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    }, "CheckAdmin");

                                    admincheck.execute(getid, Username, Password);
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Administrative ID Field is empty", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        cdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        cdialog.setCancelable(false);
                        cdialog.show();

                    }
                });
                bdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                bdialog.setCancelable(false);
                bdialog.show();
            }
        });
        adialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        adialog.setCancelable(false);
        adialog.show();

    }
}

class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {
    Bitmap bitmap;

    CustomImageView imgView;

    public GetImageFromURL(CustomImageView imgv) {
        this.imgView = imgv;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        bitmap = null;

        try {

            InputStream ist = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(ist);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        imgView.setImageBitmap(bitmap);

    }


}
