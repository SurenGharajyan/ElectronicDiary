package com.me.reactiveapp.adapter;



import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.me.reactiveapp.R;
import com.me.reactiveapp.model.DaysRememberModel;
import com.me.reactiveapp.model.RealmDaysRememberModel;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder>
        implements Filterable,RealmChangeListener {
    private ArrayList<DaysRememberModel> mArrayList;
    private ArrayList<DaysRememberModel> mFilteredList;
  //  RealmResults<RealmDaysRememberModel> realmResults;
    Realm realm;
    public DaysRecyclerViewAdapter(ArrayList<DaysRememberModel> mg) {
//        this.realmResults=realmResults;
        mArrayList = mg;
//        mArrayList.addAll(realmResults);
        mFilteredList = mArrayList;
//        mFilteredList.addAll(realmResults);

//        realmResults.addChangeListener(this);
    }

    @Override
    public DaysRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        if(mFilteredList.isEmpty()) {
//        }
            holder.tv_name.setText(mFilteredList.get(position).getTitle());
            holder.tv_version.setText(mFilteredList.get(position).getDay());
            holder.tv_api_level.setText(mFilteredList.get(position).getWithImage());

    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {

                    ArrayList<DaysRememberModel> filteredList = new ArrayList<>();

                    for (DaysRememberModel daysRememberModel : mArrayList) {

                        if (
                                   daysRememberModel.getTitle().toLowerCase().contains(charString)
                                || daysRememberModel.getWithImage().toLowerCase().contains(charString)
                                || daysRememberModel.getDay().toLowerCase().contains(charString)) {

                            filteredList.add(daysRememberModel);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;


            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DaysRememberModel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }



    @Override
    public void onChange() {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tv_api_level;
        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView)view.findViewById(R.id.tv_title);
            tv_version = (TextView)view.findViewById(R.id.tv_day);
            tv_api_level = (TextView)view.findViewById(R.id.tv_with_image);
        }
    }
}