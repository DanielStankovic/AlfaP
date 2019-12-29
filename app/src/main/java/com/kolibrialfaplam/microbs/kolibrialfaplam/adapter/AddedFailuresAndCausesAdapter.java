package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddedFailuresAndCausesAdapter extends RecyclerView.Adapter<AddedFailuresAndCausesAdapter.ViewHolder> {
    private Context context;
    private List<AddedFailuresAndCauses> failuresAndCausesList;
    private AddedFailuresAndCausesAdapterListener listener;
    private int addedTableTag;

    public AddedFailuresAndCausesAdapter(Context context, List<AddedFailuresAndCauses> failuresAndCausesList,
                                         AddedFailuresAndCausesAdapterListener listener, int addedTableTag) {
        this.context = context;
        this.failuresAndCausesList = failuresAndCausesList;
        this.listener = listener;
        this.addedTableTag = addedTableTag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_added_failure_and_cause, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddedFailuresAndCauses addedFailuresAndCauses = failuresAndCausesList.get(position);
        holder.addedFailureName.setText(context.getResources().getString(R.string.added_failure_label, addedFailuresAndCauses.getFailureName()));
        holder.addedFailureCauseName.setText(context.getResources().getString(R.string.added_cause_label, addedFailuresAndCauses.getFailureCauseName()));
    }

    @Override
    public int getItemCount() {
        return failuresAndCausesList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView addedFailureName, addedFailureCauseName;
        private ImageView deleteAddedFailureAndCauseImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addedFailureName = itemView.findViewById(R.id.addedFailureName);
            addedFailureCauseName = itemView.findViewById(R.id.addedFailureCauseName);
            deleteAddedFailureAndCauseImg = itemView.findViewById(R.id.deleteAddedFailureAndCauseImg);

            if (addedTableTag == Constants.ADDED_TABLES_DRAFT_TAG) {
                deleteAddedFailureAndCauseImg.setVisibility(View.INVISIBLE);
            } else {

                deleteAddedFailureAndCauseImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onFailureAndCauseDeleted(failuresAndCausesList.get(getAdapterPosition()), failuresAndCausesList.size() - 1);
                        failuresAndCausesList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                });

            }
        }
    }

    public interface AddedFailuresAndCausesAdapterListener {
        void onFailureAndCauseDeleted(AddedFailuresAndCauses addedFailuresAndCauses, int listSize);
    }

}
