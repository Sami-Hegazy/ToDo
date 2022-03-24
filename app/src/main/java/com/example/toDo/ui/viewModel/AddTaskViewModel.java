package com.example.toDo.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.toDo.data.datbase.AppDataBase;
import com.example.toDo.data.models.TaskEntry;

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDataBase dataBase , int taskId){
        task = dataBase.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
