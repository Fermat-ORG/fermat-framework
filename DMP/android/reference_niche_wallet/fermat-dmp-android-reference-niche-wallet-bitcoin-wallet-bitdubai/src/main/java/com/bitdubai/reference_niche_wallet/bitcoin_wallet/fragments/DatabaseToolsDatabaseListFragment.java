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
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsDatabaseListFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 *
 * @version 1.0
 */
public class DatabaseToolsDatabaseListFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    private DatabaseTool databaseTools;

    List<DeveloperDatabase> developerDatabaseList;

    private static Platform platform = new Platform();

    public static DatabaseToolsDatabaseListFragment newInstance(int position) {
        DatabaseToolsDatabaseListFragment f = new DatabaseToolsDatabaseListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        try {
            ToolManager toolManager = platform.getToolManager();
            try {
                databaseTools = toolManager.getDatabaseTool();
            } catch (Exception e) {
                showMessage("CantGetToolManager - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            showMessage("Unexpected error get Transactions - " + ex.getMessage());
            ex.printStackTrace();
        }

        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);

        try {
            String item = getArguments().getString("resource");
            String name = item.split(" - ")[0];
            String type = item.split(" - ")[1];
            if (type.equals("Addon")) {
                Addons addon = Addons.getByKey(name);
                //this.developerDatabaseList = databaseTools.getDatabaseListFromAddon(addon);
            } else if (type.equals("Plugin")) {
                Plugins plugin = Plugins.getByKey(name);
                //this.developerDatabaseList = databaseTools.getDatabaseListFromPlugin(plugin);
            }
        } catch (InvalidParameterException invalidParameterException) {
            System.out.println("******************* estas hasta la vaina");
            showMessage(invalidParameterException.getMessage());
        }

        try {

            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
            labelDatabase.setText("Click on a Database");

            List<DeveloperDatabase> developerDatabaseList = new ArrayList<>();

            developerDatabaseList.add(new DeveloperDatabaseTest("wallet_resources", "1"));
            developerDatabaseList.add(new DeveloperDatabaseTest("wallet_address_book", "2"));
            developerDatabaseList.add(new DeveloperDatabaseTest("hola_como_estas", "3"));
            developerDatabaseList.add(new DeveloperDatabaseTest("rapido_y_furioso", "4"));

            final List<DeveloperDatabase> developerDatabaseList2 = developerDatabaseList;

            String[] availableResources;
            if (developerDatabaseList.size() > 0) {
                availableResources = new String[developerDatabaseList.size()];
                for(int i = 0; i < developerDatabaseList.size() ; i++) {
                    availableResources[i] = developerDatabaseList.get(i).getName();
                }
            } else {
                availableResources = new String[0];
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, availableResources);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = (String) listView.getItemAtPosition(position);

                    for (DeveloperDatabase devDB : developerDatabaseList2) {
                        if (devDB.getName().equals(item)) {
                            DatabaseToolsDatabaseTableListFragment databaseToolsDatabaseTableListFragment = new DatabaseToolsDatabaseTableListFragment();
                            databaseToolsDatabaseTableListFragment.setDeveloperDatabase(devDB);

                            FragmentTransaction FT = getFragmentManager().beginTransaction();

                            FT.replace(R.id.hola, databaseToolsDatabaseTableListFragment);

                            FT.commit();
                        }
                    }

                }
            });

            listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }
        return rootView;
    }

    public class DeveloperDatabaseTest implements DeveloperDatabase {
        String name;
        String id;

        DeveloperDatabaseTest(String name, String id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getId() {
            return id;
        }
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