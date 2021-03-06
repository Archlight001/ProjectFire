package denokela.com.projectfire;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final int STORAGE_PERMISSION_CODE = 125;
    TextView nametview;
    Button buttonsearch, viewall, btnviewlevel, marshbtnsearch, marshbtnviewall, marshbtnviewgradlevel;
    EditText editsearch, marsheditsearch;
    ProgressDialog progressDialog;
    String searchword, marshallsearchword;
    Spinner viewerspin, marshviewerspin;
    TabHost tabHost;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestStoragePermission();
        tabHost = (TabHost) view.findViewById(R.id.tabhost);
        nametview = (TextView) view.findViewById(R.id.tvname);
        editsearch = (EditText) view.findViewById(R.id.etquicksearch);
        buttonsearch = (Button) view.findViewById(R.id.btnsearch);
        viewall = (Button) view.findViewById(R.id.btnviewall);
        btnviewlevel = (Button) view.findViewById(R.id.btnviewlevel);
        viewerspin = (Spinner) view.findViewById(R.id.spinnnerlevel);
        String[] levels = {"Select a Level", "100", "200", "300", "400", "500"};
        ArrayAdapter<String> leveladapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, levels);
        viewerspin.setAdapter(leveladapter);

        tabHost.setup();
        TabHost.TabSpec specs = tabHost.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Members");
        tabHost.addTab(specs);

        specs = tabHost.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Marshalls");
        tabHost.addTab(specs);

        fieldmarshallvariableinit();

        Bundle getnames = getActivity().getIntent().getExtras();
        String names = getnames.getString("Name");

        SharedPreferences sharedpref = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("Username", getnames.getString("Username"));
        editor.putString("Password", getnames.getString("Password"));
        editor.apply();

        nametview.setText(names);
        buttonsearch.setOnClickListener(this);
        viewall.setOnClickListener(this);
        btnviewlevel.setOnClickListener(this);
    }

    private void fieldmarshallvariableinit() {
        marshbtnsearch = (Button) getView().findViewById(R.id.marshallbtnsearch);
        marshbtnviewall = (Button) getView().findViewById(R.id.marshallbtnviewall);
        marshbtnviewgradlevel = (Button) getView().findViewById(R.id.marshallbtnviewlevel);
        marshviewerspin = (Spinner) getView().findViewById(R.id.marshallspinnnerlevel);
        String[] gradyear = {"Select a year", "2010", "2011", "2012", "2013", "2014", "2015"
                , "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027"
                , "2028", "2029", "2030"};
        ArrayAdapter<String> marshallleveladapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, gradyear);
        marshviewerspin.setAdapter(marshallleveladapter);
        marsheditsearch = (EditText) getView().findViewById(R.id.marshalletquicksearch);
        marshbtnsearch.setOnClickListener(this);
        marshbtnviewall.setOnClickListener(this);
        marshbtnviewgradlevel.setOnClickListener(this);
    }

    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View view) {


        if (view == buttonsearch) {
            if (editsearch.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Fill in the Search Field", Toast.LENGTH_LONG).show();
            } else {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                progressDialog.setMessage("Searching....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                searchword = editsearch.getText().toString().trim().toUpperCase();
                String check = "Search";
                UrlConnectivity urlConnectivity = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                    @Override
                    public void processfinish(String output) {
                        if (output != null) {
                            if (output.contains("No Data Found")) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "No Member was found", Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                Bundle bundle = new Bundle();
                                String urladress = "http://192.168.43.194/FB_DATA/searchmember.php";
                                bundle.putString("valuekey", searchword);
                                bundle.putString("URL", urladress);
                                bundle.putString("TabName", "Member");
                                Intent listv = new Intent(getContext(), Searchlist.class);
                                listv.putExtras(bundle);
                                startActivity(listv);
                                Toast.makeText(getContext(), "Member Found", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Sorry an Error Occured", Toast.LENGTH_LONG).show();
                        }
                    }
                }, check);
                urlConnectivity.execute(searchword);

            }
        }
        if (view == viewall) {
            Bundle bundle = new Bundle();
            String urladress = "http://192.168.43.194/FB_DATA/viewallmembers.php";
            bundle.putString("valuekey", "");
            bundle.putString("URL", urladress);
            bundle.putString("TabName", "Member");
            Intent listv = new Intent(getContext(), Searchlist.class);
            listv.putExtras(bundle);
            startActivity(listv);
        }

        if (view == btnviewlevel) {
            if (viewerspin.getSelectedItem().toString().equals("Select a Level")) {
                Toast.makeText(getContext(), "Select a Valid Level", Toast.LENGTH_LONG).show();
            } else {
                Bundle bundle = new Bundle();
                String urladress = "http://192.168.43.194/FB_DATA/viewmembersbylevel.php";
                bundle.putString("valuekey", viewerspin.getSelectedItem().toString());
                bundle.putString("URL", urladress);
                bundle.putString("TabName", "Member");
                Intent listv = new Intent(getContext(), Searchlist.class);
                listv.putExtras(bundle);
                startActivity(listv);
            }
        }

        if (view == marshbtnsearch) {
            if (marsheditsearch.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Fill in the Search Field", Toast.LENGTH_LONG).show();
            } else {
                progressDialog = new ProgressDialog(getContext(),R.style.MyDialogTheme);
                progressDialog.setMessage("Searching....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                marshallsearchword = marsheditsearch.getText().toString().trim().toUpperCase();
                String check = "Search";
                MarshorExcoUrlConnectivity urlConnectivity = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
                    @Override
                    public void processfinish(String output) {
                        if (output != null) {
                            if (output.contains("No Data Found")) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "No Member was found", Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                Bundle bundle = new Bundle();
                                String urladress = "http://192.168.43.194/FB_DATA/searchmarshall.php";
                                bundle.putString("valuekey", marshallsearchword);
                                bundle.putString("URL", urladress);
                                bundle.putString("TabName", "Marshall");
                                Intent listv = new Intent(getContext(), Searchlist.class);
                                listv.putExtras(bundle);
                                startActivity(listv);
                                Toast.makeText(getContext(), "Member Found", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Sorry an Error Occured", Toast.LENGTH_LONG).show();
                        }
                    }
                }, check);
                urlConnectivity.execute(marshallsearchword);

            }

        }
        if (view == marshbtnviewgradlevel) {
            if (marshviewerspin.getSelectedItem().toString().equals("Select a year")) {
                Toast.makeText(getContext(), "Select a Valid Year", Toast.LENGTH_LONG).show();
            } else {
                Bundle bundle = new Bundle();
                String urladress = "http://192.168.43.194/FB_DATA/viewmarshallsbylevel.php";
                bundle.putString("valuekey", marshviewerspin.getSelectedItem().toString());
                bundle.putString("URL", urladress);
                bundle.putString("TabName", "Marshall");
                Intent listv = new Intent(getContext(), Searchlist.class);
                listv.putExtras(bundle);
                startActivity(listv);
            }
        }


        if (view == marshbtnviewall) {
            Bundle bundle = new Bundle();
            String urladress = "http://192.168.43.194/FB_DATA/viewallmarshalls.php";
            bundle.putString("valuekey", "");
            bundle.putString("URL", urladress);
            bundle.putString("TabName", "Marshall");
            Intent listv = new Intent(getContext(), Searchlist.class);
            listv.putExtras(bundle);
            startActivity(listv);
        }
    }

}
