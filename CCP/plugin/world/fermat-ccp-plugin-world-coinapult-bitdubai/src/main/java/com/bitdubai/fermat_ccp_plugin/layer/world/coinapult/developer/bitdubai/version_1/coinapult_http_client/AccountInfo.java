package com.bitdubai.fermat_ccp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client;

import java.math.BigDecimal;
import java.util.List;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class AccountInfo {
	public static class Json extends GenericJson {
		@Key
		public String role;

		@Key
		public List<Balance> balances;
	}

	public static class Balance {
		@Key
		public String currency;

		@Key
		public BigDecimal amount;
	}
}
