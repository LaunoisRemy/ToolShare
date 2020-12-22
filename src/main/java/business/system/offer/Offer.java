package business.system.offer;

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
    private int user_id;

    /**
     * Id of offer category
     */
    private int category_id;

    /**
     * Constructors
     */
    public Offer() {}

    public Offer(int offer_id, String title, float pricePerDay, String description, ToolSate toolSate, boolean isPriority, int user_id, int category_id) {
        this.offer_id = offer_id;
        this.title = title;
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.toolSate = toolSate;
        this.isPriority = isPriority;
        this.user_id = user_id;
        this.category_id = category_id;
    }

    public Offer(String title, float pricePerDay, String description, ToolSate toolSate, boolean isPriority, int user_id, int category_id) {
        this.title = title;
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.toolSate = toolSate;
        this.isPriority = isPriority;
        this.user_id = user_id;
        this.category_id = category_id;
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
    public void setPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }

    /**
     * getter of the id of offer owner
     * @return the id of the user who own the offer
     */
    public int getUser_id() {
        return this.user_id;
    }

    /**
     * setter of the id of offer owner
     * @param user_id the id of the offer owner
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * getter of the id of offer category
     * @return the id of offer category
     */
    public int getCategory_id() {
        return this.category_id;
    }

    /**
     * setter of the id of offer category
     * @param category_id the id of the offer category
     */
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offer_id=" + offer_id +
                ", title='" + title + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", description='" + description + '\'' +
                ", toolSate=" + toolSate +
                ", isPriority=" + isPriority +
                ", user_id=" + user_id +
                ", category_id=" + category_id +
                '}';
    }
}
