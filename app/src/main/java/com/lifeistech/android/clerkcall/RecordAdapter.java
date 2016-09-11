package com.lifeistech.android.clerkcall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by asuka on 16/04/03.
 */
public class RecordAdapter extends ArrayAdapter<File> {
    LayoutInflater inflater;

    public RecordAdapter(Context context) {
        super(context, R.layout.item_record);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_record, parent, false);
        }
        TextView nameText = (TextView) convertView.findViewById(R.id.textName);
        TextView dateText = (TextView) convertView.findViewById(R.id.textDate);
        File file = getItem(position);
        nameText.setText(file.getName());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        dateText.setText(sdf.format(date));

        return convertView;
    }


}
