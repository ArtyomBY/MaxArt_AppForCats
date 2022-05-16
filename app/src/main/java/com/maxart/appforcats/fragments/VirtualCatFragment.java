package com.maxart.appforcats.fragments;

import android.media.MediaPlayer;
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
    TextView catsName;
    ImageView catsPicture;
    ImageButton stopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        VirtualCat virtualCat = new VirtualCat();

        View v = inflater.inflate(R.layout.fragment_virtual_cat, container, false);

        catsName = v.findViewById(R.id.tv_cats_name);
        catsName.setText(virtualCat.getName());

        catsPicture = v.findViewById(R.id.cats_picture);
        catsPicture.setImageResource(virtualCat.getProfile_picture());

        stopButton = v.findViewById(R.id.phone_disabled_btn);
        stopButton.setOnClickListener(view -> {
            stopPlay();
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.replaceFragment(new GamesFragment());
        });

        mp = MediaPlayer.create(getContext(), virtualCat.getAudio());
        mp.setOnCompletionListener(mediaPlayer -> stopPlay());

        return v;
    }

    private void stopPlay() {
        mp.stop();
        try {
            mp.prepare();
            mp.seekTo(0);
        } catch (Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlay();
    }
}
