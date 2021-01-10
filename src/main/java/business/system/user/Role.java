package business.system.user;

/**
 * Abstract class which represent a role of a user in the application
 */
public abstract class Role {
    /**
     * Name of role
     */
    private  String nameRole;

    /**
     * Return name/type of role
     * @return the name of the role
     */
    public String getNameRole() {
        return nameRole;
    }

    /**
     * Setter of name role
     * @param nameRole the new role name
     */
    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    /**
     * Convert the Role object into a readable String
     * @return a description of the Role object as a String
     */
    @Override
    public String toString(){
        return  " Role : " + this.nameRole;
    }

}
