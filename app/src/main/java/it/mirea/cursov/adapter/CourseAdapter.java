package it.mirea.cursov.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.mirea.cursov.model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Course> courses;

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static final class CourseViewHolder extends RecyclerView.ViewHolder{

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
