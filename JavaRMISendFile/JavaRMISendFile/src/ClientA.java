/**
 * 
 */

/**
 * @author Shieryn Tjandra
 *
 */

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


@SuppressWarnings("serial")
public class ClientA implements Serializable {
	public final static String CONTROLLER = "127.0.0.1";
	public final static int CONTROLLER_PORT = 4446;
	public final static int CLIENTB_PORT = 5555; // Client A - B send file
	public final static int CLIENTB_PORT_RESPONSE = 5444;//Client B-A send response
	public final static String CLIENTB_IP = "192.168.122.250"; //vm02
	public final static String CLIENTB_FOLDER = "/home/renshier/FileReceived/";//vm02
	public final static int CLIENTAINVOKER_PORT = 4444;
	public final static int CTL_PORT = 5656; 
	public static void main(String[] args) {
		String environment;
		String hostname;
		int portno;
		String clientapath;
		String clientbpath;
		String send="send";
		String start="start";
		String shutdown="shutdown";
		
		try {
		
			hostname = CLIENTB_IP;
			portno = CLIENTB_PORT;

			System.out.println("Connecting to "+ hostname +" : "+portno );
			
		    //if(start.equals("start"))
		    //{
		    	System.out.println("Connecting to "+ CONTROLLER +" : "+ CONTROLLER_PORT );
		    	//ClientA ca = new ClientA();
		    	//ca.recvr(CLIENTAINVOKER_PORT);
		    	//System.out.println(ca.recvr(CLIENTAINVOKER_PORT));
		    	
				Registry regctl = LocateRegistry.getRegistry(CONTROLLER, CONTROLLER_PORT);
				MsgInterface msginter = (MsgInterface)regctl.lookup("responsea");
				System.out.println("Sending response to Controller");
				msginter.sendmsgclienta("ClientA has been invoked");
				
				if(send.equals(args[0]))
				{
					//send response section (act as client and controller : server)
					Registry reg = LocateRegistry.getRegistry(hostname, portno);
					RmiInterface inter = (RmiInterface)reg.lookup("remoteObject");
					clientapath=args[1];
					clientbpath= CLIENTB_FOLDER + args[2];
					File clientapathfile = new File(clientapath);
					byte [] mydata = new byte[(int) clientapathfile.length()];
					FileInputStream in = new FileInputStream(clientapathfile);
					System.out.println("Sending the file to Client B");
					in.read(mydata, 0, mydata.length);
					inter.sendFileToClientB(mydata,clientbpath,(int)clientapathfile.length());
					in.close();
					msginter.sendmsgclienta("File sent to ClientB");
				}
		   // }
			
			
			//s.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("error with connection or command. Please check your hostname or command");
		}
	}
//	public String recvr(int destport) throws IOException
//	{
//		 Socket s=new Socket(CONTROLLER,SOCKET_PORT2); 
//		Socket sb=ssb.accept(); 
//		DataInputStream dinb=new DataInputStream(sb.getInputStream());  
//		DataOutputStream doutb=new DataOutputStream(sb.getOutputStream());  
//		BufferedReader brb=new BufferedReader(new InputStreamReader(System.in));
//		String msg=dinb.readUTF();
//		return msg;
//	}
}
