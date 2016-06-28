package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums.QueryStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums.QueryTypes;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceQuery</code> is
 * the implementation of the message<p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceQuery implements AbstractBaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID                     id                      ;
    private final String                   broadcastCode           ;
    private final DiscoveryQueryParameters discoveryQueryParameters;
    private       long                     executionTime           ;
    private final QueryTypes               type                    ;
    private       QueryStatus              status                  ;

    public NetworkServiceQuery(final UUID                     id                      ,
                               final String                   broadcastCode           ,
                               final DiscoveryQueryParameters discoveryQueryParameters,
                               final long                     executionTime           ,
                               final QueryTypes               type                    ,
                               final QueryStatus              status                  ) {

        this.id                       = id                      ;
        this.broadcastCode            = broadcastCode           ;
        this.discoveryQueryParameters = discoveryQueryParameters;
        this.executionTime            = executionTime           ;
        this.type                     = type                    ;
        this.status                   = status                  ;
    }

    public UUID getId() {
        return id;
    }

    public String getBroadcastCode() {
        return broadcastCode;
    }

    public DiscoveryQueryParameters getDiscoveryQueryParameters() {
        return discoveryQueryParameters;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public QueryTypes getType() {
        return type;
    }

    public QueryStatus getStatus() {
        return status;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public void setStatus(QueryStatus status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkServiceQuery)) return false;
        NetworkServiceQuery that = (NetworkServiceQuery) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "NetworkServiceQuery{" +
                "id=" + id +
                ", broadcastCode='" + broadcastCode + '\'' +
                ", discoveryQueryParameters=" + discoveryQueryParameters +
                ", executionTime=" + executionTime +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
