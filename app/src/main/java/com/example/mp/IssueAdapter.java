package com.example.mp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    Context context;
    ArrayList<Issue> dataset;
    public IssueAdapter(Context context, ArrayList<Issue> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.issuse_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(dataset.get(position).dept);
        holder.desc.setText(dataset.get(position).issue);
        holder.img.setImageResource(dataset.get(position).img);
        // summery
        holder.ll.setOnClickListener(view -> {
            Dialog d = new Dialog(context);
            d.setContentView(R.layout.show_issue);
            TextView where = d.findViewById(R.id.where);
            TextView who = d.findViewById(R.id.who);
            TextView when = d.findViewById(R.id.when);
            TextView what = d.findViewById(R.id.what);
            EditText st = d.findViewById(R.id.status);
            where.setText("Found at: "+dataset.get(position).dept);
            who.setText("Reported by: "+dataset.get(position).person);
            when.setText("Found on: " +dataset.get(position).date);
            what.setText("Problem: "+dataset.get(position).issue);
            st.setText(dataset.get(position).status);
            d.findViewById(R.id.close).setOnClickListener(view1 -> {
                d.dismiss();
            });
            d.findViewById(R.id.update).setOnClickListener(view1 -> {
                String status = st.getText().toString().trim();
                if (status.isEmpty())
                    Toast.makeText(context, "Status can't be empty", Toast.LENGTH_SHORT).show();
                else {
                    if (status.equalsIgnoreCase("solved")) {
                        dataset.get(position).img = R.drawable.img;
                        FirebaseDatabase.getInstance().getReference().child("Issues").child(dataset.get(position).getId()).child("img").setValue(R.drawable.img);
                        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    }else if(status.equalsIgnoreCase("under review")){
                        dataset.get(position).img=R.drawable.img_1;
                        FirebaseDatabase.getInstance().getReference().child("Issues").child(dataset.get(position).getId()).child("img").setValue(R.drawable.img_1);
                        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    }else {
                        dataset.get(position).img=R.drawable.img_2;
                        FirebaseDatabase.getInstance().getReference().child("Issues").child(dataset.get(position).getId()).child("img").setValue(R.drawable.img_2);
                    }
                    dataset.get(position).status=status;
                    FirebaseDatabase.getInstance().getReference().child("Issues").child(dataset.get(position).getId()).child("status").setValue(status);
                    notifyItemChanged(position);
                    d.dismiss();
                }
            });
            d.show();
        });
        // delete
        holder.mll.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Delete Issue")
                    .setMessage("Are you sure you want to delete?")
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .setNegativeButton("No", (dialogInterface, i) -> {
                    })
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        FirebaseDatabase.getInstance().getReference().child("Issues").child(dataset.get(position).getId()).removeValue();
                        dataset.remove(position);
                        notifyItemRemoved(position);
                    });
            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,desc;
        ImageView img;
        LinearLayout ll,mll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            img = itemView.findViewById(R.id.imgv);
            ll = itemView.findViewById(R.id.childlayout);
            mll = itemView.findViewById(R.id.mainlayout);

        }
    }
}
