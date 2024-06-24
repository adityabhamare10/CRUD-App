package com.aditya.novusarkday1.viewModel;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddingDataViewModel extends ViewModel {
    private static final MutableLiveData<List<String>> listLiveData = new MutableLiveData<>(new ArrayList<>());


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

        public void removeFromList(int position) {
            List<String> currentList = listLiveData.getValue();
            if (currentList != null && position >= 0 && position < currentList.size()) {
                currentList.remove(position);
                listLiveData.setValue(currentList);
            }
        }

    public static void updateData(int position, String updatedUser) {
        List<String> currentList = listLiveData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.set(position, updatedUser);
            listLiveData.setValue(currentList);
//            saveUsers();
        }
    }

}
