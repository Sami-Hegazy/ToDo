package com.example.toDo.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.toDo.data.datbase.AppDataBase;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDataBase mDb;
    private final int mTaskId;

    public AddTaskViewModelFactory(AppDataBase mDb, int mTaskId) {
        this.mDb = mDb;
        this.mTaskId = mTaskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(mDb , mTaskId);
    }
}
