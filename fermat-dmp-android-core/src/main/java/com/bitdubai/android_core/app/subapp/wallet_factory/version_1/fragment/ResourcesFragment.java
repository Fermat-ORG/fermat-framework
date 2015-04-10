package com.bitdubai.android_core.app.subapp.wallet_factory.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.smartwallet.R.drawable;


public class ResourcesFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;

    private int position;

    public static ResourcesFragment newInstance(int position) {
        ResourcesFragment f = new ResourcesFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] size = {"144x144 px","110x100 px","150x150 px","150x150 px","250x200 px","200x60 px","250x166 px","200x200 px","500x177 px","200x200 px","25x24 px","29x47 px","85x35 px"};
        String[] weight = {"41 kb","21 kb","23 kb","21 kb","39 kb","6 kb","42 kb","5 kb","500x177","163 kb","1 kb","3 kb","4 kb"};


        if (mlist == null)
        {

            mlist = new ArrayList<App>();

            for (int i = 0; i < 13; i++) {
                App item = new App();
                item.Size = size[i];
                item.Weight = weight[i];
                mlist.add(item);

            }
        }


        GridView gridView = new GridView(getActivity());


        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(3);
        } else {
            gridView.setNumColumns(2);
        }
        //@SuppressWarnings("unchecked")
        //ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallet_factory_resources_fragment, mlist));
        gridView.setBackgroundResource(R.drawable.background_tabs_diagonal_rotated);
        gridView.setGravity(Gravity.CENTER_HORIZONTAL);
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
        public String Size;
        public String Weight;

    }



    public class AppListAdapter extends ArrayAdapter<App> {


        public AppListAdapter(Context context, int textViewResourceId, List<App> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            App item = getItem(position);



            ViewHolder holder;
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.wallet_factory_resources_fragment, parent, false);
                holder = new ViewHolder();
            holder.sizes = (TextView) convertView.findViewById(R.id.dimensions);
            holder.sizes.setText(item.Size);
            holder.sizes.setTypeface(MyApplication.getDefaultTypeface());

            holder.weights = (TextView) convertView.findViewById(R.id.weight);
            holder.weights.setText(item.Weight);
            holder.weights.setTypeface(MyApplication.getDefaultTypeface());

            holder.Photo = (ImageView) convertView.findViewById(R.id.photo);

            switch (position)
            {
                case 0:
                    holder.Photo.setImageResource(drawable.wallet_1) ;
                    break;
                case 1:
                    holder.Photo.setImageResource(drawable.account_type_current_small);
                    break;
                case 2:
                    holder.Photo.setImageResource(drawable.account_type_savings_1_small);
                    break;
                case 3:
                    holder.Photo.setImageResource(drawable.account_type_savings_2_small);
                    break;
                case 4:
                    holder.Photo.setImageResource(drawable.discounts_month);
                    break;
                case 5:
                    holder.Photo.setImageResource(drawable.discounts_week);
                    break;
                case 6:
                    holder.Photo.setImageResource(drawable.discounts_year);
                    break;
                case 7:
                    holder.Photo.setImageResource(drawable.object_frame_1x1);
                    break;
                case 8:
                    holder.Photo.setImageResource(drawable.object_frame_3x1_frozen);
                    break;
                case 9:
                    holder.Photo.setImageResource(drawable.object_frame_1x1_white_background);
                    break;
                case 10:
                    holder.Photo.setImageResource(drawable.grid_background_clock);
                    break;
                case 11:
                    holder.Photo.setImageResource(drawable.grid_background_favorite);
                    break;
                case 12:
                    holder.Photo.setImageResource(drawable.grid_background_sale);
                    break;
            }
            return convertView;
        }

        private class ViewHolder {
            public ImageView Photo;
            public TextView sizes;
            public TextView weights;
        }

    }

}

