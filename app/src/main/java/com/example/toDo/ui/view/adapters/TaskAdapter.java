package com.example.toDo.ui.view.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toDo.R;
import com.example.toDo.data.models.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private List<TaskEntry> mTaskEntries;
    private Context mContext;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    final private ItemClickListener mItemClickListener;

    public TaskAdapter(Context mContext, ItemClickListener mItemClickListener) {
        this.mContext = mContext;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tasks_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntry taskEntry = mTaskEntries.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        //Set values
        holder.textDescription.setText(description);
        holder.textUpdateTask.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityText.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityText.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null){
            return 0;
        }
        return mTaskEntries.size();
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public List<TaskEntry> getTasks(){
        return mTaskEntries;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textDescription;
        private TextView textUpdateTask;
        private TextView priorityText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescription = itemView.findViewById(R.id.taskDescription);
            textUpdateTask = itemView.findViewById(R.id.taskUpdatedAt);
            priorityText = itemView.findViewById(R.id.priorityTextView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int elementId = mTaskEntries.get(getBindingAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
