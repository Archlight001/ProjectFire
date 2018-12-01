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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class UpgradeDatabase extends Fragment implements View.OnClickListener {
    ProgressDialog progressDialog;
    AlertDialog alertDialog;


    Spinner postspin;
    Button memberbutton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_upgrade_database, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        memberbutton = (Button) view.findViewById(R.id.btnselectmember);
        postspin = (Spinner) view.findViewById(R.id.spinnnerposts);
        String[] posts = {"Select a Post", "Coordinator", "Deputy-Coordinator",
                "Secretary", "Financial Secretary", "Treasurer", "Script 1",
        "Script 2", "Technical 1", "Technical 2", "Stage 1", "Stage 2","Costume 1",
        "Costume 2", "Prayer 1", "Prayer 2", "Security 1", "Security 2"};
        ArrayAdapter<String> leveladapter = new ArrayAdapter<String>(getActivity(), R.layout.spinnerview, posts);
        postspin.setAdapter(leveladapter);
        memberbutton.setOnClickListener(this);
        Bundle getnames = getActivity().getIntent().getExtras();
        final String uname = getnames.getString("Username");
        final String connecturl = "CheckAdminId";
        final String checkrow = "CheckRow";
        alertDialog = new AlertDialog.Builder(getContext()).create();
        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Processing.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        MarshUrlConnectivity marshConnectivity = new MarshUrlConnectivity(new MarshUrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {
                if (output.contains("17")) {
                    MarshUrlConnectivity marshUrlConnectivity = new MarshUrlConnectivity(new MarshUrlConnectivity.AsyncResponse() {
                        @Override
                        public void processfinish(String output) {
                            if (output.contains("Correct")) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Welcome", Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                alertDialog.setTitle("Restricted Access");
                                alertDialog.setMessage("Sorry, You do not have the appropriate " +
                                        "clearance level to access this page. You will be Redirected back" +
                                        " to the Home page soon");
                                alertDialog.show();
                                alertDialog.setCancelable(false);
                                final Thread redirect = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(3000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            alertDialog.dismiss();
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                                        }
                                    }
                                };
                                redirect.start();
                            }
                        }
                    }, connecturl);
                    marshUrlConnectivity.execute(uname);
                } else {
                    progressDialog.dismiss();
                }
            }
        }, checkrow);
        marshConnectivity.execute();



    }

    @Override
    public void onClick(View view) {
        if (postspin.getSelectedItem().toString().equals("Select a Pear")) {
            Toast.makeText(getContext(), "Select a Valid Post", Toast.LENGTH_LONG).show();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("valuepost", postspin.getSelectedItem().toString());
            Intent listv = new Intent(getContext(), UpgradeList.class);
            listv.putExtras(bundle);
            startActivity(listv);
        }
    }
}


