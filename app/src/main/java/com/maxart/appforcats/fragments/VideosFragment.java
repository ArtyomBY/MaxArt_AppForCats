package com.maxart.appforcats.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.maxart.appforcats.R;
import com.maxart.appforcats.Video;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);

        ListView list = view.findViewById(R.id.videos_list);
        List<Video> videos = new ArrayList<>();
        videos.add(new Video("Video 1", "https://media.istockphoto.com/videos/animation-art-looping-graphic-animation-the-rotating-head-of-a-cat-in-video-id1320486503"));
        videos.add(new Video("Video 2", "https://media.istockphoto.com/videos/funny-gray-tabby-cat-is-resting-on-the-blue-sofa-and-yawning-two-up-video-id1288348337"));
        list.setAdapter(new VideoAdapter(requireContext(), videos));

        return view;
    }

    static private class VideoAdapter extends ArrayAdapter<Video> {

        public VideoAdapter(@NonNull Context context, @NonNull List<Video> objects) {
            super(context, R.layout.layout_video_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.layout_video_item, parent, false);
            }

            TextView textView = convertView.findViewById(R.id.video_name);
            textView.setText(getItem(position).name);

            VideoView videoView = convertView.findViewById(R.id.video_view);
            videoView.setVideoURI(Uri.parse(getItem(position).uri));
            videoView.setMediaController(null);
            videoView.requestFocus(0);

            videoView.setOnClickListener(v -> {
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                else {
                    videoView.start();
                }
            });

            return convertView;
        }
    }
}
