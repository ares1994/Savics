package com.arepadeobiri.savics.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.arepadeobiri.savics.MainActivity;
import com.arepadeobiri.savics.Patient;
import com.arepadeobiri.savics.R;
import com.arepadeobiri.savics.databinding.FragmentMainBinding;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    public static ArrayList<Patient> list = new ArrayList<>();


    private FragmentMainBinding binding;
    private SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pref = this.requireActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);


        binding = FragmentMainBinding.bind(view);


        binding.patientListview.setAdapter(new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1));


        if (pref.getInt(getString(R.string.max_no), -1) == -1) {
            pref.edit().putInt(getString(R.string.max_no), 5).apply();
        }


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNullOrBlank(binding.fullNameEditText) || isNullOrBlank(binding.emailEditText) || isNullOrBlank(binding.ageEditText)) {
                    Toast.makeText(requireContext(), "Please ensure all fields are filled", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!binding.male.isChecked() && !binding.female.isChecked() && !binding.other.isChecked()) {
                    Toast.makeText(requireContext(), "Please select a gender", Toast.LENGTH_LONG).show();
                    return;
                }

                String gender = "";

                if (binding.male.isChecked()) gender = "M";
                else if (binding.female.isChecked()) gender = "F";
                else gender = "O";


                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getFullName().equals(binding.fullNameEditText.getText().toString().trim())) {
                        Toast.makeText(requireContext(), "Hi again " + binding.fullNameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                list.add(new Patient(binding.fullNameEditText.getText().toString().trim(), gender, Integer.parseInt(binding.ageEditText.getText().toString())));


//                ((MainActivity) requireActivity()).


                pref.edit().putInt(getString(R.string.current_no), list.size()).apply();


                binding.patientListview.setAdapter(new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, list));

            }
        });


    }

    private boolean isNullOrBlank(EditText editText) {
        return editText.getText() == null || editText.getText().length() == 0;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.reset) {

            if (list.size() == 0) {
                Toast.makeText(this.requireContext(), "Patient list is already empty", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }

            list.clear();

            binding.patientListview.setAdapter(new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, list));
            pref.edit().putInt(getString(R.string.current_no), list.size()).apply();

            Toast.makeText(this.requireContext(), "Patient list cleared successfully", Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.patientListview.setAdapter(new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, list));

    }
}