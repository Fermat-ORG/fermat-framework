package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.google.gson.Gson;

public class CloudClientCommunicationMessage implements Message {
	
	private static final int HASH_PRIME_NUMBER_PRODUCT = 3733;
	private static final int HASH_PRIME_NUMBER_ADD = 6781;
	
	private String textContent;
	private MessagesStatus status;
	
	public CloudClientCommunicationMessage(final String textContent, final MessagesStatus status) {
		if(textContent == null || status == null ||textContent.isEmpty())
			throw new IllegalArgumentException();
		this.textContent = textContent;
		this.status = status;
	}
	
	@Override
	public void setTextContent(final String content) {
		if(content == null || content.isEmpty())
			throw new IllegalArgumentException();
		this.textContent = content;
	}

	@Override
	public String getTextContent() {
		return textContent;
	}

	@Override
	public MessagesStatus getStatus() {
		return status;
	}

	/**
	 * (non-Javadoc)
	 * @see Message#toJson()
	 */
	@Override
	public String toJson(){

		Gson gson = new Gson();
		return gson.toJson(this);
	}

	/**
	 * (non-Javadoc)
	 * @see Message#fromJson(String)
	 */
	@Override
	public CloudClientCommunicationMessage fromJson(String json){

		Gson gson = new Gson();
		return gson.fromJson(json, CloudClientCommunicationMessage.class);

	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Message))
			return false;
		Message compare = (Message) o;
		return textContent.equals(compare.getTextContent()) && status.equals(compare.getStatus());
	}
	
	@Override
	public int hashCode() {
		int c = 0;
		c += textContent.hashCode();
		c += status.hashCode();
		return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
	}
	
}
