package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.util;

import java.io.UnsupportedEncodingException;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4SafeDecompressor;

public class DataCompressor {
	
	private static int MAX_RAW_BYTES = 1000000;
	
	public static byte[] encode(String rawString) throws UnsupportedEncodingException {
		LZ4Factory factory = LZ4Factory.fastestInstance();
		byte[] data = rawString.getBytes("UTF-8");
		LZ4Compressor compressor = factory.fastCompressor();
		byte[] compressed = compressor.compress(data);
		return compressed;
	}
	
	public static String decode(byte[] compressedBytes) throws UnsupportedEncodingException {
		LZ4Factory factory = LZ4Factory.fastestInstance();
		LZ4SafeDecompressor decompressor = factory.safeDecompressor();
		byte[] restored = decompressor.decompress(compressedBytes, MAX_RAW_BYTES);
		return new String(restored, "UTF-8");
	}
	
}
