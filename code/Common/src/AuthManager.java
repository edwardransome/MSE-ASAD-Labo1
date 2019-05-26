import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuthManager extends Remote {

    public String login(String username, String password) throws RemoteException;

    public boolean setWeight(String jwt, Position p, double newValue) throws RemoteException;
    public boolean setStart(String jwt, Position p) throws RemoteException;
    public boolean setEnd(String jwt, Position p) throws RemoteException;

    public List<String> getAlgorithms(String jwt) throws RemoteException;
    public Path getShortestPath(String jwt, String alg) throws RemoteException;

}
