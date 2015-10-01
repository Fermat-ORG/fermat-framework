/*
 * @#CloudClientCommunicationChannelPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.wifi.developer.bitdubai.version_1;



import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.wifi.CommunicationWifiInit;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.wifi.exceptions.WifiCommunicationException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Pattern;


public class WifiCommunicationPluginRoot implements CommunicationWifiInit, Plugin, Service{

	private UUID pluginId;


	private static String direccion = null;
	private static String maskara = null;


	@Override
	public List<String> lista_ipaddres_neighbors() {

		List<String> lista_red = new ArrayList<String>();
		List<String> vecinos = new ArrayList<String>();


		// lista de ip locales
		lista_red = ip_network();


		//lista de negihbors corriendo la aplicacion
		vecinos = negihbors(lista_red);


		return vecinos;
	}


	//se les pasa la lista de red local y retorna la lista de neghbors
	//corriendo la aplicacion
	private static List<String> negihbors(List<String> lista_red){

		List<String> vecinos = new ArrayList<String>();

		if(lista_red!= null){

			JsonObject obj=new JsonObject();

			obj.addProperty("message", "ready");
			obj.addProperty("direccion_source", direccion);

			int i=0;

			for(String red : lista_red){

				Socket client =null;
				BufferedReader is=null;
				PrintWriter os=null;
				String lectura=null;

				try{

					System.out.println("Calling: "+red);

					client = new Socket(red, 9001);


					is=new BufferedReader(new InputStreamReader(client.getInputStream()));

					os = new PrintWriter(client.getOutputStream());

					os.println(obj.toString());
					os.flush();

					lectura = (String) is.readLine();

					if(lectura != null){



						System.out.println(lectura);


						JsonParser parser = new JsonParser();

						JsonObject respuesta= (JsonObject) parser.parse(lectura).getAsJsonObject();

						String mensaje = (String) respuesta.get("message").getAsString();

						if(mensaje!=null && mensaje.equalsIgnoreCase("OK")){

							System.out.println("Conecta OK!");

							vecinos.add(red);

							is.close();
							os.close();
							client.close();
							continue;

						}


					}else{

						is.close();
						os.close();
						client.close();
					}

			         /*
			        	try{
							Thread.sleep(100);
						} catch (InterruptedException e) {




							continue;
						}

			        */





				}catch(Exception e){
					try{
						is.close();
						os.close();
						client.close();
					}catch(Exception ex){

					}

					continue;

				}





			}


		}


		return vecinos;



	}



	// toma la lista de ip locales
	private static List<String> ip_network(){
		List<String> lista_red = new ArrayList<String>();

		Enumeration<NetworkInterface> en = null;
		try {

			en = NetworkInterface.getNetworkInterfaces();

		} catch (SocketException e) {

			e.printStackTrace();
		}

		while (en.hasMoreElements()) {

			NetworkInterface ni = en.nextElement();

			printParameter(ni);

		}



			int bloque1,bloque2,bloque3,bloque4;
			int bloquemas1,bloquemas2,bloquemas3,bloquemas4;
			int ini1 = 0,ini2 = 0,ini3 = 0,ini4 = 0;
			int fin1 = 0,fin2 = 0,fin3 = 0,fin4 = 0;



		 	String dir= direccion.trim();
		 	String mas= maskara.trim();


				if(dir != null){

							System.out.println("Source "+dir);
							System.out.println("---------------");

							String[] parts = dir.split(Pattern.quote("."));
							String[] parts_mask = mas.split(Pattern.quote("."));


							bloque1= Integer.parseInt(parts[0]);
							bloque2=Integer.parseInt(parts[1]);
							bloque3=Integer.parseInt(parts[2]);
							bloque4=Integer.parseInt(parts[3]);

							bloquemas1=Integer.parseInt(parts_mask[0]);
							bloquemas2=Integer.parseInt(parts_mask[1]);
							bloquemas3=Integer.parseInt(parts_mask[2]);
							bloquemas4=Integer.parseInt(parts_mask[3]);
							//bloquemas4=240;


							if(bloquemas1==255){

								ini1=bloque1;
								fin1=bloque1;

								if(bloquemas2==255){
									ini2=bloque2;
									fin2=bloque2;

									if(bloquemas3==255){


										ini3=bloque3;
										fin3=bloque3;

										//barra 24

										if(bloquemas4==255){

											//quiere decir que es broadcast

										}else{

											if(bloquemas4==0){


												ini4=1;
												fin4=254;

											}else{
												//es una subnet


												int red= bloquemas4 & bloque4;

												ini4 = red + 1;
												fin4 = (red + (255-bloquemas4)) - 1;

											}

										}


									}else{
										//sino del bloquemas3
										//barra 16

										if(bloquemas3==0){
											//barra 16

											ini3=0; fin3=255;
											ini4=1; fin4=254;

										}else{


											int red= bloquemas3 & bloque3;

											ini3 = red;
											fin3 = (red + (255-bloquemas3));
											ini4=1; fin4=254;
										}



									}



								}else{


									if(bloquemas2==0){

										ini2=0; fin2=255;
										ini3=0; fin3=255;
										ini4=1; fin4=254;

									}else{


										int red= bloquemas2 & bloque2;

										ini2 = red;
										fin2 = (red + (255-bloquemas2));

										ini3=0; fin3=255;
										ini4=1; fin4=254;




									}


								}



							}

							String cad1,cad2,cad3;

							String destino;

							for(int bloq_1=ini1;bloq_1<=fin1;bloq_1++){

								cad1 = bloq_1+".";

								for(int bloq_2=ini2;bloq_2<=fin2;bloq_2++){

									cad2 = bloq_2+".";


									for(int bloq_3=ini3;bloq_3<=fin3;bloq_3++){

										cad3= bloq_3+".";


										for(int bloq_4=ini4;bloq_4<=fin4;bloq_4++){

											destino=cad1+cad2+cad3+bloq_4;



											if(!destino.equals(dir)){

												//aqui agrega la direcciones locales de red


												lista_red.add(destino);


											}





										}

										cad3=null;



									}


									cad2=null;


								}



								cad1=null;


							}







				}



		return lista_red;
	}


	//toma direccion ip local y mask
	private static void printParameter(NetworkInterface ni) {

		List<InterfaceAddress> list = ni.getInterfaceAddresses();
		Iterator<InterfaceAddress> it = list.iterator();

		while (it.hasNext()) {

            InterfaceAddress ia = it.next();


            String dir = ia.getAddress().toString();
            dir = dir.replace("/", "");

            if (ia.getNetworkPrefixLength() > 8 && ia.getNetworkPrefixLength() < 32) {


                direccion = dir;

                //toma el broadcast de dicha interface
                InetAddress broad = getIPv4LocalNetMask(ia.getAddress(), ia.getNetworkPrefixLength());

                maskara = broad.getHostAddress();


                //System.out.println(" Network Prefijo= " + ia.getNetworkPrefixLength());
                //System.out.println("");


            }

        }


	}



	//recibe prefijo de red y retorna mask de la red
	private static InetAddress getIPv4LocalNetMask(InetAddress ip, int netPrefix) {

		try {
			// Since this is for IPv4, it's 32 bits, so set the sign value of
			// the int to "negative"...
			int shiftby = (1<<31);
			// For the number of bits of the prefix -1 (we already set the sign bit)
			for (int i=netPrefix-1; i>0; i--) {
				// Shift the sign right... Java makes the sign bit sticky on a shift...
				// So no need to "set it back up"...
				shiftby = (shiftby >> 1);
			}
			// Transform the resulting value in xxx.xxx.xxx.xxx format, like if
			/// it was a standard address...
			String maskString = Integer.toString((shiftby >> 24) & 255) + "." + Integer.toString((shiftby >> 16) & 255) + "." + Integer.toString((shiftby >> 8) & 255) + "." + Integer.toString(shiftby & 255);
			// Return the address thus created...
			return InetAddress.getByName(maskString);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		// Something went wrong here...
		return null;

	}


	private WifiCommunicationException wrapFMPException(final String sender, final String destination, final String type, final String messageHash, final String signature, final FMPException cause){

		String message = WifiCommunicationException.DEFAULT_MESSAGE;
		String context = "Sender: " + sender;
		context += WifiCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Destination: " + destination;
		context += WifiCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Type: " + type;
		context += WifiCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "FermatMessage Hash: " + messageHash;
		context += WifiCommunicationException.CONTEXT_CONTENT_SEPARATOR;
		context += "Signature: " + signature;


		String possibleReason = "The FMP FermatPacketCommunication construction failed, check the cause and the values in the context";

		return new WifiCommunicationException(message, cause, context, possibleReason);
	}



		@Override
		public void setId(UUID pluginId) {
			this.pluginId = pluginId;
		}

		public UUID getPluginId() {
			return pluginId;
		}


		@Override
		public void start() throws CantStartPluginException {

		}

		@Override
		public void pause() {

		}

		@Override
		public void resume() {

		}

		@Override
		public void stop() {

		}

		@Override
		public ServiceStatus getStatus() {
			return null;
		}


}
