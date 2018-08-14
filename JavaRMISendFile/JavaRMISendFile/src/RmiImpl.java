//Class contains implementation of all the functionalities provided to the clientA
//implementation of CLientA to ClientB
//the implementation of interface
//impl of sending files

import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class RmiImpl extends UnicastRemoteObject implements RmiInterface,Serializable{
		protected RmiImpl (String s) throws RemoteException{
			File storageDir = new File(s);
			storageDir.mkdir();
		}
		public void sendFileToClientB(byte[] mydata,String clientbpath, int length) throws RemoteException {
			try {
				File clientbpathfile = new File(clientbpath);
				FileOutputStream out = new FileOutputStream(clientbpathfile);
				byte [] data = mydata;
				out.write(data);
				out.flush();
				out.close();
				System.out.println("Data has been sent to ClientB");
			}
			catch(IOException e){
				e.printStackTrace();
			}
		//System.out.println("Data has been sent to ClientB");
		}
		
//		public void ClientBtoClientA(String s) throws RemoteException {
//			System.out.println("File has been received by ClientB");
//		}
	
}
