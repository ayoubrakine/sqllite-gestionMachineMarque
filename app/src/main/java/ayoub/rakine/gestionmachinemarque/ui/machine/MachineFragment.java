package ayoub.rakine.gestionmachinemarque.ui.machine;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.adapter.MachineAdapter;
import ayoub.rakine.gestionmachinemarque.beans.Machine;
import ayoub.rakine.gestionmachinemarque.services.MachineService;


public class MachineFragment extends Fragment {

    private Button add;
    private ListView listView;
    private List<Machine> machines;
    private MachineAdapter machineAdapter;
    private MachineService machineService;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_machine, container, false);
        listView = view.findViewById(R.id.listmachines);
        add = view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMachineDialogFragment dialogFragment = new AddMachineDialogFragment(requireContext());
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "AddMachineDialog");
            }
        });


        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                machines = machineService.findAll();
                machineAdapter.updateMachineList(machines);
                listView.setAdapter(machineAdapter);
                machineAdapter.notifyDataSetChanged();
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Machine selectedMachine = (Machine) adapterView.getItemAtPosition(i);

                long longid = (long) selectedMachine.getId();

                // Créer une instance du DialogFragment et passer l'ID sélectionné
                UpdateDeleteMachineDialogFragment dialogFragment = new UpdateDeleteMachineDialogFragment(longid);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "UpdateDeleteMachineDialog");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        machineService = new MachineService(requireContext());
        machines = machineService.findAll();
        machineAdapter = new MachineAdapter(machines, requireContext());
        listView.setAdapter(machineAdapter);
        machineAdapter.notifyDataSetChanged();
    }
}