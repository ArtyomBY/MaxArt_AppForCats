package com.maxart.appforcats.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maxart.appforcats.R;

public class RecommendationFragment extends Fragment {

    ListView recommendations_list;
    final String[] recommendations = {"Правила дрессировки", "Советы выбора игрушек", "Дневник настроения кота", "Инструкции самодельных игрушек"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recommendation, container, false);
        recommendations_list = v.findViewById(R.id.recommendations_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, recommendations);
        recommendations_list.setAdapter(adapter);

        return v;
    }
}
