package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Utilities;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.ViewHolder> {

    private Context context;
    private List<Material> materialList;
   // private List<Material> filteredMaterialList;
    private MaterialAdapterListener listener;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public MaterialsAdapter(Context context, List<Material> materialList, MaterialAdapterListener listener) {
        this.context = context;
        this.materialList = materialList;
      //  this.filteredMaterialList = materialList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_dialog, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Material material = materialList.get(position);
            holder.materialNameTv.setText(material.getMaterialName());
            holder.materialCodeTv.setText(material.getMaterialCode());
            holder.materialUnitOfMeasureTv.setText(context.getResources().getString(R.string.unit_of_measure, material.getUnitOfMeasure()));
            holder.materialPriceTv.setText(context.getResources().getString(R.string.material_price, material.getPrice()));
            holder.itemView.setBackgroundColor(selectedPosition == position ? ContextCompat.getColor(context, R.color.light_gray) : ContextCompat.getColor(context, R.color.transparent));
        } catch (Exception e) {
            Utilities.writeErrorToFile(e);
        }

    }

    @Override
    public int getItemCount() {
      //  return filteredMaterialList == null ? 0 : filteredMaterialList.size();
        return materialList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredMaterialList = materialList;
//                } else {
//                    List<Material> filteredList = new ArrayList<>();
//                    for (Material row : materialList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getMaterialName().toLowerCase().contains(charString.toLowerCase()) || row.getMaterialCode().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    filteredMaterialList = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredMaterialList;
//                return filterResults;
//
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
//                try {
//                    filteredMaterialList = (List<Material>) filterResults.values;
//                    notifyDataSetChanged();
//                } catch (Exception e) {
//                   Utilities.writeErrorToFile(e);
//                }
//            }
//        };
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView materialNameTv, materialCodeTv, materialUnitOfMeasureTv, materialPriceTv;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);

            context = ctx;

            materialNameTv = itemView.findViewById(R.id.materialNameTv);
            materialCodeTv = itemView.findViewById(R.id.materialCodeTv);
            materialUnitOfMeasureTv = itemView.findViewById(R.id.materialUnitOfMeasureTv);
            materialPriceTv = itemView.findViewById(R.id.materialPriceTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selectedPosition);
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    listener.onMaterialSelected(materialList.get(getAdapterPosition()));

                }
            });
        }
    }

    public interface MaterialAdapterListener {
        void onMaterialSelected(Material material);
    }

    public void filterList (List<Material> list){

        materialList = list;
        notifyDataSetChanged();
    }
}
