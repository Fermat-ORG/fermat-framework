package com.bitdubai.sub_app.developer.fragment;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.ArrayListLoggers;
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
public class LogToolsFragmentLevel2 extends Fragment {

    private Map<String, List<ClassHierarchyLevels>> pluginClasses;

    private static final String ARG_POSITION = "position";
    View rootView;

    private LogTool logTool;

    private static Platform platform = new Platform();

    private ArrayListLoggers lstLoggers;
    private GridView gridView;

    private int loggerLevel=1;

    public static LogToolsFragmentLevel2 newInstance(int position) {
        LogToolsFragmentLevel2 f = new LogToolsFragmentLevel2();
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

            //TODO ACA AGARRAR EL LEVEL 0 Y BUSCAR EL PLUGIN
                Plugins plugin = Plugins.getByKey("Bitcoin Crypto Vault");
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


            LogToolsFragmentLevel2 logToolsFragment = new LogToolsFragmentLevel2();

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

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        try{

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }

            ArrayListLoggers lstLoggersToShow=new ArrayListLoggers();
            for(Loggers loggers:lstLoggers){
                //String level_0 = loggers.level0;
                if(!lstLoggersToShow.containsLevel1(loggers)){
                    lstLoggersToShow.add(loggers);
                }
            }

            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_wallet_desktop_front_grid_item, lstLoggersToShow);
            _adpatrer.notifyDataSetChanged();
            gridView.setAdapter(_adpatrer);

        }catch (Exception e){
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
    public void setLoggerLevel(int level){
        loggerLevel=level;
    }
    public int getLoggerLevel(){
        return loggerLevel;
    }

    public void setLoggers(ArrayListLoggers lstLoggers){
        this.lstLoggers=lstLoggers;
    }

    public class AppListAdapter extends ArrayAdapter<Loggers> {


        public AppListAdapter(Context context, int textViewResourceId, List<Loggers> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Loggers item = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_wallet_desktop_front_grid_item, parent, false);


                holder = new ViewHolder();




                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(),item.fullPath,Toast.LENGTH_SHORT);
                        Loggers item=(Loggers) gridView.getItemAtPosition(position);



                        //LogToolsFragmentLevel2 logToolsFragmentLevel2 = new LogToolsFragmentLevel2();

                        //setLoggers(lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_2));
                        //DatabaseToolsDatabaseListFragment databaseToolsDatabaseListFragment = new DatabaseToolsDatabaseListFragment();

                        //databaseToolsDatabaseListFragment.setResource(item);

                        //FragmentTransaction FT = getFragmentManager().beginTransaction();


                        //FT.add(databaseToolsDatabaseListFragment, TAG_DATABASE_TOOLS_FRAGMENT);
                        //FT.replace(R.id.logContainer,getParentFragment());
                        //FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        //FT.commit();


                        // Reload current fragment
                        LogToolsFragmentLevel2 frg = null;
                        frg =(LogToolsFragmentLevel2) getFragmentManager().findFragmentByTag("fragmento2");

                        if(loggerLevel==ArrayListLoggers.LEVEL_1){
                            frg.setLoggers(lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_2));
                            frg.setLoggerLevel(ArrayListLoggers.LEVEL_2);
                        }else if(loggerLevel==ArrayListLoggers.LEVEL_2){
                            frg.setLoggerLevel(ArrayListLoggers.LEVEL_3);
                            frg.setLoggers(lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_3));

                        }

                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                    }
                });
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String stringToShowLevel="Nada cargado";
            switch (loggerLevel){
                case ArrayListLoggers.LEVEL_1:{
                    //String[] level1_splitted=item.level1.split(".");
                    //tringToShowLevel=level1_splitted[level1_splitted.length-1];
                    //Toast.makeText(getActivity(),item.level1,Toast.LENGTH_SHORT);
                    stringToShowLevel=item.level1;
                    break;
                }
                case ArrayListLoggers.LEVEL_2:{
                    stringToShowLevel=item.level2;
                    break;
                }
                case ArrayListLoggers.LEVEL_3:{
                    stringToShowLevel=item.level3;
                    break;
                }

            }


            holder.companyTextView.setText(stringToShowLevel);
            // holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());


            switch (item.picture) {
                case "plugin":
                    holder.imageView.setImageResource(R.drawable.addon);
                    holder.imageView.setTag("CPWWRWAKAV1M|1");
                    break;
                case "addon":
                    holder.imageView.setImageResource(R.drawable.plugin);
                    holder.imageView.setTag("CPWWRWAKAV1M|2");
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.fermat);
                    holder.imageView.setTag("CPWWRWAKAV1M|3");
                    break;
            }


            return convertView;
        }


    }
    /**
     * ViewHolder.
     */
    private class ViewHolder {



        public ImageView imageView;
        public TextView companyTextView;


    }
}