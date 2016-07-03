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
    public int obtainResDrawable(Context context,int id){
        int res = 0;
        switch (id) {
            case ReferenceWalletConstants.ADD_ACCOUNT_ACTION:
                res = R.drawable.bw_add_icon_action_bar;
                break;

            case ReferenceWalletConstants.EDIT_ACCOUNT_ACTION:
                res = R.drawable.bw_ic_action_edit;
                break;

            case ReferenceWalletConstants.SAVE_ACTION:
                res = R.drawable.bw_ic_action_edit;
                break;

            case ReferenceWalletConstants.HELP_ACTION:
                res = R.drawable.bw_help_icon_action_bar;
                break;

            default:
                res = 0;
                break;
        }
        return res;
    }
}
