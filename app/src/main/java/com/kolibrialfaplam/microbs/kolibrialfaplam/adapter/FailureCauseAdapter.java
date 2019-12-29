package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.FailureCause;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class FailureCauseAdapter extends RecyclerView.Adapter<FailureCauseAdapter.ViewHolder>  {

    private Context context;
    private List<FailureCause> failureCauseList;
  //  private List<FailureCause> filteredFailureCauseList;
    private FailureCauseAdapterListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public FailureCauseAdapter(Context context, List<FailureCause> failureCauseList,
                               List<FailureCause> filteredFailureCauseList, FailureCauseAdapterListener listener) {
        this.context = context;
        this.failureCauseList = failureCauseList;
     //   this.filteredFailureCauseList = failureCauseList;
        this.listener = listener;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_failure_cause_dialog,parent,false );
       return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    FailureCause failureCause = failureCauseList.get(position);
    holder.failureCauseName.setText(failureCause.getFailureCauseName());
    holder.failureCauseCode.setText(failureCause.getFailureCauseCode());
    holder.failureCauseDesc.setText(failureCause.getDescription());
    holder.itemView.setBackgroundColor(selectedPosition == position ? ContextCompat.getColor(context, R.color.light_gray) : ContextCompat.getColor(context, R.color.transparent));

    }

    @Override
    public int getItemCount() {
        return failureCauseList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredFailureCauseList = failureCauseList;
//                }else{
//                    List<FailureCause> filteredList = new ArrayList<>();
//                    for(FailureCause row : failureCauseList){
//                        if (row.getFailureCauseName().toLowerCase().contains(charString.toLowerCase()) || row.getFailureCauseCode().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//                    filteredFailureCauseList = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredFailureCauseList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
//                filteredFailureCauseList = (List<FailureCause>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView failureCauseName, failureCauseCode, failureCauseDesc;
        public ViewHolder(@NonNull View itemView,  final Context ctx) {
            super(itemView);

            context = ctx;
            failureCauseName = itemView.findViewById(R.id.failureCauseName);
            failureCauseCode = itemView.findViewById(R.id.failureCauseCode);
            failureCauseDesc = itemView.findViewById(R.id.failureCauseDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selectedPosition);
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    listener.onFailureCauseSelected(failureCauseList.get(getAdapterPosition()));

                }
            });
        }
    }

    public interface FailureCauseAdapterListener{
        void onFailureCauseSelected(FailureCause failureCause);
    }


    public void filterList (List<FailureCause> list){

        failureCauseList = list;
        notifyDataSetChanged();
    }
}
