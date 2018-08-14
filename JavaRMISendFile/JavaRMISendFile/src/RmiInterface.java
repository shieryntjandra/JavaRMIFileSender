//Class contains interfaces directly between the ClientA and ClientB

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.rmi.*;
import java.rmi.registry.*;

public interface RmiInterface extends Remote{
	public void sendFileToClientB(byte[] mybyte,String clientbpath, int length) throws RemoteException;	
	//public void ClientBtoClientA(String s) throws RemoteException;
}
