package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.data.BrokerSQLite;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder>  {

    private Context context;
    private List<Service> serviceList;
 //   private List<Service> filteredServiceList;
    private ServiceAdapterListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ServicesAdapter(Context context, List<Service> serviceList, ServiceAdapterListener listener) {
        this.context = context;
        this.serviceList = serviceList;
     //   this.filteredServiceList = serviceList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_services_dialog, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {

        Service service = serviceList.get(position);
        holder.serviceNameTv.setText(service.getServiceName());
        holder.serviceCodeTv.setText(service.getServiceCode());
        holder.serviceUnitTv.setText(context.getResources().getString(R.string.unit_of_measure, service.getUnitOfMeasure()));
        holder.servicePriceTv.setText(context.getResources().getString(R.string.material_price, service.getPrice()));
        holder.itemView.setBackgroundColor(selectedPosition == position ? ContextCompat.getColor(context, R.color.light_gray) : ContextCompat.getColor(context, R.color.transparent));

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredServiceList = serviceList;
//                } else {
//                    List<Service> filteredList = new ArrayList<>();
//                    for (Service row : serviceList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getServiceName().toLowerCase().contains(charString.toLowerCase()) || row.getServiceCode().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    filteredServiceList = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredServiceList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
//
//                try {
//                    filteredServiceList = (List<Service>) filterResults.values;
//                    notifyDataSetChanged();
//                } catch (Exception e) {
//                    Utilities.writeErrorToFile(e);
//                }
//            }
//        };
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView serviceNameTv, serviceCodeTv, serviceUnitTv, servicePriceTv;
        private ImageView serviceNormInfoBtn;

        public ViewHolder(@NonNull final View itemView, final Context ctx) {
            super(itemView);

            context = ctx;

            serviceNameTv = itemView.findViewById(R.id.serviceNameTv);
            serviceNormInfoBtn = itemView.findViewById(R.id.serviceNormInfoBtn);
            serviceCodeTv = itemView.findViewById(R.id.serviceCodeTv);
            serviceUnitTv = itemView.findViewById(R.id.serviceUnitTv);
            servicePriceTv = itemView.findViewById(R.id.servicePriceTv);

            serviceNormInfoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onServiceNormClicked(serviceList.get(getAdapterPosition()));

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selectedPosition);
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    listener.onServiceSelected(serviceList.get(getAdapterPosition()));

                }
            });

        }
    }



    public interface ServiceAdapterListener {
        void onServiceSelected(Service contact);
        void onServiceNormClicked(Service serviceNorm);
    }


    public void filterList (List<Service> list){

        serviceList = list;
        notifyDataSetChanged();
    }
}
