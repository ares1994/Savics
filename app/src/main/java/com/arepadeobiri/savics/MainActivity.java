package com.arepadeobiri.savics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.arepadeobiri.savics.databinding.ActivityMainBinding;
import com.arepadeobiri.savics.fragments.MainFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        drawerLayout = binding.drawerLayout;
        setSupportActionBar(binding.toolbar);


        SharedPreferences pref = this.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);


        pref.edit().putInt(getString(R.string.current_no), 0).apply();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(binding.navView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, NavDestination destination, Bundle arguments) {
                if (destination.getId() == R.id.mainFragment) {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
            }
        });


        NavigationView navigationView = findViewById(R.id.navView);
        MenuItem share = navigationView.getMenu().findItem(R.id.nav_share);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                String patients = "";

                for (int i = 0; i < MainFragment.list.size(); i++) {
                    patients = MainFragment.list.get(i).toString();
                }

                intent.setType("text/plain");
                intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Here is the current list of patients:" +
                                patients
                );

                startActivity(intent);
                return true;
            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout);
    }
}