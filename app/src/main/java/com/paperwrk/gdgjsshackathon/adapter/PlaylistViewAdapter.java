package com.paperwrk.gdgjsshackathon.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.paperwrk.gdgjsshackathon.ActivityPlayer;
import com.paperwrk.gdgjsshackathon.R;
import com.paperwrk.gdgjsshackathon.fragments.PlaylistViewFragment;
import com.paperwrk.gdgjsshackathon.utils.model.PlaylistVideos;
import com.squareup.picasso.Picasso;

public class PlaylistViewAdapter extends
        RecyclerView.Adapter<PlaylistViewAdapter.ViewHolder> /*implements Filterable*/{


    private final PlaylistVideos mPlaylistVideos;
    private final PlaylistViewFragment.ItemReachedListener mListener;




    static class ViewHolder extends RecyclerView.ViewHolder {
        final Context mContext;
        final TextView mTitleText;
        final ImageView mThumbnailImage;

        ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            mTitleText =  v.findViewById(R.id.video_title);
            mThumbnailImage = v.findViewById(R.id.video_thumbnail);
        }
    }

    public PlaylistViewAdapter(PlaylistVideos playlistVideos, PlaylistViewFragment.ItemReachedListener lastItemReachedListener) {
        mPlaylistVideos = playlistVideos;
        mListener = lastItemReachedListener;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a card layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_card, parent, false);
        // populate the viewholder
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mPlaylistVideos.size() == 0) {
            return;
        }

        final Video video = mPlaylistVideos.get(position);
        final VideoSnippet videoSnippet = video.getSnippet();

        holder.mTitleText.setText(videoSnippet.getTitle());

        // load the video thumbnail image
        Picasso.with(holder.mContext)
                .load(videoSnippet.getThumbnails().getHigh().getUrl())
                .placeholder(R.drawable.video_placeholder)
                .into(holder.mThumbnailImage);

        // set the click listener to play the video
        holder.mThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ActivityPlayer.class);
                intent.putExtra("videoID",video.getId());
                holder.mContext.startActivity(intent);
            }
        });


        if (mListener != null) {
            // get the next playlist page if we're at the end of the current page and we have another page to get
            final String nextPageToken = mPlaylistVideos.getNextPageToken();
            if (!isEmpty(nextPageToken) && position == mPlaylistVideos.size() - 1) {
                holder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onLastItem(holder.getAdapterPosition(), nextPageToken);
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return mPlaylistVideos.size();
    }

    private boolean isEmpty(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

}