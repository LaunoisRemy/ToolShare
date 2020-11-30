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
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    public User(String firstName, String lastName, String email, String password) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
    }

    public User(String firstName, String lastName, String email, String password, String userCity, String phoneNumber, boolean isAdmin, boolean isBanned) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCity(userCity);
        this.setPhoneNumber(phoneNumber);
        this.setAdmin(isAdmin);
        this.setBanned(isBanned);
    }

    public User(int id, String firstName, String lastName, String email, String password, String userCity, String phoneNumber, boolean isAdmin, boolean isBanned) {
        this.setUser_id(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setUserCity(userCity);
        this.setPhoneNumber(phoneNumber);
        this.setAdmin(isAdmin);
        this.setBanned(isBanned);
    }

    /**
     * getter of first name
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * setter of first name
     * @param firstName the first name of the user as a String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter of lastname
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * setter of LastName
     * @param lastName the last name of the user as a String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getter of email
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * setter of email
     * @param email the email of the user as a String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter of password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * setter of password
     * @param password the password of the user as a String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter of user_id
     * @return user_id
     */
    public int getUser_id() { return this.user_id; }

    /**
     * setter of id user
     * @param user_id the id of the user as an int
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
     * if the user is admin
     * @return true if admin, else false
     */
    public boolean isAdmin() {
        return this.isAdmin;
    }

    /**
     * setter of boolean isAdmin
     * @param admin true is the user is an admin, else false
     */
    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    /**
     * if the user is banned
     * @return true if banned, else false
     */
    public boolean isBanned() { return this.isBanned; }

    /**
     * setter of boolean isBanned
     * @param banned true if the user is banned, else false
     */
    public void setBanned(boolean banned) { this.isBanned = banned; }

    @Override
    public String toString() {
        return "User : " +
                "user_id = " + this.getUser_id() +
                ", firstName = '" + this.getFirstName() + '\'' +
                ", lastName = '" + this.getLastName() + '\'' +
                ", email = '" + this.getEmail() + '\'' +
                ", password = '" + this.getPassword() + '\'' +
                ", userCity = '" + this.getUserCity() + '\'' +
                ", phoneNumber = '" + this.getPhoneNumber() + '\'' +
                ", isAdmin = " + this.isAdmin() +
                ", isBanned = " + this.isBanned() ;
    }

    @Override
    public boolean equals(Object user) {
        if(!(user instanceof User)) {
            return false;
        } else {
            return this.getUser_id() == ((User) user).getUser_id();
        }
    }

}
