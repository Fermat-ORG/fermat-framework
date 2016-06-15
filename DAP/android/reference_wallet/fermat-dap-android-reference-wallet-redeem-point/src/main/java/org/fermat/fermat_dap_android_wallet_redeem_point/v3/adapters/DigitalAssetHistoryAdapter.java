package org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.v3.filters.DigitalAssetHistoryAdapterFilter;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.holders.DigitalAssetHistoryItemViewHolder;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.models.DigitalAssetHistory;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.util.Utils;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Penny on 19/04/16.
 */
public class DigitalAssetHistoryAdapter extends FermatAdapter<DigitalAssetHistory, DigitalAssetHistoryItemViewHolder> implements Filterable {

    private AssetRedeemPointWalletSubAppModule manager;
    private LinkedHashMap<String, Integer> mapIndex;
    private LinkedHashMap<String, Integer> mapAssetQuantity;
    List<DigitalAssetHistory> allDigitalAssets;

    public DigitalAssetHistoryAdapter(Context context, List<DigitalAssetHistory> dataSet, AssetRedeemPointWalletSubAppModule manager) {
        super(context, dataSet);
        this.manager = manager;
        this.allDigitalAssets = dataSet;
        fillSections();
    }

    private void fillSections() {
        if (dataSet != null) {
            mapIndex = new LinkedHashMap<String, Integer>();
            mapAssetQuantity = new LinkedHashMap<String, Integer>();

            for (int x = 0; x < dataSet.size(); x++) {
                String dateAccepted = Utils.getTimeAgoHistory(dataSet.get(x).getAcceptedDate().getTime());
                if (dateAccepted != null) {
                    if (!mapIndex.containsKey(dateAccepted)) {
                        mapIndex.put(dateAccepted, x);
                        mapAssetQuantity.put(dateAccepted, 1);
                    } else {
                        mapAssetQuantity.put(dateAccepted, mapAssetQuantity.get(dateAccepted) + 1);
                    }
                }
            }
        }
    }

    @Override
    protected DigitalAssetHistoryItemViewHolder createHolder(View itemView, int type) {
        return new DigitalAssetHistoryItemViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_redeem_point_asset_user_history_item;
    }

    @Override
    protected void bindHolder(DigitalAssetHistoryItemViewHolder holder, DigitalAssetHistory data, int position) {
        holder.bind(data, Utils.getTimeAgoHistory(data.getAcceptedDate().getTime()), mapAssetQuantity.get(Utils.getTimeAgoHistory(data.getAcceptedDate().getTime())), mapIndex.get(Utils.getTimeAgoHistory(data.getAcceptedDate().getTime())) == position);
    }

    @Override
    public Filter getFilter() {
        return new DigitalAssetHistoryAdapterFilter(this.allDigitalAssets, this);
    }

    @Override
    public void changeDataSet(List<DigitalAssetHistory> dataSet) {
        super.changeDataSet(dataSet);
        fillSections();
    }
}
