package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.WorkOrderDraftActivity;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Draft;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.List;

import androidx.annotation.NonNull;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DraftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Draft> draftItemList;
    private Resources resources;
    private DraftAdapterInterface listener;

    public DraftAdapter(Context context, List<Draft> draftItemList, DraftAdapterInterface listener) {
        this.context = context;
        this.draftItemList = draftItemList;
        this.listener = listener;
        resources = context.getResources();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType == Constants.TYPE_GROUP_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_title, viewGroup, false);
            return new HeaderViewHolder(view);
        }else if(viewType == Constants.TYPE_WO_RESULT){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_draft_work_order, viewGroup, false);
            return new WorkOrderViewHolder(view, context);
        }else{
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_draft, viewGroup, false);
            return new DraftItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Draft draftWorkOrder = draftItemList.get(position);

        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.draftHeaderTitle.setText(draftWorkOrder.getHeaderTitle());
        }else if(holder instanceof WorkOrderViewHolder){
            WorkOrderViewHolder workOrderViewHolder = (WorkOrderViewHolder) holder;

            if(draftWorkOrder.isSent()){
                workOrderViewHolder.itemView.setBackground(ContextCompat.getDrawable(context,R.drawable.draft_item_sent_bg));

            }else{
                workOrderViewHolder.itemView.setBackground(ContextCompat.getDrawable(context,R.drawable.draft_item_bg_not_sent));


            }

            workOrderViewHolder.draftItemWoCode.setText(resources.getString(R.string.draft_work_order_code, draftWorkOrder.getWorkOrderCode()));
            workOrderViewHolder.draftItemWoCustomerName.setText(resources.getString(R.string.draft_work_order_cust_name, draftWorkOrder.getCustomerName()));
            workOrderViewHolder.draftItemWoCustomerAddress.setText(resources.getString(R.string.draft_work_order_cust_address, draftWorkOrder.getCustomerAddress()));
            workOrderViewHolder.draftItemWoCustomerCity.setText(resources.getString(R.string.draft_work_order_cust_city, draftWorkOrder.getCustomerCity()));
            workOrderViewHolder.draftItemWoProductName.setText(resources.getString(R.string.draft_work_order_prod_name, draftWorkOrder.getProductName()));
            workOrderViewHolder.draftItemWoPartnerName.setText(resources.getString(R.string.draft_work_order_part_name, draftWorkOrder.getPartnerName()));
            workOrderViewHolder.draftItemWoCreatedDate.setText(resources.getString(R.string.draft_work_order_created_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getCreatedDate())));
            workOrderViewHolder.draftItemWoSentDate.setText(resources.getString(R.string.draft_work_order_sent_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getSentDate())));

        }
        else{
            DraftItemViewHolder draftItemViewHolder = (DraftItemViewHolder) holder;
            String topLeft = "", bottomLeft = "", topRight = "";
            if(draftWorkOrder.isSent()){
                draftItemViewHolder.itemView.setBackground(ContextCompat.getDrawable(context,R.drawable.draft_item_sent_bg));

            }else{
                draftItemViewHolder.itemView.setBackground(ContextCompat.getDrawable(context,R.drawable.draft_item_bg_not_sent));


            }

            if(draftWorkOrder.getItemType() == Constants.TYPE_CHECKIN){
               topLeft = resources.getString(R.string.draft_check_in_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getCreatedDate()));
                bottomLeft = resources.getString(R.string.draft_check_out_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getSentDate()));

            }else if(draftWorkOrder.getItemType() == Constants.TYPE_STARTSTOP){

                topLeft = resources.getString(R.string.draft_start_stop_time, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getWorkOrderCode()));
                bottomLeft = resources.getString(R.string.draft_start_stop_status, draftWorkOrder.getProductName());

            }else if(draftWorkOrder.getItemType() == Constants.TYPE_WO_IMAGE){
                topLeft = resources.getString(R.string.draft_work_order_created_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getCreatedDate()));
                bottomLeft = resources.getString(R.string.draft_work_order_sent_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getSentDate()));
                topRight = resources.getString(R.string.draft_image_type, draftWorkOrder.getWorkOrderCode());

            } else if(draftWorkOrder.getItemType() == Constants.TYPE_WO_SIGNATURE){

                topLeft = resources.getString(R.string.draft_work_order_created_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getCreatedDate()));
                bottomLeft = resources.getString(R.string.draft_work_order_sent_date, Utilities.changeDateTimeFormatToLocalFormat(draftWorkOrder.getSentDate()));
                topRight = resources.getString(R.string.draft_image_type, draftWorkOrder.getWorkOrderCode());
            }

            draftItemViewHolder.draftItemTopLeftTv.setText(topLeft);
            draftItemViewHolder.draftItemBottomLeftTv.setText(bottomLeft);
            draftItemViewHolder.draftItemTopRightTv.setText(topRight);

        }


    }

    @Override
    public int getItemCount() {
        return draftItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return draftItemList.get(position).getItemType();
    }

    public class WorkOrderViewHolder extends RecyclerView.ViewHolder {


        private TextView draftItemWoCode, draftItemWoCustomerName, draftItemWoCustomerAddress, draftItemWoCustomerCity;
        private TextView draftItemWoProductName, draftItemWoPartnerName, draftItemWoCreatedDate, draftItemWoSentDate;


        public WorkOrderViewHolder(@NonNull View itemView, final Context ctx) {
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO OVDE IDE OTVARANJE FORME ZA PRIKAZ RADNOG NALOGA I STA JE ODRADJENO U NJEMU
                    int workOrderID = draftItemList.get(getAdapterPosition()).getWorkOrderID();
                    int workOrderResultID = draftItemList.get(getAdapterPosition()).getWorkOrderResultID();
                    Intent intent = new Intent(context, WorkOrderDraftActivity.class);
                    intent.putExtra("WorkOrderID", workOrderID);
                    intent.putExtra("WorkOrderResultID", workOrderResultID);
                    intent.putExtra("WorkOrderDraftType", Constants.WORK_ORDER_DRAFT_TAG);
                    context.startActivity(intent);
                }
            });



        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {


        private TextView draftHeaderTitle ;
        private Button sendGroupDataBtn;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            draftHeaderTitle = itemView.findViewById(R.id.draftHeaderTitle);
            sendGroupDataBtn = itemView.findViewById(R.id.sendGroupDataBtn);

            sendGroupDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSendButtonClicked(draftItemList.get(getAdapterPosition()));
                }
            });

        }
    }

    public class DraftItemViewHolder extends RecyclerView.ViewHolder {


        private TextView draftItemTopLeftTv, draftItemBottomLeftTv, draftItemTopRightTv, draftItemBottomRightTv;


        public DraftItemViewHolder(@NonNull View itemView) {
            super(itemView);

            draftItemTopLeftTv = itemView.findViewById(R.id.draftItemTopLeftTv);
            draftItemBottomLeftTv = itemView.findViewById(R.id.draftItemBottomLeftTv);
            draftItemTopRightTv = itemView.findViewById(R.id.draftItemTopRightTv);
            draftItemBottomRightTv = itemView.findViewById(R.id.draftItemBottomRightTv);


        }
    }

    public interface DraftAdapterInterface{
       void onSendButtonClicked(Draft draftItem);
    }
}
