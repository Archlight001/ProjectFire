package denokela.com.projectfire;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.InputStream;

public class Custom_Exco_Listview extends ArrayAdapter<String> {
    private String[] firstname;
    private String[] middlename;
    private String[] surname;
    private String[] post;
    private String[] imagepath;
    private Activity context;
    Bitmap bitmap;

    public Custom_Exco_Listview(Activity context, String[] firstname, String[] middlename,String[] surname,String [] post, String[] imagepath) {
        super(context, R.layout.listviewlayout,surname);
        this.context=context;
        this.firstname=firstname;
        this.middlename=middlename;
        this.surname=surname;
        this.post = post;
        this.imagepath=imagepath;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        ExViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.activity_custom__exco__listview,null,true);
            viewHolder=new ExViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ExViewHolder)r.getTag();

        }
        String nameholder = firstname[position] + " "+middlename[position]+ " "+ surname[position];
        String postholder = post[position];
        viewHolder.tvw1.setText(nameholder);
        viewHolder.tvw2.setText(postholder);
        new ExGetImageFromURL(viewHolder.ivw).execute(imagepath[position]);

        return r;
    }

    class ExViewHolder{

        TextView tvw1,tvw2;
        CustomImageView ivw;

        ExViewHolder(View v){
            tvw1=(TextView)v.findViewById(R.id.excotvprofilename);
            tvw2=(TextView)v.findViewById(R.id.excotvpost);
            ivw=(CustomImageView) v.findViewById(R.id.excolistimview);
        }

    }

    public class ExGetImageFromURL extends AsyncTask<String,Void,Bitmap>
    {

        CustomImageView imgView;
        public ExGetImageFromURL(CustomImageView imgv)
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

}
