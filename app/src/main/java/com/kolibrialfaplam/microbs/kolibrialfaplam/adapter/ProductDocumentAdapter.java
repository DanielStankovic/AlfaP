package com.kolibrialfaplam.microbs.kolibrialfaplam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kolibrialfaplam.microbs.kolibrialfaplam.R;
import com.kolibrialfaplam.microbs.kolibrialfaplam.model.ProductDocument;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDocumentAdapter extends RecyclerView.Adapter<ProductDocumentAdapter.ViewHolder> {

    private Context context;
    private List<ProductDocument> documentList;
    private ProductDocumentListener listener;





    public ProductDocumentAdapter(Context context, List<ProductDocument> documentList, ProductDocumentListener listener) {
        this.context = context;
        this.documentList = documentList;
        this.listener = listener;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_document, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDocument productDocument = documentList.get(position);
        holder.productDocumentName.setText(productDocument.getDocumentName());
        holder.productDocumentType.setText(context.getResources().getString(R.string.document_type_lbl, productDocument.getDocumentType()));
        if(productDocument.isDownloaded()){
            holder.downloadDocBtn.setText(context.getResources().getString(R.string.showProdDocument));
            holder.downloadDocBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
            holder.downloadDocBtn.setCompoundDrawablePadding(5);
        }else{
            holder.downloadDocBtn.setText(context.getResources().getString(R.string.download));
            holder.downloadDocBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.get_data_icon, 0, 0, 0);
            holder.downloadDocBtn.setCompoundDrawablePadding(5);
        }


    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productDocumentName, productDocumentType;
        private Button downloadDocBtn;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            productDocumentName = itemView.findViewById(R.id.productDocumentName);
            productDocumentType = itemView.findViewById(R.id.productDocumentType);
            downloadDocBtn = itemView.findViewById(R.id.downloadDocBtn);

            downloadDocBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onButtonClicked(documentList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ProductDocumentListener{
        void onButtonClicked(ProductDocument productDocument);
    }

}
