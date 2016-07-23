package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */

public interface FermatAppConnection {

    FermatFragmentFactory getFragmentFactory();

    NavigationViewPainter getNavigationViewPainter();

    HeaderViewPainter getHeaderViewPainter();

    FooterViewPainter getFooterViewPainter();

}
