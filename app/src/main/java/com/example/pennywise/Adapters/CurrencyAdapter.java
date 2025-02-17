package com.example.pennywise.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennywise.Models.CurrencyModel;
import com.example.pennywise.R;
import com.example.pennywise.Tools.Constraints;
import com.example.pennywise.Tools.SharedPrefs;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    List<CurrencyModel> currencyModelList;
    Context context;
    SharedPrefs prefs;
    Dialog dialog;

    public CurrencyAdapter(List<CurrencyModel> currencyModelList, Context context, Dialog dialog) {
        this.currencyModelList = currencyModelList;
        this.context = context;
        this.dialog = dialog;
    }

    public CurrencyAdapter(List<CurrencyModel> currencyModelList, Context context) {
        this.currencyModelList = currencyModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_currency, parent, false);
        return new CurrencyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        holder.name.setText(currencyModelList.get(position).getName());
        holder.abbr.setText(currencyModelList.get(position).getCode());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs=new SharedPrefs(context);
                prefs.setStr(Constraints.currency,currencyModelList.get(holder.getAdapterPosition()).getCode()+"-"+currencyModelList.get(holder.getAdapterPosition()).getName());
                dialog.dismiss();

            }
        });

    }

    @Override
    public int getItemCount() {
        return currencyModelList.size();
    }

    public void filteredList(List<CurrencyModel> filterlist) {
        currencyModelList = filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,abbr;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_nameCurrency);
            layout=itemView.findViewById(R.id.currency_item);
            abbr=itemView.findViewById(R.id.txt_abbrCurrency);

        }
    }
}
