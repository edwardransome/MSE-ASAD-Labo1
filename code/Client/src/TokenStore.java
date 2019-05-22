public class TokenStore {
    private TokenStore(){}

    private static final TokenStore instance = new TokenStore();

    private static String token;

    public static TokenStore getInstance(){
        return instance;
    }

    public String token(){
        return token;
    }

    public void setToken(String token) {
        TokenStore.token = token;
    }
}
