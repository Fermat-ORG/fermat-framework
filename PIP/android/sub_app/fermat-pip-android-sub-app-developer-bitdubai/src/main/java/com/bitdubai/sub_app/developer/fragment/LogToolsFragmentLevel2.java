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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.ArrayListLoggers;
import com.bitdubai.sub_app.developer.common.Loggers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.LogToolsFragment</code>
 * haves all methods for the log tools activity of a developer
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //getActivity().getMenuInflater().inflate(R.menu.logs_menu, menu);
        GridView gv = (GridView) v;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //String selectedWord = ((TextView) info.targetView).getText().toString();
        //menu.setHeaderTitle(selectedWord);
        //menu.add(LogLevel.NOT_LOGGING.toString());
        menu.add(6, Loggers.LOGGER_LEVEL_NOT_LOGGING, 1, LogLevel.NOT_LOGGING.toString());
        menu.add(6,Loggers.LOGGER_LEVEL_MINIMAL_LOGGING,1,LogLevel.MINIMAL_LOGGING.toString());
        menu.add(6,Loggers.LOGGER_LEVEL_MODERATE_LOGGING,1,LogLevel.MODERATE_LOGGING.toString());
        menu.add(6,Loggers.LOGGER_LEVEL_AGGRESSIVE_LOGGING,1,LogLevel.AGGRESSIVE_LOGGING.toString());
        int position = info.position;
        /*if(!(position==0 || position==2))
        {
            menu.close();
            )*/
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
       /* AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Object item = getListAdapter().getItem(info.position);*/
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        RelativeLayout relativeLayout = (RelativeLayout)info.targetView;
        String selectedWord = ((TextView)relativeLayout.findViewById(R.id.company_text_view)).getText().toString();
        //String selectedWord = ((TextView) info.targetView).getText().toString();
        //selectedWord = info.targetView.get

        int i= info.position;
        Loggers logger=lstLoggers.get(info.position);

        /*switch  (item.getItemId()) {
            case  Loggers.LOGGER_LEVEL_NOT_LOGGING: {
                Toast.makeText(getActivity(), logger.level0, Toast.LENGTH_SHORT).show();
                break;
            }
            case  Loggers.LOGGER_LEVEL_MINIMAL_LOGGING: {
                Toast.makeText(getActivity(), logger.level1, Toast.LENGTH_SHORT).show();
                break;
            }
            case  Loggers.LOGGER_LEVEL_MODERATE_LOGGING: {
                Toast.makeText(getActivity(), logger.level2, Toast.LENGTH_SHORT).show();
                break;
            }
            case  Loggers.LOGGER_LEVEL_AGGRESSIVE_LOGGING: {
                Toast.makeText(getActivity(), logger.level3, Toast.LENGTH_SHORT).show();
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
            data.put(resource, logLevel);
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

            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.grid_items, lstLoggersToShow);
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

            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_items_with_button, parent, false);


                holder = new ViewHolder();




                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(),item.fullPath,Toast.LENGTH_SHORT);
                        Loggers item=(Loggers) gridView.getItemAtPosition(position);

                        // Reload current fragment
                        LogToolsFragmentLevel2 frg = null;
                        frg =(LogToolsFragmentLevel2) getFragmentManager().findFragmentByTag("fragmento2");

                        if(loggerLevel==ArrayListLoggers.LEVEL_1){
                            ArrayListLoggers lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_2);
                            frg.setLoggers(lst);
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
                //holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);

                TextView textView =(TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                holder.companyTextView = textView;


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
                    stringToShowLevel=item.classHierarchyLevels.getLevel1().substring(item.classHierarchyLevels.getLevel1().length() - 20, item.classHierarchyLevels.getLevel1().length());
                    if(item.classHierarchyLevels.getLevel2()==null){
                        item.picture="java_class";
                        holder.imageView.setOnClickListener(null);
                    }
                    //stringToShowLevel=item.level1;
                    break;
                }
                case ArrayListLoggers.LEVEL_2:{
                    stringToShowLevel=item.classHierarchyLevels.getLevel2();
                    if(item.classHierarchyLevels.getLevel3()==null){
                        item.picture="java_class";
                        holder.imageView.setOnClickListener(null);
                    }
                    break;
                }
                case ArrayListLoggers.LEVEL_3:{
                    stringToShowLevel=item.classHierarchyLevels.getLevel3();
                    item.picture="java_class";
                    holder.imageView.setOnClickListener(null);
                    break;
                }

            }


            holder.companyTextView.setText(stringToShowLevel);
            // holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());




            switch (item.picture) {
                case "plugin":
                    holder.imageView.setImageResource(R.drawable.folder);
                    holder.imageView.setTag("CPWWRWAKAV1M|1");
                    break;
                case "addon":
                    holder.imageView.setImageResource(R.drawable.folder);
                    holder.imageView.setTag("CPWWRWAKAV1M|2");
                    break;
                case "java_class":
                    holder.imageView.setImageResource(R.drawable.java_class);
                    holder.imageView.setTag("CPWWRWAKAV1M|3");
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.fermat);
                    holder.imageView.setTag("CPWWRWAKAV1M|4");
                    break;
            }

            holder.btnLogger= (ImageView) convertView.findViewById(R.id.imageView_logger);
            holder.btnLogger.setImageResource(R.drawable.ic_menu_drawer);

            holder.btnLogger.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //Toast.makeText(getActivity(), "Soy una estrella feliz1", Toast.LENGTH_SHORT).show();
                    String loggerText = holder.companyTextView.getText().toString();
                    PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int itemId = menuItem.getItemId();
                            if (itemId == R.id.menu_no_logging) {
                                //TODO: HAcer el cambio acá para que haga el changelevel
                                changeLogLevel(item.pluginKey,LogLevel.NOT_LOGGING,item.classHierarchyLevels.getFullPath());
                                //changeLogLevel();
                                return true;
                            } else if (itemId == R.id.menu_minimal) {
                                changeLogLevel(item.pluginKey,LogLevel.MINIMAL_LOGGING,item.classHierarchyLevels.getFullPath());
                                return true;
                            } else if (itemId == R.id.menu_moderate) {
                                changeLogLevel(item.pluginKey,LogLevel.MODERATE_LOGGING,item.classHierarchyLevels.getFullPath());
                                return true;
                            } else if (itemId == R.id.menu_aggresive) {
                                changeLogLevel(item.pluginKey,LogLevel.AGGRESSIVE_LOGGING,item.classHierarchyLevels.getFullPath());
                                return true;

                            }

                            return false;
                        }
                    });

                    popupMenu.inflate(R.menu.popup_menu);
                    popupMenu.show();
                    return true;
                }
            });

            return convertView;
        }


    }
    /**
     * ViewHolder.
     */
    private class ViewHolder {



        public ImageView imageView;
        public TextView companyTextView;
        public ImageView btnLogger;

    }
}