package com.aditya.novusarkday1.ui.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aditya.novusarkday1.R;
import com.aditya.novusarkday1.databinding.FragmentAddBinding;
import com.aditya.novusarkday1.viewModel.AddingDataViewModel;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private AddingDataViewModel addingDataViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        addingDataViewModel = new ViewModelProvider(requireActivity()).get(AddingDataViewModel.class);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtName.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String mobile = binding.edtNumber.getText().toString();


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String data = "Name: " + name + "\nEmail: " + email + "\nMobile: " + mobile;
                    addingDataViewModel.addtoList(data);
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
