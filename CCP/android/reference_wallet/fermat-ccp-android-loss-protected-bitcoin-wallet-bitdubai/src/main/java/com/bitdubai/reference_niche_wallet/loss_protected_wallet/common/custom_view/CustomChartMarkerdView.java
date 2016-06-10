package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.custom_view;

import android.content.Context;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by root on 19/05/16.
 */
public class CustomChartMarkerdView extends MarkerView {

    private TextView marker_element;
    private List<BitcoinLossProtectedWalletSpend> spend_list;
    private ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;
    private LossProtectedWallet lossProtectedWalletmanager;
    private ErrorManager errorManager;
    private Activity activity;


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomChartMarkerdView(Context context,int layoutResource, List<BitcoinLossProtectedWalletSpend> spendingList
    ,ReferenceAppFermatSession<LossProtectedWallet> session , ErrorManager error,LossProtectedWallet manager) {
        super(context, layoutResource);

        marker_element = (TextView) findViewById(R.id.marker_element);
        this.spend_list = spendingList;
        this.lossProtectedWalletSession = session;
        this.errorManager = error;
        this.lossProtectedWalletmanager = manager;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry)
        {
            CandleEntry ce =(CandleEntry) e;

            marker_element.setText("U$D " + Utils.formatNumber(ce.getHigh(), 2, true));
        }else
        {
            marker_element.setText("U$D " + Utils.formatNumber(e.getVal(), 2, true));
        }
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() /2);    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
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

            //convert satoshis to bitcoin
            final double amount = Double.parseDouble(com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString(spendingAmount, ShowMoneyType.BITCOIN.getCode()));

            //calculate the Earned/Lost in dollars of the spending value
            totalInDollars = (amount * spendingExchangeRate)-(amount * transaction.getExchangeRate());

            //return the total


            return totalInDollars;

        } catch (CantListLossProtectedTransactionsException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
