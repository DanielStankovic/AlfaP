package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WarehouseStateAdapter extends RecyclerView.Adapter<WarehouseStateAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Material> materialList;
    private List<Material> filteredMaterialList;

    public WarehouseStateAdapter(Context context, List<Material> materialList) {
        this.context = context;
        this.materialList = materialList;
        this.filteredMaterialList = materialList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_warehouse_state, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Material material = filteredMaterialList.get(position);
        holder.warehouseStateMatName.setText(material.getMaterialName());
        holder.warehouseStateMatCode.setText(material.getMaterialCode());
        holder.warehouseStateMatUnit.setText(context.getResources().getString(R.string.unit_of_measure, material.getUnitOfMeasure()));
        holder.warehouseStateMatRealQuant.setText(context.getResources().getString(R.string.real_reserved_quantity, "Realna",material.getRealQuantity()));
        holder.warehouseStateMatResQuant.setText(context.getResources().getString(R.string.real_reserved_quantity, "Rezervisana",material.getReservedQuantity()));
       }

    @Override
    public int getItemCount() {
        return filteredMaterialList == null ? 0 : filteredMaterialList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredMaterialList = materialList;
                } else {
                    List<Material> filteredList = new ArrayList<>();
                    for (Material row : materialList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMaterialName().toLowerCase().contains(charString.toLowerCase()) || row.getMaterialCode().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredMaterialList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMaterialList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                filteredMaterialList = (List<Material>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView warehouseStateMatName, warehouseStateMatCode, warehouseStateMatUnit,
                warehouseStateMatRealQuant, warehouseStateMatResQuant;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warehouseStateMatName = itemView.findViewById(R.id.warehouseStateMatName);
            warehouseStateMatCode = itemView.findViewById(R.id.warehouseStateMatCode);
            warehouseStateMatUnit = itemView.findViewById(R.id.warehouseStateMatUnit);
            warehouseStateMatRealQuant = itemView.findViewById(R.id.warehouseStateMatRealQuant);
            warehouseStateMatResQuant = itemView.findViewById(R.id.warehouseStateMatResQuant);

        }
    }
}
