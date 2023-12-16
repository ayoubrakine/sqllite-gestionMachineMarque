package ayoub.rakine.gestionmachinemarque.ui.machine;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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

public class AddMachineDialogFragment extends DialogFragment {
    private EditText reference, prix, date;
    private Button cancel, add;
    private Context parentFragmentContext;
    private Spinner marque;
    private List<Marque> marques;
    private MarqueService marqueService;
    private List<String> libelles = new ArrayList<>();

    public AddMachineDialogFragment(Context context) {
        this.parentFragmentContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_machine, container, false);

        reference = view.findViewById(R.id.reference);
        prix = view.findViewById(R.id.prix);
        date = view.findViewById(R.id.date);
        cancel = view.findViewById(R.id.cancel);
        add = view.findViewById(R.id.ok);
        marque = view.findViewById(R.id.spinner_marque);

        marqueService = new MarqueService(requireContext());

        marques = marqueService.findAll();

        for (Marque marque : marques) {
            libelles.add(marque.getLibelle());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, libelles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marque.setAdapter(spinnerAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prixText = prix.getText().toString().trim();
                if (prixText.isEmpty()) {
                    Toast.makeText(parentFragmentContext, "Please enter a price", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int prixValue = Integer.parseInt(prixText);
                        String selectedMarque = marque.getSelectedItem().toString();
                        MachineService sv = new MachineService(parentFragmentContext);
                        Machine machine = new Machine(reference.getText().toString(), prixValue, date.getText().toString(), selectedMarque);
                        sv.create(machine);
                    } catch (NumberFormatException e) {
                        Toast.makeText(parentFragmentContext, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }
        });

        return view;
    }
}
