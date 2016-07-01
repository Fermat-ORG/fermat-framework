package com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount;


import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;


import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.CryptoVault.AssetsOverBitcoin.developer.bitdubai.version_1.structure.HierarchyAccount</code>
 * Defines the account object of the hierarchy. In standard BIP0032 notation, is the first derivation from the master key.
 * The asset vault account is always the first one (m/0) and new ones may be added when Redeem points are dependant on this vault.
 * New redeem points added are m/1, m/2 and so on.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class HierarchyAccount {
    private int id;
    private String description;
    private HierarchyAccountType hierarchyAccountType;
    private String pathAsString;
    private List<ChildNumber> accountPath;


    /**
     * Constructor
     * @param id the account id
     * @param description a description that identifies the account
     */
    public HierarchyAccount(int id, String description, HierarchyAccountType hierarchyAccountType) {
        this.id = id;
        this.description = description;
        this.hierarchyAccountType = hierarchyAccountType;

        /**
         * I set the account path, the m/acountId H/1
         * All keys derived will be m/0H/1/x for example
         */
        this.accountPath = ImmutableList.of(new ChildNumber(id, true), new ChildNumber(1, false));
        this.pathAsString = accountPath.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HierarchyAccountType getHierarchyAccountType() {
        return hierarchyAccountType;
    }

    public void setHierarchyAccountType(HierarchyAccountType hierarchyAccountType) {
        this.hierarchyAccountType = hierarchyAccountType;
    }

    public String getPathAsString() {
        return pathAsString;
    }

    public List<ChildNumber> getAccountPath() {
        return accountPath;
    }
}
