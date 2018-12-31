package emsi.saas.pfa.pfa_saas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import emsi.saas.pfa.pfa_saas.R;
import emsi.saas.pfa.pfa_saas.model.Assemble;

public class AssembleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Assemble> list;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    //ajouter un constructeur prenant en entrée une liste
    public AssembleAdapter(List<Assemble> list) {
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Assemble myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}