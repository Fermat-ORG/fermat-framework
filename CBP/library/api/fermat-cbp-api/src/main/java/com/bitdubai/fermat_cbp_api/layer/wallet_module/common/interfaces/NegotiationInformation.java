package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;


/**
 * Created by nelson on 22/09/15.
 */
public interface NegotiationInformation extends Serializable {
    Map<ClauseType, String> getNegotiationSummary();

    Map<ClauseType, ClauseInformation> getClauses();

    NegotiationStatus getStatus();

    String getMemo();

    void setMemo(String memo);

    long getLastNegotiationUpdateDate();

    void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate);

    long getNegotiationExpirationDate();

    void setNegotiationExpirationDate(long expirationDatetime);

    UUID getNegotiationId();

    void setCancelReason(String cancelReason);

    String getCancelReason();
}
