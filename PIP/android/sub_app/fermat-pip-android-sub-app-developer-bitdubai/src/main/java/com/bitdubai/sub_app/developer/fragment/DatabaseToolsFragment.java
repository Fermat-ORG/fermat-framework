package com.bitdubai.sub_app.developer.fragment;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.developer.FragmentFactory.DeveloperFragmentsEnumType;
import com.bitdubai.sub_app.developer.R;
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


    private ErrorManager errorManager;

    /**
     * SubApp session
     */

    public  DeveloperSubAppSession developerSubAppSession;

    View rootView;

    private DatabaseTool databaseTools;


    private ArrayList<Resource> mlist;

    private GridView gridView;

    public static DatabaseToolsFragment newInstance() {
        return new DatabaseToolsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(super.subAppsSession!=null){
            developerSubAppSession = (DeveloperSubAppSession) super.subAppsSession;
        }

        errorManager = developerSubAppSession.getErrorManager();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.start, container, false);
        rootView.setTag(1);
        gridView=(GridView) rootView.findViewById(R.id.gridView);
        try {

            List<PluginVersionReference> plugins = databaseTools.getAvailablePluginList();
            List<AddonVersionReference> addons = databaseTools.getAvailableAddonList();

            mlist=new ArrayList<>();

            for (int i = 0; i < plugins.size(); i++) {

                PluginVersionReference pvr = plugins.get(i);
                String label = pvr.getPluginDeveloperReference().getPluginReference().getLayerReference().getPlatformReference().getPlatform().name()+" "+
                        pvr.getPluginDeveloperReference().getPluginReference().getLayerReference().getLayer().name()+" "+
                        pvr.getPluginDeveloperReference().getPluginReference().getPlugin().name();

                mlist.add(
                        new Resource(
                                "plugin",
                                label,
                                pvr.toKey(),
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
                item.type=Resource.TYPE_ADDON;
                mlist.add(item);
            }

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }
            AppListAdapter adapter = new AppListAdapter(getActivity(), R.layout.developer_app_grid_item, mlist);
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Resource item=(Resource) gridView.getItemAtPosition(position);
                    developerSubAppSession.setData("resource",item);
                    ((FermatScreenSwapper) getActivity()).changeScreen(DeveloperFragmentsEnumType.CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT.getKey(),R.id.startContainer,null);


                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        return rootView;
    }


    public class AppListAdapter extends ArrayAdapter<Resource> {


        public AppListAdapter(Context context, int textViewResourceId, List<Resource> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

           final Resource item = getItem(position);


            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.developer_app_grid_item, parent, false);


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



    }
    /**
     * ViewHolder.
     */
    private class ViewHolder {
        public ImageView imageView;
        public TextView companyTextView;
    }
}