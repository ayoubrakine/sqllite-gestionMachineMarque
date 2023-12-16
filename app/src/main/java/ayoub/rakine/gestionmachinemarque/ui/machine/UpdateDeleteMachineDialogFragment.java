package ayoub.rakine.gestionmachinemarque.ui.machine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.beans.Machine;
import ayoub.rakine.gestionmachinemarque.beans.Marque;
import ayoub.rakine.gestionmachinemarque.services.MachineService;
import ayoub.rakine.gestionmachinemarque.services.MarqueService;


public class UpdateDeleteMachineDialogFragment extends DialogFragment {
    private Long id;
    private EditText reference, prix,date;//marqueCode;
    private Button update, delete;
    private MachineService machineService;

    Spinner marque;
    List<Marque> marques;
    MarqueService marqueService;
    List<String> libelles=new ArrayList<>();

    //String selectedmarque ;

    public UpdateDeleteMachineDialogFragment(Long id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_delete_machine, container, false);

        reference = view.findViewById(R.id.reference);
        prix = view.findViewById(R.id.prix);
        date = view.findViewById(R.id.date);
        //marqueCode = view.findViewById(R.id.marqueCode);
        delete = view.findViewById(R.id.delete);
        update = view.findViewById(R.id.update);

        marque = view.findViewById(R.id.spinner_marque);

        machineService = new MachineService(requireContext());

        marqueService=new MarqueService(requireContext());

        marques=marqueService.findAll();

        for(Marque marque:marques){
            libelles.add(marque.getLibelle());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, libelles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marque.setAdapter(spinnerAdapter);


        int idint=Long.valueOf(id).intValue();
        Machine machine = machineService.findById(idint);

        if (machine != null) {
            reference.setText(machine.getReference());
            prix.setText(String.valueOf(machine.getPrix()));
            date.setText(machine.getDate());
            //marqueCode.setText(machine.getMarqueCode());
            String selectedmarque =  marque.getSelectedItem().toString();


        } else {
            Toast.makeText(requireContext(), "No data found.", Toast.LENGTH_SHORT).show();
            dismiss();
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedReference = reference.getText().toString().trim();
                String updatedPrix = prix.getText().toString().trim();
                String updatedDate = date.getText().toString().trim();
                //String updatedMarqueCode = marqueCode.getText().toString().trim();
                String selectedmarqueupdated = marque.getSelectedItem().toString();
                String updatedMarqueCode = selectedmarqueupdated;


                Machine updatedMachine = new Machine(updatedReference, Integer.parseInt(updatedPrix),updatedDate,updatedMarqueCode);
                machineService.update(updatedMachine, idint);

                dismiss();
            }
        });

        return view;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MachineService srv = new MachineService(requireContext());
                srv.delete(Long.valueOf(id).intValue());
                dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.create().show();
    }
}
