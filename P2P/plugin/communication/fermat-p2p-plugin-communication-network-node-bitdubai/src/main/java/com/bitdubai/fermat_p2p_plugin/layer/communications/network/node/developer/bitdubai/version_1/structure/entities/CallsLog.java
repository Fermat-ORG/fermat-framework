package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/**
 * The persistent class for the "CALLS_LOG" database table.
 * 
 */
public class CallsLog extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID callId;

	private Timestamp callTimestamp;

	private Timestamp finishTimestamp;

	private Timestamp startTimestamp;

	private String step;

	public CallsLog() {
		super();
        this.callId = UUID.randomUUID();
	}

    public UUID getCallId() {
        return callId;
    }

    public void setCallId(UUID callId) {
        this.callId = callId;
    }

    public Timestamp getCallTimestamp() {
        return callTimestamp;
    }

    public void setCallTimestamp(Timestamp callTimestamp) {
        this.callTimestamp = callTimestamp;
    }

    public Timestamp getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(Timestamp finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String getId() {
        return callId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallsLog)) return false;
        CallsLog callsLog = (CallsLog) o;
        return Objects.equals(getCallId(), callsLog.getCallId()) &&
                Objects.equals(getCallTimestamp(), callsLog.getCallTimestamp()) &&
                Objects.equals(getFinishTimestamp(), callsLog.getFinishTimestamp()) &&
                Objects.equals(getStartTimestamp(), callsLog.getStartTimestamp()) &&
                Objects.equals(getStep(), callsLog.getStep());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCallId(), getCallTimestamp(), getFinishTimestamp(), getStartTimestamp(), getStep());
    }

    @Override
    public String toString() {
        return "CallsLog{" +
                "callId=" + callId +
                '}';
    }
}