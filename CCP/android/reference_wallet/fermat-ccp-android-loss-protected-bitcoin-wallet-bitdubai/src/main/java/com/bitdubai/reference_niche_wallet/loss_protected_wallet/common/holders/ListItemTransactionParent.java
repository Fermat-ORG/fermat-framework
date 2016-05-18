package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Gian Barboza on 16/05/16.
 */
public class ListItemTransactionParent extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private static final float PIVOT_VALUE = 0.5f;
    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    private TextView transaction_amount;
    private TextView transaction_date;
    private ImageView transaction_arrow_expand;
    private LinearLayout linear_parent_row;
    private View divider_row;

    Resources res;
    private View itemView;

    /**
     * ListItemTransactionParent constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ListItemTransactionParent(View itemView, Resources res) {
        super(itemView);

        this.res = res;
        this.itemView = itemView;

        transaction_amount = (TextView) itemView.findViewById(R.id.transaction_amount);
        transaction_date   = (TextView) itemView.findViewById(R.id.transaction_date);
        transaction_arrow_expand = (ImageView) itemView.findViewById(R.id.transaction_arrow_expand);
        linear_parent_row = (LinearLayout) itemView.findViewById(R.id.linear_parent_row);
        divider_row = (View) itemView.findViewById(R.id.divider_row);
    }


    public void bind(LossProtectedWalletTransaction data){

       transaction_amount.setText(WalletUtils.formatBalanceString(data.getAmount(), ShowMoneyType.BITCOIN.getCode())+" BTC");

       SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:ss a", Locale.US);
       transaction_date.setText("Date: "+sdf.format(data.getTimestamp()));

       linear_parent_row.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (isExpanded()) {
                   collapseView();

               }else{
                   expandView();

               }
           }
       });

   }

    @Override
    @SuppressLint("NewApi")
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (!HONEYCOMB_AND_ABOVE) {
            return;
        }

        if (expanded) {
            transaction_arrow_expand.setRotation(ROTATED_POSITION);

            divider_row.setVisibility(View.GONE);
        } else {
            transaction_arrow_expand.setRotation(INITIAL_POSITION);
            divider_row.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onExpansionToggled(boolean wasExpanded) {
        super.onExpansionToggled(wasExpanded);

        //mNumberTextView.setVisibility(wasExpanded ? View.VISIBLE : View.GONE);

        if (!HONEYCOMB_AND_ABOVE) {
            return;
        }

        RotateAnimation rotateAnimation = new RotateAnimation(
                ROTATED_POSITION, INITIAL_POSITION,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE);

        rotateAnimation.setDuration(DEFAULT_ROTATE_DURATION_MS);
        rotateAnimation.setFillAfter(true);

        transaction_arrow_expand.startAnimation(rotateAnimation);
    }
}
