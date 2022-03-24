package com.example.toDo.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.toDo.ui.view.activities.MainActivity;
import com.example.toDo.data.datbase.AppDataBase;
import com.example.toDo.data.models.TaskEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<TaskEntry>> tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDataBase dataBase = AppDataBase.getInstance(this.getApplication());
        tasks = dataBase.taskDao().loadAllTasks();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }
}
