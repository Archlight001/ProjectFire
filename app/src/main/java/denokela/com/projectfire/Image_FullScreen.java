package denokela.com.projectfire;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Image_FullScreen extends AppCompatActivity {

    ImageView largepic;
    String fname, sname;
    String rand;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__full_screen);
        largepic = (ImageView) findViewById(R.id.imageViewfullscreen);
        String picurl = getIntent().getExtras().getString("picurl");
        fname = getIntent().getExtras().getString("fname");
        sname = getIntent().getExtras().getString("sname");
        new grabImageFromURL(largepic).execute(picurl);

    }


    public void DownloadImage(View view) {

        OutputStream fout = null;
        largepic.buildDrawingCache();
        Bitmap imgbitmap = largepic.getDrawingCache();
        rand = Integer.toString(new Random().nextInt(10) + 1);
        try {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Firebrand" + File.separator);
            folder.mkdirs();
            File sdImagemainDirectory = new File(folder, fname + sname + rand + ".jpg");
            fout = new FileOutputStream(sdImagemainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, "An error Occured please try again later", Toast.LENGTH_LONG).show();
        }
        try {
            imgbitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Couldn't save file", Toast.LENGTH_LONG).show();
        }
    }
}


class grabImageFromURL extends AsyncTask<String, Void, Bitmap> {
    Bitmap bitmap;

    ImageView imgView;

    public grabImageFromURL(ImageView imgv) {
        this.imgView = imgv;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        bitmap = null;

        try {

            InputStream ist = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(ist);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        if (bitmap != null)
            imgView.setImageBitmap(bitmap);

    }

}
