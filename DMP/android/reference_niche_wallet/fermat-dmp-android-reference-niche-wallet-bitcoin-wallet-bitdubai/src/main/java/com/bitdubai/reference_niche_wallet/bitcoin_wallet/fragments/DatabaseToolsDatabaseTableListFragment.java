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
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
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
public class DatabaseToolsDatabaseTableListFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    private DatabaseTool databaseTools;

    private DeveloperDatabase developerDatabase;

    public void setDeveloperDatabase(DeveloperDatabase developerDatabase) {
        this.developerDatabase = developerDatabase;
    }

    List<DeveloperDatabaseTable> developerDatabaseTableList;

    private static Platform platform = new Platform();

    public static DatabaseToolsDatabaseTableListFragment newInstance(int position) {
        DatabaseToolsDatabaseTableListFragment f = new DatabaseToolsDatabaseTableListFragment();
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
            //developerDatabaseTableList = databaseTools.getTableListFromDatabase(developerDatabase);
        } catch (Exception e) {
            System.out.println("***********************hasta la vaina, baby");
        }

        try {
            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
            labelDatabase.setText("Click on a Table");

            developerDatabaseTableList = new ArrayList<>();

            developerDatabaseTableList.add(new DeveloperDatabaseTableTest("incoming_crypto_transaction", null));
            developerDatabaseTableList.add(new DeveloperDatabaseTableTest("outgoing_crypto_transaction", null));
            developerDatabaseTableList.add(new DeveloperDatabaseTableTest("crypto_transaction", null));
            developerDatabaseTableList.add(new DeveloperDatabaseTableTest("wallet_crypto_address", null));

            final List<DeveloperDatabaseTable> developerDatabaseTableList2 = developerDatabaseTableList;

            String[] availableResources;
            if (developerDatabaseTableList.size() > 0) {
                availableResources = new String[developerDatabaseTableList.size()];
                for(int i = 0; i < developerDatabaseTableList.size() ; i++) {
                    availableResources[i] = developerDatabaseTableList.get(i).getName();
                }
            } else {
                availableResources = new String[0];
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, availableResources);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);

                    for (DeveloperDatabaseTable devDBTable : developerDatabaseTableList2) {
                        if (devDBTable.getName().equals(item)) {
                            DatabaseToolsDatabaseTableRecordListFragment databaseToolsDatabaseTableRecordListFragment = new DatabaseToolsDatabaseTableRecordListFragment();
                            databaseToolsDatabaseTableRecordListFragment.setDeveloperDatabase(developerDatabase);
                            databaseToolsDatabaseTableRecordListFragment.setDeveloperDatabaseTable(devDBTable);

                            FragmentTransaction FT = getFragmentManager().beginTransaction();

                            FT.replace(R.id.hola, databaseToolsDatabaseTableRecordListFragment);

                            FT.commit();
                        }
                    }

                }
            });

            listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Database Table List Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }
        return rootView;
    }

    public class DeveloperDatabaseTableTest implements DeveloperDatabaseTable {
        String name;
        List<String> fieldsNames;

        DeveloperDatabaseTableTest(String name, List<String> fieldsNames) {
            this.name = name;
            this.fieldsNames = fieldsNames;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<String> getFieldNames() {
            return fieldsNames;
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