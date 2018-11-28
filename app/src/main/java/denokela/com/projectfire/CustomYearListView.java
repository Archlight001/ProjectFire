package denokela.com.projectfire;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomYearListView extends ArrayAdapter<String> {
    private String[] year;
    private Activity context;
    Bitmap bitmap;

    public CustomYearListView(Activity context,String[] year) {
        super(context, R.layout.activity_custom_year_list_view,year);
        this.context=context;
        this.year=year;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        YearViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.activity_custom_year_list_view,null,true);
            viewHolder=new YearViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(YearViewHolder)r.getTag();

        }
        String yearholder = year[position];
        viewHolder.tvw1.setText(yearholder);
        return r;

    }

    class YearViewHolder{

        TextView tvw1;
        YearViewHolder(View v){
            tvw1=(TextView)v.findViewById(R.id.excotvlistyear);
        }

    }



}
