package denokela.com.projectfire;

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

public class MarshUrlConnectivity  extends AsyncTask<String, Void, String> {

    String connecturl,pnumber;

    public interface AsyncResponse {
        void processfinish(String output);
    }
    public AsyncResponse delegate=null;


    public MarshUrlConnectivity(AsyncResponse delegate, String connecturl) {
        this.delegate = delegate;
        this.connecturl=connecturl;
    }

    @Override
    protected String doInBackground(String... params) {
        if(connecturl.equals("Search")){
            String searchkey = params[0];
            try{
                String address="http://192.168.43.194/FB_DATA/searchmarshall.php";
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

            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (connecturl.equals("RetrieveData")) {
            pnumber = params[0];

            try{
                String address="http://192.168.43.194/FB_DATA/marshall_profile.php";
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

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processfinish(s);
    }
}
