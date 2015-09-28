package functional.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.mocks;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;

/**
 * Created by jorgeejgonzalez on 27/04/15.
 */
public class MockNIOClient implements Runnable {
	
	private final String serverHost;
	private final int serverPort;
	
	private Selector selector;
	private SocketChannel channel;
	private SelectionKey selectionKey;
	
	private StringBuffer inputMessage;
	private String outputMessage;
	private FMPPacket responsePacket;
		
	
	public MockNIOClient(final String serverHost, final int serverPort) throws ConnectException, IOException {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.selector = Selector.open();
		this.channel = SocketChannel.open();
		this.inputMessage = new StringBuffer("");
		this.outputMessage="";
		initializeChannel();
	}
	
	private void initializeChannel() throws ConnectException, IOException{		
		channel.configureBlocking(false);		
		channel.register(selector, SelectionKey.OP_CONNECT);
		channel.connect(new InetSocketAddress(serverHost, serverPort));
		iterateKeys();
	}

    @Override
    public void run(){
        try{
            iterateKeys();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
	
	private void iterateKeys() throws ConnectException, IOException {
		selector.select(100);
		Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
		while(keys.hasNext()){
			SelectionKey key = keys.next();
			
			if(!key.isValid())
				continue;					
			
			if(key.isConnectable())
				connectToKey(key);
			
			if(key.isReadable())
				readFromKey(key);
			
			if(key.isWritable())
				writeToKey(key);
		}
	}
	
	public void connectToKey(SelectionKey key) throws ConnectException, IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		if(socketChannel.isConnectionPending())
			socketChannel.finishConnect();
		socketChannel.configureBlocking(false);
		selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
	}

	public void readFromKey(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(FMPPacket.PACKET_MAX_BYTE_SIZE);
		readBuffer.clear();
		int length;		
		try{
			length = socketChannel.read(readBuffer);
		} catch(IOException ex){
			 key.cancel();
             socketChannel.close();
             return;
		}
		if(length==-1){
			socketChannel.close();
			key.cancel();
			return;
		}
		readBuffer.flip();
		byte[] data = new byte[FMPPacket.PACKET_MAX_BYTE_SIZE];
		readBuffer.get(data,0,length);
		StringBuffer stringBuffer = new StringBuffer(new String(data,"UTF-8"));
        if(!stringBuffer.toString().trim().isEmpty())
            try{
                responsePacket = FMPPacketFactory.constructCloudFMPPacket(stringBuffer.toString().trim());
            } catch(FMPException ex){
                ex.printStackTrace();
            }

	}

	public void writeToKey(SelectionKey key) throws IOException {
		String message = inputMessage.toString();
		if(!message.isEmpty()){
			SocketChannel socketChannel = (SocketChannel)key.channel();
			socketChannel.write(ByteBuffer.wrap(message.getBytes("UTF-8")));
			inputMessage = new StringBuffer("");
			key.interestOps(SelectionKey.OP_READ);
		}
	}
	
	public boolean isConnected(){
		return channel.isConnected();
	}
	
	public void sendMessage(final FMPPacket packet) throws IOException {		
		inputMessage = new StringBuffer(packet.toString());		
		selectionKey.interestOps(SelectionKey.OP_WRITE);
		iterateKeys();
        iterateKeys();
	}
	
	public String getMessage(){
		return outputMessage;
	}
	
	public FMPPacket getResponse() throws IOException{
        iterateKeys();
        FMPPacket response = responsePacket;
        responsePacket = null;
		return response;
	}
	
}
