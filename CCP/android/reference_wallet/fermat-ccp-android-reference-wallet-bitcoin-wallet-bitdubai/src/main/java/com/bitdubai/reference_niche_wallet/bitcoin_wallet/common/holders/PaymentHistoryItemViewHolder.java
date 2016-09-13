package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class PaymentHistoryItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;

    private String feeLevel = "SLOW";

    private LinearLayout linear_layour_container_state;
    private TextView txt_state;

    private LinearLayout linear_layour_container_buttons;
    public Button btn_refuse_request;
    public Button btn_accept_request;

    private CryptoWallet cryptoWallet;
    private ReferenceAppFermatSession referenceWalletSession;
    private Resources res;
    private Typeface tf;
    private BlockchainNetworkType blockchainNetworkType;
    private Context context;
    PaymentRequestHistoryAdapter adapter;



    public PaymentHistoryItemViewHolder(View itemView,int holderType,Context context, CryptoWallet cryptoWallet, ReferenceAppFermatSession<CryptoWallet> referenceWalletSession, PaymentRequestHistoryAdapter adapter) {
        super(itemView,holderType);

        res = itemView.getResources();
        this.context = context;
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession = referenceWalletSession;
        this.adapter = adapter;


        contactIcon = (ImageView) itemView.findViewById(R.id.profile_Image);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        txt_state = (TextView) itemView.findViewById(R.id.txt_state);
        btn_refuse_request = (Button) itemView.findViewById(R.id.btn_refuse_request);
        btn_accept_request = (Button) itemView.findViewById(R.id.btn_accept_request);
        linear_layour_container_state = (LinearLayout) itemView.findViewById(R.id.linear_layour_container_state);
        linear_layour_container_buttons = (LinearLayout) itemView.findViewById(R.id.linear_layour_container_buttons);


        try {


            if(referenceWalletSession.getData(SessionConstant.BLOCKCHANIN_TYPE) != null)
                blockchainNetworkType = (BlockchainNetworkType)referenceWalletSession.getData(SessionConstant.BLOCKCHANIN_TYPE);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

            if(referenceWalletSession.getData(SessionConstant.FEE_LEVEL) != null)
                feeLevel = (String)referenceWalletSession.getData(SessionConstant.FEE_LEVEL);
            else
                feeLevel = BitcoinFee.NORMAL.toString();


        }
        catch (Exception e) {
            e.printStackTrace();
        }


        tf = Typeface.createFromAsset(res.getAssets(), "fonts/Roboto-Regular.ttf");

    }

    public void bind( final PaymentRequest data){

        try {
            contactIcon.setImageDrawable(ImagesUtils.getRoundedBitmap(res, data.getContact().getProfilePicture()));
        }catch (Exception e){
            contactIcon.setImageDrawable(ImagesUtils.getRoundedBitmap(res, R.drawable.ic_profile_male));
        }

        txt_amount.setText(formatBalanceString(data.getAmount(), ((ShowMoneyType) referenceWalletSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED)).getCode()));
        txt_amount.setTypeface(tf) ;

        if(data.getContact() != null)
            txt_contactName.setText(data.getContact().getActorName());
        else
            txt_contactName.setText("Unknown");

        txt_contactName.setTypeface(tf);

        txt_notes.setText(data.getReason());
        txt_notes.setTypeface(tf);

        txt_time.setText(data.getDate() + " hs");
        txt_time.setTypeface(tf);

        String state = "";
        switch (data.getState()){
            case WAITING_RECEPTION_CONFIRMATION:
                state = res.getString(R.string.pr_status_1); //"Waiting for response";
                break;
            case APPROVED:
                state = res.getString(R.string.pr_status_2); //"Accepted";
                break;
            case PAID:
                state = res.getString(R.string.pr_status_3); //"Paid";
                break;
            case PENDING_RESPONSE:
                state = res.getString(R.string.pr_status_4); //"Pending response";
                break;
            case ERROR:
                state = res.getString(R.string.pr_status_5); //"Error";
                break;
            case NOT_SENT_YET:
                state = res.getString(R.string.pr_status_6); //"Not sent yet";
                break;
            case PAYMENT_PROCESS_STARTED:
                state = res.getString(R.string.pr_status_7); //"Payment process started";
                break;
            case DENIED_BY_INCOMPATIBILITY:
                state = res.getString(R.string.pr_status_8); //"Denied by incompatibility";
                break;
            case IN_APPROVING_PROCESS:
                state = res.getString(R.string.pr_status_9); //"In approving process";
                break;
            case REFUSED:
                state = res.getString(R.string.pr_status_10); //"Denied";
                break;
            default:
                state = res.getString(R.string.pr_status_11); //"Error, contact with support";
                break;

        }


        if(data.getType() == 0) //SEND
        {
            linear_layour_container_buttons.setVisibility(View.GONE);
            linear_layour_container_state.setVisibility(View.VISIBLE);
            txt_state.setText(state);
            txt_state.setTypeface(tf);
        }
        else
        {
            if(data.getState().equals(CryptoPaymentState.APPROVED) || data.getState().equals(CryptoPaymentState.REFUSED)) {
                linear_layour_container_buttons.setVisibility(View.GONE);
                linear_layour_container_state.setVisibility(View.VISIBLE);

                txt_state.setText(state);
                txt_state.setTypeface(tf);
            }
            else
            {
                linear_layour_container_buttons.setVisibility(View.VISIBLE);
                linear_layour_container_state.setVisibility(View.GONE);

                txt_state.setText(state);
                txt_state.setTypeface(tf);
            }
        }



        btn_accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //check amount + fee less than balance
                    long availableBalance = cryptoWallet.getBalance(com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(), blockchainNetworkType);
                    if ((data.getAmount() + BitcoinFee.valueOf(feeLevel).getFee()) < availableBalance) {
                        cryptoWallet.approveRequest(data.getRequestId()
                                , cryptoWallet.getSelectedActorIdentity().getPublicKey(),
                                BitcoinFee.valueOf(feeLevel).getFee(), FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS);
                        Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
                        adapter.refresh();
                    } else
                        showMessage(context, "Insufficient funds - Can't Accept Receive Payment");
                } catch (Exception e) {
                    showMessage(context, "Cant Accept Receive Payment Exception- " + e.getMessage());
                }

            }
        });

        btn_refuse_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cryptoWallet.refuseRequest(data.getRequestId());
                    Toast.makeText(context, "Request denied", Toast.LENGTH_SHORT).show();
                    adapter.refresh();
                } catch (Exception e) {
                    showMessage(context, "Cant Denied Receive Payment Exception- " + e.getMessage());
                }
            }
        });
    }


}
