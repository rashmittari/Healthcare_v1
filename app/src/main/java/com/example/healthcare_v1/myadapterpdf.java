package com.example.healthcare_v1;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class myadapterpdf extends RecyclerView.Adapter<myadapterpdf.MyViewHolderPdf>  {

    Context context;
    ArrayList<model_pdf> userArrayList2;

    public myadapterpdf(Context context, ArrayList<model_pdf> userArrayList2) {
        this.context = context;
        this.userArrayList2 = userArrayList2;
    }

    @NonNull
    @Override
    public myadapterpdf.MyViewHolderPdf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pdfrowdesign,parent,false);

        return new MyViewHolderPdf(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterpdf.MyViewHolderPdf holder, int position) {

        model_pdf mpdf = userArrayList2.get(position);

        holder.header.setText(mpdf.FileName);
        // holder.profession.setText(mpdf.profession);

        holder.relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(),viewpdf.class);
                intent.putExtra("the file name",mpdf.FileName);
                intent.putExtra("fileurl",mpdf.getURL());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList2.size();
    }

    public static class MyViewHolderPdf extends RecyclerView.ViewHolder{

        //TextView name,profession,qualifications,timings,address,fees;
        RelativeLayout relativeLayout2;
        //ImageView img1;
        TextView header;


        public MyViewHolderPdf(@NonNull View itemView) {
            super(itemView);
            //img1 = itemView.findViewById(R.id.img1);
            header = itemView.findViewById(R.id.header);

            relativeLayout2 = itemView.findViewById(R.id.relativelayout2);
        }
    }

}