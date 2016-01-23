package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

/**
 *Created by Yordin Alayn on 22.01.16.
 * Based in AmountToBuyViewHolder of Star_negotiation by nelson
 */
public class OpenNegotiationAdapter extends FermatAdapter<ClauseInformation, FermatViewHolder> {

    private static final int TYPE_ITEM_SINGLE_CHOICE = 1;
    private static final int TYPE_ITEM_EXCHANGE_RATE = 3;
    private static final int TYPE_ITEM_AMOUNT_TO_BUY = 4;
    private static final int TYPE_FOOTER = 5;

    private CustomerBrokerNegotiationInformation negotiationInformation;

    public OpenNegotiationAdapter (Context context, CustomerBrokerNegotiationInformation negotiationInformation) {

        super(context);

        this.negotiationInformation = negotiationInformation;

    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return null;
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        /*switch (type) {
            case TYPE_ITEM_SINGLE_CHOICE:
                return new SingleChoiceViewHolder(itemView);

            case TYPE_ITEM_EXCHANGE_RATE:
                return new ExchangeRateViewHolder(itemView);

            case TYPE_ITEM_AMOUNT_TO_BUY:
                return new AmountToBuyViewHolder(itemView);

            case TYPE_FOOTER:
                final FooterViewHolder footerViewHolder = new FooterViewHolder(itemView);
                footerViewHolder.setListener(footerListener);
                return footerViewHolder;

            default:
                throw new IllegalArgumentException("Cant recognise the given value");
        }*/
        return  null;
    }

    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {

    }

    @Override
    protected void bindHolder(FermatViewHolder holder, ClauseInformation clause, int position) {

    }

}
