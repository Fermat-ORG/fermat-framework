package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the "CHECKED_IN_CLIENTS" database table.
 * 
 */
@Entity
@Table(name="\"CHECKED_IN_CLIENTS\"")
@NamedQuery(name="CheckedInClient.findAll", query="SELECT c FROM CheckedInClient c")
public class CheckedInClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"IDENTITY_PUBLIC_KEY\"", unique=true, nullable=false, length=255)
	private String identityPublicKey;

	@Column(name="\"CHECKED_IN_TIMESTAMP\"", nullable=false)
	private Timestamp checkedInTimestamp;

	@Column(name="\"DEVICE_TYPE\"", nullable=false, length=50)
	private String deviceType;

	@Column(name="\"LATITUDE\"", nullable=false)
	private double latitude;

	@Column(name="\"LONGITUDE\"", nullable=false)
	private double longitude;

	//bi-directional many-to-one association to CheckedInNetworkService
	@OneToMany(mappedBy="checkedInClient")
	private List<CheckedInNetworkService> checkedInNetworkServices;

	public CheckedInClient() {
	}

	public String getIdentityPublicKey() {
		return this.identityPublicKey;
	}

	public void setIdentityPublicKey(String identityPublicKey) {
		this.identityPublicKey = identityPublicKey;
	}

	public Timestamp getCheckedInTimestamp() {
		return this.checkedInTimestamp;
	}

	public void setCheckedInTimestamp(Timestamp checkedInTimestamp) {
		this.checkedInTimestamp = checkedInTimestamp;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<CheckedInNetworkService> getCheckedInNetworkServices() {
		return this.checkedInNetworkServices;
	}

	public void setCheckedInNetworkServices(List<CheckedInNetworkService> checkedInNetworkServices) {
		this.checkedInNetworkServices = checkedInNetworkServices;
	}

	public CheckedInNetworkService addCheckedInNetworkService(CheckedInNetworkService checkedInNetworkService) {
		getCheckedInNetworkServices().add(checkedInNetworkService);
		checkedInNetworkService.setCheckedInClient(this);

		return checkedInNetworkService;
	}

	public CheckedInNetworkService removeCheckedInNetworkService(CheckedInNetworkService checkedInNetworkService) {
		getCheckedInNetworkServices().remove(checkedInNetworkService);
		checkedInNetworkService.setCheckedInClient(null);

		return checkedInNetworkService;
	}

}