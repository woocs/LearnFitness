package com.example.woo.learnfitness;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.VideoView;

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

        View rowView = inflater.inflate(R.layout.fragment_home, parent, false);

        TextView textViewVideoTitle;
        VideoView videoView;

        textViewVideoTitle = (TextView)rowView.findViewById(R.id.textViewVideoTitle);
        videoView = (VideoView)rowView.findViewById(R.id.videoView);


        textViewVideoTitle.setText(textViewVideoTitle.getText() + ":" +video.getVideo());
        videoView.setVideoURI(Uri.parse(video.getVideo()));


        return rowView;
    }
}

