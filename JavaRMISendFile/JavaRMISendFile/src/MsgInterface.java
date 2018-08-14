import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MsgInterface extends Remote {
	public void sendmsgclienta(String msg) throws RemoteException;
	public void sendmsgclientb(String msg) throws RemoteException;

}
