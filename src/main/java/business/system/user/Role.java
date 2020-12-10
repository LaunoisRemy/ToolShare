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
     */
    public String getNameRole() {
        return nameRole;
    }

    /**
     * Setter of name role
     * @param nameRole
     */
    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString(){
        return  this.nameRole+" : ";
    }

}
