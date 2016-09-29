package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;


/**
 * Created by Matias Furszyfer 22/09/2015
 * updated by Andres Abreu aabreu1 2016.09.08..
 */


public class PaymentHistoryItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;

    private String feeLevel = "NORMAL";

    private LinearLayout linear_layour_container_state;
    private TextView txt_state;

    private LossProtectedWallet wallet;
    private ReferenceAppFermatSession referenceWalletSession;
    private Resources res;
    private Typeface tf;
    private BlockchainNetworkType blockchainNetworkType;
    private Context context;

    private LinearLayout linear_layour_container_buttons;
    private Button btn_refuse_request;
    private Button btn_accept_request;
    PaymentRequestHistoryAdapter adapter;


    public PaymentHistoryItemViewHolder(View itemView,int holderType,Context context, LossProtectedWallet wallet, ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession, PaymentRequestHistoryAdapter adapter) {
        super(itemView,holderType);

        res = itemView.getResources();
        this.context = context;
        this.wallet = wallet;
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


    //tf = Typeface.createFromAsset(res.getAssets(), "fonts/Roboto-Regular.ttf");
}

    public void bind( final LossProtectedPaymentRequest data){

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

       // txt_contactName.setTypeface(tf);

        txt_notes.setText(data.getReason());
        //txt_notes.setTypeface(tf);

        txt_time.setText(data.getDate() + " hs");
        //txt_time.setTypeface(tf);

        String state = "";

        switch (data.getState()){
            case WAITING_RECEPTION_CONFIRMATION:
                //state = "Waiting for response";
                state = context.getResources().getString(R.string.waiting_receive_text);

                break;
            case APPROVED:
                //state = "Accepted";
                state = context.getResources().getString(R.string.accepted_text);
                break;
            case PAID:
                //state = "Paid";
                state = context.getResources().getString(R.string.Paid_text);
                break;
            case PENDING_RESPONSE:
                //state = "Pending response";
                state = context.getResources().getString(R.string.Pending_response);
                break;
            case ERROR:
                // state = "Error";
                state = context.getResources().getString(R.string.Error);
                break;
            case NOT_SENT_YET:
                //state = "Not sent yet";
                state = context.getResources().getString(R.string.Not_sent_yet);
                break;
            case PAYMENT_PROCESS_STARTED:
                //state = "Payment process started";
                state = context.getResources().getString(R.string.Payment_process_started);
                break;
            case DENIED_BY_INCOMPATIBILITY:
                //state = "Denied by incompatibility";
                state = context.getResources().getString(R.string.Denied_by_incompatibility);
                break;
            case IN_APPROVING_PROCESS:
                //state = "In approving process";
                state = context.getResources().getString(R.string.In_approving_process);
                break;
            case REFUSED:
                //state = "Denied";
                state = context.getResources().getString(R.string.denied);
                break;
            default:
                // state = "Error, contact with support";
                state = context.getResources().getString(R.string.Error_contact_with_support);
                break;

        }


        if(data.getType() == 0) //SEND
        {
            linear_layour_container_buttons.setVisibility(View.GONE);
            linear_layour_container_state.setVisibility(View.VISIBLE);
            txt_state.setText(state);
           // txt_state.setTypeface(tf);
        }
        else
        {
            if(data.getState().equals(CryptoPaymentState.APPROVED) || data.getState().equals(CryptoPaymentState.REFUSED)) {
                linear_layour_container_buttons.setVisibility(View.GONE);
                linear_layour_container_state.setVisibility(View.VISIBLE);

                txt_state.setText(state);
               // txt_state.setTypeface(tf);
            }
            else
            {
                linear_layour_container_buttons.setVisibility(View.VISIBLE);
                linear_layour_container_state.setVisibility(View.GONE);

                txt_state.setText(state);
               // txt_state.setTypeface(tf);
            }
        }



        btn_accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //check amount + fee less than balance
                    long availableBalance = wallet.getBalance(BalanceType.AVAILABLE, referenceWalletSession.getAppPublicKey(), blockchainNetworkType,String.valueOf(referenceWalletSession.getData(SessionConstant.ACTUAL_EXCHANGE_RATE)));
                    if ((data.getAmount() + BitcoinFee.valueOf(feeLevel).getFee()) < availableBalance) {
                        wallet.approveRequest(data.getRequestId()
                                , wallet.getSelectedActorIdentity().getPublicKey());
                        Toast.makeText(context, context.getResources().getString(R.string.Request_accepted), Toast.LENGTH_SHORT).show();
                        adapter.refresh();
                    } else
                    showMessage(context, context.getResources().getString(R.string.Insufficient_funds));
                } catch (Exception e) {
                    showMessage(context, context.getResources().getString(R.string.Cant_accept)+" " + e.getMessage());

                }

            }
        });

        btn_refuse_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    wallet.refuseRequest(data.getRequestId());
                    Toast.makeText(context, context.getResources().getString(R.string.Request_denied), Toast.LENGTH_SHORT).show();
                    adapter.refresh();
                    //notifyDataSetChanged();
                } catch (Exception e) {
                    showMessage(context, context.getResources().getString(R.string.Cant_accept)+ e.getMessage());
                }
            }
        });
    }

}
