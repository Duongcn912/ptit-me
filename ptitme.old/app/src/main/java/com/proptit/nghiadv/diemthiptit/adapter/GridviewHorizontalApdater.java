package com.proptit.nghiadv.diemthiptit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.Lesson;

import java.util.ArrayList;

/**
 * Created by nghia on 7/14/2017.
 */

public class GridviewHorizontalApdater extends BaseAdapter {
    Context mContext;
    TextView tv_room, tv_subject;
    ArrayList<Lesson> listLesson;
    LayoutInflater inflater;


    public GridviewHorizontalApdater(Context context, ArrayList<Lesson> listLesson) {
        this.listLesson = listLesson;
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listLesson.size();
    }

    @Override
    public Object getItem(int position) {
        return listLesson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderGridView holderGridView = new HolderGridView();
        View cellView = inflater.inflate(R.layout.cell_layout, null);

        holderGridView.tv_room = (TextView) cellView.findViewById(R.id.tv_room);
        holderGridView.tv_subject = (TextView) cellView.findViewById(R.id.tv_subject);

        Lesson currentLesson = listLesson.get(position);

        holderGridView.tv_room.setText(currentLesson.getPhongHoc());
        holderGridView.tv_subject.setText(currentLesson.getTenMon());
        return cellView;
    }

}
