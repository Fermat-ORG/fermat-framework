package com.bitdubai.sub_app.wallet_manager.fragment;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantGetIfIntraWalletUsersExistsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantGetUserWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.popup.CreateUserFragmentDialog;


import java.util.List;

/**
 * Created by Matias Furszyfer
 */


public class WalletDesktopFragment extends Fragment implements Thread.UncaughtExceptionHandler, DialogInterface.OnDismissListener {

    private static final String ARG_POSITION = "position";
    private static final String CWP_WALLET_BASIC_ALL_MAIN = Activities.CWP_WALLET_BASIC_ALL_MAIN.getCode();

    Typeface tf;

    private WalletManager walletManager;


    private List<InstalledWallet> lstInstalledWallet;

    public static WalletDesktopFragment newInstance(int position, WalletManager walletManager) {
        WalletDesktopFragment f = new WalletDesktopFragment();
        f.setWalletManager(walletManager);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        setHasOptionsMenu(true);

        if (walletManager != null)
            try {
                lstInstalledWallet = walletManager.getUserWallets();
            } catch (CantGetUserWalletException e) {
                e.printStackTrace();
            }


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        AppListAdapter adapter = new AppListAdapter(getActivity(), R.layout.shell_wallet_desktop_front_grid_item, lstInstalledWallet);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(),"mati",Toast.LENGTH_SHORT).show();
                return ;
            }
        });



        return gridView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.wallet_manager_desktop_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if(id == R.id.action_search){
        //    Toast.makeText(getActivity(), "holaa", Toast.LENGTH_LONG);
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set Wallet manager plugin
     *
     * @param walletManager
     */
    public void setWalletManager(WalletManager walletManager) {
        this.walletManager = walletManager;
    }


//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {
//            contactImageBitmap = null;
//            switch (requestCode) {
//                case REQUEST_IMAGE_CAPTURE:
//                    Bundle extras = data.getExtras();
//                    contactImageBitmap = (Bitmap) extras.get("data");
//                    break;
//                case REQUEST_LOAD_IMAGE:
//                    Uri selectedImage = data.getData();
//                    try {
//                        contactImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//
//                        //imageBitmap = Bitmap.createScaledBitmap(imageBitmap,take_picture_btn.getWidth(),take_picture_btn.getHeight(),true);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//            //take_picture_btn.setBackground(new RoundedDrawable(imageBitmap, take_picture_btn));
//            //take_picture_btn.setImageDrawable(null);
//            //contactPicture = imageBitmap;
//            lauchCreateContactDialog(true);
//
//        }
//    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        menu.setHeaderTitle("Select contact picture");
//        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
//        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
//        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
//        menu.add(Menu.NONE, CONTEXT_MENU_NO_PHOTO, Menu.NONE, "No photo");
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case CONTEXT_MENU_CAMERA:
//                dispatchTakePictureIntent();
//                break;
//            case CONTEXT_MENU_GALLERY:
//                loadImageFromGallery();
//                break;
//            case CONTEXT_MENU_NO_PHOTO:
//                lauchCreateContactDialog(false);
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }
//
//    private void loadImageFromGallery() {
//        Intent intentLoad = new Intent(
//                Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
//    }

    private void lauchCreateContactDialog(boolean withImage){
//        CreateUserFragmentDialog dialog = new CreateUserFragmentDialog(
//                getActivity(),
//                user_id,
//                ((withImage) ? contactImageBitmap : null));
//        dialog.setOnDismissListener(this);
//        dialog.show();
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                contactsAdapter.clear();
//                contactsAdapter.addAll(getWalletContactList());
//
//                contactsAdapter.notifyDataSetChanged();

            }
        };
        Thread thread = new Thread(runnable);

        thread.setUncaughtExceptionHandler(this);

        thread.start();

//        if()
//        ((FermatScreenSwapper) getActivity()).selectWallet(installedWallet);


    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Toast.makeText(getActivity(),"oooopps",Toast.LENGTH_SHORT).show();
    }


    public class AppListAdapter extends ArrayAdapter<InstalledWallet> {


        public AppListAdapter(Context context, int textViewResourceId, List<InstalledWallet> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            try
            {
                final InstalledWallet installedWallet = getItem(position);

                ViewHolder holder;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.shell_wallet_desktop_front_grid_item, parent, false);
                    holder = new ViewHolder();


                    holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                    holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.companyTextView.setText(installedWallet.getWalletName());
                holder.companyTextView.setTypeface(tf, Typeface.BOLD);


                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.wallet_3);

                //Hardcodeado hasta que esté el wallet resources
                switch (installedWallet.getWalletIcon()) {

                    case "reference_wallet_icon":
                        holder.imageView.setImageResource(R.drawable.fermat);
                        holder.imageView.setTag("WalletBitcoinActivity|4");
                        linearLayout.setTag("WalletBitcoinActivity|4");

                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // try {
                                // check if intra user identity exists
                                //if (walletManager.hasIntraUserIdentity()) {
                                //set the next fragment and params


                                // }
                                // else
                                // {

                                // }
                                // } catch (Exception e) {
                                // Toast.makeText(getActivity(),"oooopps have an error ",Toast.LENGTH_SHORT).show();
                                // }


                            }
                        });

                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {


                                    if (walletManager.hasIntraUserIdentity())
                                        ((FermatScreenSwapper) getActivity()).selectWallet(installedWallet);
                                    else {
                                        Toast.makeText(getActivity(),"Es necesario crear una identidad",Toast.LENGTH_SHORT).show();
                                    }


                                } catch (CantGetIfIntraWalletUsersExistsException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        break;

                }

            }
            catch (Exception e)
            {
                Toast.makeText(getActivity(),"oooopps ",Toast.LENGTH_SHORT).show();
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