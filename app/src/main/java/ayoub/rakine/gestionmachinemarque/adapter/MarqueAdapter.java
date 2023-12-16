package ayoub.rakine.gestionmachinemarque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.beans.Marque;

public class MarqueAdapter extends BaseAdapter {
    private List<Marque> marques;
    private LayoutInflater inflater;

    public MarqueAdapter(List<Marque> marques, Context context) {
        this.marques = marques;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateMarqueList(List<Marque> newMarques) {
        marques.clear();
        marques.addAll(newMarques);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return marques.size();
    }

    @Override
    public Object getItem(int position) {
        return marques.get(position);
    }

    @Override
    public long getItemId(int position) {
        return marques.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_marque, parent, false);
        }

        TextView id = convertView.findViewById(R.id.id);
        TextView code = convertView.findViewById(R.id.code);
        TextView libelle = convertView.findViewById(R.id.libelle);


        Marque marque = marques.get(position);

        id.setText(String.valueOf(marque.getId()));

        code.setText(marque.getCode());
        libelle.setText(marque.getLibelle());


        return convertView;
    }

}
