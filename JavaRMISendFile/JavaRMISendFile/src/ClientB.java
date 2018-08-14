//Class receives files
//SERVER- RECEIVER
import java.io.*;
import java.net.Socket;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.concurrent.TimeUnit;

public class ClientB implements Serializable{
	public final static int CLIENTB_PORT = 5555; // clienta-b send file
	public final static int CLIENTB_PORT_RESPONSE = 5444;
	public final static String CONTROLLER = "192.168.122.213"; // VM01same ip as ClientA
	public final static int CONTROLLER_PORT = 5557;
	public final static String CTL = "127.0.0.1";
	public final static int CTL_PORT = 5656; 
	static int portno;
	static String start = "start";
	public static void main (String[] args) {
		try {
			Socket s=new Socket(CONTROLLER,CTL_PORT); 
			DataInputStream dinb=new DataInputStream(s.getInputStream());  
			DataOutputStream doutb=new DataOutputStream(s.getOutputStream());  
			BufferedReader brb=new BufferedReader(new InputStreamReader(System.in));
			String msg=dinb.readUTF();
			//System.out.println(msg);
				if(start.equals(msg))
				{
					portno = CLIENTB_PORT;
					
				}
				//send response section
				System.out.println("Connecting to "+ CONTROLLER +" : "+ CONTROLLER_PORT );
				
				Registry regctl = LocateRegistry.getRegistry(CONTROLLER,CONTROLLER_PORT);
				System.setProperty("java.rmi.server.hostname","192.168.122.1");
				MsgInterface msginter = (MsgInterface)regctl.lookup("responseb");
				System.out.println("Sending response to Controller");
				msginter.sendmsgclientb("ClientB has been invoked");

				Registry reg = LocateRegistry.createRegistry(portno); //creates and exports a registry instance on the local host that acceots requests on specified port
				RmiImpl imp = new RmiImpl("/home/shieryntjandra/FileReceived");
				reg.bind("remoteObject", imp);
				System.out.println("ClientB is ready");
				System.out.println(portno);
				TimeUnit.SECONDS.sleep(17);
				msginter.sendmsgclientb("Data received by ClientB");
		}
		catch (Exception e)
		{
			System.out.println("ClientB failed: "+e);
		}
	}

	
}
