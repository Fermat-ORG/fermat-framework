package com.bitdubai.fermat_api.layer.all_definition.enums.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum</code>
 * haves the representation of the basic functionality of a Fermat Vault Enum.
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 23/09/2015.
 */
public interface FermatVaultEnum extends FermatEnum {

    /**
     * Throw the method <code>getVaultType</code> you can know to which VaultType the vault belongs.
     *
     * @return an element of VaultType enum.
     */
    VaultType getVaultType();

    /**
     * Throw the method <code>getCryptoCurrency</code> you can know to which CryptoCurrency the vault belongs.
     *
     * @return an element of CryptoCurrency enum.
     */
    CryptoCurrency getCryptoCurrency();

}
