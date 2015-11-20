package com.bitdubai.sub_app.wallet_publisher.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.holders.WalletItemViewHolder;
import com.bitdubai.sub_app.wallet_publisher.interfaces.PopupMenu;

import java.util.ArrayList;

/**
 * Wallet Factory Projects Adapter
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class WalletFactoryProjectsAdapter extends FermatAdapter<WalletFactoryProject, WalletItemViewHolder> {

    private PopupMenu onMenuClickListener;

    public WalletFactoryProjectsAdapter(Context context) {
        super(context);
    }

    public WalletFactoryProjectsAdapter(Context context, ArrayList<WalletFactoryProject> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected WalletItemViewHolder createHolder(View itemView, int type) {
        return new WalletItemViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.wfp_item;
    }

    @Override
    protected void bindHolder(final WalletItemViewHolder holder, final WalletFactoryProject data, final int position) {
        if (data != null) {
            holder.title.setText(data.getName() != null ? data.getName() : "No name given");
            holder.description.setText(data.getDescription() != null ? data.getDescription() : "");
            if (data.getFactoryProjectType() != null) {
                switch (data.getFactoryProjectType()) {
                    case WALLET:
                        holder.type.setText(context.getString(R.string.main_factory_project_type_wallet));
                        break;
                    case SKIN:
                        holder.type.setText(context.getString(R.string.main_factory_project_type_skin));
                        break;
                    case LANGUAGE:
                        holder.type.setText(context.getString(R.string.main_factory_project_type_language));
                        break;
                }
            }
            if (onMenuClickListener != null) {
                holder.menu.setVisibility(View.VISIBLE);
                holder.menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onMenuClickListener.onMenuItemClickListener(holder.menu, data, position);
                    }
                });
            } else
                holder.menu.setVisibility(View.INVISIBLE);
        }
    }

    public void setOnMenuClickListener(PopupMenu onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }
}
