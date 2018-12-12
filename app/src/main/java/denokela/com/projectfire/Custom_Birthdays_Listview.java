package denokela.com.projectfire;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_Birthdays_Listview extends ArrayAdapter<String> {

    private String[] firstname;
    private String[] middlename;
    private String[] surname;
    private String[] bday;
    private String[] bmonth;
    private String[] unitstatus;
    private Activity context;

    public Custom_Birthdays_Listview(Activity context, String[] firstname , String[] middlename,String[] surname, String[] bday, String[] bmonth,String[] unitstatus) {
        super(context,R.layout.listviewlayout,firstname);
        this.context=context;
        this.firstname=firstname;
        this.middlename=middlename;
        this.surname = surname;
        this.bday = bday;
        this.bmonth = bmonth;
        this.unitstatus = unitstatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View viewer=convertView;
        BViewholder bViewholder=null;
        if(viewer==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            viewer=layoutInflater.inflate(R.layout.activity_birthdays__of__the__month,null,true);
            bViewholder= new BViewholder(viewer);
            viewer.setTag(bViewholder);
        }
        else{
            bViewholder = (BViewholder) viewer.getTag();
        }
        String nameholder = firstname[position] + " "+middlename[position]+ " "+ surname[position];
        String dateholder = bday[position] + " "+ bmonth[position];
        String status = unitstatus[position];

        bViewholder.tvname.setText(nameholder);
        bViewholder.tvdate.setText(dateholder);
        bViewholder.tvstatus.setText(status);
        return viewer;
    }

    class BViewholder{
        TextView tvname,tvdate,tvstatus;
        BViewholder(View v){
            tvname = (TextView) v.findViewById(R.id.bday_profile_name);
            tvdate = (TextView) v.findViewById(R.id.bday_date);
            tvstatus = (TextView) v.findViewById(R.id.bday_unitstatus);
        }
    }
}
