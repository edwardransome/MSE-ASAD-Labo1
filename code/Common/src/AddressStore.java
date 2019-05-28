public class AddressStore {

    private AddressStore(){}

    private static final AddressStore instance = new AddressStore();

    private static String address = "localhost";

    public static AddressStore getInstance(){
        return instance;
    }

    public String address(){
        return address;
    }

    public void setAddress(String address) {
        instance.address = address;
    }
}
