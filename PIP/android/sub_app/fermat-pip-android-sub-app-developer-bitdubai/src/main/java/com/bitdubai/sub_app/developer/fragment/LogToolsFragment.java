package com.bitdubai.sub_app.developer.fragment;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_api.layer.pip_actor.developer.ClassHierarchyLevels;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
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
public class LogToolsFragment extends Fragment {


    private Map<String, List<ClassHierarchyLevels>> pluginClasses;
    //List<LoggerPluginClassHierarchy> loggerPluginClassHierarchy;

    private static final String ARG_POSITION = "position";
    View rootView;

    private LogTool logTool;

    private static Platform platform = new Platform();

    private ArrayListLoggers lstLoggers;

    private GridView gridView;

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

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String selectedWord = ((TextView) info.targetView).getText().toString();


        menu.setHeaderTitle(selectedWord);
        menu.add(LogLevel.NOT_LOGGING.toString());
        menu.add(LogLevel.MINIMAL_LOGGING.toString());
        menu.add(LogLevel.MODERATE_LOGGING.toString());
        menu.add(LogLevel.AGGRESSIVE_LOGGING.toString());
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        GridView gv = (GridView) v;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //String selectedWord = ((TextView) info.targetView).getText().toString();
        //menu.setHeaderTitle(selectedWord);
        //menu.add(LogLevel.NOT_LOGGING.toString());
        menu.add(5,Loggers.LOGGER_LEVEL_NOT_LOGGING,1,LogLevel.NOT_LOGGING.toString());
        menu.add(5,Loggers.LOGGER_LEVEL_MINIMAL_LOGGING,1,LogLevel.MINIMAL_LOGGING.toString());
        menu.add(5,Loggers.LOGGER_LEVEL_MODERATE_LOGGING,1,LogLevel.MODERATE_LOGGING.toString());
        menu.add(5,Loggers.LOGGER_LEVEL_AGGRESSIVE_LOGGING,1,LogLevel.AGGRESSIVE_LOGGING.toString());
        int position = info.position;
        /*if(!(position==0 || position==2))
        {
            menu.close();
            )*/
        }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        RelativeLayout relativeLayout = (RelativeLayout)info.targetView;
        String selectedWord = ((TextView)relativeLayout.findViewById(R.id.company_text_view)).getText().toString();
        Loggers logger=lstLoggers.get(info.position);

        //TODO: MATI
        //TODO: rodri fijate que ahí lo está haciendo

        switch  (item.getItemId()) {
            case  Loggers.LOGGER_LEVEL_NOT_LOGGING: {
                Toast.makeText(getActivity(), selectedWord, Toast.LENGTH_SHORT).show();
                changeLogLevel(logger.level0, LogLevel.NOT_LOGGING, selectedWord);
                break;
            }
            case  Loggers.LOGGER_LEVEL_MINIMAL_LOGGING: {
                //Toast.makeText(getActivity(), logger.level1, Toast.LENGTH_SHORT).show();
                changeLogLevel(logger.level1, LogLevel.MINIMAL_LOGGING, selectedWord);
                break;
            }
            case  Loggers.LOGGER_LEVEL_MODERATE_LOGGING: {
                //Toast.makeText(getActivity(), logger.level2, Toast.LENGTH_SHORT).show();
                changeLogLevel(logger.level0, LogLevel.MODERATE_LOGGING, selectedWord);
                break;
            }
            case  Loggers.LOGGER_LEVEL_AGGRESSIVE_LOGGING: {
                //Toast.makeText(getActivity(), logger.level3, Toast.LENGTH_SHORT).show();
                changeLogLevel(logger.level0, LogLevel.AGGRESSIVE_LOGGING, selectedWord);
                break;
            }
            default: {
                Toast.makeText(getActivity(), "Nada seleccionado", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        //preguntar que carajo es el resource
        /*Loggers logger = lstLoggers.get(info.position);
        if (item.getTitle() == LogLevel.NOT_LOGGING.toString()) {
            changeLogLevel(logger.level0,LogLevel.NOT_LOGGING, selectedWord);
        } else if (item.getTitle() == LogLevel.MINIMAL_LOGGING.toString()) {
            changeLogLevel(logger.level0,LogLevel.MINIMAL_LOGGING, selectedWord);
        } else if (item.getTitle() == LogLevel.MODERATE_LOGGING.toString()) {
            changeLogLevel(logger.level0,LogLevel.MODERATE_LOGGING, selectedWord);
        } else if (item.getTitle() == LogLevel.AGGRESSIVE_LOGGING.toString()) {
            changeLogLevel(logger.level0,LogLevel.AGGRESSIVE_LOGGING, selectedWord);
        } else {
            return false;
        }*/
        return true;
    }

    private void changeLogLevel(String pluginKey,LogLevel logLevel, String resource) {
        try {
            //String name = resource.split(" - ")[0];
           // String type = resource.split(" - ")[1];
           // if (type.equals("Addon")) {
            //    Addons addon = Addons.getByKey(name);
           //     logTool.setLogLevel(addon, logLevel);
           // } else // por ahora no tengo como detectar si es un plug in o no.if (type.equals("Plugin"))
             //{
                //Plugins plugin = Plugins.getByKey("Bitcoin Crypto Network");
            Plugins plugin = Plugins.getByKey(pluginKey);
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

            //TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelLog);
            //labelDatabase.setVisibility(View.GONE);

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

        lstLoggers=new ArrayListLoggers();
        try {
            // Get ListView object from xml
            gridView = (GridView) rootView.findViewById(R.id.gridView);

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
                    log.level0=classes.getLevel0();
                    log.level1=classes.getLevel1();
                    log.level2=classes.getLevel2();
                    log.level3=classes.getLevel3();
                    log.fullPath=classes.getFullPath();
                    log.type=Loggers.TYPE_PLUGIN;
                    log.picture="plugin";
                    lstLoggers.add(log);
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
                    log.level0 = classes.getLevel0();
                    log.level1 = classes.getLevel1();
                    log.level2 = classes.getLevel2();
                    log.level3 = classes.getLevel3();
                    log.fullPath = classes.getFullPath();
                    log.type = Loggers.TYPE_ADDON;
                    log.picture = "addon";
                    lstLoggers.add(log);
                }


            }


            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }

            ArrayListLoggers lstLoggersToShow=new ArrayListLoggers();
            for(Loggers loggers:lstLoggers){
               //String level_0 = loggers.level0;
                if(!lstLoggersToShow.containsLevel0(loggers)){
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
        registerForContextMenu(gridView);
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
                        Loggers item = (Loggers) gridView.getItemAtPosition(position);


                        LogToolsFragmentLevel2 logToolsFragmentLevel2 = new LogToolsFragmentLevel2();

                        logToolsFragmentLevel2.setLoggers(lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_0));
                        //DatabaseToolsDatabaseListFragment databaseToolsDatabaseListFragment = new DatabaseToolsDatabaseListFragment();

                        //databaseToolsDatabaseListFragment.setResource(item);

                        FragmentTransaction FT = getFragmentManager().beginTransaction();


                        //FT.add(databaseToolsDatabaseListFragment, TAG_DATABASE_TOOLS_FRAGMENT);
                        FT.replace(R.id.logContainer, logToolsFragmentLevel2, "fragmento2");
                        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        FT.commit();
                    }
                });
                /*holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Toast.makeText(getActivity(),"tocando",Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });
                */
                registerForContextMenu(holder.imageView );
                TextView textView =(TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                holder.companyTextView = textView;


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(item.level0);
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