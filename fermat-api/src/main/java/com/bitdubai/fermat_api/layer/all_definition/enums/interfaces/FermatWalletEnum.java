package com.bitdubai.fermat_api.layer.all_definition.enums.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatWalletEnum</code>
 * haves the representation of the basic functionality of a Fermat Wallet Enum.
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 23/09/2015.
 */
public interface FermatWalletEnum extends FermatEnum {

    /**
     * Throw the method <code>getReferenceWallet</code> you can know to which ReferenceWallet the wallet belongs.
     *
     * @return an element of ReferenceWallet enum.
     */
    ReferenceWallet getReferenceWallet();

}
