package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> productList;
   // private List<Product> filteredProductList;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private ProductAdapterListener listener;


    public ProductsAdapter(Context context, List<Product> productList, ProductAdapterListener listener) {
        this.context = context;
        this.productList = productList;
      //  this.filteredProductList = productList;
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
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTv.setText(product.getProductName());
        holder.productCodeTv.setText(product.getProductCode());
        holder.itemView.setBackgroundColor(selectedPosition == position ? ContextCompat.getColor(context, R.color.light_gray) : ContextCompat.getColor(context, R.color.transparent));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    filteredProductList = productList;
//                }else{
//                    List<Product> filteredList = new ArrayList<>();
//                    for (Product row : productList) {
//                        if (row.getProductName().toLowerCase().contains(charString.toLowerCase()) || row.getProductCode().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//                    filteredProductList = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredProductList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
//                filteredProductList = (List<Product>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameTv, productCodeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productNameTv = itemView.findViewById(R.id.materialNameTv);
            productCodeTv = itemView.findViewById(R.id.materialCodeTv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selectedPosition);
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    listener.onProductSelected(productList.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface ProductAdapterListener{
        void onProductSelected(Product product);
    }


    public void filterList (List<Product> list){

        productList = list;
        notifyDataSetChanged();
    }
}
