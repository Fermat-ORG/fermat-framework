package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 11/08/16.
 */
public class BlockPackages implements Serializable {

    private List<Package> packages;

    public BlockPackages() {
        packages = new ArrayList<>();
    }

    public void add(Package p) {
        packages.add(p);
    }

    public int size() {
        return packages.size();
    }

    public List<Package> getPackages() {
        return packages;
    }
}
