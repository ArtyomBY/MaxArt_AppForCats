package com.maxart.appforcats.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        recommendations_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String title = adapterView.getItemAtPosition(position).toString();
                String url;



                if (title.equals("Правила дрессировки")){
                    url = "https://zen.yandex.ru/media/supercat/7-prostyh-pravil-kak-dressirovat-koshku-60a81281f264e45e6c5c8b52";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                else if (title.equals("Советы выбора игрушек")){
                    url = "https://sestratk.livejournal.com/431539.html";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                else if (title.equals("Дневник настроения кота")){
                    url = "https://www.purinaone.ru/cat/catmag/psychology/the-mood-of-the-cats";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                else if (title.equals("Инструкции самодельных игрушек")){
                    url = "https://zen.yandex.ru/media/snowdance/top-20-igrushek-dlia-koshek-svoimi-rukami-koshka-budet-v-vostorge-607782eaf63f032f258fda6b";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }



            }
        });

        return v;
    }
}
