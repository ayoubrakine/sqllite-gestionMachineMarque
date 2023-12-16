package ayoub.rakine.gestionmachinemarque.ui.marque;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.beans.Marque;
import ayoub.rakine.gestionmachinemarque.services.MarqueService;

public class UpdateDeleteMarqueDialogFragment extends DialogFragment {
    private Long id;
    private EditText code, libelle;
    private Button update, delete;
    private MarqueService marqueService;

    public UpdateDeleteMarqueDialogFragment(Long id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_delete_marque, container, false);

        code = view.findViewById(R.id.code);
        libelle = view.findViewById(R.id.libelle);
        delete = view.findViewById(R.id.delete);
        update = view.findViewById(R.id.update);

        marqueService = new MarqueService(requireContext());

        int idint=Long.valueOf(id).intValue();
        Marque marque = marqueService.findById(idint);
        if (marque != null) {
            code.setText(marque.getCode());
            libelle.setText(marque.getLibelle());
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
                String updatedCode = code.getText().toString().trim();
                String updatedLibelle = libelle.getText().toString().trim();

                Marque updatedMarque = new Marque(updatedCode, updatedLibelle);
                marqueService.update(updatedMarque, idint);

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
                MarqueService srv = new MarqueService(requireContext());
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
