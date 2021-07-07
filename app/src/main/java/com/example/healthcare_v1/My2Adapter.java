package com.example.healthcare_v1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class My2Adapter extends RecyclerView.Adapter<My2Adapter.MyViewHolder> {

    Context context;
    ArrayList<Detuser> detuserArrayList;

    public My2Adapter(Context context, ArrayList<Detuser> detuserArrayList) {
        this.context = context;
        this.detuserArrayList = detuserArrayList;
    }

    @NonNull
    @Override
    public My2Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item2_apt,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull My2Adapter.MyViewHolder holder, int position) {

        Detuser detuser= detuserArrayList.get(position);

        holder.Aname.setText(detuser.name);
        holder.Aprofession.setText(detuser.profession);
        holder.Aqualification.setText(detuser.qualifications);
        holder.Aaddress.setText(detuser.address);
        holder.Atimings.setText(detuser.timings);
        holder.Afees.setText(detuser.fees);
        holder.Astatus.setText(detuser.status);


    }

    @Override
    public int getItemCount() {
        return detuserArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Aname,Afees,Aaddress,Aprofession,Aqualification,Astatus,Atimings;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Aname = itemView.findViewById(R.id.userFullName2);
            Aprofession = itemView.findViewById(R.id.drproffesion2);
            Aqualification = itemView.findViewById(R.id.drqualification2);
            Aaddress = itemView.findViewById(R.id.draddress2);
            Atimings = itemView.findViewById(R.id.drtimings2);
            Afees = itemView.findViewById(R.id.drfees2);
            Astatus = itemView.findViewById(R.id.status2);
        }
    }
}
