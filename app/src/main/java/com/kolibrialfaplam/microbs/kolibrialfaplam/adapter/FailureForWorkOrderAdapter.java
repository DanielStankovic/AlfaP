package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FailureForWorkOrderAdapter extends RecyclerView.Adapter<FailureForWorkOrderAdapter.ViewHolder> {


    private List<AddedFailuresAndCauses> failuresForWoList;
    private Context context;

    public FailureForWorkOrderAdapter(Context context, List<AddedFailuresAndCauses> failuresForWoList) {

        this.context = context;
        this.failuresForWoList = failuresForWoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_text, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AddedFailuresAndCauses afc = failuresForWoList.get(position);
        holder.failureForWOName.setText(context.getResources().getString(R.string.failure_and_cause,afc.getFailureName(), afc.getFailureCauseName()));

    }

    @Override
    public int getItemCount() {
        return failuresForWoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView failureForWOName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            failureForWOName = itemView.findViewById(R.id.failureForWOName);

        }
    }
}
