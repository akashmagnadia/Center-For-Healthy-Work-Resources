package com.armcomptech.akash.cfhw_resources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.armcomptech.akash.cfhw_resources.R;
import com.armcomptech.akash.cfhw_resources.model.MyDataModel;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<MyDataModel> {

    private List<MyDataModel> modelList;
    private Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapter(Context context, List<MyDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        //to change
        //to change Visibility when N/A
        if (item.getOrganization() .equals("N/A")) {
            vh.textViewOrganization.setVisibility(View.GONE);
        } else {
            vh.textViewOrganization.setText(item.getOrganization());
        }

        if (item.getDescription() .equals("N/A")) {
            vh.textViewDescription.setVisibility(View.GONE);
        } else {
            vh.textViewDescription.setText(item.getDescription());
        }

        if (item.getLocation() .equals("N/A")) {
            vh.textViewLocation.setVisibility(View.GONE);
        } else {
            vh.textViewLocation.setText(item.getLocation());
        }

        if (item.getWebsite() .equals("N/A")) {
            vh.textViewWebsite.setVisibility(View.GONE);
        } else {
            vh.textViewWebsite.setText(item.getWebsite());
        }

        if (item.getPhone_Number() .equals("N/A")) {
            vh.textViewPhone_Number.setVisibility(View.GONE);
        } else {
            vh.textViewPhone_Number.setText(item.getPhone_Number());
        }

        if (item.getEmail() .equals("N/A")) {
            vh.textViewEmail.setVisibility(View.GONE);
        } else {
            vh.textViewEmail.setText(item.getEmail());
        }

        return vh.rootView;
    }

    private static class ViewHolder {
        final RelativeLayout rootView;

        //to change
        final TextView textViewOrganization;
        final TextView textViewDescription;
        final TextView textViewLocation;
        final TextView textViewWebsite;
        final TextView textViewPhone_Number;
        final TextView textViewEmail;

        //to change
        private ViewHolder(RelativeLayout rootView, TextView textViewOrganization, TextView textViewDescription, TextView textViewLocation, TextView textViewWebsite, TextView textViewPhone_Number, TextView textViewEmail) {
            this.textViewOrganization = textViewOrganization;
            this.textViewDescription = textViewDescription;
            this.textViewLocation = textViewLocation;
            this.textViewWebsite = textViewWebsite;
            this.textViewPhone_Number = textViewPhone_Number;
            this.textViewEmail = textViewEmail;
            this.rootView = rootView;
        }

        //to change
        static ViewHolder create(RelativeLayout rootView) {
            TextView textViewOrganization = rootView.findViewById(R.id.textViewOrganization);
            TextView textViewDescription = rootView.findViewById(R.id.textViewDescription);
            TextView textViewLocation = rootView.findViewById(R.id.textViewLocation);
            TextView textViewWebsite = rootView.findViewById(R.id.textViewWebsite);
            TextView textViewPhone_Number = rootView.findViewById(R.id.textViewPhone_Number);
            TextView textViewEmail = rootView.findViewById(R.id.textViewEmail);
            return new ViewHolder(rootView, textViewOrganization, textViewDescription, textViewLocation, textViewWebsite, textViewPhone_Number, textViewEmail);
        }
    }
}