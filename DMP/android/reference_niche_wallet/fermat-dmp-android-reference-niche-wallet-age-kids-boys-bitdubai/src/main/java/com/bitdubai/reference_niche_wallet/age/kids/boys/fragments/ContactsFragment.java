package com.bitdubai.reference_niche_wallet.age.kids.boys.fragments;

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


import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;
import com.bitdubai.fermat_dmp.wallet_runtime.R;
import com.bitdubai.reference_niche_wallet.age.kids.boys.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    /**
     * ContactsFragment member variables.
     */
    private static final String ARG_POSITION = "position";
    private ArrayList<App> appList;
    private static Platform platform;
    private  static WalletResourcesManager walletResourceManger;

    /**
     * Constructor.
     */

    public static ContactsFragment newInstance(int position) {
        walletResourceManger = platform.getWalletResourcesManager();
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);

        ContactsFragment f = new ContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;

    }


    /**
     * Fragment Class implementation.
     */

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


        if (this.appList == null)
        {

            this.appList = new ArrayList<App>();

            for (int i = 0; i < 7; i++) {
                App item = new App();
                item.Names = names[i];
                this.appList.add(item);

            }
        }


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(5);
        } else {
            gridView.setNumColumns(3);
        }

        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallets_kids_fragment_contacts_filter, this.appList));

        return gridView;
    }


    /**
     * ContactsFragment implementation.
     */

    public  static void setPlatform (Platform platformWallet){
        platform = platformWallet;
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


            try {

                byte[] imageResource;
                Bitmap bitmap;
                switch (position) {
                    case 0:
                        imageResource = walletResourceManger.getImageResource("kid_3.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 1:
                        imageResource = walletResourceManger.getImageResource("kid_1.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 2:
                        imageResource = walletResourceManger.getImageResource("kid_4.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 3:
                        imageResource = walletResourceManger.getImageResource("kid_5.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 4:
                        imageResource = walletResourceManger.getImageResource("kid_2.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 5:
                        imageResource = walletResourceManger.getImageResource("kid_6.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                    case 6:
                        imageResource = walletResourceManger.getImageResource("kid_7.png");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        break;
                }


            } catch (CantGetResourcesException e) {
              // platform.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
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

