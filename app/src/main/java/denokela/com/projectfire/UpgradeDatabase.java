package denokela.com.projectfire;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpgradeDatabase extends Fragment implements View.OnClickListener {
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    AlertDialog.Builder sDialog,withinsdialog;


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
        sDialog = new AlertDialog.Builder(getContext());
        withinsdialog = new AlertDialog.Builder(getContext());
        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Processing.......");
        progressDialog.show();
        progressDialog.setCancelable(false);
        MarshorExcoUrlConnectivity marshConnectivity = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
            @Override
            public void processfinish(String output) {
                if (output.contains("17")) {
                    MarshorExcoUrlConnectivity marshorExcoUrlConnectivity = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
                        @Override
                        public void processfinish(String output) {
                            if (output.contains("Correct")) {
                                progressDialog.dismiss();
                                sDialog.setTitle("Upgrade");
                                sDialog.setMessage("You are about to upgrade the Database of the app. Note" +
                                        " that this process is irreversible.Kindly Press Ok to Enter your Administrative " +
                                        "ID and Continue");
                                sDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        withinsdialog.setTitle("Input your Administrative ID");
                                        final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                                        withinsdialog.setView(input);
                                        withinsdialog.setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                progressDialog.setMessage("Upgrading Database");
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
                                                        if (output.contains("Correct admin ID")){
                                                            MarshorExcoUrlConnectivity upgrade = new MarshorExcoUrlConnectivity(new MarshorExcoUrlConnectivity.AsyncResponse() {
                                                                @Override
                                                                public void processfinish(String output) {
                                                                    if(output.contains("Done")){
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(getContext(), "Database Upgraded Successfully", Toast.LENGTH_LONG).show();

                                                                    }else{
                                                                        progressDialog.dismiss();
                                                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                                                                        Toast.makeText(getContext(), "Something went wrong Try again later", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                            },"Upgrade");
                                                            upgrade.execute();

                                                        }
                                                        else {
                                                            progressDialog.dismiss();
                                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                                                            Toast.makeText(getContext(), "Incorrect Admin ID", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }, check);
                                                urlConnectivity.execute(adminid, Username, Password);

                                            }
                                        });
                                        withinsdialog.show();
                                        withinsdialog.setCancelable(false);

                                    }
                                });
                                sDialog.show();
                                sDialog.setCancelable(false);
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
                    marshorExcoUrlConnectivity.execute(uname);
                } else {
                    progressDialog.dismiss();
                }
            }
        }, checkrow);
        marshConnectivity.execute();



    }

    @Override
    public void onClick(View view) {
        if (postspin.getSelectedItem().toString().equals("Select a Post")) {
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


