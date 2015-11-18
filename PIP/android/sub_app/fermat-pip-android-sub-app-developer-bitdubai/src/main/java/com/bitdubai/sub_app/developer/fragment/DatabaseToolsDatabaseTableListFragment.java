package com.bitdubai.sub_app.developer.fragment;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.developer.FragmentFactory.DeveloperFragmentsEnumType;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.Databases;
import com.bitdubai.sub_app.developer.common.DatabasesTable;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.common.StringUtils;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsDatabaseListFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Mati
 *
 * @version 1.0
 */
public class DatabaseToolsDatabaseTableListFragment extends FermatFragment {

    private static final String ARG_POS1ITION = "position";
    private static final String CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS = Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_RECORDS.getKey();

    View rootView;
    private ErrorManager errorManager;

    private DatabaseTool databaseTools;

    private DeveloperDatabase developerDatabase;

    private List<DatabasesTable> lstTables;

    List<DeveloperDatabaseTable> developerDatabaseTableList;

    public void setResource(Resource databases) {
        this.databases = databases;
    }

    private Resource databases;

    private GridView gridView;


    /**
     * SubApp session
     */
    DeveloperSubAppSession developerSubAppSession;

    public static DatabaseToolsDatabaseTableListFragment newInstance() {
        return new DatabaseToolsDatabaseTableListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(super.subAppsSession!=null){
            developerSubAppSession = (DeveloperSubAppSession) super.subAppsSession;

            databases = (Resource)developerSubAppSession.getData("resource");
        }


        errorManager = developerSubAppSession.getErrorManager();
        setRetainInstance(true);
        try {
            ToolManager toolManager = developerSubAppSession.getToolManager();
            databaseTools = toolManager.getDatabaseTool();
        } catch (CantGetDataBaseToolException e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        /*savedInstanceState.putBoolean("MyBoolean", true);
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
          */
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);

        lstTables=new ArrayList<DatabasesTable>();

        gridView =(GridView) rootView.findViewById(R.id.gridView);

        try {
            if (databases.type==Databases.TYPE_ADDON) {
                AddonVersionReference addon = AddonVersionReference.getByKey(databases.code);
                this.developerDatabaseTableList = databaseTools.getAddonTableListFromDatabase(addon, developerDatabase);
            } else if (databases.type==Databases.TYPE_PLUGIN) {
                PluginVersionReference plugin = PluginVersionReference.getByKey(databases.code);
                this.developerDatabaseTableList = databaseTools.getPluginTableListFromDatabase(plugin, developerDatabase);
            }

            for(int i = 0; i < developerDatabaseTableList.size() ; i++) {
                //availableResources[i] = developerDatabaseList.get(i).getName();
                DatabasesTable item = new DatabasesTable();

                item.picture = "databases";
                item.databases =  developerDatabaseTableList.get(i).getName();
                //item.developer = plugins.get(i).getDeveloper().toString();
                item.type=Resource.TYPE_PLUGIN;
                lstTables.add(item);

            }

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }
            //@SuppressWarnings("unchecked")
            //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.developer_app_grid_item, lstTables);
            _adpatrer.notifyDataSetChanged();
            gridView.setAdapter(_adpatrer);

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
        return rootView;
    }



    public void setDeveloperDatabase(DeveloperDatabase developerDatabase) {
        this.developerDatabase = developerDatabase;
    }

    public void setDeveloperSubAppSession(DeveloperSubAppSession developerSubAppSession) {
        this.developerSubAppSession = developerSubAppSession;
    }


    public class AppListAdapter extends ArrayAdapter<DatabasesTable> {


        public AppListAdapter(Context context, int textViewResourceId, List<DatabasesTable> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final DatabasesTable item = getItem(position);




            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.developer_app_grid_item, parent, false);


                holder = new ViewHolder();




                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        DatabaseToolsDatabaseTableRecordListFragment dabaDatabaseToolsDatabaseTableListFragment = new DatabaseToolsDatabaseTableRecordListFragment();

                        dabaDatabaseToolsDatabaseTableListFragment.setResource(databases);
                        dabaDatabaseToolsDatabaseTableListFragment.setDeveloperDatabaseTable(developerDatabaseTableList.get(position));
                        dabaDatabaseToolsDatabaseTableListFragment.setDeveloperDatabase(developerDatabase);

                        //set the next fragment and params

                        developerSubAppSession.setData("resource",databases);
                        developerSubAppSession.setData("developerDataBase",developerDatabase);
                        developerSubAppSession.setData("databaseTable",developerDatabaseTableList.get(position));

                        ((FermatScreenSwapper)getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT.getKey(),R.id.startContainer,null);
                    }
                });
                //holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            TextView textView =(TextView) convertView.findViewById(R.id.company_text_view);
            String formatedString = StringUtils.replaceStringByUnderScore(item.databases);
            formatedString=StringUtils.splitCamelCase(formatedString);
            textView.setText(formatedString);

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
            textView.setTypeface(tf);
            holder.companyTextView = textView;
            // holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());


            switch (item.picture) {
                case "plugin":
                    holder.imageView.setImageResource(R.drawable.table);
                    holder.imageView.setTag("DeveloperRecordsFragment");
                    break;
                case "addon":
                    holder.imageView.setImageResource(R.drawable.table);
                    holder.imageView.setTag("DeveloperRecordsFragment" );
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.table);
                    holder.imageView.setTag("DeveloperRecordsFragment");
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