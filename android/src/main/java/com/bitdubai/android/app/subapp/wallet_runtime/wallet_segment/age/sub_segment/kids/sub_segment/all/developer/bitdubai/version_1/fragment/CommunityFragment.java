package com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

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

import com.bitdubai.fermat_api.layer._10_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private ArrayList<App> mlist;
    private  static WalletResourcesManager walletResourceManger;
    private int position;

    public static CommunityFragment newInstance(int position) {
        Platform platform = MyApplication.getPlatform();
        CorePlatformContext platformContext = platform.getCorePlatformContext();
        walletResourceManger = (WalletResourcesManager)platformContext.getPlugin(Plugins.BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE);
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
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

        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.wallets_kids_fragment_community_filter, mlist));

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

            holder.name.setTypeface(MyApplication.getDefaultTypeface());
            holder.country.setTypeface(MyApplication.getDefaultTypeface());
            holder.state.setTypeface(MyApplication.getDefaultTypeface());
            try {

                byte[] imageResource;
                Bitmap bitmap;
                switch (position)
                {
                    case 0:
                        walletResourceManger.setImageName("kid_8.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);
                        //  holder.Photo.setImageResource(R.drawable.kid_8) ;
                        break;
                    case 1:
                        walletResourceManger.setImageName("kid_9.jpeg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 2:
                        walletResourceManger.setImageName("kid_12.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 3:
                        walletResourceManger.setImageName("kid_11.jpeg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 4:
                        walletResourceManger.setImageName("kid_12.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 5:
                        walletResourceManger.setImageName("kid_13.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 6:
                        walletResourceManger.setImageName("kid_14.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 7:
                        walletResourceManger.setImageName("kid_16.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 8:
                        walletResourceManger.setImageName("kid_17.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 9:
                        walletResourceManger.setImageName("kid_18.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 10:
                        walletResourceManger.setImageName("kid_19.jpg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 11:
                        walletResourceManger.setImageName("kid_20.jpeg");
                        imageResource = walletResourceManger.getImageResource();
                        bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                        holder.Photo.setImageBitmap(bitmap);

                        break;
                    case 12:
                        walletResourceManger.setImageName("kid_21.jpg");
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

        private class ViewHolder {
            public TextView name;
            public TextView country;
            public TextView state;
            public ImageView Photo;
        }
    }
}