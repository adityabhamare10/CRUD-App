package com.aditya.novusarkday1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
            public void onChanged(List<String> users) {
                adapter.clear();
                adapter.addAll(users);
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

        return view;
    }
}
