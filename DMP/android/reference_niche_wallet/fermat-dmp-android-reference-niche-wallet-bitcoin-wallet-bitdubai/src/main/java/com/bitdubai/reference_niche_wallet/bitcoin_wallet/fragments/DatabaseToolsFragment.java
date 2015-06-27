package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 *
 * @version 1.0
 */
public class DatabaseToolsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    private DatabaseTool databaseTools;

    private static Platform platform = new Platform();

    public static DatabaseToolsFragment newInstance(int position) {
        DatabaseToolsFragment f = new DatabaseToolsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ToolManager toolManager = platform.getToolManager();
            try {
                databaseTools = toolManager.getDatabaseTool();
            } catch (Exception e) {
                showMessage("CantGetToolManager - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            showMessage("Unexpected error get tool manager - " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);
        try {
            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            List<Plugins> plugins = databaseTools.getAvailablePluginList();
            List<Addons> addons = databaseTools.getAvailableAddonList();

            List<String> list = new ArrayList<>();

            for(Plugins plugin : plugins){ list.add(plugin.getKey()+" - Plugin"); }
            for(Addons addon : addons){ list.add(addon.getKey()+" - Addon"); }

            String[] availableResources;
            if (list.size() > 0) {
                availableResources = new String[list.size()];
                for(int i = 0; i < list.size() ; i++) {
                    availableResources[i] = list.get(i);
                }
            } else {
                availableResources = new String[0];
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, availableResources);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
                    labelDatabase.setVisibility(View.GONE);
                    String item = (String) listView.getItemAtPosition(position);

                    DatabaseToolsDatabaseListFragment databaseToolsDatabaseListFragment = new DatabaseToolsDatabaseListFragment ();

                    databaseToolsDatabaseListFragment.setResource(item);

                    FragmentTransaction FT = getFragmentManager().beginTransaction();

                    FT.replace(R.id.hola, databaseToolsDatabaseListFragment);

                    FT.commit();
                }
            });

            listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }
        return rootView;
    }

    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
}