package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the "CALLS_LOG" database table.
 * 
 */
@Entity
@Table(name="\"CALLS_LOG\"")
@NamedQuery(name="CallsLog.findAll", query="SELECT c FROM CallsLog c")
public class CallsLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"ID\"", unique=true, nullable=false)
	private Integer id;

	@Column(name="\"CALL_ID\"", nullable=false, length=100)
	private String callId;

	@Column(name="\"CALL_TIMESTAMP\"", nullable=false)
	private Timestamp callTimestamp;

	@Column(name="\"FINISH_TIMESTAMP\"", nullable=false)
	private Timestamp finishTimestamp;

	@Column(name="\"START_TIMESTAMP\"", nullable=false)
	private Timestamp startTimestamp;

	@Column(name="\"STEP\"", nullable=false, length=12)
	private String step;

	public CallsLog() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Timestamp getCallTimestamp() {
		return this.callTimestamp;
	}

	public void setCallTimestamp(Timestamp callTimestamp) {
		this.callTimestamp = callTimestamp;
	}

	public Timestamp getFinishTimestamp() {
		return this.finishTimestamp;
	}

	public void setFinishTimestamp(Timestamp finishTimestamp) {
		this.finishTimestamp = finishTimestamp;
	}

	public Timestamp getStartTimestamp() {
		return this.startTimestamp;
	}

	public void setStartTimestamp(Timestamp startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public String getStep() {
		return this.step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}