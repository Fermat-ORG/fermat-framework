package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.mnemonic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetMnemonicTextException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;


/**
 * Created by mati on 2016.02.20..
 */
public class MnemonicFragment extends AbstractFermatFragment {

    /**
     * Platform
     */
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;
    private ReferenceWalletSession referenceWalletSession;

    //constuctor
    public static MnemonicFragment newInstance() {
        return new MnemonicFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            referenceWalletSession = (ReferenceWalletSession) appSession;
            cryptoWallet = referenceWalletSession.getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        

        View view = inflater.inflate(R.layout.bitcoin_mnemonic_fragment_main,container,false);

        final EditText txt_mnemonic = (EditText)view.findViewById(R.id.txt_mnemonic);
       final EditText mail = (EditText ) view.findViewById(R.id.text_mail);
        FermatButton send_button= (FermatButton)view.findViewById(R.id.send_button);
        final EditText encriptKey = (EditText ) view.findViewById(R.id.text_key);


        try {
            //get mnemonic text from Crypto Wallet Module
            String MnemonicText = "";
            for(int x=0;x<cryptoWallet.getMnemonicText().size();x++) {
                MnemonicText += cryptoWallet.getMnemonicText().get(x)+" ";
            }
            txt_mnemonic.setText(MnemonicText);

        } catch (CantGetMnemonicTextException e) {
            Toast.makeText(getActivity(), "Error Getting Mnemonic Text", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //send mail
                //encript mnemonic text to send by mail
                try {
                    sendMail(mail.getText().toString(), txt_mnemonic.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });
        return view;
    }
}
