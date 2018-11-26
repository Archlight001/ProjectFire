package denokela.com.projectfire;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
    Button buttonsearch,viewall,btnviewlevel;
    EditText editsearch;
    ProgressDialog progressDialog;
    String searchword;
    Spinner viewerspin;
    TabHost tabHost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment,container,false);
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

        specs= tabHost.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Marshalls");
        tabHost.addTab(specs);

        Bundle getnames =getActivity().getIntent().getExtras();
        String names =getnames.getString("Name");
        nametview.setText(names);
        buttonsearch.setOnClickListener(this);
        viewall.setOnClickListener(this);
        btnviewlevel.setOnClickListener(this);
    }

    public void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_GRANTED){
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Permission granted",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view==buttonsearch){
            if(editsearch.getText().toString().equals("")){
                Toast.makeText(getContext(),"Fill in the Search Field",Toast.LENGTH_LONG).show();
            }else{
                progressDialog=new ProgressDialog(getContext());
                progressDialog.setMessage("Searching....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                searchword= editsearch.getText().toString().trim().toUpperCase();
                String check = "Search";
                UrlConnectivity urlConnectivity = new UrlConnectivity(new UrlConnectivity.AsyncResponse() {
                    @Override
                    public void processfinish(String output) {
                        if (output.contains("No Data Found")){
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),"No Member was found",Toast.LENGTH_LONG).show();
                        }
                        else {
                            progressDialog.dismiss();
                            Bundle bundle = new Bundle();
                            String urladress = "http://192.168.43.194/FB_DATA/searchmember.php";
                            bundle.putString("valuekey",searchword);
                            bundle.putString("URL", urladress);
                            Intent listv = new Intent(getContext(),Searchlist.class);
                            listv.putExtras(bundle);
                            startActivity(listv);
                            Toast.makeText(getContext(),"Member Found",Toast.LENGTH_LONG).show();

                            }
                    }
                }, check);
                urlConnectivity.execute(searchword);

            }
        }
        if(view==viewall){
            Bundle bundle = new Bundle();
            String urladress = "http://192.168.43.194/FB_DATA/viewallmembers.php";
            bundle.putString("valuekey","");
            bundle.putString("URL", urladress);
            Intent listv = new Intent(getContext(),Searchlist.class);
            listv.putExtras(bundle);
            startActivity(listv);
        }

        if(view==btnviewlevel){
            if(viewerspin.getSelectedItem().toString().equals("Select a Level")){
                Toast.makeText(getContext(),"Select a Valid Level",Toast.LENGTH_LONG).show();
            }else{
                Bundle bundle = new Bundle();
                String urladress = "http://192.168.43.194/FB_DATA/viewmembersbylevel.php";
                bundle.putString("valuekey",viewerspin.getSelectedItem().toString());
                bundle.putString("URL", urladress);
                Intent listv = new Intent(getContext(),Searchlist.class);
                listv.putExtras(bundle);
                startActivity(listv);
            }
        }
    }
}
