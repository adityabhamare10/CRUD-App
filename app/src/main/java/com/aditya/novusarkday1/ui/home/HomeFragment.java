package com.aditya.novusarkday1.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private LinearLayout container;
    private AddingDataViewModel addingDataViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.container = view.findViewById(R.id.container);

        addingDataViewModel = new ViewModelProvider(requireActivity()).get(AddingDataViewModel.class);
        addingDataViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> data) {
                updateUI(data);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_add);
            }
        });

        return view;
    }

    private void updateUI(List<String> data) {
        container.removeAllViews();

        for (int i = 0; i < data.size(); i++) {
            String user = data.get(i);
            View userItemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_items, container, false);

            TextView nameTextView = userItemView.findViewById(R.id.name);
            TextView emailTextView = userItemView.findViewById(R.id.email);
            TextView numberTextView = userItemView.findViewById(R.id.number);

            String[] parts = user.split("\n");
            if (parts.length >= 3) {
                nameTextView.setText(parts[0].split(": ")[1]);
                emailTextView.setText(parts[1].split(": ")[1]);
                numberTextView.setText(parts[2].split(": ")[1]);
            } else {
                nameTextView.setText("Name not available");
                emailTextView.setText("Email not available");
                numberTextView.setText("Number not available");
            }

            final int position = i;

            userItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUpdateDialog(position);
                }
            });

            userItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDeleteDialog(position);
                    return true;
                }
            });

            container.addView(userItemView);
        }
    }

    private void showUpdateDialog(int position) {
        List<String> dataList = addingDataViewModel.getList().getValue();
        if (dataList == null || position < 0 || position >= dataList.size()) {
            return; // Ensure dataList is not null and position is within bounds
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.layout_dialogue_update, null);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtEmail = dialogView.findViewById(R.id.edtEmail);
        EditText edtNumber = dialogView.findViewById(R.id.edtNumber);

        String user = dataList.get(position);
        if (user != null) {
            String[] parts = user.split("\n");
            if (parts.length >= 3) { // Ensure parts has enough elements to prevent ArrayIndexOutOfBoundsException
                edtName.setText(parts[0].split(": ")[1]);
                edtEmail.setText(parts[1].split(": ")[1]);
                edtNumber.setText(parts[2].split(": ")[1]);
            }
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Update User")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedUser = "Name: " + edtName.getText().toString()
                            + "\nEmail: " + edtEmail.getText().toString()
                            + "\nMobile: " + edtNumber.getText().toString();
                    addingDataViewModel.updateData(position, updatedUser);
                    Toast.makeText(getContext(), "User updated", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    private void showDeleteDialog(int position) {
        List<String> dataList = addingDataViewModel.getList().getValue();
        if (dataList == null || position < 0 || position >= dataList.size()) {
            return; // Ensure dataList is not null and position is within bounds
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    addingDataViewModel.removeFromList(position);
                    Toast.makeText(getContext(), "User deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
