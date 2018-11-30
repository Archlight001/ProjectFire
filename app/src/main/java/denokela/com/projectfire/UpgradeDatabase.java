package denokela.com.projectfire;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class UpgradeDatabase extends Fragment {
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_upgrade_database, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle getnames = getActivity().getIntent().getExtras();
        String uname = getnames.getString("Username");
        String connecturl = "CheckAdminId";
        alertDialog = new AlertDialog.Builder(getContext()).create();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Processing.......");
        progressDialog.show();
        progressDialog.setCancelable(false);

        MarshUrlConnectivity marshUrlConnectivity=new MarshUrlConnectivity(new MarshUrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {
                if(output.contains("Correct")){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Welcome",Toast.LENGTH_LONG).show();

                }else{
                    progressDialog.dismiss();
                    alertDialog.setTitle("Restricted Access");
                    alertDialog.setMessage("Sorry, You do not have the appropriate " +
                            "clearance level to access this page. You will be Redirected back" +
                            " to the Home page soon");
                    alertDialog.show();
                    alertDialog.setCancelable(false);
                    final Thread redirect = new Thread(){
                        @Override
                        public void run() {
                           try {
                               sleep(3000);
                           }catch (Exception e){
                               e.printStackTrace();
                           }finally {
                               alertDialog.dismiss();
                               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                           }
                        }
                    };
                    redirect.start();
                }
            }
        },connecturl);
        marshUrlConnectivity.execute(uname);
    }
}
