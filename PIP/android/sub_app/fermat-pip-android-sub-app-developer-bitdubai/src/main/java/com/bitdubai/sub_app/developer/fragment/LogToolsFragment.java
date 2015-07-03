package com.bitdubai.sub_app.developer.fragment;

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


import com.bitdubai.fermat_api.layer.pip_actor.developer.ClassHierarchyLevels;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.common.Loggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, List<ClassHierarchyLevels>> pluginClasses;
    //List<LoggerPluginClassHierarchy> loggerPluginClassHierarchy;

    private static final String ARG_POSITION = "position";
    View rootView;

    private LogTool logTool;

    private static Platform platform = new Platform();

    private List<Loggers> lstLoggers;

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

        pluginClasses = new HashMap<String,List<ClassHierarchyLevels>>();

        /**
         * I will load the list of classes that will be used in other fragments.
         */
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String selectedWord = ((TextView) info.targetView).getText().toString();


        menu.setHeaderTitle(selectedWord);
        menu.add(LogLevel.NOT_LOGGING.toString());
        menu.add(LogLevel.MINIMAL_LOGGING.toString());
        menu.add(LogLevel.MODERATE_LOGGING.toString());
        menu.add(LogLevel.AGGRESSIVE_LOGGING.toString());
    }

    public boolean onContextItemSelected(MenuItem item) {
       /* AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Object item = getListAdapter().getItem(info.position);*/
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedWord = ((TextView) info.targetView).getText().toString();


        if (item.getTitle() == LogLevel.NOT_LOGGING.toString()) {
            changeLogLevel(LogLevel.NOT_LOGGING, selectedWord);
        } else if (item.getTitle() == LogLevel.MINIMAL_LOGGING.toString()) {
            changeLogLevel(LogLevel.MINIMAL_LOGGING, selectedWord);
        } else if (item.getTitle() == LogLevel.MODERATE_LOGGING.toString()) {
            changeLogLevel(LogLevel.MODERATE_LOGGING, selectedWord);
        } else if (item.getTitle() == LogLevel.AGGRESSIVE_LOGGING.toString()) {
            changeLogLevel(LogLevel.AGGRESSIVE_LOGGING, selectedWord);
        } else {
            return false;
        }
        return true;
    }

    private void changeLogLevel(LogLevel logLevel, String resource) {
        try {
            //String name = resource.split(" - ")[0];
           // String type = resource.split(" - ")[1];
           // if (type.equals("Addon")) {
            //    Addons addon = Addons.getByKey(name);
           //     logTool.setLogLevel(addon, logLevel);
           // } else // por ahora no tengo como detectar si es un plug in o no.if (type.equals("Plugin"))
             //{
                Plugins plugin = Plugins.getByKey("Bitcoin Crypto Network");
                //logTool.setLogLevel(plugin, logLevel);
            /**
             * Now I must look in pluginClasses map the match of the selected class to pass the full path
             */
            HashMap<String, LogLevel> data = new HashMap<String, LogLevel>();
            for (ClassHierarchyLevels c : pluginClasses.get(plugin.getKey())){
                    if (c.getLevel3().equals(resource))
                        data.put(c.getFullPath(), logLevel);
                if (c.getLevel2().equals(resource))
                        data.put(c.getFullPath(), logLevel);
                if (c.getLevel1().equals(resource))
                        data.put(c.getFullPath(), logLevel);
            }
                logTool.setNewLogLevelInClass(plugin, data);

            //}

            TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelLog);
            labelDatabase.setVisibility(View.GONE);

            LogToolsFragment logToolsFragment = new LogToolsFragment();

            FragmentTransaction FT = getFragmentManager().beginTransaction();

            FT.replace(R.id.logContainer, logToolsFragment);

            FT.commit();
        } catch (Exception e) {
            System.out.println("*********** soy un error " + e.getMessage());
        }
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log_tools, container, false);

        lstLoggers=new ArrayList<Loggers>();
        try {
            // Get ListView object from xml
            final ListView listView = (ListView) rootView.findViewById(R.id.listaLogResources);

            List<Plugins> plugins = logTool.getAvailablePluginList();
            List<Addons> addons = logTool.getAvailableAddonList();

            List<String> list = new ArrayList<>();

            for(Plugins plugin : plugins){

                list.add(plugin.getKey()); //+" - Plugin || LogLevel: "+logTool.getLogLevel(plugin));
                /**
                 * I will get the list of the available classes on the plug in
                 */
                String level1="";
                String level2="";
                String toReplace = "";
                List<ClassHierarchyLevels> newList = new ArrayList<ClassHierarchyLevels>();
                //esto es sacar con getClassesHierarchy
                for (ClassHierarchyLevels classes : logTool.getClassesHierarchyPlugins(plugin)){
                    //loading de loggers class

                    Loggers log = new Loggers();
                    log.level0=plugin.getKey();
                    log.level1=classes.getLevel1();
                    log.level2=classes.getLevel2();
                    log.level3=classes.getLevel3();
                    log.fullPath=classes.getFullPath();
                    log.type=Loggers.TYPE_PLUGIN;
                    log.picture="plugin";

                    /**
                     * I insert the modified class in a new map with the plug in and the classes.
                     */
                    //newList.add(classes);
                }

            }

            for(Addons addon : addons) {

                //list.add(plugin.getKey()); //+" - Plugin || LogLevel: "+logTool.getLogLevel(plugin));
                /**
                 * I will get the list of the available classes on the plug in
                 */
                String level1 = "";
                String level2 = "";
                String toReplace = "";
                List<ClassHierarchyLevels> newList = new ArrayList<ClassHierarchyLevels>();
                //esto es sacar con getClassesHierarchy
                for (ClassHierarchyLevels classes : logTool.getClassesHierarchyAddons(addon)) {
                    //loading de loggers class

                    Loggers log = new Loggers();
                    log.level0 = addon.getKey();
                    log.level1 = classes.getLevel1();
                    log.level2 = classes.getLevel2();
                    log.level3 = classes.getLevel3();
                    log.fullPath = classes.getFullPath();
                    log.type = Loggers.TYPE_PLUGIN;
                    log.picture = "plugin";

                    /**
                     * I insert the modified class in a new map with the plug in and the classes.
                     */
                    //newList.add(classes);
                }
            /*for(Addons addon : addons){ list.add(addon.getKey() + " - Addon || LogLevel: " + logTool.getLogLevel(addon)); }

            String[] availableResources;
            if (list.size() > 0) {
                availableResources = new String[list.size()];
                for(int i = 0; i < list.size() ; i++) {
                    availableResources[i] = list.get(i);
                }
            } else {
                availableResources = new String[0];
            }*/

                //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                //        android.R.layout.simple_list_item_1, android.R.id.text1, availableResources);

                //listView.setAdapter(adapter);

                registerForContextMenu(listView);
            }}catch (Exception e){
            e.printStackTrace();
        }
        /*} catch (Exception e) {
            showMessage("LogTools Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }*/

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