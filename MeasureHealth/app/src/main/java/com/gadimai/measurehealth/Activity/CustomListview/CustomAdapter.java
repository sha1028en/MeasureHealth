package com.gadimai.measurehealth.Activity.CustomListview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gadimai.measurehealth.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<CustomList> mList = new ArrayList<>();

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CustomList getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    public void addItem(String title, String subTitle){
        CustomList customList = new CustomList();

        customList.setTitle(title);
        customList.setsubTitle(subTitle);

        mList.add(customList);

    }

    public void addItem(List<String> item){
        try {
            for (int i = 0; i < item.size(); i += 2) {
                addItem(item.get(i), item.get(i + 1));
            }

        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        TextView titleTextview = convertView.findViewById(R.id.customListviewItemTitle);
        TextView phoneTextview = convertView.findViewById(R.id.customListviewItemSubTitle);

        CustomList customList = getItem(position);

        titleTextview.setText(customList.getTitle());
        phoneTextview.setText(customList.getsubTitle());

        return convertView;
    }
}
