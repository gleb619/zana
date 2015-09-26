package org.test.boris.testapp.data.api.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.test.boris.testapp.R;
import org.test.boris.testapp.data.api.Project;
import org.test.boris.testapp.data.api.domain.entity.CurrCourse;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BORIS on 26.07.2015.
 */
public class CurrCourseListAdapter extends RecyclerView.Adapter<CurrCourseListAdapter.ViewHolder> {

    private List<CurrCourse> currCourses;
    private Project project;
    ViewHolder.ViewHolderClickListener viewHolderClickListener;

    public CurrCourseListAdapter(Project project, ViewHolder.ViewHolderClickListener viewHolderClickListener) {
        this.project = project;
        this.viewHolderClickListener = viewHolderClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_curr_course, parent, false);
        return new ViewHolder(rowView, viewHolderClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrCourse currCourse = currCourses.get(position);
        holder.setRowId(currCourse.getId());
        holder.name.setText(currCourse.getCourse().toString());
        holder.description.setText(new SimpleDateFormat(project.getDATE_FORMAT()).format(currCourse.getDatecourse()).toString());
    }

    @Override
    public int getItemCount() {
        return currCourses == null ? 0 : currCourses.size();
    }

    public List<CurrCourse> getCurrCourses() {
        return currCourses;
    }

    public void setCurrCourses(List<CurrCourse> currCourses) {
        this.currCourses = currCourses;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int rowId;
        @Bind(R.id.name) TextView name;
        @Bind(R.id.description) TextView description;
        ViewHolderClickListener viewHolderClickListener;

        public ViewHolder(View itemView, ViewHolderClickListener viewHolderClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.viewHolderClickListener = viewHolderClickListener;
            itemView.setOnClickListener(this);
        }

        public int getRowId() {
            return rowId;
        }

        public void setRowId(int rowId) {
            this.rowId = rowId;
        }

        @Override
        public void onClick(View v) {
            viewHolderClickListener.onItemClick(v, rowId);
        }

        public interface ViewHolderClickListener {
            void onItemClick(View caller, int rowId);
        }
    }
}
