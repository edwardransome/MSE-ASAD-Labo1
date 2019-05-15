import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuthManager extends Remote {

    public String login(String username, String password) throws RemoteException;

    public void setWeight(String jwt, Position p, double newValue) throws RemoteException;
    public void setStart(String jwt, Position p) throws RemoteException;
    public void setEnd(String jwt, Position p) throws RemoteException;

    public List<String> getAlgorithms(String jwt) throws RemoteException;
    public Path getShortestPath(String jwt, String alg) throws RemoteException;

}
