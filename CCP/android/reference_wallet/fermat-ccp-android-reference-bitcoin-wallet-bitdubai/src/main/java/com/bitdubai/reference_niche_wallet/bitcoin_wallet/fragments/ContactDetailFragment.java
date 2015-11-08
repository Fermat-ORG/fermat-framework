package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.util.MemoryUtils;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ReceiveFragmentDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceFragmentsEnumType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;
import com.squareup.picasso.Picasso;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Contact Detail Fragment.
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class ContactDetailFragment extends FermatWalletFragment implements View.OnClickListener {


    /**
     * Root fragment view reference
     */
    private View mFragmentView;
    /**
     * Fragment UI controls
     */
    private ImageView image_view_profile;
    private EditText edit_text_name;
    private TextView text_view_address;
    private LinearLayout linear_layout_extra_user_receive;

    /**
     * Typeface Font
     */
    private Typeface typeface;
    /**
     * Platform
     */
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;
    private CryptoWalletManager cryptoWalletManager;
    private WalletSettingsManager walletSettingsManager;

    /**
     * DATA
     */
    private CryptoWalletWalletContact cryptoWalletWalletContact;
    /**
     *  Resources
     */
    WalletResourcesProviderManager walletResourcesProviderManager;
    private ReferenceWalletSession referenceWalletSession;
    private FermatButton send_button;
    private FermatButton receive_button;


    public static ContactDetailFragment newInstance() {
        ContactDetailFragment f = new ContactDetailFragment();
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            referenceWalletSession = (ReferenceWalletSession) walletSession;

            cryptoWalletWalletContact = referenceWalletSession.getLastContactSelected();
            //typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
            cryptoWalletManager = referenceWalletSession.getCryptoWalletManager();
            errorManager = walletSession.getErrorManager();
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            makeText(getActivity(), "Oooops! recovering from system error",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error",Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
//        /* Load Wallet Contact */
//        walletContact = CollectionUtils.find(getWalletContactList(), new Predicate<WalletContact>() {
//            @Override
//            public boolean evaluate(WalletContact walletContact) {
//                try {
//                    return walletContact.name.equalsIgnoreCase(accountName);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                return false;
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            mFragmentView = inflater.inflate(R.layout.contact_detail_base, container, false);

            setUp();
            setUpContact();
            return mFragmentView;
        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE,e);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        try{
            if ( id == R.id.send_button) {
                referenceWalletSession.setLastContactSelected(cryptoWalletWalletContact);
                referenceWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS,false);
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY);
            }
            else if( id == R.id.receive_button){
                    referenceWalletSession.setLastContactSelected(cryptoWalletWalletContact);
                    referenceWalletSession.setData(SessionConstant.FROM_ACTIONBAR_SEND_ICON_CONTACTS,false);
                    changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY);
            }
            else if ( id == R.id.linear_layout_extra_user_receive){
                ReceiveFragmentDialog receiveFragmentDialog = new ReceiveFragmentDialog(
                        getActivity(),
                        cryptoWallet,
                        referenceWalletSession.getErrorManager(),
                        cryptoWalletWalletContact,
                        referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey(),
                        referenceWalletSession.getWalletSessionType().getWalletPublicKey());
                receiveFragmentDialog.show();
            }

        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE,e);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Setup UI references
     */
    private void setUp() {
        if (mFragmentView != null) {
            image_view_profile = (ImageView) mFragmentView.findViewById(R.id.image_view_profile);
            edit_text_name = (EditText) mFragmentView.findViewById(R.id.edit_text_name);
            text_view_address = (TextView) mFragmentView.findViewById(R.id.text_view_address);
            receive_button = (FermatButton) mFragmentView.findViewById(R.id.receive_button);
            send_button = (FermatButton) mFragmentView.findViewById(R.id.send_button);
            linear_layout_extra_user_receive = (LinearLayout) mFragmentView.findViewById(R.id.linear_layout_extra_user_receive);
            send_button.setOnClickListener(this);
            receive_button.setOnClickListener(this);
            linear_layout_extra_user_receive.setOnClickListener(this);
            if (typeface != null) {
                edit_text_name.setTypeface(typeface);
                text_view_address.setTypeface(typeface);
                receive_button.setTypeface(typeface);
                send_button.setTypeface(typeface);
            }
            if(cryptoWalletWalletContact.getActorType().equals(Actors.CCP_INTRA_WALLET_USER)){
                linear_layout_extra_user_receive.setVisibility(View.GONE);
            }
        }
    }


    /**
     * Setting up wallet contact value
     */
    private void setUpContact() {
        if (cryptoWalletWalletContact != null) {
            if(image_view_profile!=null){
                if(cryptoWalletWalletContact.getProfilePicture().length>0) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    Bitmap bitmapDrawable = BitmapFactory.decodeByteArray(cryptoWalletWalletContact.getProfilePicture(), 0, cryptoWalletWalletContact.getProfilePicture().length, options);// MemoryUtils.decodeSampledBitmapFromByteArray(cryptoWalletWalletContact.getProfilePicture(),image_view_profile.getMaxWidth(),image_view_profile.getMaxHeight());
                    image_view_profile.setImageBitmap(bitmapDrawable);
                }else
                    Picasso.with(getActivity()).load(R.drawable.celine_profile_picture).into(image_view_profile);
            }
            if (edit_text_name != null)
                edit_text_name.setText(cryptoWalletWalletContact.getActorName());
            if (text_view_address != null)
                text_view_address.setText(cryptoWalletWalletContact.getReceivedCryptoAddress().get(0).getAddress());
        }
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }

    /**
     * Set Wallet Session
     *
     * @param walletSession session
     */
    public void setWalletSession(ReferenceWalletSession walletSession) {
        this.walletSession = walletSession;
    }
}
