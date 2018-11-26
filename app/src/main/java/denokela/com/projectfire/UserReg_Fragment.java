package denokela.com.projectfire;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.net.Uri;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import denokela.com.projectfire.core.ImageCompressTask;
import denokela.com.projectfire.listeners.IImageCompressTaskListener;

public class UserReg_Fragment extends Fragment implements View.OnClickListener {
    Spinner stateoforigin, yearjoined, currentlevel, birthdayday, birthdaymonth;
    EditText firstname, middlename, surname, phonenumber, courseofstudy;
    CustomImageView profilepic;
    Button uploadpic, uploaddata;
    private static int PICK_IMAGE_REQUEST = 975;

    private String filepath;
    private Bitmap bitmap;

    ProgressDialog progressDialog;

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);

    private ImageCompressTask imageCompressTask;


    String fname, mname, sname, pnumber, course, state, year, level, bday, bmonth;

    private static final String UPLOAD_URL = "http://192.168.43.194/FB_DATA/reg_upload.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_reg__fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        initializespanner(view);


    }

    private void initialize(View view) {
        firstname = (EditText) view.findViewById(R.id.etfirstname);
        middlename = (EditText) view.findViewById(R.id.etmiddlename);
        surname = (EditText) view.findViewById(R.id.etsurname);
        phonenumber = (EditText) view.findViewById(R.id.etphonenumber);
        courseofstudy = (EditText) view.findViewById(R.id.etcourse);
        profilepic = (CustomImageView) view.findViewById(R.id.profilepic);
        uploadpic = (Button) view.findViewById(R.id.bgprofilepic);
        uploaddata = (Button) view.findViewById(R.id.btnUsrRegister);
        uploaddata.setOnClickListener(this);
        uploadpic.setOnClickListener(this);
        progressDialog = new ProgressDialog(getContext());
    }

    private void initializespanner(View view) {
        stateoforigin = (Spinner) view.findViewById(R.id.spinstateoforigin);
        String[] items = {"State of Origin", "Abia","Adamawa","Akwa-Ibom","Anambra","Bauchi","Bayelsa",
                "Benue","Borno","Cross_River","Delta","Ebonyi","Edo","Ekiti","Enugu","Gombe",
        "Imo","Jigawa","Kaduna","Kano","Katsina","Kebbi","Kogi","Kwara","Lagos","Nassarawa",
        "Niger","ogun","Ondo","Osun","Oyo","Plateau","Rivers","Sokoto","Taraba","Yobe","Zamfara","F.c.t"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, items);
        stateoforigin.setAdapter(arrayAdapter);

        yearjoined = (Spinner) view.findViewById(R.id.spinyearjoined);
        String[] years = {"Year joined", "2018", "2017", "2016", "2015"};
        ArrayAdapter<String> yeararrayadpter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, years);
        yearjoined.setAdapter(yeararrayadpter);

        currentlevel = (Spinner) view.findViewById(R.id.spincurrentlevel);
        String[] levels = {"Current Level", "100", "200", "300", "400", "500"};
        ArrayAdapter<String> leveladapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, levels);
        currentlevel.setAdapter(leveladapter);

        birthdayday = (Spinner) view.findViewById(R.id.spinbirthdayday);
        String[] days = {"Day", "1", "2", "30"};
        ArrayAdapter<String> bdayadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, days);
        birthdayday.setAdapter(bdayadapter);

        birthdaymonth = (Spinner) view.findViewById(R.id.spinbirthdaymonth);
        String[] month = {"Month", "January", "Febuary", "30"};
        ArrayAdapter<String> bmonthadapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, month);
        birthdaymonth.setAdapter(bmonthadapter);

    }



    public void convertinput() {
        fname = firstname.getText().toString().trim().toUpperCase();
        mname = middlename.getText().toString().trim().toUpperCase();
        sname = surname.getText().toString().trim().toUpperCase();
        pnumber = phonenumber.getText().toString().toUpperCase();
        course = courseofstudy.getText().toString().toUpperCase();
        state = stateoforigin.getSelectedItem().toString().toUpperCase();
        year = yearjoined.getSelectedItem().toString().toUpperCase();
        level = currentlevel.getSelectedItem().toString().toUpperCase();
        bday = birthdayday.getSelectedItem().toString().toUpperCase();
        bmonth = birthdaymonth.getSelectedItem().toString().toUpperCase();

    }

    public void uploadMultipart() {



        try {
            String uploadid = UUID.randomUUID().toString();

            new MultipartUploadRequest(getContext(), uploadid, UPLOAD_URL)
                    .addFileToUpload(filepath, "image")
                    .addParameter("firstname", fname)
                    .addParameter("surname", sname)
                    .addParameter("middlename", mname)
                    .addParameter("phonenumber", pnumber)
                    .addParameter("birthday_day", bday)
                    .addParameter("birthday_month", bmonth)
                    .addParameter("state", state)
                    .addParameter("course", course)
                    .addParameter("level", level)
                    .addParameter("year", year)
                    .setMaxRetries(2)
                    .startUpload();
            progressDialog.dismiss();

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Upload Successful");
            builder.setMessage("Click View to view Profile or Exit to Go back");
            builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Bundle bundle = new Bundle();
                    bundle.putString("phonenumber", pnumber);
                    Intent intent = new Intent(getActivity(), Member_Profile.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            builder.show();
            builder.setCancelable(false);

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK &&
                data != null) {
            //extract absolute image path from Uri
            Uri uri = data.getData();

            Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), uri, new String[]{MediaStore.Images.Media.DATA});

            if (cursor != null && cursor.moveToFirst()) {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //Create ImageCompressTask and execute with Executor.
                imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                mExecutorService.execute(imageCompressTask);
            }
        }
    }


    private IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
        @Override
        public void onComplete(List<File> compressed) {
            //photo compressed. Yay!

            //prepare for uploads. Use an Http library like Retrofit, Volley or async-http-client (My favourite)

            File file = compressed.get(0);
            filepath=file.getPath();
            Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.

            profilepic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }

        @Override
        public void onError(Throwable error) {
            //very unlikely, but it might happen on a device with extremely low storage.
            //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
            Log.wtf("ImageCompressor", "Error occurred", error);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        //clean up!
        mExecutorService.shutdown();

        mExecutorService = null;
        imageCompressTask = null;
    }


    @Override
    public void onClick(View view) {
        if (view == uploadpic) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        } else if (view == uploaddata) {
            convertinput();
            if (fname.equals("") || sname.equals("") || pnumber.equals("") || course.equals("") || bday.equals("Day") || bmonth.equals("Month")) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_LONG).show();
            } else if (state.equals("State of Origin")) {
                Toast.makeText(getActivity(), "Please select a valid state of origin", Toast.LENGTH_LONG).show();
            } else if (year.equals("Year joined")) {
                Toast.makeText(getActivity(), "Please select a valid year", Toast.LENGTH_LONG).show();
            } else if (level.equals("Current Level")) {
                Toast.makeText(getActivity(), "Please select a valid level", Toast.LENGTH_LONG).show();
            } else if (profilepic.getDrawable() == null) {
                Toast.makeText(getActivity(), "Upload an Image", Toast.LENGTH_LONG).show();
            } else {
                validateAdmin();


            }
        }
    }

    public void validateAdmin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Input your Administrative ID");

// Set up the input
        final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setMessage("Processing......");
                progressDialog.show();
                progressDialog.setCancelable(false);
                String adminid = input.getText().toString().trim().toUpperCase();
                Bundle names = getActivity().getIntent().getExtras();
                String Username = names.getString("Username");
                String Password = names.getString("Password");
                String check = "CheckAdmin";

                UrlConnectivity urlConnectivity = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                    @Override
                    public void processfinish(String output) {
                        if (output.contains("Correct admin ID"))
                            checkduplicate();
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Incorrect Admin ID", Toast.LENGTH_LONG).show();
                        }
                    }
                }, check);
                urlConnectivity.execute(adminid, Username, Password);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void checkduplicate() {
        convertinput();
        String check = "CheckDuplicate";
        UrlConnectivity urlConnectivity = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {

                if (output.contains("Duplicate Data Found")) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Duplicate Data Found", Toast.LENGTH_SHORT).show();
                } else if (output.contains("Phone Number has Already Been Used")) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Phone Number has Already Been Used", Toast.LENGTH_LONG).show();
                } else if (output.contains("None")) {
                    uploadMultipart();
                }

            }
        }, check);
        urlConnectivity.execute(fname, mname, sname, pnumber);

    }
}
