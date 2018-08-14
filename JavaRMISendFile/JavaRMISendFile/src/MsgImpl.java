import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class MsgImpl extends UnicastRemoteObject implements MsgInterface{
	public MsgImpl() throws RemoteException{
		
	}
	@Override
	public void sendmsgclienta(String msg) throws RemoteException{
		System.out.println(msg);
	}
	@Override
	public void sendmsgclientb(String msg) throws RemoteException{
		System.out.println(msg);
	}
}
