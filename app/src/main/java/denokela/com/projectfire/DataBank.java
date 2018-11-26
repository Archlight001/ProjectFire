package denokela.com.projectfire;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DataBank extends AsyncTask<String, Void, String> {

    Context context;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    String ParsedUsername;
    String Parsedpassword;
    String grabnames_url = "http://192.168.43.194/grabnames.php";
    String names;

    DataBank(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String register_url = "http://192.168.43.194/registeradmin.php";
        String reset_url = "http://192.168.43.194/forgottenpassword.php";
        String login_url = "http://192.168.43.194/adminlogin.php";


        if (type.equals("Login")) {
            try {
                ParsedUsername = params[1];
                Parsedpassword = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                        outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(ParsedUsername, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(Parsedpassword, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (type.equals("Register")) {
            try {
                String firstname = params[1];
                String surname = params[2];
                String username = params[3];
                String password = params[4];
                String adminid = params[5];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                        outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("first_name", "UTF-8") + "=" +
                        URLEncoder.encode(firstname, "UTF-8") + "&" +
                        URLEncoder.encode("surname", "UTF-8") + "=" +
                        URLEncoder.encode(surname, "UTF-8") + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("admin_id", "UTF-8") + "=" +
                        URLEncoder.encode(adminid, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Get response
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (type.equals("Reset")) {
            try {
                String fadminid = params[1];
                String fusername = params[2];
                String fnewpassword = params[3];
                URL url = new URL(reset_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                        outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(fusername, "UTF-8") + "&" +
                        URLEncoder.encode("adminid", "UTF-8") + "=" +
                        URLEncoder.encode(fadminid, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(fnewpassword, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Get response
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Status");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null) {
            result = "Error... Could not Connect to database";

        }
        progressDialog.cancel();

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
        alertDialog.setMessage(result);
        alertDialog.show();


        if (result.contains("Successful") || result.contains("successfully")) {
            Thread next = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        context.startActivity(new Intent(context, LoginScreen.class));
                    }

                }

            };
            next.start();
        } else if (result.contains("Login Success")) {
            final Thread next = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        URL url = new URL(grabnames_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                                outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("username", "UTF-8") + "=" +
                                URLEncoder.encode(ParsedUsername, "UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();


                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        String result = "";
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;

                        }
                        names = result;
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();


                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Bundle Basket = new Bundle();
                        Basket.putString("Name", names);
                        Basket.putString("Username", ParsedUsername);
                        Basket.putString("Password", Parsedpassword);
                        context.startActivity(new Intent(context, WelcomeScreen.class).putExtras(Basket));

                    }
                }
            };
            next.start();
        }

    }

}

