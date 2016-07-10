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

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.sub_app.developer.R;
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
 * Created by Matias Furszyfer
 *
 * @version 1.0
 */
public class DatabaseToolsDatabaseListFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ToolManager>, ResourceProviderManager> {

    private static final String ARG_POSITION = "position";
//    private static final String CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES = Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_TABLES.getKey();
    View rootView;
    private Resource resource;
    List<DeveloperDatabase> developerDatabaseList;
    private GridView gridView;
    private List<Databases> lstDatabases;
    private int database_type;

    public static DatabaseToolsDatabaseListFragment newInstance() {
        return new DatabaseToolsDatabaseListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        resource = (Resource) appSession.getData("resource");

        try {
            ToolManager toolManager = appSession.getModuleManager();

        } catch (Exception ex) {
            appSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_database_tools, container, false);

        lstDatabases = new ArrayList<Databases>();

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        try {
            if (Resource.TYPE_ADDON == resource.type) {
                AddonVersionReference addon = AddonVersionReference.getByKey(resource.code);
                this.developerDatabaseList = appSession.getModuleManager().getDatabaseListFromAddon(addon);
                database_type = Databases.TYPE_PLUGIN;
            } else if (Resource.TYPE_PLUGIN == resource.type) {

                this.developerDatabaseList = appSession.getModuleManager().getDatabaseListFromPlugin(resource.pluginVersionReference);
                database_type = Databases.TYPE_ADDON;
            }

            for (DeveloperDatabase database : developerDatabaseList) {
                Databases item = new Databases();
                item.picture = "databases";
                item.databases = database.getName();
                item.type = Resource.TYPE_PLUGIN;
                lstDatabases.add(item);
            }

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }
            AppListAdapter adapter = new AppListAdapter(getActivity(), R.layout.developer_app_grid_item, lstDatabases);
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);

        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
        return rootView;
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
                        //set the next fragment and params
                        appSession.setData("resource", resource);
                        appSession.setData("database", developerDatabaseList.get(position));
//                        ((FermatScreenSwapper)getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT.getKey(),R.id.startContainer,null);
                        changeActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST, appSession.getAppPublicKey());
                    }
                });
                TextView textView = (TextView) convertView.findViewById(R.id.company_text_view);
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
