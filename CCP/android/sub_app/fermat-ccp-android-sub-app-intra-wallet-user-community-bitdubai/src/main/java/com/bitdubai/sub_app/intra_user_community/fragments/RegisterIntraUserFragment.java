package com.bitdubai.sub_app.intra_user_community.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;

import com.bitdubai.sub_app.intra_user_community.common.Views.RoundedDrawable;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.R;
import com.squareup.picasso.Picasso;

/**
 * Created by natalia on 19/06/15.
 * Modifed by Ronner Velazquez on 07/08/2015
 */
public class RegisterIntraUserFragment extends FermatFragment {
    private static final String ARG_POSITION = "position";


    /**
     * Wallet session
     */

    IntraUserSubAppSession intraUserSubAppSession;
    /**
     * Members
     */
    String contactName = "";
    String sufixName = "";
    String prefixName = "";
    String lastName = "";
    String middleName = "";
    String name = "";
    String alias = "";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMAGE = 2;
    static final int CONTEXT_MENU_CAMERA = 1;
    static final int CONTEXT_MENU_GALLERY = 2;
    static final int CONTEXT_MENU_DELETE = 3;

    boolean detailsVisible = false;
    boolean detailsSeparated = false;
    Bitmap contactPicture;
    /**
     * Fragment style
     */
    Typeface tf;
    /**
     * Screen views
     */
    private View rootView;
    private EditText editContactName;
    private EditText editPrefixName;
    private EditText editLastName;
    private EditText editMiddleName;
    private EditText editSufixName;
    private EditText editAlias;
    private ImageView takePictureButton;


    private ProgressBar progressBar;

    /**
     * deals with Error manager
     */
    private ErrorManager errorManager;

    /**
     * Identity Image
     */
    byte[] identityImage = new byte[0];

    private TextView txtView_account_name;
    private ImageView imageView_profile;

    private RelativeLayout header;

    /**
     * Resources
     */
    //private WalletResourcesProviderManager walletResourcesProviderManager;


    /**
     *
     * @param
     * @param
     * @return
     */

    public static RegisterIntraUserFragment newInstance() {
        RegisterIntraUserFragment f = new RegisterIntraUserFragment();
        Bundle b = new Bundle();
        //b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        //f.setContactName(intraUserSubAppSession.getIntraUserModuleManager());
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            intraUserSubAppSession = (IntraUserSubAppSession)subAppsSession;
            tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
            errorManager = intraUserSubAppSession.getErrorManager();
        }catch (Exception e){
            e.printStackTrace();
        }

        //try {
            //cryptoWalletManager = walletSession.getCryptoWalletManager();
           // cryptoWallet = cryptoWalletManager.getCryptoWallet();

        //} catch (CantGetCryptoWalletException e) {
         //   errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
         //   Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

