package com.bitdubai.reference_niche_wallet.fermat_wallet.interfaces;

/**
 * Created by root on 04/07/16.
 */

import com.bitdubai.reference_niche_wallet.fermat_wallet.common.custom_view.WheelView;

/**
 * Wheel changed listener interface.
 * <p>The onChanged() method is called whenever current spinnerwheel positions is changed:
 * <li> New Wheel position is set
 * <li> Wheel view is scrolled
 */
public interface OnWheelChangedListener {
    /**
     * Callback method to be invoked when current item changed
     * @param wheel the spinnerwheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
