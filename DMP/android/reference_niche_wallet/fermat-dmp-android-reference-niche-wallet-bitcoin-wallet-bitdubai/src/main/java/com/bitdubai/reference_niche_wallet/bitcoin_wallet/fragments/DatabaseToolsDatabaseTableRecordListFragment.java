package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

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
public class DatabaseToolsDatabaseTableRecordListFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    private DatabaseTool databaseTools;

    private String resource;
    private DeveloperDatabase developerDatabase;
    private DeveloperDatabaseTable developerDatabaseTable;

    List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList;

    private static Platform platform = new Platform();

    public static DatabaseToolsDatabaseTableRecordListFragment newInstance(int position) {
        DatabaseToolsDatabaseTableRecordListFragment f = new DatabaseToolsDatabaseTableRecordListFragment();
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
            showMessage("Unexpected error get Transactions - " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);
        try {
            String name = resource.split(" - ")[0];
            String type = resource.split(" - ")[1];
            if (type.equals("Addon")) {
                Addons addon = Addons.getByKey(name);
                this.developerDatabaseTableRecordList = databaseTools.getAddonTableContent(addon, developerDatabase, developerDatabaseTable);
            } else if (type.equals("Plugin")) {
                Plugins plugin = Plugins.getByKey(name);
                this.developerDatabaseTableRecordList = databaseTools.getPluginTableContent(plugin, developerDatabase, developerDatabaseTable);
            }

            List<String> columnNames = developerDatabaseTable.getFieldNames();
            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
            labelDatabase.setText(developerDatabase.getName()+" - Table "+developerDatabaseTable.getName()+" records");

            String[] listString;

            if (developerDatabaseTableRecordList.size() > 0) {
                listString = new String[developerDatabaseTableRecordList.size()+1];
                listString[0] = strJoin(columnNames, " | ");
                for(int i = 1; i < developerDatabaseTableRecordList.size()+1 ; i++){
                    listString[i] = strJoin(developerDatabaseTableRecordList.get(i-1).getValues(), " | ");
                }
            } else {
                listString = new String[0];
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, listString);

            listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Database Table List Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }
        return rootView;
    }

    public static String strJoin(List<String> aArr, String sSep) {
        StringBuilder sbStr = new StringBuilder();
        for (int i = 0, il = aArr.size(); i < il; i++) {
            if (i > 0)
                sbStr.append(sSep);
            sbStr.append(aArr.get(i));
        }
        return sbStr.toString();
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

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setDeveloperDatabase(DeveloperDatabase developerDatabase) {
        this.developerDatabase = developerDatabase;
    }

    public void setDeveloperDatabaseTable(DeveloperDatabaseTable developerDatabaseTable) {
        this.developerDatabaseTable = developerDatabaseTable;
    }
}