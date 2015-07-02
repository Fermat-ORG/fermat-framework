package com.bitdubai.sub_app.manager.fragment;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
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

import com.bitdubai.sub_app.manager.R;
//import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;

/**
 * Created by Natalia on 12/01/2015.
 */
public class SubAppDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private static int tabId;

    private int position;

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

        String[] installed =
                {  "true"
                };
        String[] sub_app_names =
                { "Developer App"
                };


        String[] sub_app_picture =
                {"ic_launcher"


                };

        mlist = new ArrayList<App>();


        for (int i = 0; i < installed.length; i++) {
            if (installed[i] == "true") {
                App item = new App();

                item.picture = sub_app_picture[i];
                item.company = sub_app_names[i];
                item.rate = (float) Math.random() * 5;
                item.value = (int) Math.floor((Math.random() * (500 - 80 + 1))) + 80;
                item.favorite = (float) Math.random() * 5;
                item.timetoarraive = (float) Math.random() * 5;
                item.sale = (float) Math.random() * 5;
                item.installed = true;
                mlist.add(item);
            }
        }

        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        //@SuppressWarnings("unchecked")
        //   ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_sub_app_desktop_fragment_grid_item ,mlist);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);



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



    public class AppListAdapter extends ArrayAdapter<App> {


        public AppListAdapter(Context context, int textViewResourceId, List<App> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            App item = getItem(position);

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

            holder.companyTextView.setText(item.company);
            LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.wallet_3);
            switch (item.picture)
            {
                case "ic_launcher":
                    holder.imageView.setImageResource(R.drawable.developer_sub_app);
                  holder.imageView.setTag("DevelopersActivity|1");
                    linearLayout.setTag("DevelopersActivity|1");
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

