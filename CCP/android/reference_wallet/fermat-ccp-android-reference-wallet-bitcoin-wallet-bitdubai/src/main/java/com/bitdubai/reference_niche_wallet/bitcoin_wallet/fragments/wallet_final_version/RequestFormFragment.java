package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.squareup.picasso.Picasso;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 */

public class RequestFormFragment extends FermatWalletFragment implements View.OnClickListener{

    /**
     * Plaform reference
     */
    private ReferenceWalletSession referenceWalletSession;
    private CryptoWallet cryptoWallet;
    private IntraUserModuleManager intraUserModuleManager;

    /**
     * UI
     */
    private View rootView;
    private FermatTextView contactName;
    private EditText editTextAmount;
    private ImageView imageView_contact;
    private FermatButton send_button;
    private TextView txt_notes;

    /**
     * User selected
     */
    private CryptoWalletWalletContact cryptoWalletWalletContact;


    public static RequestFormFragment newInstance() {
        return new RequestFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = (ReferenceWalletSession) walletSession;
        intraUserModuleManager = referenceWalletSession.getIntraUserModuleManager();
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.request_form_base, container, false);
            setUpUI();
            setUpActions();
            setUpUIData();
            return rootView;
        }catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return null;
    }

    private void setUpUI(){
        contactName = (FermatTextView) rootView.findViewById(R.id.contact_name);
        txt_notes = (TextView) rootView.findViewById(R.id.notes);
        editTextAmount = (EditText) rootView.findViewById(R.id.amount);
        imageView_contact = (ImageView) rootView.findViewById(R.id.profile_Image);
        send_button = (FermatButton) rootView.findViewById(R.id.send_button);
    }

    private void setUpActions(){
        /**
         * Listeners
         */
        imageView_contact.setOnClickListener(this);
        send_button.setOnClickListener(this);
        rootView.findViewById(R.id.scan_qr).setOnClickListener(this);

        /**
         *  Amount observer
         */
        editTextAmount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                try {
                    //Long amount = Long.parseLong(editTextAmount.getText().toString());
                    //if (amount > 0) {
                    //long actualBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
                    //editTextAmount.setHint("Available amount: " + actualBalance + " bits");
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        /**
         * Selector
         */
        send_button.selector(R.drawable.bg_home_accept_active, R.drawable.bg_home_accept_normal, R.drawable.bg_home_accept_active);
    }

    private void setUpUIData(){

        cryptoWalletWalletContact = referenceWalletSession.getLastContactSelected();
        try {
            imageView_contact.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), cryptoWalletWalletContact.getProfilePicture()));
        }catch (Exception e){
            imageView_contact.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(),R.drawable.profile_image));
        }
        contactName.setText(cryptoWalletWalletContact.getActorName());
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.scan_qr) {
            IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
            integrator.initiateScan();
        }
        else if (id == R.id.send_button){
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }

            sendRequest();

        }
        else if (id == R.id.imageView_contact){
            // if user press the profile image
        }
    }

    private void sendRequest(){

        try {

            if (cryptoWalletWalletContact == null) {
                Toast.makeText(getActivity(), "Contact not found, please add it first.", Toast.LENGTH_LONG).show();
            } else if (cryptoWalletWalletContact.getCompatibility().equals(Compatibility.INCOMPATIBLE)) {
                Toast.makeText(getActivity(), "The user doesn't have a compatible wallet.", Toast.LENGTH_LONG).show();
            } else if (cryptoWalletWalletContact.getReceivedCryptoAddress().isEmpty()) {
                Toast.makeText(getActivity(), "We can't find an address for the contact yet.", Toast.LENGTH_LONG).show();
            } else {
                cryptoWallet.sendCryptoPaymentRequest(
                        cryptoWalletWalletContact.getWalletPublicKey(),
                        intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey(),
                        Actors.INTRA_USER,
                        cryptoWalletWalletContact.getActorPublicKey(),
                        cryptoWalletWalletContact.getActorType(),
                        cryptoWalletWalletContact.getReceivedCryptoAddress().get(0),
                        txt_notes.getText().toString(),
                        Long.valueOf(editTextAmount.getText().toString()),
                        BlockchainNetworkType.DEFAULT
                );
            }

        } catch (Exception e) {

            reportUnexpectedError(e);
        }


    }

    void reportUnexpectedError(final Exception e) {

        referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.TASK, UnexpectedUIExceptionSeverity.UNSTABLE, e);
    }
}
