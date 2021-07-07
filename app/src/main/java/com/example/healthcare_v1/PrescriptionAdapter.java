package com.example.healthcare_v1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.CricketerView> {

    ArrayList<MedicineDetail_modalclass> cricketersList = new ArrayList<>();

    public PrescriptionAdapter(ArrayList<MedicineDetail_modalclass> cricketersList) {
        this.cricketersList = cricketersList;
    }

    @NonNull
    @Override
    public CricketerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_medicine,parent,false);

        return new CricketerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CricketerView holder, int position) {

        MedicineDetail_modalclass medicineDetailmodalclass = cricketersList.get(position);
        holder.textCricketerName.setText(medicineDetailmodalclass.getCricketerName());
        holder.textTeamName.setText(medicineDetailmodalclass.getTeamName());

        //Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        String name = medicineDetailmodalclass.getCricketerName();



    }

    @Override
    public int getItemCount() {
        return cricketersList.size();
    }

    public class CricketerView extends RecyclerView.ViewHolder{

        TextView textCricketerName,textTeamName;
        public CricketerView(@NonNull View itemView) {
            super(itemView);

            textCricketerName = (TextView)itemView.findViewById(R.id.text_medicine_name);
            textTeamName = (TextView)itemView.findViewById(R.id.text_dose);

        }
    }
}
