package com.proptit.nghiadv.diemthiptit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proptit.nghiadv.diemthiptit.R;
import com.proptit.nghiadv.diemthiptit.model.ItemSetting;

import java.util.ArrayList;

/**
 * Created by nghia on 7/25/2017.
 */

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    TextView tv_description;
    ArrayList<ItemSetting> itemList;
    LayoutInflater inflater;


    public ListViewAdapter(Context context, ArrayList<ItemSetting> itemList) {
        this.itemList = itemList;
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderListView holderListView = new HolderListView();

        View rootView = inflater.inflate(R.layout.item_list_view, null);

        holderListView.image_icon = (ImageView) rootView.findViewById(R.id.imageView);
        holderListView.textView = (TextView) rootView.findViewById(R.id.tv_description);
        holderListView.image_button = (ImageView) rootView.findViewById(R.id.image_button);

        ItemSetting item = itemList.get(position);

        holderListView.textView.setText(item.description);
        holderListView.image_icon.setImageResource(item.idRes);
        return rootView;
    }

}
