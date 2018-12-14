package denokela.com.projectfire;


import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UrlConnectivity extends AsyncTask<String, Void, String> {
    String connecturl, username, password, adminid, fname, sname, mname, pnumber;
    Context context;

    public interface AsyncResponse {
        void processfinish(String output);
    }

    public AsyncResponse delegate = null;

    public UrlConnectivity(AsyncResponse delegate, String connecturl) {
        this.delegate = delegate;
        this.connecturl = connecturl;
    }

    @Override
    protected String doInBackground(String... params) {

        {
            if (connecturl.equals("CheckAdmin")) {
                username = params[1];
                password = params[2];
                adminid = params[0];

                try {
                    String address = "http://192.168.43.194/FB_DATA/checkadmin.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("admin", "UTF-8") + "=" +
                            URLEncoder.encode(adminid, "UTF-8") + "&" +
                            URLEncoder.encode("Username", "UTF-8") + "=" +
                            URLEncoder.encode(username, "UTF-8") + "&" +
                            URLEncoder.encode("Password", "UTF-8") + "=" +
                            URLEncoder.encode(password, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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

            } else if (connecturl.equals("CheckDuplicate")) {
                fname = params[0];
                mname = params[1];
                sname = params[2];
                pnumber = params[3];

                try {
                    String address = "http://192.168.43.194/FB_DATA/checkduplicate.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("firstname", "UTF-8") + "=" +
                            URLEncoder.encode(fname, "UTF-8") + "&" +
                            URLEncoder.encode("middlename", "UTF-8") + "=" +
                            URLEncoder.encode(mname, "UTF-8") + "&" +
                            URLEncoder.encode("surname", "UTF-8") + "=" +
                            URLEncoder.encode(sname, "UTF-8") + "&" +
                            URLEncoder.encode("phonenumber", "UTF-8") + "=" +
                            URLEncoder.encode(pnumber, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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
            } else if (connecturl.equals("RetrieveData")) {
                pnumber = params[0];

                try {
                    String address = "http://192.168.43.194/FB_DATA/member_profile.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("PhoneNumber", "UTF-8") + "=" +
                            URLEncoder.encode(pnumber, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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
            } else if (connecturl.equals("Search")) {
                String searchkey = params[0];
                try {
                    String address = "http://192.168.43.194/FB_DATA/searchmember.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("Value", "UTF-8") + "=" +
                            URLEncoder.encode(searchkey, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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
            } else if (connecturl.equals("GetAdmin")) {
                String Username = params[0];
                String Password = params[1];
                try {
                    String address = "http://192.168.43.194/FB_DATA/getadminid.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("Username", "UTF-8") + "=" +
                            URLEncoder.encode(Username, "UTF-8") + "&" +
                            URLEncoder.encode("Password", "UTF-8") + "=" +
                            URLEncoder.encode(Password, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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
            } else if (connecturl.equals("EditDetails")) {
                String PhoneNumber = params[0];
                String Field = params[1];
                String New_data = params[2];
                try {
                    String address = "http://192.168.43.194/FB_DATA/editmemberdetails.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("PhoneNumber", "UTF-8") + "=" +
                            URLEncoder.encode(PhoneNumber, "UTF-8") + "&" +
                            URLEncoder.encode("Field", "UTF-8") + "=" +
                            URLEncoder.encode(Field, "UTF-8") + "&" +
                            URLEncoder.encode("NewData", "UTF-8") + "=" +
                            URLEncoder.encode(New_data, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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
            } else if (connecturl.equals("Log")) {
                String Log = params[0];
                try {
                    String address = "http://192.168.43.194/FB_DATA/logs.php";
                    URL url = new URL(address);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("Log", "UTF-8") + "=" +
                            URLEncoder.encode(Log, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    String result = "";
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

    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processfinish(s);
    }
}
