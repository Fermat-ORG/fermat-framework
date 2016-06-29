package com.bitdubai.sub_app.developer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.sub_app.developer.FragmentFactory.DeveloperFragmentsEnumType;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.ArrayListLoggers;
import com.bitdubai.sub_app.developer.common.Loggers;
import com.bitdubai.sub_app.developer.common.StringUtils;

import java.util.ArrayList;
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
public class LogToolsFragmentLevel1 extends AbstractFermatFragment<ReferenceAppFermatSession<ToolManager>, ResourceProviderManager> {

//    private static final String CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS = Fragments.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS.getKey();
    private Map<String, List<ClassHierarchyLevels>> pluginClasses;
    View rootView;
    //    private LogTool logTool;
    private ErrorManager errorManager;
    ToolManager toolManager;
    private ArrayListLoggers lstLoggers;
    private GridView gridView;
    private int loggerLevel = 1;

    /**
     * SubApp Session
     */
    public static LogToolsFragmentLevel1 newInstance() {
        return new LogToolsFragmentLevel1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        lstLoggers = (ArrayListLoggers) appSession.getData("list");
        errorManager = appSession.getErrorManager();
        try {
            toolManager = appSession.getModuleManager();
//            logTool = toolManager.getLogTool();

//        } catch (CantGetLogToolException e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        pluginClasses = new HashMap<String, List<ClassHierarchyLevels>>();
    }


    private void changeLogLevel(PluginVersionReference pluginKey, LogLevel logLevel, String resource) {

        try {

            /**
             * Now I must look in pluginClasses map the match of the selected class to pass the full path
             */
            HashMap<String, LogLevel> data = new HashMap<String, LogLevel>();
            data.put(resource, logLevel);
            appSession.getModuleManager().setNewLogLevelInClass(pluginKey, data);

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        try {

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }

            ArrayListLoggers lstLoggersToShow = new ArrayListLoggers();
            for (Loggers loggers : lstLoggers) {
                //String level_0 = loggers.level0;
                switch (loggerLevel) {
                    case ArrayListLoggers.LEVEL_1: {
                        if (!lstLoggersToShow.containsLevel1(loggers)) {
                            lstLoggersToShow.add(loggers);
                        }
                        break;
                    }
                    case ArrayListLoggers.LEVEL_2:
                        if (!lstLoggersToShow.containsLevel2(loggers)) {
                            lstLoggersToShow.add(loggers);
                        }
                        break;
                    case ArrayListLoggers.LEVEL_3:
                        if (!lstLoggersToShow.containsLevel3(loggers)) {
                            lstLoggersToShow.add(loggers);
                        }
                        break;
                }

            }


            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.grid_items, lstLoggersToShow);
            _adpatrer.notifyDataSetChanged();
            gridView.setAdapter(_adpatrer);

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        //registerForContextMenu(gridView);
        return rootView;
    }


    public void setLoggerLevel(int level) {
        loggerLevel = level;
    }

    public int getLoggerLevel() {
        return loggerLevel;
    }

    public void setLoggers(ArrayListLoggers lstLoggers) {
        this.lstLoggers = lstLoggers;
    }

//    public void setDeveloperSubAppSession(DeveloperSubAppSessionReferenceApp developerSubAppSession) {
//        this.developerSubAppSession = developerSubAppSession;
//    }

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
                convertView = inflater.inflate(R.layout.grid_items_with_button2, parent, false);


                holder = new ViewHolder();


                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(),item.fullPath,Toast.LENGTH_SHORT);
                        Loggers item = (Loggers) gridView.getItemAtPosition(position);

                        // Reload current fragment
                        //  LogToolsFragmentLevel1 frg = null;
                        //  frg =(LogToolsFragmentLevel1) getFragmentManager().findFragmentByTag("fragmento2");

                        ArrayListLoggers lst = null;
                        int level = 0;
                        if (loggerLevel == ArrayListLoggers.LEVEL_1) {

                            lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_1);
                            // frg.setLoggers(lst);
                            // frg.setLoggerLevel(ArrayListLoggers.LEVEL_2);
                            level = ArrayListLoggers.LEVEL_2;
                        } else if (loggerLevel == ArrayListLoggers.LEVEL_2) {
                            lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_2);
                            //  frg.setLoggerLevel(ArrayListLoggers.LEVEL_3);
                            //  frg.setLoggers(lst);
                            level = ArrayListLoggers.LEVEL_3;

                        }

                        //set the next fragment and params

                        appSession.setData("list", lst);
                        appSession.setData("level", level);
