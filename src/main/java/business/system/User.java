package business.system;

public class User {
    /**
     * Id of user
     */
    private int user_id;

    /**
     * First name user
     */
    private String firstName;

    /**
     * Last name of  user
     */
    private String lastName;

    /**
     * Email of user
     */
    private String email;

    /**
     * password of user
     */
    private String password;

    /**
     * City of user if is not an admin, else null
     */
    private String userCity;

    /**
     * Phone number of user
     */
    private String phoneNumber;

    /**
     * True if the user is admin, else false
     */
    private boolean isAdmin;

    /**
     * True if the user isBanned, else false
     */
    private boolean isBanned;

    /**
     * Default constructor
     */
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * getter of first name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter of first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter of lastname
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * setter of LastName
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getter of email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter of email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter of password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter of password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    /**
     * setter of id user
     * @param password
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserCity() {
        return userCity;
    }

    /**
     * setter of user city
     * @param userCity
     */
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * setter of phone number of user
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * if the user is admin
     * @return true if admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * setter of boolean Admin
     * @param admin
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
