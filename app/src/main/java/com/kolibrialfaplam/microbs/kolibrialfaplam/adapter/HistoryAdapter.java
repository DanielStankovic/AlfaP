package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.WorkOrderDraftActivity;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.History;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> workOrderHistoryList;
    private Context context;
    private Resources resources;

    public HistoryAdapter(List<History> workOrderHistoryList, Context context) {
        this.workOrderHistoryList = workOrderHistoryList;
        this.context = context;
        resources = context.getResources();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draft_work_order, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        History historyWorkOrder = workOrderHistoryList.get(position);

        holder.draftItemWoCode.setText(resources.getString(R.string.draft_work_order_code, historyWorkOrder.getWorkOrderCode()));
        holder.draftItemWoCustomerName.setText(resources.getString(R.string.draft_work_order_cust_name, historyWorkOrder.getCustomerName()));
        holder.draftItemWoCustomerAddress.setText(resources.getString(R.string.draft_work_order_cust_address, historyWorkOrder.getCustomerAddress()));
        holder.draftItemWoCustomerCity.setText(resources.getString(R.string.draft_work_order_cust_city, historyWorkOrder.getCustomerCity()));
        holder.draftItemWoProductName.setText(resources.getString(R.string.draft_work_order_prod_name, historyWorkOrder.getProductName()));
        holder.draftItemWoPartnerName.setText(resources.getString(R.string.draft_work_order_part_name, historyWorkOrder.getPartnerName()));
        holder.draftItemWoCreatedDate.setText(resources.getString(R.string.draft_work_order_created_date, Utilities.changeDateTimeFormatToLocalFormat(historyWorkOrder.getCreatedDate())));
        holder.draftItemWoSentDate.setText(resources.getString(R.string.draft_work_order_sent_date, Utilities.changeDateTimeFormatToLocalFormat(historyWorkOrder.getSentDate())));

    }

    @Override
    public int getItemCount() {
        return workOrderHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView draftItemWoCode, draftItemWoCustomerName, draftItemWoCustomerAddress, draftItemWoCustomerCity;
        private TextView draftItemWoProductName, draftItemWoPartnerName, draftItemWoCreatedDate, draftItemWoSentDate;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            draftItemWoCode = itemView.findViewById(R.id.draftItemWoCode);
            draftItemWoCustomerName = itemView.findViewById(R.id.draftItemWoCustomerName);
            draftItemWoCustomerAddress = itemView.findViewById(R.id.draftItemWoCustomerAddress);
            draftItemWoCustomerCity = itemView.findViewById(R.id.draftItemWoCustomerCity);
            draftItemWoProductName = itemView.findViewById(R.id.draftItemWoProductName);
            draftItemWoPartnerName = itemView.findViewById(R.id.draftItemWoPartnerName);
            draftItemWoCreatedDate = itemView.findViewById(R.id.draftItemWoCreatedDate);
            draftItemWoSentDate = itemView.findViewById(R.id.draftItemWoSentDate);


            //Postavljam boju ovde zato sto koristim isti layout za Draft i za ovde
            itemView.setBackground(ContextCompat.getDrawable(context,R.drawable.draft_item_sent_bg));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO OVDE IDE OTVARANJE FORME ZA PRIKAZ RADNOG NALOGA I STA JE ODRADJENO U NJEMU
                    int workOrderID = workOrderHistoryList.get(getAdapterPosition()).getWorkOrderID();
                    int workOrderResultID = workOrderHistoryList.get(getAdapterPosition()).getWorkOrderResultID();
                    Intent intent = new Intent(context, WorkOrderDraftActivity.class);
                    intent.putExtra("WorkOrderID", workOrderID);
                    intent.putExtra("WorkOrderResultID", workOrderResultID);
                    intent.putExtra("WorkOrderDraftType", Constants.WORK_ORDER_HISTORY_TAG);
                    context.startActivity(intent);
                }
            });
        }
    }
}