//                        ((FermatScreenSwapper) getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT.getKey(), R.id.logContainer, null);
                        changeActivity(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_2_TOOLS, appSession.getAppPublicKey());
                    }
                });
                holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        String loggerText = holder.companyTextView.getText().toString();

                        CustomDialogClass cdd = new CustomDialogClass(getActivity(), item, item.pluginVersionReference);
                        cdd.show();

                        return true;
                    }
                });
                //holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);

                TextView textView = (TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                textView.setGravity(Gravity.CENTER);
                holder.companyTextView = textView;


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String stringToShowLevel = "Nada cargado";
            switch (loggerLevel) {
                case ArrayListLoggers.LEVEL_1: {


                    stringToShowLevel = item.classHierarchyLevels.getLevel1();
                    if (item.classHierarchyLevels.getLevel2() == null) {
                        stringToShowLevel = StringUtils.splitCamelCase(stringToShowLevel);
                        item.picture = "java_class";
                        holder.imageView.setOnClickListener(null);
                    } else {
                        String[] stringToFormat = item.classHierarchyLevels.getLevel1().split("\\.");
                        stringToShowLevel = stringToFormat[stringToFormat.length - 1];
                        stringToShowLevel = StringUtils.replaceStringByPoint(stringToShowLevel);
                        stringToShowLevel = StringUtils.replaceStringByUnderScore(stringToShowLevel);
                    }
                    //stringToShowLevel=item.level1;
                    break;
                }
                case ArrayListLoggers.LEVEL_2: {
                    stringToShowLevel = StringUtils.splitCamelCase(item.classHierarchyLevels.getLevel2());
                    if (item.classHierarchyLevels.getLevel3() == null) {
                        item.picture = "java_class";
                        holder.imageView.setOnClickListener(null);

                    }
                    break;
                }
                case ArrayListLoggers.LEVEL_3: {
                    stringToShowLevel = item.classHierarchyLevels.getLevel3();
                    item.picture = "java_class";
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

    public class CustomDialogClass extends Dialog implements
            View.OnClickListener {


        private Loggers logger;
        private PluginVersionReference pluginKey;
        public Activity c;
        public Dialog d;

        ListView list;
        String[] web = {
                "Not logging",
                "Minimal logging",
                "Moderate logging",
                "Agressive logging"
        };

        List<String> lstEnum;

        Integer[] img = {
                R.drawable.ic_action_accept_grey,
                0,
                0,
                0
        };

        public CustomDialogClass(Activity a, Loggers loggers, PluginVersionReference pluginKey) {
            super(a);
            this.logger = loggers;
            this.pluginKey = pluginKey;
            testing();
            //loadEnumsLogger();
            // TODO Auto-generated constructor stub
            this.c = a;
            setLogLevelImage();

            logger.logLevel = LogLevel.NOT_LOGGING;
        }

        private void testing() {
            lstEnum = new ArrayList<>();
            for (int i = 0; i < LogLevel.values().length; i++) {
                lstEnum.add(LogLevel.values()[i].getDisplayName());
            }
        }

        private void setLogLevelImage() {
            if (logger.logLevel != null) {
                switch (logger.logLevel) {
                    case NOT_LOGGING:
                        img = new Integer[]{
                                1, 0, 0, 0
                        };
                        break;
                    case MINIMAL_LOGGING:
                        img = new Integer[]{
                                0, 1, 0, 0
                        };
                        break;
                    case MODERATE_LOGGING:
                        img = new Integer[]{
                                0, 0, 1, 0
                        };
                        break;
                    case AGGRESSIVE_LOGGING:
                        img = new Integer[]{
                                0, 0, 0, 1
                        };
                        break;
                }
            } else {
                logger.logLevel = LogLevel.NOT_LOGGING;
            }
        }

        /*private void loadEnumsLogger(){
            LogLevel[] enum_logLevel = LogLevel.values();
            List<String> lstEnum = new ArrayList<String>();
            for(int i=0;i<enum_logLevel.length;i++){
                lstEnum.add(enum_logLevel[i].getDisplayName());
            }
        }
        private void setIconSelected(){
            if(logger!=null){
                logger.logLevel.getCode();
            }else{
                //logger=new LogLevel(LogLevel.);
            }

        }*/


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.popup);


            CustomList adapter = new
                    CustomList(c, lstEnum, img);
            list = (ListView) findViewById(R.id.listView);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(c, web[+position] + " activated", Toast.LENGTH_SHORT).show();
                    String item = list.getItemAtPosition(position).toString();
                    if (item.compareTo(LogLevel.NOT_LOGGING.toString()) == 0) {
                        changeLogLevel(pluginKey, LogLevel.NOT_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.NOT_LOGGING;
                    } else if (item.compareTo(LogLevel.MINIMAL_LOGGING.toString()) == 0) {
                        changeLogLevel(pluginKey, LogLevel.MINIMAL_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.MINIMAL_LOGGING;
                    } else if (item.compareTo(LogLevel.MODERATE_LOGGING.toString()) == 0) {
                        changeLogLevel(pluginKey, LogLevel.MODERATE_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.MODERATE_LOGGING;
                    } else if (item.compareTo(LogLevel.AGGRESSIVE_LOGGING.toString()) == 0) {
                        changeLogLevel(pluginKey, LogLevel.AGGRESSIVE_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.AGGRESSIVE_LOGGING;
                    }
                    dismiss();
                }
            });

        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            /*if (i == R.id.btn_yes) {
                c.finish();

            } else if (i == R.id.btn_no) {
                dismiss();

            } else {
            }*/
            dismiss();
        }

        public class CustomList extends ArrayAdapter<String> {

            private final Activity context;
            private final List<String> listEnumsToDisplay;
            private final Integer[] imageId;

            public CustomList(Activity context,
                              List<String> listEnumsToDisplay, Integer[] imageId) {
                super(context, R.layout.list_single, listEnumsToDisplay);
                this.context = context;
                this.listEnumsToDisplay = listEnumsToDisplay;
                this.imageId = imageId;

            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View rowView = inflater.inflate(R.layout.list_single, null, true);
                TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

                ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
                txtTitle.setTextColor(Color.WHITE);
                txtTitle.setText(listEnumsToDisplay.get(position));
                //txtTitle.setText(LogLevel.MINIMAL_LOGGING.toString());

                setLogLevelImage();
                if (imageId[position] != 0) {
                    imageView.setImageResource(R.drawable.ic_action_accept_grey);
                }

                return rowView;
            }
        }

    }
}