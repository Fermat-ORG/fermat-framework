package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by Dev Dan on 31/05/16.
 */

public class ChartDetailDialog extends Dialog implements View.OnClickListener
{

    private LossProtectedWallet lossProtectedWalletmanager;
    private ErrorManager errorManager;
    private ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;

    public Activity activity;
    public Dialog d;
    private BitcoinLossProtectedWalletSpend spending;
    private int EARN_AND_LOST_MAX_DECIMAL_FORMAT = 6;
    private int EARN_AND_LOST_MIN_DECIMAL_FORMAT = 2;

    private ImageView earnOrLostImage;

    private Typeface tf;

    /**
     *  UI components
     */

    private Button button;
    private TextView txt_date;
    private TextView txt_amount;



    public ChartDetailDialog(Activity a,
                             BitcoinLossProtectedWalletSpend spending,
                             LossProtectedWallet lossProtectedWalletmanager,
                             ErrorManager errorManager,
                             ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession)
    {
        super(a);
        this.activity = a;
        this.spending=spending;
        this.lossProtectedWalletmanager = lossProtectedWalletmanager;
        this.errorManager = errorManager;
        this.lossProtectedWalletSession = lossProtectedWalletSession;
        this.tf = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Regular.ttf");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
    }

    private void setUpScreenComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loss_detail);

        button = (Button) findViewById(R.id.accept_btn);
        txt_date = (TextView) findViewById(R.id.date);
        txt_amount =(TextView) findViewById(R.id.amount);
        earnOrLostImage = (ImageView) findViewById(R.id.earnOrLostImage);
        button.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a ", Locale.US);


        txt_date.setText(sdf.format(spending.getTimestamp()));
        txt_date.setTypeface(tf);

        final double amount = getEarnOrLostOfSpending(
                spending.getAmount(),
                spending.getExchangeRate(),
                spending.getTransactionId());
        //txt_amount.setText(WalletUtils.formatAmountString(amount));

        if (amount > 0){

            txt_amount.setText("USD "+WalletUtils.formatAmountStringWithDecimalEntry(
                            amount,
                            EARN_AND_LOST_MAX_DECIMAL_FORMAT,
                            EARN_AND_LOST_MIN_DECIMAL_FORMAT)+" earned");

            earnOrLostImage.setBackgroundResource(R.drawable.earning_icon);

        }else if (amount== 0){

            txt_amount.setText("USD 0.00 earned");

        }else if (amount< 0){

            txt_amount.setText("USD "+WalletUtils.formatAmountStringWithDecimalEntry(
                    amount,
                    EARN_AND_LOST_MAX_DECIMAL_FORMAT,
                    EARN_AND_LOST_MIN_DECIMAL_FORMAT)+" lost");

            earnOrLostImage.setBackgroundResource(R.drawable.lost_icon);
        }
        txt_amount.setTypeface(tf);

    }


    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.accept_btn){
            dismiss();
        }
    }


    private double getEarnOrLostOfSpending(long spendingAmount,double spendingExchangeRate, UUID transactionId){

        double totalInDollars = 0;

        try{

            //get the intra user login identity
            ActiveActorIdentityInformation intraUserLoginIdentity = lossProtectedWalletmanager.getSelectedActorIdentity();
            String intraUserPk = null;
            if (intraUserLoginIdentity != null) {
                intraUserPk = intraUserLoginIdentity.getPublicKey();
            }

            //Get the transaction of this Spending
            LossProtectedWalletTransaction transaction = lossProtectedWalletmanager.getTransaction(
                    transactionId,
                    lossProtectedWalletSession.getAppPublicKey(),
                    intraUserPk);

            DecimalFormatSymbols separator = new DecimalFormatSymbols();
            separator.setDecimalSeparator('.');
            DecimalFormat form = new DecimalFormat("##########.######",separator);

            //convert satoshis to bitcoin
            String monto = WalletUtils.formatBalanceString(Long.parseLong(form.format(spendingAmount).replace(',', '.')), ShowMoneyType.BITCOIN.getCode());


            final double amount = Double.parseDouble(monto.replace(',','.'));

            //calculate the Earned/Lost in dollars of the spending value
            totalInDollars = (amount * spendingExchangeRate)-(amount * transaction.getExchangeRate());

            //return the total


            return totalInDollars;

        } catch (CantListLossProtectedTransactionsException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(activity, "CantListLossProtectedTransactionsException",
                    Toast.LENGTH_SHORT).show();
        } catch (CantGetSelectedActorIdentityException e) {
            makeText(activity, "CantGetSelectedActorIdentityException", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            makeText(activity, "ActorIdentityNotSelectedException", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
        }
        return 0;
    }



}
