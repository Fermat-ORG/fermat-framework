package com.bitdubai.sub_app.developer.fragment;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_pip_api.layer.module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.filters.DeveloperPluginFilter;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 */
public class DatabaseToolsFragment extends AbstractFermatFragment {


    private ErrorManager errorManager;

    /**
     * SubApp session
     */

    public DeveloperSubAppSession developerSubAppSession;

    View rootView;

    private DatabaseTool databaseTools;


    private ArrayList<Resource> mlist;

    private ListView listView;

    private SearchView searchView;

    private AppListAdapter adapter;

    public static DatabaseToolsFragment newInstance() {
        return new DatabaseToolsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (super.appSession != null) {
            developerSubAppSession = (DeveloperSubAppSession) super.appSession;
        }

        errorManager = developerSubAppSession.getErrorManager();
        try {

            ToolManager toolManager = developerSubAppSession.getModuleManager();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.start_init, container, false);
        rootView.setTag(1);

        listView =(ListView) rootView.findViewById(R.id.gridView);

        configureToolbar();

        try {

            List<PluginVersionReference> plugins = databaseTools.listAvailablePlugins();
            List<AddonVersionReference> addons = databaseTools.listAvailableAddons();

            mlist = new ArrayList<>();

            for (int i = 0; i < plugins.size(); i++) {

                PluginVersionReference pvr = plugins.get(i);

                String label = pvr.getPluginDeveloperReference().getPluginReference().getLayerReference().getPlatformReference().getPlatform().getCode()+" "+
                        pvr.getPluginDeveloperReference().getPluginReference().getLayerReference().getLayer().name()+" "+
                        ((Plugins)pvr.getPluginDeveloperReference().getPluginReference().getPlugin()).name();

                mlist.add(
                        new Resource(
                                "plugin",
                                label.replaceAll("_", " "),
                                pvr,
                                pvr.getPluginDeveloperReference().getDeveloper().name(),
                                Resource.TYPE_PLUGIN
                        )
                );

            }
            for (int i = 0; i < addons.size(); i++) {
                Resource item = new Resource();
                item.picture = "addon";
                item.label = addons.get(i).toString3();
                item.code = addons.get(i).toKey();
                item.type = Resource.TYPE_ADDON;
                mlist.add(item);
            }

            Collections.sort(mlist, new Comparator<Resource>() {
                public int compare(Resource o1, Resource o2) {
                    return (o1.label).compareTo(o2.label);
                }
            });

            adapter = new AppListAdapter(getActivity(), R.layout.developer_app_grid_item_init, mlist);

            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Resource item = (Resource) listView.getItemAtPosition(position);
                    developerSubAppSession.setData("resource", item);
                    developerSubAppSession.setData("filterString", adapter.getFilterString());
//                    ((FermatScreenSwapper) getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey(), R.id.startContainer, null);
                    changeActivity(Activities.CWP_WALLET_DEVELOPER_TOOL_DATABASE, appSession.getAppPublicKey());

                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.developer_sub_app_principal));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.developer_sub_app_principal));
            }
        }
    }

    public class AppListAdapter extends ArrayAdapter<Resource> implements Filterable {
        private int layoutResource;

        List<Resource> filteredData;
        List<Resource> originalData;

        private String filterString;

        public AppListAdapter(Context context, int layoutResource, List<Resource> objects) {
            super(context, layoutResource);
            this.layoutResource = layoutResource;
            this.filteredData = objects;
            this.originalData = objects;
        }

        @Override
        public int getCount() {
            return filteredData.size();
        }

        @Override
        public Resource getItem(int position) {
            return filteredData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Resource item = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.developer_app_grid_item_init, parent, false);


                holder = new ViewHolder();

                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                TextView textView =(TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                holder.companyTextView = textView;

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(item.label);

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

        public void setData(List<Resource> data) {
            this.filteredData = data;
        }

        public Filter getFilter() {
            return new DeveloperPluginFilter(mlist, adapter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.developer_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.developer_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.developer_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals(searchView.getQuery().toString())) {
                    adapter.getFilter().filter(s);
                }
                return false;
            }
        });
        if (developerSubAppSession.getData("filterString") != null) {
            String filterString = (String) developerSubAppSession.getData("filterString");
            if (filterString.length() > 0) {
                searchView.setQuery(filterString, true);
                searchView.setIconified(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
