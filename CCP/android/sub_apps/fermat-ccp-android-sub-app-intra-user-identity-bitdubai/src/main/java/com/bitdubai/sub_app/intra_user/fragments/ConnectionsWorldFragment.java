package com.bitdubai.sub_app.intra_user.fragments;


import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user.util.CommonLogger;
import com.bitdubai.intra_user_identity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 15/09/15.
 */
public class ConnectionsWorldFragment  extends FermatFragment {

    /**
     * ContactsFragment member variables.Fragment
     */
    private final int POPUP_MENU_WIDHT = 325;


    private static final String ARG_POSITION = "position";
    /**
     * MANAGERS
     */
    private  static IntraUserModuleManager moduleManager;
    private  static ErrorManager errorManager;

    private List<IntraUserInformation> lstIntraUserInformations;


    protected final String TAG = "Recycler Base";

    private static final int MAX = 20;

    private int offset = 0;

    private int mNotificationsCount=0;

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

            mNotificationsCount = moduleManager.getIntraUsersWaitingYourAcceptanceCount();

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


            intraUserSearch.setNameToSearch("");

            lstIntraUserInformations = intraUserSearch.getResult();



            moduleManager.getSuggestionsToContact(MAX, offset);


            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(5);
            } else {
                gridView.setNumColumns(3);
            }

            gridView.setAdapter(new AppListAdapter(getActivity(), R.layout.intra_user_connection_word_filter, lstIntraUserInformations));



            }
            catch(Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
            }

        RelativeLayout rootView = new RelativeLayout(getActivity());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        rootView.setLayoutParams(layoutParams);

        rootView.setBackgroundColor(Color.parseColor("#000b12"));

        rootView.addView(gridView);

            return rootView;
    }


    /**
     * ContactsFragment implementation.
     */



    public class AppListAdapter extends ArrayAdapter<IntraUserInformation> {

        public AppListAdapter(Context context, int textViewResourceId, List<IntraUserInformation> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            IntraUserInformation item = getItem(position);

            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.world_frament_row, parent, false);
                holder = new ViewHolder();

                holder.name = (TextView) convertView.findViewById(R.id.community_name);
                holder.Photo = (ImageView) convertView.findViewById(R.id.profile_Image);
                holder.connection = (ImageView) convertView.findViewById(R.id.imageView_connection);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(item.getName());

            try {

                switch (position){
                    case 0:
                        holder.Photo.setImageResource(R.drawable.piper_profile_picture);
                        break;
                    case 1:
                        holder.Photo.setImageResource(R.drawable.luis_profile_picture);
                        break;
                    case 2:
                        holder.Photo.setImageResource(R.drawable.brant_profile_picture);
                        break;
                    case 3:
                        holder.Photo.setImageResource(R.drawable.louis_profile_picture);
                        break;
                    case 4:
                        holder.Photo.setImageResource(R.drawable.madaleine_profile_picture);
                        break;
                    case 5:
                        holder.Photo.setImageResource(R.drawable.adrian_profile_picture);
                        break;
                    case 6:
                        holder.Photo.setImageResource(R.drawable.deniz_profile_picture);
                        break;
                    case 7:
                        holder.Photo.setImageResource(R.drawable.dea_profile_picture);
                        break;
                    case 8:
                        holder.Photo.setImageResource(R.drawable.florence_profile_picture);
                        break;
                     case 9:
                         holder.Photo.setImageResource(R.drawable.alexandra_profile_picture);
                         break;
                    case 10:
                        holder.Photo.setImageResource(R.drawable.simon_profile_picture);
                        break;
                    case 11:
                        holder.Photo.setImageResource(R.drawable.victoria_profile_picture);
                        break;
                    default:
                        holder.Photo.setImageResource(R.drawable.robert_profile_picture);
                        break;
                }

                /**
                 * falta poner si la conexion está activa
                 *
                 * */


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
            public ImageView connection;
        }

    }

}




