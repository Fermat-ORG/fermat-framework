package com.bitdubai.sub_app.intra_user.fragments;


import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.sub_app.intra_user.common.models.IntraUserConnectionListItem;

import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user.util.CommonLogger;
import com.bitdubai.intra_user_identity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 15/09/15.
 */
public class ConnectionsWorldFragment  extends FermatFragment {

    /**
     * ContactsFragment member variables.Fragment
     */
    private final int POPUP_MENU_WIDHT = 325;

    int MAX = 5;
    int OFFSET= 0;
    private static final String ARG_POSITION = "position";
    private ArrayList<App> appList;
    /**
     * MANAGERS
     */
    private  static IntraUserModuleManager moduleManager;
    private  static ErrorManager errorManager;

    protected final String TAG = "Recycler Base";

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionsWorldFragment newInstance() {
        return new ConnectionsWorldFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up  module
            moduleManager = ((IntraUserSubAppSession) subAppsSession).getIntraUserModuleManager();
            errorManager = subAppsSession.getErrorManager();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView gridView = new GridView(getActivity());
        try
        {


        IntraUserSearch intraUserSearch = moduleManager.searchIntraUser();

        intraUserSearch.setNameToSearch("n");

        List<IntraUserInformation> intraUserInformationList = intraUserSearch.getResult();


            this.appList = new ArrayList<App>();

            for (IntraUserInformation intraUserInformation : intraUserInformationList) {
                App item = new App();
                item.Names = intraUserInformation.getName();
                this.appList.add(item);

            }



        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(5);
        } else {
            gridView.setNumColumns(3);
        }

        gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.intra_user_connection_word_filter, this.appList));



        }
        catch(Exception ex)
        {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }

        return gridView;
    }


    /**
     * ContactsFragment implementation.
     */


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
                convertView = inflater.inflate(R.layout.intra_user_connection_word_list, parent, false);
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
              /*  switch (position) {
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
                }*/


            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
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




