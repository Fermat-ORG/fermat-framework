package com.bitdubai.sub_app.intra_user_community.fragments;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantLoginIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.common.Views.Utils;
import com.bitdubai.sub_app.intra_user_community.common.adapters.IntraUserConnectionsAdapter;
import com.bitdubai.sub_app.intra_user_community.common.concurrent.CallbackMati;
import com.bitdubai.sub_app.intra_user_community.common.concurrent.MyThread;
import com.bitdubai.sub_app.intra_user_community.common.models.IntraUserConnectionListItem;
import com.bitdubai.sub_app.intra_user_community.fragmentFactory.IntraUserFragmentsEnumType;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 15/09/15.
 */
public class ConnectionsWorldFragment  extends FermatFragment implements SearchView.OnCloseListener,
        SearchView.OnQueryTextListener,
        ActionBar.OnNavigationListener,
        AdapterView.OnItemClickListener,
        CallbackMati{

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
    private SearchView mSearchView;

    private AppListAdapter adapter;
    private boolean isStartList;

    ExecutorService executor;


    private ProgressDialog mDialog;

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

            setHasOptionsMenu(true);
            // setting up  module
            moduleManager = ((IntraUserSubAppSession) subAppsSession).getIntraUserModuleManager();
            errorManager = subAppsSession.getErrorManager();

            mNotificationsCount = moduleManager.getIntraUsersWaitingYourAcceptanceCount();

            executor = Executors.newFixedThreadPool(2);

            // TODO: display unread notifications.
            // Run a task to fetch the notifications count
            new FetchCountTask().execute();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,UnexpectedUIExceptionSeverity.CRASH,ex);
        }
    }

    /**
     * Fragment Class implementation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView gridView = new GridView(getActivity());
        try {
//            IntraUserSearch intraUserSearch = moduleManager.searchIntraUser();
//
//            intraUserSearch.setNameToSearch("");
//            lstIntraUserInformations = intraUserSearch.getResult();


            // execute the Runnable

            mDialog = new ProgressDialog(getActivity());
                mDialog.setMessage("Please wait...");
                mDialog.setCancelable(false);
                mDialog.show();

            MyThread myThread = new MyThread(getActivity(), this) {
                @Override
                public List<IntraUserInformation> mainTask() {
                    try {
                        return moduleManager.getSuggestionsToContact(MAX, offset);
                    } catch (CantGetIntraUsersListException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };

            executor.execute(myThread);

            lstIntraUserInformations = new ArrayList<>();

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(5);
            } else {
                gridView.setNumColumns(3);
            }

            adapter = new AppListAdapter(getActivity(), R.layout.intra_user_connection_word_filter, lstIntraUserInformations);

            gridView.setAdapter(adapter);

        }
        catch(Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        RelativeLayout rootView = new RelativeLayout(getActivity());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        rootView.setLayoutParams(layoutParams);

        rootView.setBackgroundColor(Color.parseColor("#000b12"));

        rootView.addView(gridView);

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.intra_user_menu, menu);

        //MenuItem menuItem = new SearchView(getActivity());

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setShowAsAction(searchItem, MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_ALWAYS);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnCloseListener(this);



        //MenuItem action_connection_request = menu.findItem(R.id.action_connection_request);
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_notifications);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(getActivity(), icon, mNotificationsCount);



//        List<String> lst = new ArrayList<String>();
//        lst.add("Matias");
//        lst.add("Work");
//        ArrayAdapter<String> itemsAdapter =
//                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lst);
//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        spinner.setAdapter(itemsAdapter); // set the adapter to provide layout of rows and content
//        //s.setOnItemSelectedListener(onItemSelectedListener); // set the listener, to perform actions based on item selection

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            CharSequence itemTitle = item.getTitle();

            // Esto podria ser un enum de item menu que correspondan a otro menu
            if(itemTitle.equals("New Identity")){
                changeActivity(Activities.CWP_INTRA_USER_CREATE_ACTIVITY.getCode());

            }
//            if(id == R.id.action_connection_request){
//                Toast.makeText(getActivity(),"Intra user request",Toast.LENGTH_SHORT).show();
//            }
            if (item.getItemId() == R.id.action_notifications) {
                changeActivity(Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_REQUEST.getCode());
                return true;
            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
Updates the count of notifications in the ActionBar.
 */
    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        getActivity().invalidateOptionsMenu();
    }




//    /**
//     * onItem click listener event
//     *
//     * @param data
//     * @param position
//     */
//    @Override
//    public void onItemClickListener(IntraUserConnectionListItem data, int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Hubo un problema");
//        builder.setMessage("No se pudieron obtener los detalles de la connexión seleccionada");
//        builder.setPositiveButton("OK", null);
//        builder.show();
//    }
//
//    /**
//     * On Long item Click Listener
//     *
//     * @param data
//     * @param position
//     */
//    @Override
//    public void onLongItemClickListener(IntraUserConnectionListItem data, int position) {
//
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onQueryTextSubmit(String name) {
        //swipeRefreshLayout.setRefreshing(true);
        IntraUserSearch intraUserSearch = moduleManager.searchIntraUser();
        intraUserSearch.setNameToSearch(name);

        // This method does not exist
        mSearchView.onActionViewCollapsed();
        //TODO: cuando esté el network service, esto va a descomentarse
//        try {
//            adapter.changeDataSet(intraUserSearch.getResult());
//
//        } catch (CantGetIntraUserSearchResult cantGetIntraUserSearchResult) {
//            cantGetIntraUserSearchResult.printStackTrace();
//        }


        //adapter.setAddButtonVisible(true);
        //adapter.changeDataSet(IntraUserConnectionListItem.getTestDataExample(getResources()));
        //swipeRefreshLayout.setRefreshing(false);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //Toast.makeText(getActivity(), "Probando busqueda completa", Toast.LENGTH_SHORT).show();
        if(s.length()==0 && isStartList){
            //((IntraUserConnectionsAdapter)adapter).setAddButtonVisible(false);
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
            return true;
        }
        return false;
    }

    @Override
    public boolean onClose() {
        if(!mSearchView.isActivated()){
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int position, long idItem) {
        try {
            IntraUserLoginIdentity intraUserLoginIdentity = moduleManager.showAvailableLoginIdentities().get(position);
            moduleManager.login(intraUserLoginIdentity.getPublicKey());
            //TODO: para despues
            //adapter.changeDataSet(intraUserModuleManager.getAllIntraUsers());

            //mientras tanto testeo
            //adapter.changeDataSet(IntraUserConnectionListItem.getTestData(getResources()));

            return true;
        } catch (CantShowLoginIdentitiesException e) {
            e.printStackTrace();
        } catch (CantLoginIntraUserException e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public void onPostExecute(List<IntraUserInformation> lst) {
        mDialog.dismiss();
        lstIntraUserInformations = lst;
        adapter.addAll(lstIntraUserInformations);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }





    /*
    Sample AsyncTask to fetch the notifications count
    */
    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return mNotificationsCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }

//    private static final String TITLE = "title";
//    private static final String ICON = "icon";
//
//    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//
//    // Use this to add items to the list that the ListPopupWindow will use
//    private void addItem(String title, int iconResourceId) {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put(TITLE, title);
//        map.put(ICON, iconResourceId);
//        data.add(map);
//    }

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

            byte[] profileImage=null;
            try {
               profileImage  = item.getProfileImage();
            }catch (Exception e){

            }

            if(profileImage!=null){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length, options);
                holder.Photo.setImageBitmap(bitmap);
            }else {

                try {

                    switch (position) {
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