       // }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_intra_user_fragment, container, false);
        try {

            // save_contact and cancel button definition
            Button saveContactButton = (Button) rootView.findViewById(R.id.save_contact_btn);
            Button cancelButton = (Button) rootView.findViewById(R.id.cancel_btn);

            saveContactButton.setTypeface(tf);
            cancelButton.setTypeface(tf);

            saveContactButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    saveContact();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnToContacts();
                }
            });

            //details_button definition
            final ImageButton detailsButton = (ImageButton) rootView.findViewById(R.id.details_btn);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!detailsVisible) {
                        showDetails();
                        Picasso.with(getActivity()).load(R.drawable.ic_arrow_up_grey).into(detailsButton);
                    } else {
                        hideDetails();
                        Picasso.with(getActivity()).load(R.drawable.ic_arrow_down).into(detailsButton);
                    }
                }
            });

            //take_picture_button definition
            takePictureButton = (ImageView) rootView.findViewById(R.id.take_picture_btn);
            takePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerForContextMenu(takePictureButton);
                    getActivity().openContextMenu(takePictureButton);
                }
            });


            editContactName = (EditText) rootView.findViewById(R.id.contact_name);
            editLastName = (EditText) rootView.findViewById(R.id.contact_last_name);
            editPrefixName = (EditText) rootView.findViewById(R.id.contact_name_prefix);
            editMiddleName = (EditText) rootView.findViewById(R.id.contact_middle_name);
            editSufixName = (EditText) rootView.findViewById(R.id.contact_name_sufix);
            editAlias = (EditText) rootView.findViewById(R.id.contact_alias);

            editContactName.setText(contactName);

            editContactName.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    txtView_account_name.setText(editContactName.getText().toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }


            });

            editContactName.setTypeface(tf);
            editLastName.setTypeface(tf);
            editPrefixName.setTypeface(tf);
            editMiddleName.setTypeface(tf);
            editSufixName.setTypeface(tf);
            editAlias.setTypeface(tf);

            txtView_account_name = (TextView) rootView.findViewById(R.id.txtView_account_name);

            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

            imageView_profile = (ImageView) rootView.findViewById(R.id.imageView_profile);

            header = (RelativeLayout) rootView.findViewById(R.id.header);

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    /**
     * create contact and save it into database
     */
    private void saveContact() {
        try {
            editContactName = (EditText) rootView.findViewById(R.id.contact_name);

            //MyThread thread = new MyThread();

            progressBar.setVisibility(View.VISIBLE);
            // Start lengthy operation in a background thread

//            Runnable runnable = new Runnable() {
//                public void run() {
//
//                    try {
//
//                        intraUserSubAppSession.getIntraUserModuleManager().createIntraUser(editContactName.getText().toString(),identityImage);
//                        Toast.makeText(getActivity(), "Identity sucessfully created", Toast.LENGTH_SHORT).show();
//
//
//                    } catch (CouldNotCreateIntraUserException e) {
//                        e.printStackTrace();
//                    }
////                    while (mProgressStatus < 100) {
////                        mProgressStatus = doWork();
////
////                        // Update the progress bar
////                        mHandler.post(new Runnable() {
////                            public void run() {
////                                mProgress.setProgress(mProgressStatus);
////                            }
////                        });
////                    }
//                }
//            };
//            //MyThread thread = new MyThread();
//
//            getActivity().runOnUiThread(runnable);


            //thread.start();

            intraUserSubAppSession.getModuleManager().createIntraUser(editContactName.getText().toString(),identityImage);

            Toast.makeText(getActivity(), "Identity sucessfully created", Toast.LENGTH_SHORT).show();

            ((FermatScreenSwapper)getActivity()).changeActivity(Activities.CWP_INTRA_USER_ACTIVITY.getCode(),subAppsSession.getAppPublicKey());

            //CryptoAddress validAddress = validateAddress(address.getText().toString(), cryptoWallet);

//            if (validAddress != null) {
//
//                // first i add the contact
//                cryptoWallet.createWalletContact(validAddress, contact_name.getText().toString(), Actors.EXTRA_USER, ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, walletPublicKey);
//
//                Toast.makeText(getActivity().getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();
//                returnToContacts();
//
//
//            } else {
//                Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
//            }
//        } catch (CantCreateWalletContactException e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }


    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * back to contacts
     */
    private void returnToContacts() {

        ((FermatScreenSwapper)getActivity()).changeActivity(Activities.CWP_INTRA_USER_ACTIVITY.getCode(),null);
//        ContactsFragment contactsFragment = new ContactsFragment();
//        contactsFragment.setWalletSession(walletSession);
//
//        FragmentTransaction FT = getFragmentManager().beginTransaction();
//
//        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        FT.replace(R.id.fragment_container2, contactsFragment);
//        FT.commit();
    }


    /**
     * Set wallet session
     *
     * @param walletSession
     */
    public void setIntraUserSubAppSession(IntraUserSubAppSession walletSession) {
        this.intraUserSubAppSession = walletSession;
    }

    private void showDetails() {
        if (!detailsSeparated) {
            contactName = editContactName.getText().toString();
            if (prefixName.length() > 0) {
                editPrefixName.setText(prefixName);
                contactName = contactName.replace(prefixName + " ", "");
            }
            if (alias.length() > 0) {
                editAlias.setText(alias);
                contactName = contactName.replace(" " + alias, "");
            }
            if (contactName.contains(",")) {
                sufixName = contactName.substring(contactName.indexOf(",") + 2);
                editSufixName.setText(sufixName);
                contactName = contactName.substring(0, contactName.indexOf(","));
            }
            String[] nameArray = contactName.split(" ");
            switch (nameArray.length) {
                case 0:
                    prefixName = "";
                    name = "";
                    middleName = "";
                    lastName = "";
                    sufixName = "";
                    alias = "";

                    editContactName.setHint("first name");

                    editPrefixName.setText("");
                    editContactName.setText("");
                    editMiddleName.setText("");
                    editLastName.setText("");
                    editSufixName.setText("");
                    editAlias.setText("");
                    break;
                case 1:
                    name = nameArray[0];
                    editContactName.setText(name);
                    break;
                case 2:
                    name = nameArray[0];
                    lastName = nameArray[1];
                    editContactName.setText(name);
                    editLastName.setText(lastName);
                    break;
                case 3:
                    name = nameArray[0];
                    middleName = nameArray[1];
                    lastName = nameArray[2];
                    editContactName.setText(name);
                    editMiddleName.setText(middleName);
                    editLastName.setText(lastName);
                    break;
                default:
                    lastName = nameArray[nameArray.length - 1];
                    middleName = nameArray[nameArray.length - 2];
                    editMiddleName.setText(middleName);
                    editLastName.setText(lastName);
                    name = "";
                    for (int i = 0; i < nameArray.length - 2; i++) {
                        name += nameArray[i] + " ";
                    }
                    editContactName.setText(name.trim());
            }
        } else {
            editPrefixName.setText(prefixName);
            editContactName.setText(contactName);
            editMiddleName.setText(middleName);
            editLastName.setText(lastName);
            editSufixName.setText(sufixName);
            editAlias.setText(alias);
        }
        detailsSeparated = true;
        editLastName.setVisibility(View.VISIBLE);
        editPrefixName.setVisibility(View.VISIBLE);
        editMiddleName.setVisibility(View.VISIBLE);
        editSufixName.setVisibility(View.VISIBLE);
        editAlias.setVisibility(View.VISIBLE);
        detailsVisible = true;
    }

    private void hideDetails() {
        editLastName.setVisibility(View.GONE);
        editPrefixName.setVisibility(View.GONE);
        editMiddleName.setVisibility(View.GONE);
        editSufixName.setVisibility(View.GONE);
        editAlias.setVisibility(View.GONE);
        detailsVisible = false;
        prefixName = editPrefixName.getText().toString();
        name = editContactName.getText().toString();
        middleName = editMiddleName.getText().toString();
        lastName = editLastName.getText().toString();
        sufixName = editSufixName.getText().toString();
        alias = editAlias.getText().toString();

        contactName = ((prefixName.length() > 0) ? prefixName + " " : "") + ((name.length() > 0) ? name + " " : "") +
                ((middleName.length() > 0) ? middleName + " " : "") + ((lastName.length() > 0) ? lastName : "") +
                ((sufixName.length() > 0) ? ", " + sufixName : "");
        editContactName.setText(contactName.trim());
        detailsSeparated = false;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap =null;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        imageBitmap = Bitmap.createScaledBitmap(imageBitmap, takePictureButton.getWidth(), takePictureButton.getHeight(), true);
                        identityImage = imageBitmap.getNinePatchChunk();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            takePictureButton.setBackground(new RoundedDrawable(imageBitmap, takePictureButton));
            takePictureButton.setImageDrawable(null);
            contactPicture = imageBitmap;
            Drawable drawable = new BitmapDrawable(imageBitmap);
            header.setBackground(drawable);
            imageView_profile.setImageBitmap(null);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select contact picture");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_action_camera_grey));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        if(contactPicture!=null)
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case CONTEXT_MENU_CAMERA:
                dispatchTakePictureIntent();
                break;
            case CONTEXT_MENU_GALLERY:
                loadImageFromGallery();
                break;
            case CONTEXT_MENU_DELETE:
                takePictureButton.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.rounded_button_green_selector));
                takePictureButton.setImageResource(R.drawable.ic_action_camera_grey);
                contactPicture = null;
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void loadImageFromGallery() {
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

}
