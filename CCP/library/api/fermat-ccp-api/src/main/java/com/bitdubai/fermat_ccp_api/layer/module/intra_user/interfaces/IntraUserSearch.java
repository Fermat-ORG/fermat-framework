package com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces;

import java.util.List;

/**
 * The interface <code>IntraUserSearch</code>
 * provides the methods to search for a particular intra user
 */
public interface IntraUserSearch {

    public void setNameToSearch(String nameToSearch);

    List<IntraUserInformation> getResult() throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUserSearchResult;
}
