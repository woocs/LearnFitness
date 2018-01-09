package com.example.woo.learnfitness;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

/**
 * Created by woo on 12/22/2017.
 */

public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Context context, int resource, List<Video> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.home_video, parent, false);

        final TextView textViewVideoTitle, textViewVideoCategories, textViewLike, textViewFavorite, textViewName;
        final VideoView videoView;
        final ImageButton imageButtonFavorite;
        final Button buttonLike;

        videoView = (VideoView)rowView.findViewById(R.id.videoView);
        textViewVideoTitle = (TextView)rowView.findViewById(R.id.textViewVideoTitle);
        textViewVideoCategories = (TextView) rowView.findViewById(R.id.textViewVideoCategories);
        textViewLike = (TextView) rowView.findViewById(R.id.textViewLike);
        textViewFavorite = (TextView) rowView.findViewById(R.id.textViewFavorite);
        textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        imageButtonFavorite = (ImageButton) rowView.findViewById(R.id.imageButtonFavorite);
        buttonLike = (Button) rowView.findViewById(R.id.buttonLike);

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

        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewFavorite.getText().toString().equalsIgnoreCase("not")) {
                    textViewFavorite.setText("yes");
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                }else{
                    textViewFavorite.setText("not");
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        });

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int like = Integer.parseInt(textViewLike.getText().toString());
                int total;
                if(buttonLike.getText().equals("like")) {
                    total = like + 1;
                    textViewLike.setText(String.valueOf(total));
                    buttonLike.setText("liked");
                }else{
                    total = like - 1;
                    textViewLike.setText(String.valueOf(total));
                    buttonLike.setText("like");
                }
            }
        });
        return rowView;
    }


}

