package com.bitdubai.desktop.sub_app_manager;

import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.desktop.sub_app_manager.util.CbpSubAppListGenerator;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Natalia on 12/01/2015.
 */
public class SubAppDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String CWP_SUB_APP_ALL_DEVELOPER = Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode();
    private static final String CWP_WALLET_BASIC_ALL_MAIN = Activities.CWP_WALLET_BASIC_ALL_MAIN.getCode();
    private static final String CWP_WALLET_FACTORY_MAIN = Activities.CWP_WALLET_FACTORY_MAIN.getCode();
    private static final String CWP_WALLET_PUBLISHER_MAIN = Activities.CWP_WALLET_PUBLISHER_MAIN.getCode();
    private static final String CWP_WALLET_RUNTIME_STORE_MAIN = Activities.CWP_WALLET_RUNTIME_STORE_MAIN.getCode();

    private static int tabId;

    private int position;
    Typeface tf;

    public static SubAppDesktopFragment newInstance(int position) {
        SubAppDesktopFragment f = new SubAppDesktopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        //    f.setArguments(b);
        return f;
    }

    // @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  setHasOptionsMenu(true);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        String[] installed = {
                "true",
                "true",
                "true",
                "true",
                "true"
        };
        String[] sub_app_names = {
                "Developer",
                "Wallet Factory",
                "Wallet Publisher",
                "Wallet Store",
                "Intra user"
        };
        String[] sub_app_picture = {
                "developer_sub_app",
                "wallet_factory",
                "wallet_publisher",
                "wallet_store",
                "intra_user"
        };

        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        List<InstalledSubApp> subAppsList = CbpSubAppListGenerator.instance.createSubAppsList();

        AppListAdapter adapter = new AppListAdapter(
                getActivity(),
                R.layout.shell_sub_app_desktop_fragment_grid_item,
                subAppsList);

        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);

        return gridView;
    }


    //  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //   inflater.inflate(R.menu.shell_shop_desktop_fragment_menu, menu);
        //  super.onCreateOptionsMenu(menu,inflater);
    }


    public class App implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;

        public String title;

        public String description;

        public String picture;

        public String company;


        public String Address;


        public float rate;

        public int value;

        public float favorite;

        public float sale;

        public float timetoarraive;

        public boolean installed;

    }


    public class AppListAdapter extends ArrayAdapter<InstalledSubApp> {


        public AppListAdapter(Context context, int textViewResourceId, List<InstalledSubApp> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final InstalledSubApp cbpInstalledSubApp = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_sub_app_desktop_fragment_grid_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(cbpInstalledSubApp.getSubAppName());
            holder.companyTextView.setTypeface(tf, Typeface.BOLD);

            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.sub_apps);
            switch (cbpInstalledSubApp.getSubAppIcon()) {

                case "sub_app_crypto_broker_identity":
                    holder.imageView.setImageResource(R.drawable.crypto_broker_identity);
                    holder.imageView.setTag("FactoryActivity|1");
                    linearLayout.setTag("FactoryActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(cbpInstalledSubApp);
                        }
                    });
                    break;

                case "sub_app_crypto_broker_community":
                    holder.imageView.setImageResource(R.drawable.crypto_broker_community1);
                    holder.imageView.setTag("PublisherActivity|1");
                    linearLayout.setTag("PublisherActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(cbpInstalledSubApp);
                        }
                    });
                    break;

                case "sub_app_crypto_customer_identity":
                    holder.imageView.setImageResource(R.drawable.store_nuevo);
                    holder.imageView.setTag("StoreFrontActivity|1");
                    linearLayout.setTag("StoreFrontActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(cbpInstalledSubApp);
                        }
                    });
                    break;

                case "sub_app_crypto_customer_community":
                    holder.imageView.setImageResource(R.drawable.intra_user);
                    holder.imageView.setTag("StoreFrontActivity|1");
                    linearLayout.setTag("StoreFrontActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(cbpInstalledSubApp);
                        }
                    });
                    break;

                case "sub_app_customers":
                    holder.imageView.setImageResource(R.drawable.customer_icon);
                    holder.imageView.setTag("DevelopersActivity|1");
                    linearLayout.setTag("DevelopersActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(cbpInstalledSubApp);
                        }
                    });
                    break;
            }

            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {

            public ImageView imageView;
            public TextView companyTextView;

        }

    }

}

