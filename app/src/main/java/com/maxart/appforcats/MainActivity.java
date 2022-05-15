package com.maxart.appforcats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maxart.appforcats.fragments.ChatsFragment;
import com.maxart.appforcats.fragments.GamesFragment;
import com.maxart.appforcats.fragments.RecommendationFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Для корректной работы приложения включите интернет", Toast.LENGTH_SHORT).show();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.games_menu);
        replaceFragment(new GamesFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.chats_menu) {
                replaceFragment(new ChatsFragment());
            }
            else if (itemId == R.id.games_menu) {
                replaceFragment(new GamesFragment());
            }
            else if (itemId == R.id.recommendation_menu) {
                replaceFragment(new RecommendationFragment());
            }
            return true;
        });

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
