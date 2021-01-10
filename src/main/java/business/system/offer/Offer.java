package business.system.offer;

import business.system.Category;
import business.system.user.User;

/**
 * Class used to instantiate Offer object
 */
public class Offer {

    /**
     * Id of offer
     */
    private int offer_id;

    /**
     * Title of offer
     */
    private String title;

    /**
     * Daily price of offer
     */
    private float pricePerDay;

    /**
     * Description of offer
     */
    private String description;

    /**
     * Tool state of offer
     */
    private ToolSate toolSate;

    /**
     * true if offer is priority, else false
     */
    private boolean isPriority;

    /**
     * Id of offer owner
     */
    private User user;

    /**
     * Id of offer category
     */
    private Category category;

    /**
     * Constructors
     */
    public Offer() {}

    public Offer(int offer_id, String title, float pricePerDay, String description, ToolSate toolSate, boolean isPriority, User user, Category category) {
        this.offer_id = offer_id;
        this.title = title;
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.toolSate = toolSate;
        this.isPriority = isPriority;
        this.user = user;
        this.category = category;
    }

    public Offer(String title, float pricePerDay, String description, ToolSate toolSate, boolean isPriority, User user, Category category) {
        this.title = title;
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.toolSate = toolSate;
        this.isPriority = isPriority;
        this.user = user;
        this.category = category;
    }

    /**
     * getter of offer id
     * @return offer_id
     */
    public int getOffer_id() {
        return this.offer_id;
    }

    /**
     * setter of offer id
     * @param offer_id the id of the offer
     */
    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    /**
     * getter of offer title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * setter of offer title
     * @param title the title of the offer
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter of offer daily price
     * @return pricePerDay
     */
    public float getPricePerDay() {
        return this.pricePerDay;
    }

    /**
     * setter of offer daily price
     * @param pricePerDay the daily price of the offer
     */
    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    /**
     * getter of offer description
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * setter of offer description
     * @param description the description of the offer
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter of offer tool state
     * @return toolState
     */
    public ToolSate getToolSate() {
        return this.toolSate;
    }

    /**
     * setter of offer tool state
     * @param toolSate the state of tool of the offer
     */
    public void setToolSate(ToolSate toolSate) {
        this.toolSate = toolSate;
    }

    /**
     * getter of the priority of the offer
     * @return true if priority, else false
     */
    public boolean getIsPriority() {
        return this.isPriority;
    }

    /**
     * setter of the priority of the offer
     * @param isPriority true if the offer is priority, else false
     */
    public void setIsPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }

    /**
     * getter of the offer owner
     * @return the user who own the offer
     */
    public User getUser() {
        return this.user;
    }

    /**
     * setter of the offer owner
     * @param user the offer owner
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * getter of the offer category
     * @return the offer category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * setter of the offer category
     * @param category the new offer category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Method to display an offer
     * @return return a String which is a description of the Offer object
     */
    @Override
    public String toString() {
        return "Offer{" +
                "offer_id=" + offer_id +
                ", title='" + title + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", description='" + description + '\'' +
                ", toolSate=" + toolSate +
                ", isPriority=" + isPriority +
                ", userId="+ getUser().getUser_id()+
                '}';
    }
}
