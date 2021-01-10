package business.system.user;

/**
 * Represent attributes and methods of an Admin
 */
public class Admin extends Role {

    /**
     * Name of all admin user
     */
    public static final String ADMIN = "Admin";

    /**
     * Constructor of role admin
     */
    public Admin() {
        this.setNameRole(Admin.ADMIN);
    }


}
