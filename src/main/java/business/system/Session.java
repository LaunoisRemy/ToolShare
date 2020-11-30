package business.system;

import business.system.user.User;

public class Session {
    private User user;

    private Session() {

    }

    /**
     * The static class definition LazyHolder within it is not initialized until the JVM determines that LazyHolder must be executed
     */
    private static class LazyHolder {
        public static final Session INSTANCE= new Session();
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static Session getInstance(){
        return Session.LazyHolder.INSTANCE;
    }

    /**
     * Method return user connected
     * @return user
     */
    public User getUser(){
        return user;
    };

    public void setUser(User user) {
        this.user = user;
    }
}
