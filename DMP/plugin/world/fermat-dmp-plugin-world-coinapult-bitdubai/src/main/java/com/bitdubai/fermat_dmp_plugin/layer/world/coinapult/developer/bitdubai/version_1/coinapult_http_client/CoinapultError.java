package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client;

public class CoinapultError {

	public static class CoinapultException extends Exception {
		private static final long serialVersionUID = -5696705076981149480L;

		public CoinapultException(String message) {
			super(message);
		}
	}

	public static class CoinapultExceptionECC extends CoinapultException {
		private static final long serialVersionUID = 4251558118147850780L;

		public CoinapultExceptionECC(String message) {
			super(message);
		}
	}
}
