package ayoub.rakine.gestionmachinemarque.ui.marque;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.adapter.MarqueAdapter;
import ayoub.rakine.gestionmachinemarque.beans.Marque;
import ayoub.rakine.gestionmachinemarque.services.MarqueService;

public class MarqueFragment extends Fragment {

    private Button add;
    private ListView listView;
    private List<Marque> marques;
    private MarqueAdapter marqueAdapter;
    private MarqueService marqueService;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marque, container, false);
        listView = view.findViewById(R.id.listmarques);
        add = view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMarqueDialogFragment dialogFragment = new AddMarqueDialogFragment(requireContext());
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "AddMarqueDialog");
            }
        });

        //MarqueFragment binding = null;
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //marqueService = new MarqueService(requireContext());
                //marques = marqueService.findAll();
                //marqueAdapter = new MarqueAdapter(marques, requireContext());
                //marqueAdapter.updateMarqueList(marques);
                //listView.setAdapter(marqueAdapter);
                //marqueAdapter.updateMarqueList(marques);

                marques = marqueService.findAll();
                marqueAdapter.updateMarqueList(marques);
                listView.setAdapter(marqueAdapter);
                marqueAdapter.notifyDataSetChanged();
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
                Marque selectedMarque = (Marque) adapterView.getItemAtPosition(i);

                long longid = (long) selectedMarque.getId();

                // Créer une instance du DialogFragment et passer l'ID sélectionné
                UpdateDeleteMarqueDialogFragment dialogFragment = new UpdateDeleteMarqueDialogFragment(longid);
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "UpdateDeleteMarqueDialog");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        marqueService = new MarqueService(requireContext());
        marques = marqueService.findAll();
        marqueAdapter = new MarqueAdapter(marques, requireContext());
        listView.setAdapter(marqueAdapter);
        marqueAdapter.notifyDataSetChanged();
    }
}
