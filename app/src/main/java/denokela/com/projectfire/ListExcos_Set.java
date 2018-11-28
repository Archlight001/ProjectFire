package denokela.com.projectfire;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListExcos_Set extends Fragment {

    ListView list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list_excos__set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=(ListView) view.findViewById(R.id.excolistset);
        String [] years = {"2010","2011","2012","2013","2014","2015","2016","2017","2018","2019"
        ,"2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
        ListAdapter viewadapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,years);
        list.setAdapter(viewadapter);

    }
}
