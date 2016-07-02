package com.bitdubai.sub_app.developer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.module.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.ArrayListLoggers;
import com.bitdubai.sub_app.developer.common.Loggers;
import com.bitdubai.sub_app.developer.filters.DeveloperLogFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.LogToolsFragment</code>
 * haves all methods for the log tools activity of a developer
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 */
public class LogToolsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ToolManager>, ResourceProviderManager> {

    private ErrorManager errorManager;
    ToolManager toolManager;
    View rootView;
    private ArrayListLoggers lstLoggers;
    private ListView listView;
    private SearchView searchView;
    AppListAdapter _adpatrer;
    private int menuItemSize;
    Typeface tf;

    public static LogToolsFragment newInstance() {
        return new LogToolsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        errorManager = appSession.getErrorManager();

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

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

    }

    private void changeLogLevel(PluginVersionReference plugin, LogLevel logLevel, String resource) {
        try {

            /**
             * Now I must look in pluginClasses map the match of the selected class to pass the full path
             */
            HashMap<String, LogLevel> data = new HashMap<String, LogLevel>();
            data.put(resource, logLevel);
            appSession.getModuleManager().setNewLogLevelInClass(plugin, data);

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log_tools, container, false);

        lstLoggers = new ArrayListLoggers();
        try {
            // Get ListView object from xml
            listView = (ListView) rootView.findViewById(R.id.gridView);
//            gridView = (GridView) rootView.findViewById(R.id.gridView);

            List<PluginVersionReference> plugins = appSession.getModuleManager().getAvailablePluginList();
            List<AddonVersionReference> addons = appSession.getModuleManager().getAvailableAddonList();


            for (PluginVersionReference plugin : plugins) {

                for (ClassHierarchyLevels classes : appSession.getModuleManager().getClassesHierarchyPlugins(plugin)) {
                    //loading de loggers class
                    Loggers log = new Loggers();

                    log.type = Loggers.TYPE_PLUGIN;
                    log.classHierarchyLevels = classes;
                    log.picture = "plugin";
                    log.pluginVersionReference = plugin;
                    lstLoggers.add(log);
                }
            }
            /**
             * TODO add addons
             */


//            Configuration config = getResources().getConfiguration();
//            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                gridView.setNumColumns(6);
//            } else {
//                gridView.setNumColumns(3);
//            }

            ArrayListLoggers lstLoggersToShow = new ArrayListLoggers();
            for (Loggers loggers : lstLoggers) {
                //String level_0 = loggers.level0;
                if (!lstLoggersToShow.containsLevel0(loggers)) {
                    lstLoggersToShow.add(loggers);

                }
            }

            Collections.sort(lstLoggersToShow, new Comparator<Loggers>() {
                public int compare(Loggers o1, Loggers o2) {
                    return (o1.classHierarchyLevels.getLevel0()).compareTo(o2.classHierarchyLevels.getLevel0());
                }
            });

            _adpatrer = new AppListAdapter(getActivity(), R.layout.grid_items, lstLoggersToShow);
            _adpatrer.notifyDataSetChanged();
            listView.setAdapter(_adpatrer);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Loggers item = (Loggers) listView.getItemAtPosition(position);
                    ArrayListLoggers lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_0);

                    //set the next fragment and params

                    appSession.setData("list", lst);
                    appSession.setData("filterString", _adpatrer.getFilterString());
//                        ((FermatScreenSwapper) getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey(), R.id.logContainer, null);
                    changeActivity(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS, appSession.getAppPublicKey());


                }
            });
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        registerForContextMenu(listView);
        return rootView;

    }

    public class AppListAdapter extends ArrayAdapter<Loggers> implements Filterable {
        private int layoutResource;
        List<Loggers> filteredData;
        List<Loggers> originalData;
        private String filterString;

        public AppListAdapter(Context context, int textViewResourceId, List<Loggers> objects) {
            super(context, textViewResourceId, objects);
            this.layoutResource = textViewResourceId;
            this.filteredData = objects;
            this.originalData = objects;
        }

        @Override
        public int getCount() {
            return filteredData.size();
        }

        @Override
        public Loggers getItem(int position) {
            return filteredData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
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

//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Loggers item = (Loggers) listView.getItemAtPosition(position);
//
//                        ArrayListLoggers lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_0);
//
//                        //set the next fragment and params
//
//                        appSession.setData("list", lst);
//                        appSession.setData("filterString", _adpatrer.getFilterString());
////                        ((FermatScreenSwapper) getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT.getKey(), R.id.logContainer, null);
//                        changeActivity(Activities.CWP_SUB_APP_DEVELOPER_LOG_LEVEL_1_TOOLS, appSession.getAppPublicKey());
//
//
//                    }
//                });

//                holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        CustomDialogClass cdd = new CustomDialogClass(getActivity(), item, item.pluginVersionReference);
//                        cdd.show();
//                        return true;
//                    }
//                });

                TextView textView = (TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                holder.companyTextView = textView;
                String textToShow = item.classHierarchyLevels.getLevel0();

                holder.companyTextView.setText(textToShow);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


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

        public void setData(List<Loggers> data) {
            this.filteredData = data;
        }

        public Filter getFilter() {
            return new DeveloperLogFilter(lstLoggers, _adpatrer);
        }

        public void setFilterString(String filterString) {
            this.filterString = filterString;
        }

        public String getFilterString() {
            return filterString;
        }
    }

    /**
     * ViewHolder.
     */
    private class ViewHolder {
        public ImageView imageView;
        public TextView companyTextView;
    }

    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        private Loggers logger;
        private PluginVersionReference pluginKey;
        public Activity c;
        public Dialog d;

        ListView list;
        String[] web = {
                "Not logging",
                "Minimal logging",
                "Moderate logging",
                "Aggressive logging"
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
            loadDisplayName();
            this.c = a;
            setLogLevelImage();

            logger.logLevel = LogLevel.NOT_LOGGING;
        }

        private void loadDisplayName() {
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

                setLogLevelImage();
                if (imageId[position] != 0) {
                    imageView.setImageResource(R.drawable.ic_action_accept_grey);
                }
                return rowView;
            }
        }
    }

    @Override
    public void onOptionMenuPrepared(Menu menu) {
        if (menuItemSize == 0 || menuItemSize == menu.size()) {
            menuItemSize = menu.size();

            searchView = (SearchView) menu.findItem(1).getActionView();
            searchView.setQueryHint(getResources().getString(R.string.developer_search_hint));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals(searchView.getQuery().toString())) {
                        _adpatrer.getFilter().filter(s);
                    }
                    return false;
                }
            });
            if (appSession.getData("filterString") != null) {
                String filterString = (String) appSession.getData("filterString");
                if (filterString.length() > 0) {
                    searchView.setQuery(filterString, true);
                    searchView.setIconified(false);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}