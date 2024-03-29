package com.maxart.appforcats.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maxart.appforcats.CatchTheMouseActivity;
import com.maxart.appforcats.HitTheMoleActivity;
import com.maxart.appforcats.MainActivity;
import com.maxart.appforcats.R;

public class GamesFragment extends Fragment {

    ListView games_list;
    final String[] games = {"Стукни крота", "Поймай мышку", "Общение с виртуальными котами", "Видео животных со звуками"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_games, container, false);
        games_list = v.findViewById(R.id.games_list);
        GameAdapter adapter = new GameAdapter(requireContext(), games);
        games_list.setAdapter(adapter);
        return v;
    }

    private class GameAdapter extends ArrayAdapter<String> {

        public GameAdapter(@NonNull Context context, @NonNull String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            convertView.setOnClickListener(v -> {
                MainActivity mainActivity = (MainActivity) requireActivity();
                switch (((TextView)v).getText().toString()) {
                    case "Стукни крота":
                        Intent intent = new Intent(requireContext(), HitTheMoleActivity.class);
                        startActivity(intent);
                        break;
                    case "Поймай мышку":
                        Intent i = new Intent(requireContext(), CatchTheMouseActivity.class);
                        startActivity(i);
                        break;
                    case "Общение с виртуальными котами":
                        mainActivity.replaceFragment(new VirtualCatFragment());
                        break;
                    case "Видео животных со звуками":
                        mainActivity.replaceFragment(new VideosFragment());
                        break;
                }
            });

            return convertView;
        }
    }
}
