package com.bitdubai.sub_app.intra_user_community.common.concurrent;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

import java.util.List;

/**
 * Created by mati on 2015.10.17..
 */
public interface CallbackMati {

    void onPostExecute(List<IntraUserInformation> lst);

    void onException(Exception e);

}
