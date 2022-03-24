package com.example.toDo.data.datbase;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.toDo.data.models.TaskEntry;

@Database(entities = {TaskEntry.class} , version = 1 , exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDataBase extends RoomDatabase {
    public static AppDataBase sInstance;
    private static final String LOG_TAG = AppDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";

    public static AppDataBase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating New Database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class,
                        AppDataBase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract TaskDao taskDao();
}
