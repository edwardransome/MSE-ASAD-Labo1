import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AuthServer implements AuthManager{
    //This is an example, do not store you keys this way...
    private static String SECRET_KEY = "9c2qTkcI+lmNl3NYNispJ/1Nh0TAV3HwARBEd88UFqE=";
    private static String PATH_TO_USERS = "./Manager/src/main/resources/users.txt";
    HashMap<String, Terrain> terrains = new HashMap<>();

    public static void main(String[] args) {
        if(args.length > 1){
            AddressStore.getInstance().setAddress(args[1]);
        }

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
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        try {
            Optional<String[]> line = Files.lines(new File(PATH_TO_USERS).toPath())
                    .map(s -> s.split("\t"))
                    .filter(s -> s[0].equalsIgnoreCase(username))
                    .findFirst();
            if(line.isPresent()){
                byte[] storedHash = DatatypeConverter.parseHexBinary(line.get()[1]);
                if(Arrays.equals(hash, storedHash)){
                    // We do not store terrains. Reset on each login. If we had a DB
                    // we could store them persistently.
                    terrains.put(username, new Terrain(20,20));
                    return createJWT(username);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean setWeight(String jwt, Position p, double newValue) throws RemoteException {
        Claims claim = null;
        try{
            claim = parseJwt(jwt);
        }catch (SignatureException e){
            return false;
        }
        Terrain t = terrains.get(claim.getSubject());
        t.setWeight(p, newValue);
        return true;
    }

    @Override
    public boolean setStart(String jwt, Position p) throws RemoteException {
        Claims claim = null;
        try{
            claim = parseJwt(jwt);
        }catch (SignatureException e){
            return false;
        }
        Terrain t = terrains.get(claim.getSubject());
        t.setStart(p);
        return true;
    }

    @Override
    public boolean setEnd(String jwt, Position p) throws RemoteException {
        Claims claim = null;
        try{
            claim = parseJwt(jwt);
        }catch (SignatureException e){
            return false;
        }
        Terrain t = terrains.get(claim.getSubject());
        t.setEnd(p);
        return true;
    }

    @Override
    public List<String> getAlgorithms(String jwt) throws RemoteException {
        Claims claim = null;
        try{
            claim = parseJwt(jwt);
        }catch (SignatureException e){
            return new LinkedList<String>();
        }

        try {
            Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
            ShortestPathSolver stub = (ShortestPathSolver) registry.lookup("ComputationServer");
            return stub.getAlgorithms();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<String>();
    }

    @Override
    public Path getShortestPath(String jwt, String alg) throws RemoteException {
        Claims claim = null;
        try{
            claim = parseJwt(jwt);
        }catch (SignatureException e){
            return null;
        }

        try {
            Registry registry = LocateRegistry.getRegistry(AddressStore.getInstance().address());
            ShortestPathSolver stub = (ShortestPathSolver) registry.lookup("ComputationServer");
            return stub.calculateShortestPath(alg, terrains.get(claim.getSubject()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Claims parseJwt(String jwt) throws SignatureException {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    //Sample method to construct a JWT
    private String createJWT(String user) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setSubject(user)
                .setIssuer("AuthServer")
                .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
