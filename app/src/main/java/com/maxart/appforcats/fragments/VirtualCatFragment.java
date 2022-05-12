package com.maxart.appforcats.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxart.appforcats.MainActivity;
import com.maxart.appforcats.R;
import com.maxart.appforcats.VirtualCat;

public class VirtualCatFragment extends Fragment {

    MediaPlayer mp;
    TextView cats_name;
    ImageView cats_picture;
    ImageButton btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        VirtualCat virtualCat = new VirtualCat();

        View v = inflater.inflate(R.layout.fragment_virtual_cat, container, false);

        cats_name = v.findViewById(R.id.tv_cats_name);
        cats_name.setText(virtualCat.getName());

        cats_picture = v.findViewById(R.id.cats_picture);
        cats_picture.setImageResource(virtualCat.getProfile_picture());

        btn = v.findViewById(R.id.phone_disabled_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlay();
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.replaceFragment(new GamesFragment());
            }
        });

        mp = MediaPlayer.create(getContext(), virtualCat.getAudio());
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopPlay();
            }
        });




        return v;
    }

    private void stopPlay() {
        mp.stop();
        try {
            mp.prepare();
            mp.seekTo(0);
        } catch (Throwable t) {
            Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}