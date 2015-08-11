package com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch;

import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch</code>
 * is the implementation of IntraUserSearch interface.
 * And provides the methods to search for a particular intra user
 *
 * Created by natalia on 11/08/15.
 */
public class IntraUserModuleSearch implements IntraUserSearch {

    /**
     * That metod search the intra user name to search
     * @param nameToSearch Intra User name
     */
    @Override
    public void setNameToSearch(String nameToSearch) {

    }

    /**
     * That metod return a list of intra user that match the search condition
     * @return list of IntraUserInformation>
     */
    @Override
    public List<IntraUserInformation> getResult() {
        return null;
    }
}
