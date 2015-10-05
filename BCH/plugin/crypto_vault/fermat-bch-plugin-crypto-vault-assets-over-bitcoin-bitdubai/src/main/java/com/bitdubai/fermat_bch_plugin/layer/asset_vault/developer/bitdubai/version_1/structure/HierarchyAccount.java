package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.google.common.collect.ImmutableList;

import org.bitcoinj.crypto.ChildNumber;

import java.util.List;

/**
 * Created by rodrigo on 10/4/15.
 */
class HierarchyAccount {
    private int id;
    private String description;
    private String pathAsString;
    private List<ChildNumber> accountPath;


    public HierarchyAccount(int id, String description) {
        this.id = id;
        this.description = description;

        /**
         * I set the account path, the m/path
         */
        this.accountPath = ImmutableList.of(new ChildNumber(id, true));
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

    public String getPathAsString() {
        return pathAsString;
    }

    public List<ChildNumber> getAccountPath() {
        return accountPath;
    }
}
