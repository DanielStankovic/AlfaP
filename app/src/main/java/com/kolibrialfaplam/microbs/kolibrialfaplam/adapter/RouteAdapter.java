package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.content.Intent;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.RouteActivity;
import com.kolibrialfaplam.microbs.kolibrialfaplam.activity.WorkOrderActivity;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.AddedFailuresAndCauses;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Route;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Route> routeArrayList;
    int daysBetween;

    public RouteAdapter(Context context, ArrayList<Route> routeArrayList) {
        this.context = context;
        this.routeArrayList = routeArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_item, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Route routeModel = routeArrayList.get(position);
        Resources resources = context.getResources();
        daysBetween = Utilities.calculateDaysBetweenDateAndToday(routeModel.getPlannedDate());

        holder.workOrderRouteLabel.setText(resources.getString(R.string.workOrderCodeLabel, routeModel.getWorkOrderCode()));
        holder.workOrderTypeRouteTv.setText(resources.getString(R.string.workOrderTypeLabel, routeModel.getWorkOrderType()));
        holder.workOrderDateRouteTv.setText(resources.getString(R.string.workOrderPlannedDateLabel, Utilities.changeDateFormatToLocalFormat(routeModel.getPlannedDate())));
        holder.workOrderDateUntilLimit.setText(resources.getString(R.string.workOrderDateUntilLimit, daysBetween));
        holder.workOrderStatusID.setText(resources.getString(R.string.workOrderStatusID, routeModel.getStatusText()));
        holder.productNameRouteTv.setText(resources.getString(R.string.workOrderProductName, routeModel.getProductName()));
        holder.productCodeRouteTv.setText(resources.getString(R.string.workOrderProductCode, routeModel.getProductCode()));
        holder.custNameRouteTv.setText(resources.getString(R.string.workOrderCustomerName, routeModel.getCustomerName()));
        holder.custAddressRouteTv.setText(resources.getString(R.string.workOrderCustomerAddress, routeModel.getCustomerAddress(), routeModel.getCustomerAddressNo()));
        holder.custCityRouteTv.setText(resources.getString(R.string.workOrderCustomerCity, routeModel.getCustomerCity()));
        holder.custWarning.setText(routeModel.isCustomerWarning() ? resources.getString(R.string.workOrderCustomerWarning): resources.getString(R.string.workOrderCustomerNotWarning));
        holder.failureNameRouteTv.setText(createFailuresStringList(routeModel.getWorkOrderFailureList()));


       if(daysBetween <= 3)
           holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.lessThan3Days));
        else if(daysBetween <= 5)
           holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.between3And5Days));
        else if(daysBetween <= 10)
           holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.between5And10Days));
        else{
           holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.text_subtitle_white));
       }

       if(routeModel.isDraft())
           holder.isDraftLblRouteTv.setText(resources.getString(R.string.draft));
       else
           holder.isDraftLblRouteTv.setText("");

    }

    @Override
    public int getItemCount() {
        return  routeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView workOrderRouteLabel, workOrderTypeRouteTv, workOrderDateRouteTv,workOrderDateUntilLimit, workOrderStatusID,
                productNameRouteTv, productCodeRouteTv,
                custNameRouteTv, custWarning, custAddressRouteTv, custCityRouteTv,
                failureNameRouteTv, isDraftLblRouteTv;
        private LinearLayout routeItemContainer;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);

            context = ctx;

            workOrderRouteLabel = itemView.findViewById(R.id.workOrderRouteLabel);
            workOrderTypeRouteTv = itemView.findViewById(R.id.workOrderTypeRouteTv);
            workOrderDateRouteTv = itemView.findViewById(R.id.workOrderDateRouteTv);
            workOrderDateUntilLimit = itemView.findViewById(R.id.workOrderDateUntilLimit);
            workOrderStatusID = itemView.findViewById(R.id.workOrderStatusID);
            productNameRouteTv = itemView.findViewById(R.id.productNameRouteTv);
            productCodeRouteTv = itemView.findViewById(R.id.productCodeRouteTv);
            custNameRouteTv = itemView.findViewById(R.id.custNameRouteTv);
            custAddressRouteTv = itemView.findViewById(R.id.custAddressRouteTv);
            custCityRouteTv = itemView.findViewById(R.id.custCityRouteTv);
            custWarning = itemView.findViewById(R.id.custWarning);
            failureNameRouteTv = itemView.findViewById(R.id.failureNameRouteTv);

            isDraftLblRouteTv = itemView.findViewById(R.id.isDraftLblRouteTv);

            routeItemContainer = itemView.findViewById(R.id.routeItemContainer);

            routeItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RouteActivity.selectedRouteID = routeArrayList.get(getAdapterPosition()).getWorkOrderID();
                    Intent intent = new Intent(ctx, WorkOrderActivity.class);
                    ctx.startActivity(intent);

                }
            });


        }
    }

    private String createFailuresStringList(List<AddedFailuresAndCauses> addedFailuresAndCausesList){

        StringBuilder sb = new StringBuilder();
        if(addedFailuresAndCausesList.isEmpty())
            return context.getResources().getString(R.string.no_failures_for_wo);

        for (AddedFailuresAndCauses addedFailuresAndCauses : addedFailuresAndCausesList) {
            sb.append(context.getResources().getString(R.string.failure_and_cause,addedFailuresAndCauses.getFailureName(), addedFailuresAndCauses.getFailureCauseName()));
            sb.append(" / ");
        }
        sb.setLength(sb.length() - 3);
       return sb.toString();
    }
}
