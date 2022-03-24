package com.example.toDo.ui.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import com.example.toDo.data.AppExecutors;
import com.example.toDo.R;
import com.example.toDo.data.datbase.AppDataBase;
import com.example.toDo.data.models.TaskEntry;
import com.example.toDo.ui.view.adapters.TaskAdapter;
import com.example.toDo.ui.viewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private AppDataBase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    int position = viewHolder.getBindingAdapterPosition();
                    List<TaskEntry> taskEntries = mAdapter.getTasks();
                    mDb.taskDao().deleteTask(taskEntries.get(position));
                });
            }
        }).attachToRecyclerView(mRecyclerView);

          /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(view -> {
            // Create a new intent to start an AddTaskActivity
            Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(addTaskIntent);
        });

        mDb = AppDataBase.getInstance(getApplicationContext());
        setUpViewModel();
    }

    private void setUpViewModel() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this , taskEntries -> {
            mAdapter.setTasks(taskEntries);
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(MainActivity.this , AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID , itemId);
        startActivity(intent);
    }
}