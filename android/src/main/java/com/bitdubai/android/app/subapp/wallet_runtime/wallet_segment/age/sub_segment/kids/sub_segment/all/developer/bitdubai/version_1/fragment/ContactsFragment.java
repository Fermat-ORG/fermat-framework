package com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

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
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;


import com.bitdubai.fermat_api.layer._10_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.fermat_core.CorePlatformContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;

    private int position;
    private  static WalletResourcesManager walletResourceManger;

    public static ContactsFragment newInstance(int position) {
        Platform platform = MyApplication.getPlatform();
        CorePlatformContext platformContext = platform.getCorePlatformContext();
        walletResourceManger = (WalletResourcesManager)platformContext.getPlugin(Plugins.WALLET_RESOURCES_NETWORK_SERVICE);
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);

        ContactsFragment f = new ContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] names = {
                "Mark",
                "Anna",
                "Marie",
                "Roger",
                "Michael",
                "Juliet",
                "Devin"};


        if (mlist == null)
        {

            mlist = new ArrayList<App>();

            for (int i = 0; i < 7; i++) {
                App item = new App();
                item.Names = names[i];
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
        String layoutContent = "";
        try {
            walletResourceManger.setImageName("wallets_kids_fragment_contacts_filter.txt");
            layoutContent = walletResourceManger.getLayoutResource();
        } catch (CantGetResourcesException e) {
            System.err.println("CantGetResourcesException: " + e.getMessage());
        }

        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallets_kids_fragment_contacts_filter, mlist));

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
                convertView = inflater.inflate(R.layout.wallets_kids_fragment_contacts, parent, false);
                holder = new ViewHolder();

                holder.name = (TextView) convertView.findViewById(R.id.community_name);
                holder.Photo = (ImageView) convertView.findViewById(R.id.profile_Image);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.name.setText(item.Names);


            holder.name.setTypeface(MyApplication.getDefaultTypeface());

            try {

                byte[] imageResource;
                Bitmap bitmap;
                switch (position) {
                    case 0:
                        walletResourceManger.setImageName("kid_3.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 1:
                        // holder.Photo.setImageResource(R.drawable.kid_1);
                        walletResourceManger.setImageName("kid_1.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 2:
                        walletResourceManger.setImageName("kid_4.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 3:
                        walletResourceManger.setImageName("kid_5.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 4:
                        walletResourceManger.setImageName("kid_2.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 5:
                        walletResourceManger.setImageName("kid_6.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 6:
                        walletResourceManger.setImageName("kid_7.png");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                }


            } catch (CantGetResourcesException e) {
                System.err.println("CantGetResourcesException: " + e.getMessage());
            }

            return convertView;
        }
        /**
         * ViewHolder.
         */
        private class ViewHolder {
            public TextView name;
            public ImageView Photo;
        }

    }

}

