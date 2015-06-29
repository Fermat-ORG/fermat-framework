package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.LogToolsFragment</code>
 * haves all methods for the log tools activity of a developer
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 *
 * @version 1.0
 */
public class LogToolsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    private LogTool logTool;

    private static Platform platform = new Platform();

    public static LogToolsFragment newInstance(int position) {
        LogToolsFragment f = new LogToolsFragment();
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
                logTool = toolManager.getLogTool();
            } catch (Exception e) {
                showMessage("CantGetToolManager - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            showMessage("Unexpected error get tool manager - " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String selectedWord = ((TextView) info.targetView).getText().toString();
        selectedWord = selectedWord.split(" \\|\\| ")[0];

        menu.setHeaderTitle(selectedWord);
        menu.add(0, 1, 0, "NOT_LOGGING");
        menu.add(0, 2, 0, "MINIMAL_LOGGING");
        menu.add(0, 3, 0, "MODERATE_LOGGING");
        menu.add(0, 4, 0, "AGGRESSIVE_LOGGING");
    }

    public boolean onContextItemSelected(MenuItem item) {
       /* AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Object item = getListAdapter().getItem(info.position);*/
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedWord = ((TextView) info.targetView).getText().toString();
        selectedWord = selectedWord.split(" \\|\\| ")[0];

        if (item.getItemId() == 1) {
            changeLogLevel(LogLevel.NOT_LOGGING, selectedWord);
        } else if (item.getItemId() == 2) {
            changeLogLevel(LogLevel.MINIMAL_LOGGING, selectedWord);
        } else if (item.getItemId() == 3) {
            changeLogLevel(LogLevel.MODERATE_LOGGING, selectedWord);
        } else if (item.getItemId() == 4) {
            changeLogLevel(LogLevel.AGGRESSIVE_LOGGING, selectedWord);
        } else {
            return false;
        }
        return true;
    }

    private void changeLogLevel(LogLevel logLevel, String resource) {
        try {
            String name = resource.split(" - ")[0];
            String type = resource.split(" - ")[1];
            if (type.equals("Addon")) {
                Addons addon = Addons.getByKey(name);
                logTool.setLogLevel(addon, logLevel);
            } else if (type.equals("Plugin")) {
                Plugins plugin = Plugins.getByKey(name);
                logTool.setLogLevel(plugin, logLevel);
            }

            TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelLog);
            labelDatabase.setVisibility(View.GONE);

            LogToolsFragment logToolsFragment = new LogToolsFragment();

            FragmentTransaction FT = getFragmentManager().beginTransaction();

            FT.replace(R.id.hola, logToolsFragment);

            FT.commit();
        } catch (Exception e) {
            System.out.println("*********** soy un error "+e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log_tools, container, false);
        try {
            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            List<Plugins> plugins = logTool.getAvailablePluginList();
            List<Addons> addons = logTool.getAvailableAddonList();

            List<String> list = new ArrayList<>();

            for(Plugins plugin : plugins){ list.add(plugin.getKey()+" - Plugin || LogLevel: "+logTool.getLogLevel(plugin)); }
            for(Addons addon : addons){ list.add(addon.getKey() + " - Addon || LogLevel: " + logTool.getLogLevel(addon)); }

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

            listView.setAdapter(adapter);

            registerForContextMenu(listView);
        } catch (Exception e) {
            showMessage("LogTools Fragment onCreateView Exception - " + e.getMessage());
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