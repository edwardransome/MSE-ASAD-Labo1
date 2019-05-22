import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import io.jsonwebtoken.*;

import javax.xml.bind.DatatypeConverter;

public class AuthServer implements AuthManager{
    private static String SECRET_KEY = "ThisSeemsSafeLol";

    public static void main(String[] args) {
        try {
            AuthServer obj = new AuthServer();
            AuthManager stub = (AuthManager) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("AuthManager", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public String login(String username, String password) throws RemoteException {
        return null;
    }

    @Override
    public void setWeight(String jwt, Position p, double newValue) throws RemoteException {

    }

    @Override
    public void setStart(String jwt, Position p) throws RemoteException {

    }

    @Override
    public void setEnd(String jwt, Position p) throws RemoteException {

    }

    @Override
    public List<String> getAlgorithms(String jwt) throws RemoteException {
        return null;
    }

    @Override
    public Path getShortestPath(String jwt, String alg) throws RemoteException {
        return null;
    }

    private void parseJwt(String jwt){
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
    }
}
