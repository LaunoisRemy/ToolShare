package business.system.user;

/**
 * Represent attributes and methods of an  ordinary user
 */
public class OrdinaryUser extends Role{

    /**
     * Name of the ordinary_user
     */
    public static final String ORDINARY_USER = "ORDINARY_USER";

    /**
     * City of user if is not an admin, else null
     */
    private String userCity;

    /**
     * Phone number of user
     */
    private String phoneNumber;

    /**
     * Constructor
     */
    public OrdinaryUser(String userCity, String phoneNumber) {
        this.userCity = userCity;
        this.phoneNumber = phoneNumber;
        this.setNameRole(OrdinaryUser.ORDINARY_USER);
    }

    /**
     * getter of userCity
     * @return userCity
     */
    public String getUserCity() { return this.userCity; }

    /**
     * setter of user city
     * @param userCity the city of the user as a String
     */
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    /**
     * getter of phoneNumber
     * @return phoneNumber
     */
    public String getPhoneNumber() { return this.phoneNumber; }

    /**
     * setter of phone number of user
     * @param phoneNumber the phone number of the user as a String
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Convert the OrdinaryUser object into a readable String
     * @return a description of the OrdinaryUser object as a String
     */
    @Override
    public String toString() {
        return  "ordinary user : "+
                "userCity = '" + userCity + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' ;
    }


}
