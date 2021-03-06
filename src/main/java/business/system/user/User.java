package business.system.user;

/**
 * Represent user in the application
 */
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
     * True if the user isBanned, else false
     */
    private boolean isBanned;

    /**
     * Salt of the user
     */
    private String salt;

    /**
     * The role of an user, ordinary or admin
     */
    private final Role role;

    /**
     * The code used if the user forgot his/her password
     */
    private String recoveryCode;

    /**
     * Default constructor
     */
    public User(String firstName, String lastName, String email, String password, String userCity, String phoneNumber, String salt, boolean isAdmin) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        if(isAdmin) {
            this.role = new Admin();
        } else {
            this.role = new OrdinaryUser(userCity, phoneNumber);
        }
        this.setSalt(salt);
        this.setIsBanned(false);
    }

    public User(int id, String firstName, String lastName, String email, String password, String userCity, String phoneNumber, boolean isAdmin, boolean isBanned, String salt) {
        this.setUser_id(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        if(isAdmin){
            this.role = new Admin();
        }else{
            this.role= new OrdinaryUser(userCity,phoneNumber);
        }
        this.setIsBanned(isBanned);
        this.setSalt(salt);
    }

    public User(int user_id, String firstName, String lastName, String email, String password, String userCity, String phoneNumber, boolean isAdmin, boolean isBanned, String salt, String recoveryCode) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isBanned = isBanned;
        this.salt = salt;
        if(isAdmin){
            this.role = new Admin();
        }else{
            this.role= new OrdinaryUser(userCity,phoneNumber);
        }
        this.recoveryCode = recoveryCode;
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
     * if the user is banned
     * @return true if banned, else false
     */
    public boolean getIsBanned() { return this.isBanned; }

    /**
     * setter of boolean isBanned
     * @param banned true if the user is banned, else false
     */
    public void setIsBanned(boolean banned) { this.isBanned = banned; }

    /**
     * getter of salt
     * @return salt
     */
    public String getSalt() { return this.salt; }

    /**
     * setter of salt
     * @param salt the salt of the user as a String
     */
    public void setSalt(String salt) { this.salt = salt; }

    /**
     * Getter of the role
     * @return Admin or OrdinaryUser
     */
    public Role getRole() {
        return role;
    }

    /**
     * Getter of the recovery code
     * @return the recovery code of the user
     */
    public String getRecoveryCode() {
        return recoveryCode;
    }

    /**
     * Setter of the recovery code
     * @param recoveryCode the new recovery code of the user
     */
    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    /**
     * Check if user is an admin
     * @return true if user is an admin, else false
     */
    public boolean isAdmin(){
        return getRole().getNameRole().equals(Admin.ADMIN);
    }

    /**
     * Convert the User object into a readable String
     * @return a description of the User object as a String
     */
    @Override
    public String toString() {
        return "User : " +
                "user_id = " + this.getUser_id() +
                ", firstName = '" + this.getFirstName() + '\'' +
                ", lastName = '" + this.getLastName() + '\'' +
                ", email = '" + this.getEmail() + '\'' +
                ", password = '" + this.getPassword() + '\'' +
                ", isBanned = " + this.getIsBanned() + '\''+
                this.role.toString();
    }

    /**
     * Check if users are equals
     * @param user the user you want to compare
     * @return true if there are equals, else false
     */
    @Override
    public boolean equals(Object user) {
        if(!(user instanceof User)) {
            return false;
        } else {
            return this.getUser_id() == ((User) user).getUser_id();
        }
    }

}
