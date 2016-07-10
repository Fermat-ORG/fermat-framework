package com.bitdubai.android_core.app.common.version_1.adapters;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.03.28..
 */
public interface FermatUIAdapter<F extends AbstractFermatFragmentInterface> {

    List<F> getLstCurrentFragments();

}
