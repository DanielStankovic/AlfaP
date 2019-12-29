package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Material;
import com.kolibrialfaplam.microbs.kolibrialfaplam.utility.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddedMaterialAdapter extends RecyclerView.Adapter<AddedMaterialAdapter.ViewHolder>{

    private Context context;
    private List<Material> materialList;
    private AddedMaterialAdapterListener listener;
    private int addedTableTag;

    public AddedMaterialAdapter(Context context, List<Material> materialList, AddedMaterialAdapterListener listener, int addedTableTag) {
        this.context = context;
        this.materialList = materialList;
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

        Material material = materialList.get(position);
        holder.addedMaterialNameTv.setText(material.getMaterialName());
        holder.addedMaterialUnitTv.setText(context.getResources().getString(R.string.material_unit_spent,material.getQuantitySpent(), material.getUnitOfMeasure()));
        holder.addedMaterialPriceTv.setText(context.getResources().getString(R.string.material_price,material.getPrice()));
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView addedMaterialNameTv, addedMaterialUnitTv, addedMaterialPriceTv;
        private ImageView deleteAddedMaterialImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addedMaterialNameTv = itemView.findViewById(R.id.addedServiceNameTv);
            addedMaterialUnitTv = itemView.findViewById(R.id.addedServiceUnitTv);
            deleteAddedMaterialImg = itemView.findViewById(R.id.deleteAddedServiceImg);
            addedMaterialPriceTv = itemView.findViewById(R.id.addedServicePriceTv);

            if (addedTableTag == Constants.ADDED_TABLES_DRAFT_TAG) {
                deleteAddedMaterialImg.setVisibility(View.INVISIBLE);
            } else {
                deleteAddedMaterialImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mora ovako sa -1 zato sto ako se prvo skloni iz liste, posle je nemoguce dobiti poziciju tog elementa. a puca aplikacija ako se sklone svi elementi
                        listener.onMaterialDeleted(materialList.get(getAdapterPosition()), materialList.size() - 1);
                        materialList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                });
            }
        }
    }

    public interface AddedMaterialAdapterListener {
        void onMaterialDeleted(Material material, int listSize);
    }
}
