package com.example.healthcare_v1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class My3Adapter extends RecyclerView.Adapter<My3Adapter.MyViewHolder> {

    Context context;
    ArrayList<Detuser> detuserArrayList3;

    public My3Adapter(Context context, ArrayList<Detuser> detuserArrayList3) {
        this.context = context;
        this.detuserArrayList3 = detuserArrayList3;
    }

    @NonNull
    @Override
    public My3Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item3_drapt,parent,false);

        return new My3Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull My3Adapter.MyViewHolder holder, int position) {

        Detuser detuser3= detuserArrayList3.get(position);

        holder.DAname.setText(detuser3.Pname);
        holder.DAage.setText(detuser3.Age);
        holder.DAgender.setText(detuser3.Gender);
        holder.DAtime.setText(detuser3.Time);
        //holder.Atimings.setText(detuser.timings);

        holder.patientinfofordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(),Prescription.class);

                intent.putExtra("patient_uid",detuser3.user_id);

                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return detuserArrayList3.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DAname,DAage,DAgender,DAtime,DAdate;
        RelativeLayout patientinfofordr ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            DAname = itemView.findViewById(R.id.userFullName3);
            DAage = itemView.findViewById(R.id.userage);
            DAtime = itemView.findViewById(R.id.userapttime);
            DAgender = itemView.findViewById(R.id.usergender);
            //DAdate = itemView.findViewById(R.id.userFullName3);
            patientinfofordr = itemView.findViewById(R.id.patientinfofordr);
        }
    }
}
