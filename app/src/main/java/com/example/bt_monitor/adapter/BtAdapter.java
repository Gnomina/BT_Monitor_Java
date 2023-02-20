package com.example.bt_monitor.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bt_monitor.R;

import java.util.ArrayList;
import java.util.List;

public class BtAdapter extends ArrayAdapter<ListItem> {
    public static final String DEF_ITEM_TYPE = "normal";
    public static final String TITLE_ITEM_TYPE = "title";
    public static final String DISCOVERY_ITEM_TYPE = "discovery";
    private List<ListItem> mainList;
    private List<ViewHolder> listViewHolders;
    private SharedPreferences pref;
    private boolean isDiscoveryType = false;

    public BtAdapter(@NonNull Context context, int resource, List<ListItem> btList) {
        super(context, resource, btList);
        mainList = btList;
        listViewHolders = new ArrayList<>();
        pref = context.getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        switch (mainList.get(position).getItemType()){
            case TITLE_ITEM_TYPE: convertView = titleItem(convertView,parent);
                break;
            default: convertView = defaultItem(convertView, position, parent);
            break;
        }
        //convertView = defaultItem(convertView, position, parent);

        return convertView;

    }

    private void savePref(int pos){//сохранение мака в памяти
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(BtConsts.MAC_KEY, mainList.get(pos).getBtDevice().getAddress());
        editor.apply();
    }

    static class ViewHolder{

        TextView tvBtName;
        CheckBox chBtSelected;
    }

    private View defaultItem(View convertView, int position, ViewGroup parent){
        //шаблон активити
        ViewHolder viewHolder;
        boolean hasViewHolder = false;
        if(convertView != null) hasViewHolder = (convertView.getTag() instanceof ViewHolder);
        if (convertView == null || !hasViewHolder) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_list_item,
                    null,false);

            viewHolder.tvBtName = convertView.findViewById(R.id.tvBtName);
            viewHolder.chBtSelected = convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
            listViewHolders.add(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.chBtSelected.setChecked(false);
        }
        if(mainList.get(position).getItemType().equals(BtAdapter.DISCOVERY_ITEM_TYPE)) {
            viewHolder.chBtSelected.setVisibility(View.GONE);
            isDiscoveryType = true;
        }else {
            viewHolder.chBtSelected.setVisibility(View.VISIBLE);
            isDiscoveryType = false;
        }
        viewHolder.tvBtName.setText(mainList.get(position).getBtDevice().getName());
        viewHolder.chBtSelected.setOnClickListener(view -> {

            if(!isDiscoveryType)

            for(ViewHolder holder : listViewHolders){
                holder.chBtSelected.setChecked(false);
            }
            viewHolder.chBtSelected.setChecked(true);
            savePref(position);

        });
        if(pref.getString(BtConsts.MAC_KEY,"no bt selected")
                .equals(mainList.get(position).getBtDevice().getAddress()))viewHolder
                .chBtSelected.setChecked(true);
        isDiscoveryType = false;
        return convertView;
    }

    private View titleItem(View convertView, ViewGroup parent){
        boolean hasViewHolder = false;
        if(convertView !=null) hasViewHolder = (convertView.getTag() instanceof ViewHolder);
        if (convertView == null || hasViewHolder) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_list_item_title,
                    null, false);
        }
        return convertView;
    }


}
