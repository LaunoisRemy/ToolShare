package business.system.user;

public abstract class Role {
    public String nameRole;

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString(){
        return  this.nameRole+" : ";
    }

}
