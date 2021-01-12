package com.arepadeobiri.savics.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.arepadeobiri.savics.R;
import com.arepadeobiri.savics.databinding.FragmentMainBinding;
import com.arepadeobiri.savics.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pref = this.requireActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentSettingsBinding.bind(view);


        binding.currentUsernameEditText.setText(pref.getString(getString(R.string.current_username), ""));

        binding.maxNoEditText.setText(String.valueOf(pref.getInt(getString(R.string.max_no), 5)));


        binding.currentAddEditText.setText(String.valueOf(pref.getInt(getString(R.string.current_no), 0)));


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNullOrBlank(binding.currentAddEditText)) {
                    Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                    return;
                }


                if (isNullOrBlank(binding.maxNoEditText)) {
                    Toast.makeText(requireContext(), "Please enter a max number of users", Toast.LENGTH_LONG).show();
                    return;
                }

                pref.edit().putString(getString(R.string.current_username), binding.currentUsernameEditText.getText().toString()).putInt(getString(R.string.max_no), Integer.parseInt(binding.maxNoEditText.getText().toString())).apply();
                Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_LONG).show();

            }
        });

    }


    private boolean isNullOrBlank(EditText editText) {
        return editText.getText() == null || editText.getText().length() == 0;
    }


}