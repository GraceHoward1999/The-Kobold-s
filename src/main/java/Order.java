/**
 * An Order relating a Title and a customer. Every order has a customer
 * that is requesting it and a title that is to be requested. Every
 * order must also have a specified quantity and issue #.
 */
public class Order {

    private String title;
    private int quantity;
    private int issue;
    private int customerId;
    private int titleId;


    /**
     * Constructor. Sets the values for the Order equal to the values provided.
     * @param customerId ID of the customer requesting the order
     * @param titleId ID of the Title to be requested
     * @param title Name of the Title being requested
     * @param quantity Number of copies of the title that are requested
     * @param issue Specific issue number to request
     */
    public Order(int customerId, int titleId, String title, int quantity, int issue) {
        this.customerId = customerId;
        this.titleId =titleId;
        this.title =title;
        this.quantity = quantity;
        this.issue = issue;
    }

    /**
     * Gets ID of Customer for this Order
     * @return Customer ID for this Order
     */
    public int getCustomerId(){
        return this.customerId;
    }

    /**
     * Gets ID of Title for this order
     * @return Title ID for this order
     */
    public int getTitleId() {
        return this.titleId;
    }

    /**
     * Gets name of the Title for this order
     * @return Name of the Title for this order
     */
    public String getTitleName() {
        return this.title;
    }

    /**
     * Gets the quantity of copies for this order
     * @return Quantity for this order
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Gets the specific issue # for this order
     * @return Issue # for this order
     */
    public int getIssue() {
        return this.issue;
    }

}
