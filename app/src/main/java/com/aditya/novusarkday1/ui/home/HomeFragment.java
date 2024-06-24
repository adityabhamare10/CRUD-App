package com.aditya.novusarkday1.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.aditya.novusarkday1.R;
import com.aditya.novusarkday1.viewModel.AddingDataViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private AddingDataViewModel addingDataViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        addingDataViewModel = new ViewModelProvider(requireActivity()).get(AddingDataViewModel.class);
        addingDataViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> data) {
                adapter.clear();
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_add);
            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            showUpdateDialog(position);
        });

        listView.setOnItemLongClickListener((parent, view1, position, id) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete Details")
                    .setMessage("Are you sure you want to delete this details ?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        addingDataViewModel.removeFromList(position);
                        Toast.makeText(getContext(), "Data deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });





        return view;
    }
    private void showUpdateDialog(int position) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.layout_dialogue_update, null);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        EditText edtNumber = dialogView.findViewById(R.id.edtNumber);

        String user = adapter.getItem(position);
        if (user != null) {
            String[] parts = user.split("\n");
            edtName.setText(parts[0].split(": ")[1]);
            edtEmail.setText(parts[1].split(": ")[1]);
            edtNumber.setText(parts[2].split(": ")[1]);
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Update User")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedUser = "Name: " + edtName.getText().toString()
                            + "\nEmail: " + edtEmail.getText().toString()
                            + "\nMobile: " + edtNumber.getText().toString();
                    AddingDataViewModel.updateData(position, updatedUser);
                    Toast.makeText(getContext(), "User updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
