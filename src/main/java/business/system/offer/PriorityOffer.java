package business.system.offer;

import business.system.Category;
import business.system.user.User;
import java.util.Date;

/**
 * Class used to instantiate PriorityOffer
 */
public class PriorityOffer extends Offer {

    /**
     * Starting date as priority offer
     */
    private Date dateStartPriority;

    /**
     * Ending date as a priority offer
     */
    private Date dateEndPriority;

    /**
     * Constructors
     */
    public PriorityOffer(int offer_id, String title, float pricePerDay, String description, ToolSate toolSate, boolean isPriority, User user, Category category, Date dateStartPriority, Date dateEndPriority) {
        super(offer_id, title, pricePerDay, description, toolSate, isPriority, user, category);
        this.dateStartPriority = dateStartPriority;
        this.dateEndPriority = dateEndPriority;
    }

    public PriorityOffer(String title, String description, float pricePerDay, ToolSate toolSate, boolean isPriority, User user, Category category, Date dateStartPriority, Date dateEndPriority) {
        super(title, pricePerDay, description, toolSate, isPriority, user, category);
        this.dateStartPriority = dateStartPriority;
        this.dateEndPriority = dateEndPriority;
    }

    /**
     * getter of priority offer starting date
     * @return the starting date of the priority privilege
     */
    public Date getDateStartPriority() {
        return this.dateStartPriority;
    }

    /**
     * setter of the priority offer starting date
     * @param dateStartPriority the starting date of the priority privilege
     */
    public void setDateStartPriority(Date dateStartPriority) {
        this.dateStartPriority = dateStartPriority;
    }

    /**
     * getter of priority offer starting ending
     * @return the ending date of the priority privilege
     */
    public Date getDateEndPriority() {
        return dateEndPriority;
    }

    /**
     * setter of the priority offer ending date
     * @param dateEndPriority the ending date of the priority privilege
     */
    public void setDateEndPriority(Date dateEndPriority) {
        this.dateEndPriority = dateEndPriority;
    }

    /**
     * check if the priority offer is expired
     * @return true if expired, else false
     */
    public boolean isExpired() {
        if(new Date().compareTo(this.getDateEndPriority()) > 0) {
            this.setDateEndPriority(null);
            this.setDateStartPriority(null);
            this.setIsPriority(false);
            return true;
        }
        return false;
    }
}
