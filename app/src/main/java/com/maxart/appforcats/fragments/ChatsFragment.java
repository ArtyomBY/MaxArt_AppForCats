package com.maxart.appforcats.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.maxart.appforcats.R;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    ListView chats_list;
    SearchView searchView;
    final String[] chats_array = {"Кот Валера", "Кошка Маша", "Кот Аркадий", "Беседа ..."};
    final ArrayList<String> chats = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        chats_list = v.findViewById(R.id.chats_list);
        searchView = v.findViewById(R.id.search_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chats_array);
        chats_list.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        if (chats.contains(query)) {
                            adapter.getFilter().filter(query);
                        }
                        else {
                            Toast.makeText(getContext(), "Not found", Toast.LENGTH_LONG).show();
                        }

                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });


        return v;
    }
}
