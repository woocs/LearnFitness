package com.example.woo.learnfitness;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by woo on 12/22/2017.
 */

public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Activity context, int resource, List<Video> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.home_video, parent, false);

        TextView textViewVideoTitle, textViewVideoCategories, textViewLike, textViewFavorite, textViewName;
        final VideoView videoView;

        videoView = (VideoView)rowView.findViewById(R.id.videoView);
        textViewVideoTitle = (TextView)rowView.findViewById(R.id.textViewVideoTitle);
        textViewVideoCategories = (TextView) rowView.findViewById(R.id.textViewVideoCategories);
        textViewLike = (TextView) rowView.findViewById(R.id.textViewLike);
        textViewFavorite = (TextView) rowView.findViewById(R.id.textViewFavorite);
        textViewName = (TextView) rowView.findViewById(R.id.textViewName);


        videoView.setVideoPath(video.getVideo());
        videoView.pause();
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer vmp) {
                videoView.stopPlayback();
            }
        });

        textViewVideoTitle.setText(video.getTitle());
        textViewVideoCategories.setText(video.getCategories());
        textViewFavorite.setText(video.getFavorite());
        textViewLike.setText(video.getLikes());
        textViewName.setText(video.getId());
        return rowView;
    }


}

