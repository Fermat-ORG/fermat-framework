package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;

    private int position;

    public static CommunityFragment newInstance(int position) {
        CommunityFragment f = new CommunityFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] names = {
                "Tom",
                "Debra",
                "Connor",
                "Barbara",
                "Angela",
                "Aaron",
                "Sophie",
                "Megan",
                "Catalina",
                "Robin",
                "Mia",
                "Blue",
                "Katrina",
                "Adam",
                "Paul"




        };
        String[] state = {
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY",
                "NY"
        };
        String[] country = {
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA",
                "USA"
        };

        if (mlist == null)
        {

            mlist = new ArrayList<App>();

            for (int i = 0; i < 13; i++) {
                App item = new App();
                item.Names = names[i];
                item.States = state[i];
                item.Countries = country[i];
                mlist.add(item);

            }
        }


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(5);
        } else {
            gridView.setNumColumns(3);
        }

        //@SuppressWarnings("unchecked")
        //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallets_kids_fragment_community, mlist));

        //        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //  public void onItemClick(AdapterView<?> parent, View v,
        //                         int position, long id) {
        //      Intent intent;
        //      intent = new Intent(getActivity(), RefillPointActivity.class);
        //      startActivity(intent);
        //      return ;
        //  }
        // });


        return gridView;
    }


    public class App implements Serializable {


        private static final long serialVersionUID = -8730067026050196758L;

        public String Names;

        public String Countries;

        public String States;
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
                convertView = inflater.inflate(R.layout.wallets_kids_fragment_community, parent, false);
                holder = new ViewHolder();

                holder.name = (TextView) convertView.findViewById(R.id.community_name);
                holder.country = (TextView) convertView.findViewById(R.id.community_country);
                holder.state= (TextView) convertView.findViewById(R.id.community_state);
                holder.Photo = (ImageView) convertView.findViewById(R.id.profile_Image);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(item.Names);
            holder.country.setText(item.Countries);
            holder.state.setText(item.States);

            holder.name.setTypeface(MyApplication.getDefaultTypeface());
            holder.country.setTypeface(MyApplication.getDefaultTypeface());
            holder.state.setTypeface(MyApplication.getDefaultTypeface());

            switch (position)
            {
                case 0:
                    holder.Photo.setImageResource(R.drawable.kid_8) ;
                    break;
                case 1:
                    holder.Photo.setImageResource(R.drawable.kid_9);
                    break;
                case 2:
                    holder.Photo.setImageResource(R.drawable.kid_12);
                    break;
                case 3:
                    holder.Photo.setImageResource(R.drawable.kid_11);
                    break;
                case 4:
                    holder.Photo.setImageResource(R.drawable.kid_10);
                    break;
                case 5:
                    holder.Photo.setImageResource(R.drawable.kid_13);
                    break;
                case 6:
                    holder.Photo.setImageResource(R.drawable.kid_14);
                    break;
                case 7:
                    holder.Photo.setImageResource(R.drawable.kid_16);
                    break;
                case 8:
                    holder.Photo.setImageResource(R.drawable.kid_17);
                    break;
                case 9:
                    holder.Photo.setImageResource(R.drawable.kid_18);
                    break;
                case 10:
                    holder.Photo.setImageResource(R.drawable.kid_19);
                    break;
                case 11:
                    holder.Photo.setImageResource(R.drawable.kid_20);
                    break;
                case 12:
                    holder.Photo.setImageResource(R.drawable.kid_21);
                    break;
            }
            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {
            public TextView name;
            public TextView country;
            public TextView state;
            public ImageView Photo;
        }

    }

}

