package com.bitdubai.sub_app.developer.fragment;

import android.app.AlertDialog;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.ScreenSwapper;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.common.Databases;
import com.bitdubai.sub_app.developer.common.DatabasesTable;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.common.StringUtils;

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
public class DatabaseToolsDatabaseTableListFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String TAG_DATABASE_TABLES_TOOLS_FRAGMENT = "databases tables list tools";
    View rootView;


    private DatabaseTool databaseTools;

    private DeveloperDatabase developerDatabase;

    private List<DatabasesTable> lstTables;

    List<DeveloperDatabaseTable> developerDatabaseTableList;

    public void setResource(Resource databases) {
        this.databases = databases;
    }

    private Resource databases;

    private String[] params;
    private GridView gridView;

    private static Platform platform = new Platform();

    public static DatabaseToolsDatabaseTableListFragment newInstance(int position) {
        DatabaseToolsDatabaseTableListFragment f = new DatabaseToolsDatabaseTableListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
                Addons addon = Addons.getByKey(databases.resource);
                this.developerDatabaseTableList = databaseTools.getAddonTableListFromDatabase(addon, developerDatabase);
            } else if (databases.type==Databases.TYPE_PLUGIN) {
                Plugins plugin = Plugins.getByKey(databases.resource);
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
            showMessage("DatabaseTools Database Table List Fragment onCreateView Exception - " + e.getMessage());
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

    public void setDeveloperDatabase(DeveloperDatabase developerDatabase) {
        this.developerDatabase = developerDatabase;
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
                        Object[] params = new Object[3];

                        params[0] = databases;
                        params[1] = developerDatabase;
                        params[2] = developerDatabaseTableList.get(position);


                        ((ScreenSwapper)getActivity()).setScreen("DeveloperRecordsFragment");
                        ((ScreenSwapper)getActivity()).setParams(params);
                        ((ScreenSwapper)getActivity()).changeScreen();
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