package com.maxart.appforcats.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.maxart.appforcats.R;
import com.maxart.appforcats.Video;

import java.io.IOException;
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

    private class VideoAdapter extends ArrayAdapter<Video> {

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
            Uri uri = Uri.parse(getItem(position).uri);
            videoView.setVideoURI(uri);
            videoView.setMediaController(null);
            videoView.requestFocus(0);

            /*ContentResolver contentResolver = requireContext().getContentResolver();
            try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                Bitmap preview = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    int thumbnailColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID);
                    int thumbnailId = cursor.getInt(thumbnailColumn);
                    Uri uri_ = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, thumbnailId);
                    preview = contentResolver.loadThumbnail(
                            uri_,
                            new Size(videoView.getWidth(), videoView.getHeight()),
                            null
                    );
                } else {
                    if (cursor.moveToFirst()) {
                        preview = MediaStore.Video.Thumbnails.getThumbnail(
                                contentResolver,
                                cursor.getInt(0),
                                MediaStore.Video.Thumbnails.MINI_KIND,
                                null
                        );
                    }
                }

                if (preview != null) {
                    videoView.setBackground(new BitmapDrawable(getResources(), preview));
                }
            }
            catch (IOException e) {
                Log.e("AAAAAAAAAAA", "getView: ", e);
                videoView.setBackgroundColor(getResources().getColor(R.color.purple_200));
            }*/

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
