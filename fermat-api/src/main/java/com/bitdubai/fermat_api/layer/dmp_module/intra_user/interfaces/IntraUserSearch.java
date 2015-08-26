package com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetIntraUserSearchResult;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserSearch</code>
 * provides the methods to search for a particular intra user
 */
public interface IntraUserSearch {

    public void setNameToSearch(String nameToSearch);

    List<IntraUserInformation> getResult() throws CantGetIntraUserSearchResult;
}
