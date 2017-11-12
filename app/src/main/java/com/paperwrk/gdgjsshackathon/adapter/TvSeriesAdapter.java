package com.paperwrk.gdgjsshackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paperwrk.gdgjsshackathon.PlaylistActivity;
import com.paperwrk.gdgjsshackathon.R;
import com.paperwrk.gdgjsshackathon.utils.model.TvSeriesModel;

import java.util.ArrayList;

/**
 * Created by Kartik on 13-8-17.
 */

public class TvSeriesAdapter extends RecyclerView.Adapter<TvSeriesAdapter.ViewHolder> implements Filterable {

    private ArrayList<TvSeriesModel> mArrayList;
    private ArrayList<TvSeriesModel> mFilteredList;
    private Context context;


    public TvSeriesAdapter(ArrayList<TvSeriesModel> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context temp_Context = viewGroup.getContext();
        View view = LayoutInflater.from(temp_Context).inflate(R.layout.tv_series_card, viewGroup, false);
        view.setClickable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final TvSeriesModel model = mFilteredList.get(position);
        Context my_context = viewHolder.itemView.getContext();
        viewHolder.tv_name.setText(model.getName());
        viewHolder.tv_url.setText(model.getLinkPlaylist());
        Glide.with(my_context).load(model.getDrawable()).into(viewHolder.tv_logo);

        viewHolder.tv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playlist;
                playlist = model.getLinkPlaylist();
                Bundle bundle = new Bundle();
                bundle.putStringArray("videoID", new String[]{playlist});
                Intent intent = new Intent(view.getContext(), PlaylistActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                //Toast.makeText(view.getContext(),"pos is: " + model.getLinkPlaylist(), Toast.LENGTH_SHORT).show();
            }
        });
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

                    ArrayList<TvSeriesModel> filteredList = new ArrayList<>();

                    for (TvSeriesModel androidVersion : mArrayList) {

                        if (androidVersion.getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
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
                mFilteredList = (ArrayList<TvSeriesModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_url;
        private ImageView tv_logo;

        public ViewHolder(final View view) {
            super(view);
            tv_url =  view.findViewById(R.id.linkPlaylist);
            tv_name = view.findViewById(R.id.title);
            tv_logo = view.findViewById(R.id.thumbnail);

        }

    }

}
