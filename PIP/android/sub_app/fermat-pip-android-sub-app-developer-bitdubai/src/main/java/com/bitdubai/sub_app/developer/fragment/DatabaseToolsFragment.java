package com.bitdubai.sub_app.developer.fragment;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.common.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/06/15.
 *
 * @version 1.0
 */
public class DatabaseToolsFragment extends Fragment{


    public static final String TAG_DATABASE_TOOLS_FRAGMENT= "DatabaseToolsFragment";

    private static final String ARG_POSITION = "position";
    View rootView;

    private DatabaseTool databaseTools;

    private static Platform platform = new Platform();

    private ArrayList<Resource> mlist;

    private GridView gridView;

    public static DatabaseToolsFragment newInstance(int position) {
        DatabaseToolsFragment f = new DatabaseToolsFragment();
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
                databaseTools = toolManager.getDatabaseTool();
            } catch (Exception e) {
                showMessage("CantGetToolManager - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            showMessage("Unexpected error get tool manager - " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.start, container, false);
        gridView =(GridView) rootView.findViewById(R.id.gridView);
        try {
            // Get ListView object from xml
            //final ListView listView = (ListView) rootView.findViewById(R.id.lista1);

            List<Plugins> plugins = databaseTools.getAvailablePluginList();
            List<Addons> addons = databaseTools.getAvailableAddonList();



            mlist=new ArrayList<Resource>();

            for (int i = 0; i < plugins.size(); i++) {
                Resource item = new Resource();

                    item.picture = "plugin";
                    item.resource = plugins.get(i).getKey();
                    //item.developer = plugins.get(i).getDeveloper().toString();
                    item.type=Resource.TYPE_PLUGIN;
                    mlist.add(item);
                //}
            }
            for (int i = 0; i < addons.size(); i++) {
                Resource item = new Resource();

                item.picture = "addon";
                item.resource = addons.get(i).getKey();
                //item.developer = plugins.get(i).getDeveloper().toString();
                item.type=Resource.TYPE_ADDON;
                mlist.add(item);
                //}
            }


            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(4);
            }
            //@SuppressWarnings("unchecked")
            //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_wallet_desktop_front_grid_item, mlist);
            _adpatrer.notifyDataSetChanged();
            gridView.setAdapter(_adpatrer);






            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView labelDatabase = (TextView) rootView.findViewById(R.id.labelDatabase);
                    labelDatabase.setVisibility(View.GONE);
                    App item = (App) listView.getItemAtPosition(position);

                    DatabaseToolsDatabaseListFragment databaseToolsDatabaseListFragment = new DatabaseToolsDatabaseListFragment();

                    databaseToolsDatabaseListFragment.setResource(item.company);

                    FragmentTransaction FT = getFragmentManager().beginTransaction();

                    FT.replace(R.id.hola, databaseToolsDatabaseListFragment);

                    FT.add(databaseToolsDatabaseListFragment, databaseToolsDatabaseListFragment);


                    FT.commit();
                }
            });
            */

            //listView.setAdapter(adapter);
        } catch (Exception e) {
            showMessage("DatabaseTools Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }

        //LinearLayout l=(LinearLayout)rootView.findViewById(R.id.hola);
        //l.addView(gridView);

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




    public class AppListAdapter extends ArrayAdapter<Resource> {


        public AppListAdapter(Context context, int textViewResourceId, List<Resource> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Resource item = getItem(position);


            /*gridView =(GridView) rootView.findViewById(R.id.gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(getActivity(), "natalia+"+position, Toast.LENGTH_SHORT).show();
                    //Resource item=(Resource) gridView.getItemAtPosition(position);
                    //Toast.makeText(getActivity(),item.resource,Toast.LENGTH_SHORT).show();
                    //DatabaseToolsDatabaseListFragment databaseToolsDatabaseListFragment = new DatabaseToolsDatabaseListFragment();

                    //databaseToolsDatabaseListFragment.setResource(item);

                    //FragmentTransaction FT = getFragmentManager().beginTransaction();


                    //FT.add(databaseToolsDatabaseListFragment, TAG_DATABASE_TOOLS_FRAGMENT);
                    //FT.replace(R.id.hola, databaseToolsDatabaseListFragment);

                    //FT.commit();


                }
            });
            */

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_wallet_desktop_front_grid_item, parent, false);


                holder = new ViewHolder();




                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Resource item=(Resource) gridView.getItemAtPosition(position);
                        Toast.makeText(getActivity(),item.resource,Toast.LENGTH_SHORT).show();
                        DatabaseToolsDatabaseListFragment databaseToolsDatabaseListFragment = new DatabaseToolsDatabaseListFragment();

                        databaseToolsDatabaseListFragment.setResource(item);

                        FragmentTransaction FT = getFragmentManager().beginTransaction();


                        FT.add(databaseToolsDatabaseListFragment, TAG_DATABASE_TOOLS_FRAGMENT);
                        FT.replace(R.id.hola, databaseToolsDatabaseListFragment);

                        FT.commit();
                    }
                });
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(item.resource);
            // holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());


            switch (item.picture) {
                case "plugin":
                    holder.imageView.setImageResource(R.drawable.plugin);
                    holder.imageView.setTag("CPWWRWAKAV1M|1");
                    break;
                case "addon":
                    holder.imageView.setImageResource(R.drawable.addon);
                    holder.imageView.setTag("CPWWRWAKAV1M|2");
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.addon);
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