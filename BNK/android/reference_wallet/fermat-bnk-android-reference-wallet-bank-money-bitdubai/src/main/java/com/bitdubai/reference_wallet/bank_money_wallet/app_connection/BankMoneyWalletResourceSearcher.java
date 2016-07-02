package com.bitdubai.reference_wallet.bank_money_wallet.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;


/**
 * Created by nelsonalfo on 02/07/16.
 */
public class BankMoneyWalletResourceSearcher extends ResourceSearcher {

    @Override
    public int obtainResDrawable(Context context, int id) {
        switch (id) {
            case ReferenceWalletConstants.ADD_ACCOUNT_ACTION:
                return R.drawable.bw_add_icon_action_bar;

            case ReferenceWalletConstants.EDIT_ACCOUNT_ACTION:
                return R.drawable.bw_ic_action_edit;

            case ReferenceWalletConstants.SAVE_ACTION:
                return R.drawable.bw_ic_action_edit;

            case ReferenceWalletConstants.HELP_ACTION:
                return R.drawable.bw_help_icon_action_bar;

            default:
                return 0;
        }
    }
}
