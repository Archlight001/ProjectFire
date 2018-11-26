package denokela.com.projectfire;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Image_FullScreen extends AppCompatActivity {

    ImageView largepic;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__full_screen);
        largepic = (ImageView) findViewById(R.id.imageViewfullscreen);
        String picurl = getIntent().getExtras().getString("picurl");
        new grabImageFromURL(largepic).execute(picurl);

    }


}


class grabImageFromURL extends AsyncTask<String,Void,Bitmap>
{
    Bitmap bitmap;

    ImageView imgView;
    public grabImageFromURL(ImageView imgv)
    {
        this.imgView=imgv;
    }
    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay=url[0];
        bitmap=null;

        try{

            InputStream ist=new java.net.URL(urldisplay).openStream();
            bitmap= BitmapFactory.decodeStream(ist);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){

        super.onPostExecute(bitmap);
        imgView.setImageBitmap(bitmap);

    }

}
