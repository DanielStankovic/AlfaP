package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Service;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddedServiceAdapter extends RecyclerView.Adapter<AddedServiceAdapter.ViewHolder>{

    private Context context;
    private List<Service> serviceList;
    private AddedServiceAdapterListener listener;
    private int addedTableTag;

    public AddedServiceAdapter(Context context, List<Service> serviceList, AddedServiceAdapterListener listener, int addedTableTag) {
        this.context = context;
        this.serviceList = serviceList;
        this.listener = listener;
        this.addedTableTag = addedTableTag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_added_service, parent,false);
         return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.addedServiceNameTv.setText(service.getServiceName());
        holder.addedServiceUnitTv.setText(context.getResources().getString(R.string.service_unit_spent,service.getUnitSpent(), service.getUnitOfMeasure()));
        holder.addedServicePriceTv.setText(context.getResources().getString(R.string.material_price,service.getPrice()));


    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView addedServiceNameTv, addedServiceUnitTv, addedServicePriceTv;
        private ImageView deleteAddedServiceImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addedServiceNameTv = itemView.findViewById(R.id.addedServiceNameTv);
            addedServiceUnitTv = itemView.findViewById(R.id.addedServiceUnitTv);
            deleteAddedServiceImg = itemView.findViewById(R.id.deleteAddedServiceImg);
            addedServicePriceTv = itemView.findViewById(R.id.addedServicePriceTv);

            if (addedTableTag == Constants.ADDED_TABLES_DRAFT_TAG) {
                deleteAddedServiceImg.setVisibility(View.INVISIBLE);
            } else {

                deleteAddedServiceImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mora ovako sa -1 zato sto ako se prvo skloni iz liste, posle je nemoguce dobiti poziciju tog elementa. a puca aplikacija ako se sklone svi elementi
                        listener.onServiceDeleted(serviceList.get(getAdapterPosition()), serviceList.size() - 1);
                        serviceList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                });
            }
        }


    }

    public interface AddedServiceAdapterListener {
        void onServiceDeleted(Service service, int listSize);
    }
}
