package ayoub.rakine.gestionmachinemarque.ui.marque;

import android.content.Context;
import android.content.Intent;
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

public class AddMarqueDialogFragment extends DialogFragment {
    private EditText code, libelle;
    private Button cancel;
    private Button add;

    private Context parentFragmentContext;

    public AddMarqueDialogFragment(Context context) {
        this.parentFragmentContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_marque, container, false);
        code = view.findViewById(R.id.code);
        libelle = view.findViewById(R.id.libelle);
        cancel = view.findViewById(R.id.cancel);
        add = view.findViewById(R.id.ok);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarqueService sv = new MarqueService(parentFragmentContext); // Utilisation du contexte parentFragmentContext
                if (code.getText().toString().isEmpty() || libelle.getText().toString().isEmpty()) {
                    Toast.makeText(parentFragmentContext, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Marque marque = new Marque(code.getText().toString(), libelle.getText().toString());
                    sv.create(marque);
                }

                dismiss();
            }
        });

        return view;
    }
}
