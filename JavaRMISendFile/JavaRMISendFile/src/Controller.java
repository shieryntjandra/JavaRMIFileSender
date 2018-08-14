import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
public class Controller implements Serializable{
	public final static int CLIENTACONTROLLER = 4446; //ClientA - Controller Invoke response
	public final static int CLIENTBCONTROLLER = 5557;//ClientB - Controller Invoke response
	public final static String FILETOSEND ="/home/stjandra/Desktop/UDP+TCPBIG.csv"; //need to be changed
	public final static String CLIENTAINVOKER = "127.0.0.1";
	public final static int CLIENTAINVOKER_PORT = 4444;
	public final static String CLIENTBINVOKER = "192.168.122.250";
	public final static int CLIENTBINVOKER_PORT = 5656;
	public static void main(String[] args) throws AlreadyBoundException, IOException, InterruptedException, NotBoundException {
//		Controller ctl = new Controller();
		//ctl.commanders(CLIENTAINVOKER,CLIENTBINVOKER_PORT,"start");
//		ctl.commanders(CLIENTAINVOKER,CLIENTAINVOKER_PORT,"start");
		//Socket ssb=new Socket("127.0.0.1",CLIENTAINVOKER_PORT);

		System.out.println("HI, Welcome to Java RMI File Sender");
		System.out.println("Controller is ready");
		System.out.println("Initializing...");


		Registry rega = LocateRegistry.createRegistry(CLIENTACONTROLLER);
		Registry regb = LocateRegistry.createRegistry(CLIENTBCONTROLLER);
		ServerSocket ss=new ServerSocket(CLIENTBINVOKER_PORT);
		System.out.println("Invoking ClientB Hostname :"+CLIENTBINVOKER+" Port : "+CLIENTBINVOKER_PORT);
		Socket s=ss.accept(); 
		DataInputStream dinb=new DataInputStream(s.getInputStream());  
		DataOutputStream doutb=new DataOutputStream(s.getOutputStream());  
		BufferedReader brb=new BufferedReader(new InputStreamReader(System.in));
		doutb.writeUTF("start");
		doutb.flush();doutb.close();
		s.close();
		MsgImpl msgimp = new MsgImpl();
		regb.bind("responseb", msgimp);
		System.out.println("Invoking ClientA Hostname :"+CLIENTAINVOKER+" Port : "+CLIENTAINVOKER_PORT);
		String[] clientacmd = new String[] {"xterm","-hold","-e","pwd && java ClientA send "+FILETOSEND+" copy.csv"};
		Process clienta = new ProcessBuilder(clientacmd).start();
		
		rega.bind("responsea", msgimp);
		
		System.out.println("ClientA Port: "+CLIENTACONTROLLER+" and ClientB Port "+ CLIENTBCONTROLLER);
		
	}
	public void commanders(String destip,int destport,String msg) throws IOException
	{
		Socket ssb=new Socket(destip,destport);
		
		DataInputStream dinb=new DataInputStream(ssb.getInputStream());  
		DataOutputStream doutb=new DataOutputStream(ssb.getOutputStream());  
		BufferedReader brb=new BufferedReader(new InputStreamReader(System.in));
		doutb.writeUTF(msg);
		doutb.flush();
		doutb.close();
		ssb.close();

	}
}



//notes
//String[] clientbinvoker = new String[] {"xterm","-hold","-e","pwd && java ClientB start"};
//Process clientb = new ProcessBuilder(clientbinvoker).start();

//TimeUnit.SECONDS.sleep(8);
//String[] clientainvoker = new String[] {"xterm","-hold","-e","pwd && java ClientA send "+FILETOSEND+" copy.csv"};
//Process clienta = new ProcessBuilder(clientainvoker).start();
//String[] clientbinvoker = new String[] {"xterm","-hold","-e","pwd && ssh renshier@192.168.122.250 "
		//+ "java ~/Desktop/JavaSampleProgram/socketapp.ClientA"};
//Process clientb = new ProcessBuilder(clientbinvoker).start();
//Registry ivkregb = LocateRegistry.getRegistry(CLIENTAINVOKER,CLIENTAINVOKER_PORT);
//MsgInterface msginterb = (MsgInterface)regb.lookup("commandb");
//msginterb.sendmsgclientb("start");
//Registry ivkrega = LocateRegistry.getRegistry(CLIENTAINVOKER,CLIENTAINVOKER_PORT);
//MsgInterface msgintera = (MsgInterface)rega.lookup("commanda");
//msgintera.sendmsgclienta("start");