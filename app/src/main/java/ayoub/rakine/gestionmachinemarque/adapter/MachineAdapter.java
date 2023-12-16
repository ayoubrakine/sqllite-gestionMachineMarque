package ayoub.rakine.gestionmachinemarque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.beans.Machine;
import ayoub.rakine.gestionmachinemarque.beans.Marque;

public class MachineAdapter extends BaseAdapter {
    private List<Machine> machines;
    private LayoutInflater inflater;

    public MachineAdapter(List<Machine> machines, Context context) {
        this.machines = machines;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateMachineList(List<Machine> newMachines) {
        machines.clear();
        machines.addAll(newMachines);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return machines.size();
    }

    @Override
    public Object getItem(int position) {
        return machines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return machines.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_machine, parent, false);
        }

        TextView id = convertView.findViewById(R.id.id);
        TextView reference = convertView.findViewById(R.id.reference);
        TextView prix = convertView.findViewById(R.id.prix);
        TextView date = convertView.findViewById(R.id.date);
        TextView marqueCode = convertView.findViewById(R.id.marqueCode);



        Machine machine = machines.get(position);

        id.setText(String.valueOf(machine.getId()));

        reference.setText(machine.getReference());
        //String prixx = String.valueOf(machine.getPrix());
        //prix.setText(prixx);
        prix.setText(machine.getPrix()+" dhs");
        date.setText(machine.getDate());
        marqueCode.setText(machine.getMarqueCode());




        return convertView;
    }

}
