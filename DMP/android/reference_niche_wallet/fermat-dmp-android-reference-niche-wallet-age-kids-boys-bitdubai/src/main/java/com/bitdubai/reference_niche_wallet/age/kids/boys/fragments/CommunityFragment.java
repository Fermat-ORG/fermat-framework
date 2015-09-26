package com.bitdubai.reference_niche_wallet.age.kids.boys.fragments;

/**
 * Created by ciencias on 25.11.14.
 */

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;
import com.bitdubai.fermat_dmp.wallet_runtime.R;
import com.bitdubai.reference_niche_wallet.age.kids.boys.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    /**
     * CommunityFragment member variables.
     */

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private  static WalletResourcesManager walletResourceManger;
    private static Platform platform;

    public static CommunityFragment newInstance(int position) {

        walletResourceManger = platform.getWalletResourcesManager();
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
        CommunityFragment f = new CommunityFragment();
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

        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallets_kids_fragment_community_filter, mlist));

        return gridView;
    }

    /**
     * CommunityFragment implementation.
     */

    public  static void setPlatform (Platform platformWallet){
        platform = platformWallet;
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
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.wallets_kids_fragment_community, parent, false);

            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.community_name);
            holder.country = (TextView) convertView.findViewById(R.id.community_country);
            holder.state= (TextView) convertView.findViewById(R.id.community_state);
            holder.Photo = (ImageView) convertView.findViewById(R.id.profile_Image);

            convertView.setTag(holder);
            holder = (ViewHolder) convertView.getTag();

            holder.name.setText(item.Names);
            holder.country.setText(item.Countries);
            holder.state.setText(item.States);


            try {

                byte[] imageResource;
                Bitmap bitmap;
                switch (position)
                {
                    case 0:
                        imageResource = walletResourceManger.getImageResource("kid_8.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 1:
                       imageResource = walletResourceManger.getImageResource("kid_9.jpeg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 2:
                        imageResource = walletResourceManger.getImageResource("kid_12.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 3:
                        imageResource = walletResourceManger.getImageResource("kid_11.jpeg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 4:
                       imageResource = walletResourceManger.getImageResource("kid_12.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 5:
                        imageResource = walletResourceManger.getImageResource("kid_13.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 6:
                        imageResource = walletResourceManger.getImageResource("kid_14.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 7:
                       imageResource = walletResourceManger.getImageResource("kid_16.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 8:
                        imageResource = walletResourceManger.getImageResource("kid_17.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 9:
                       imageResource = walletResourceManger.getImageResource("kid_18.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 10:
                        imageResource = walletResourceManger.getImageResource("kid_19.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 11:
                        imageResource = walletResourceManger.getImageResource("kid_20.jpeg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 12:
                       imageResource = walletResourceManger.getImageResource("kid_21.jpg");
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                }

            } catch (CantGetResourcesException e) {
                System.err.println("CantGetResourcesException: " + e.getMessage());

            }
            return convertView;
        }

        private class ViewHolder {
            public TextView name;
            public TextView country;
            public TextView state;
            public ImageView Photo;
        }
    }
}