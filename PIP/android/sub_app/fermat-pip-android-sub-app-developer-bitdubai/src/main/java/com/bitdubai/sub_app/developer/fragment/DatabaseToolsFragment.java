package com.bitdubai.sub_app.developer.fragment;

import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.developer.FragmentFactory.DeveloperFragmentsEnumType;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 */
public class DatabaseToolsFragment extends FermatFragment {


    private static final String CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES = Fragments.CWP_SUB_APP_DEVELOPER_DATABASE_TOOLS_DATABASES.getKey();
    private ErrorManager errorManager;

    /**
     * SubApp session
     */

    DeveloperSubAppSession developerSubAppSession;

    private static final String ARG_POSITION = "position";
    private static final int TAG_FRAGMENT_DATABASE = 1;
    View rootView;

    private DatabaseTool databaseTools;


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
        setRetainInstance(true);
        developerSubAppSession = (DeveloperSubAppSession) super.subAppsSession;

        if (developerSubAppSession != null)
            errorManager = developerSubAppSession.getErrorManager();
        try {

            ToolManager toolManager = developerSubAppSession.getToolManager();
            databaseTools = toolManager.getDatabaseTool();
        } catch (CantGetDataBaseToolException e) {
            if (errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.start, container, false);
        rootView.setTag(1);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        try {

            List<Plugins> plugins = databaseTools.getAvailablePluginList();
            List<Addons> addons = databaseTools.getAvailableAddonList();


            mlist = new ArrayList<Resource>();

            for (int i = 0; i < plugins.size(); i++) {
                Resource item = new Resource();
                item.picture = "plugin";
                item.label = plugins.get(i).toString().toLowerCase().replace("_", " ");
                item.resource = plugins.get(i).getKey();
                item.type = Resource.TYPE_PLUGIN;
                mlist.add(item);
            }
            for (int i = 0; i < addons.size(); i++) {
                Resource item = new Resource();
                item.picture = "addon";
                item.label = addons.get(i).toString().replace("_", " ");
                item.resource = addons.get(i).getCode();
                item.type = Resource.TYPE_ADDON;
                mlist.add(item);
            }

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }
            AppListAdapter adapter = new AppListAdapter(getActivity(), R.layout.developer_app_grid_item, mlist);
            gridView.setAdapter(adapter);
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }


    public void setDeveloperSubAppSession(DeveloperSubAppSession developerSubAppSession) {
        this.developerSubAppSession = developerSubAppSession;
    }


    public class AppListAdapter extends BaseAdapter {

        private List<Resource> dataSet;
        private Context context;
        private int textViewResourceId;

        public AppListAdapter(Context context, int textViewResourceId, List<Resource> objects) {
            this.context = context;
            this.dataSet = objects;
            this.textViewResourceId = textViewResourceId;
        }

        @Override
        public int getCount() {
            return dataSet != null ? dataSet.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return dataSet.get(position);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            final Resource item = dataSet.get(position);
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.developer_app_grid_item, viewGroup, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) view.findViewById(R.id.image_view);
                TextView textView = (TextView) view.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                holder.companyTextView = textView;
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.companyTextView.setText(item.label);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //set the next fragment and params
                    Object[] params = new Object[1];
                    params[0] = item;
                    ((FermatScreenSwapper) getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey(), params);

                }
            });
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

            return view;
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