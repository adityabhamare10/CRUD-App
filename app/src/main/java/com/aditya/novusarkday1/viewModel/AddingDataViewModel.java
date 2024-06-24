package com.aditya.novusarkday1.viewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddingDataViewModel extends ViewModel {
    private final MutableLiveData<List<String>> listLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getList() {
        return listLiveData;
    }

    public void addtoList(String data) {
        List<String> currentList = listLiveData.getValue();
        if (currentList != null) {
            currentList.add(data);
            listLiveData.setValue(currentList);
        }
    }
}
