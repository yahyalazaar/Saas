package emsi.saas.pfa.pfa_saas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import emsi.saas.pfa.pfa_saas.R;
import emsi.saas.pfa.pfa_saas.model.Assemble;

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewNom;
    private TextView textViewDate;
    private TextView textViewDuree;
    private ImageView img;
    //itemView est la vue correspondante à 1 cellule
    public MyViewHolder(View itemView,final AssembleAdapter.OnItemClickListener listener) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewNom= (TextView) itemView.findViewById(R.id.txtName);
        textViewDate = (TextView) itemView.findViewById(R.id.txtDate);
        textViewDuree = (TextView) itemView.findViewById(R.id.txtDuree);
        img = (ImageView) itemView.findViewById(R.id.img);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Assemble myObject){
        textViewNom.setText("Assemble ["+myObject.getNom_propriete()+"]");
        textViewDate.setText(myObject.getDate_assemble());
        textViewDuree.setText(Integer.toString(myObject.getDuree_assemble())+" min");
        img.setImageResource(R.drawable.calendar);
    }
}