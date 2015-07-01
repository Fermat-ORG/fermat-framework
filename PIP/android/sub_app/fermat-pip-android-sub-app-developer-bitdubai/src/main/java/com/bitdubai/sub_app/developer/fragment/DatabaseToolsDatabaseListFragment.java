package com.bitdubai.sub_app.developer.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.common.Resource;

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

    private Resource resource;


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
            if (Resource.TYPE_ADDON==resource.type) {
                Toast.makeText(getActivity(),resource.resource,Toast.LENGTH_SHORT).show();
                Addons addon = Addons.getByKey(resource.resource);
                this.developerDatabaseList = databaseTools.getDatabaseListFromAddon(addon);
            } else if (Resource.TYPE_PLUGIN==resource.type) {
                Plugins plugin = Plugins.getByKey(resource.resource);
                this.developerDatabaseList = databaseTools.getDatabaseListFromPlugin(plugin);
            }

            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
            labelDatabase.setText(resource+" - Databases List");

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
                            databaseToolsDatabaseTableListFragment.setResource(resource.resource);
                            databaseToolsDatabaseTableListFragment.setDeveloperDatabase(devDB);

                            FragmentTransaction FT = getFragmentManager().beginTransaction();

                            FT.replace(R.id.hola, databaseToolsDatabaseTableListFragment);

                            FT.addToBackStack(null);

                            FT.commit();
                        }
                    }

                }
            });

            listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Database List onCreateView Exception - " + e.getMessage());
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



    public void setResource(Resource resource) {
        this.resource = resource;
    }
}