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
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.common.Databases;
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
public class DatabaseToolsDatabaseListFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String TAG_DATABASE_TABLES_FRAGMENT = "database table list fragment";
    View rootView;

    private DatabaseTool databaseTools;

    private Resource resource;

    List<DeveloperDatabase> developerDatabaseList;

    private GridView gridView;

    private List<Databases> lstDatabases;

    private int database_type;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);

        lstDatabases=new ArrayList<Databases>();

        gridView =(GridView) rootView.findViewById(R.id.gridView);
        try {
            if (Resource.TYPE_ADDON == resource.type) {
                Addons addon = Addons.getByKey(resource.resource);
                this.developerDatabaseList = databaseTools.getDatabaseListFromAddon(addon);
                database_type=Databases.TYPE_PLUGIN;
            } else if (Resource.TYPE_PLUGIN==resource.type) {
                Plugins plugin = Plugins.getByKey(resource.resource);
                this.developerDatabaseList = databaseTools.getDatabaseListFromPlugin(plugin);
                database_type=Databases.TYPE_ADDON;
            }

            // Get ListView object from xml
            //final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            //TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
            //labelDatabase.setText(resource+" - Databases List");


            for(int i = 0; i < developerDatabaseList.size() ; i++) {
                //availableResources[i] = developerDatabaseList.get(i).getName();
                Databases item = new Databases();

                item.picture = "databases";
                item.databases =  developerDatabaseList.get(i).getName();
                //item.developer = plugins.get(i).getDeveloper().toString();
                item.type=Resource.TYPE_PLUGIN;
                lstDatabases.add(item);
                //}
            }

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }
            //@SuppressWarnings("unchecked")
            //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.developer_app_grid_item, lstDatabases);
            _adpatrer.notifyDataSetChanged();
            gridView.setAdapter(_adpatrer);

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


    public class AppListAdapter extends ArrayAdapter<Databases> {


        public AppListAdapter(Context context, int textViewResourceId, List<Databases> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {



            final Databases item = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.developer_app_grid_item, parent, false);


                holder = new ViewHolder();




                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

               holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Toast.makeText(getActivity(),item.databases,Toast.LENGTH_SHORT).show();

                        //set the next fragment and params
                        Object[] params = new Object[2];

                        params[0] = resource;
                        params[1] = developerDatabaseList.get(position);
                        ((ScreenSwapper)getActivity()).setScreen("DeveloperTablesFragment");
                        ((ScreenSwapper)getActivity()).setParams(params);
                        ((ScreenSwapper)getActivity()).changeScreen();



                    }
                });
                TextView textView =(TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                holder.companyTextView = textView;

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(StringUtils.splitCamelCase(item.databases));
            // holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());


            switch (item.picture) {
                case "plugin":
                    holder.imageView.setImageResource(R.drawable.db);
                    holder.imageView.setTag("CPWWRWAKAV1M|1");
                    break;
                case "addon":
                    holder.imageView.setImageResource(R.drawable.db);
                    holder.imageView.setTag("CPWWRWAKAV1M|2");
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.db);
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