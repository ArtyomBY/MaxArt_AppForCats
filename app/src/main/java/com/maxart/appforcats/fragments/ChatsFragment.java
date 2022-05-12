package com.maxart.appforcats.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxart.appforcats.Chat;
import com.maxart.appforcats.R;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    ListView chats_listView;
    SearchView searchView;
    final String[] chats_array = {"Кот Валера", "Кошка Маша", "Кот Аркадий", "Беседа ..."};
    final ArrayList<String> chats = new ArrayList<>();
    List<Chat> chats_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        chats_listView = v.findViewById(R.id.chats_list);
        searchView = v.findViewById(R.id.search_view);

        chats_list.add(new Chat("Кот Аркадий"));
        chats_list.add(new Chat("Кошка Маша"));
        chats_list.add(new Chat("Кот Борис"));
        chats_list.add(new Chat("Беседа ..."));

        ChatsAdapter chatsAdapter = new ChatsAdapter(getContext(), R.layout.layout_chat_item, chats_list);
        chats_listView.setAdapter(chatsAdapter);

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, chats_array);
        chats_listView.setAdapter(adapter);

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
                */


        return v;
    }


    private class ChatsAdapter extends ArrayAdapter<Chat>{

        public ChatsAdapter(@NonNull Context context, int resource, @NonNull List<Chat> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final Chat chat = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_chat_item, null);
            }

            ((ImageView) convertView.findViewById(R.id.profile_picture)).setImageResource(chat.getProfile_picture());
            ((TextView) convertView.findViewById(R.id.profile_name)).setText(chat.getName());



            return convertView;
        }
    }
}
