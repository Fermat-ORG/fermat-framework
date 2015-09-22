package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentRequestItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version.RequestPaymentListItem;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class PaymentRequestPendingAdapter extends FermatAdapterNew<RequestPaymentListItem, PaymentRequestItemViewHolder> {

    //private WalletStoreItemPopupMenuListener menuClickListener;

    ViewInflater viewInflater;

    public PaymentRequestPendingAdapter(Context context, ArrayList<RequestPaymentListItem> dataSet,ViewInflater viewInflater,ResourceProviderManager resourceProviderManager) {
        super(context, dataSet,viewInflater,resourceProviderManager);
        viewInflater = viewInflater;
    }

    @Override
    protected PaymentRequestItemViewHolder createHolder(View itemView, int type) {
        return new PaymentRequestItemViewHolder(itemView,viewInflater);
    }

    @Override
    protected String getCardViewResourceName() {
        return "layout";//R.layout.wallet_store_catalog_item;
    }

    @Override
    protected void bindHolder(final PaymentRequestItemViewHolder holder, final RequestPaymentListItem data, final int position) {
//        holder.getWalletName().setText(data.getWalletName());
//        holder.getWalletIcon().setImageBitmap(data.getWalletIcon());
//        holder.getWalletPublisherName().setText("Publisher Name");
//
//        InstallationStatus installStatus = data.getInstallationStatus();
//        int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installStatus);
//        holder.getInstallStatus().setText(resId);
//
//        final ImageView menu = holder.getMenu();
//        if (menuClickListener != null) {
//            menu.setVisibility(View.VISIBLE);
//            menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    menuClickListener.onMenuItemClickListener(menu, data, position);
//                }
//            });
//        } else
//            menu.setVisibility(View.INVISIBLE);
    }

    public void setOnClickListerAcceptButton(View.OnClickListener onClickListener){

    }

    public void setOnClickListerRefuseButton(View.OnClickListener onClickListener){

    }

}
