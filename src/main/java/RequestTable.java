/**
 * Helper class to create and display customer requests in a table
 */
public class RequestTable {

    private String requestLastName;
    private String requestFirstName;
    private int requestQuantity;

    /**
     * Creates a RequestTable object based on the parameters provided
     * @param requestLastName the last name of the requesting customer
     * @param requestFirstName the first name of the requestign customer
     * @param requestQuantity the quantity of the customer's order
     */
    public RequestTable(String requestLastName, String requestFirstName, int requestQuantity){

        this.requestLastName = requestLastName;
        this.requestFirstName = requestFirstName;
        this.requestQuantity = requestQuantity;
    }

    /**
     * Gets the last name of the customer for this object
     * @return the customer's last name
     */
    public String getRequestLastName(){ return this.requestLastName; }

    /**
     * Gets the first name of the customer for this object
     * @return the customer's first name
     */
    public String getRequestFirstName(){ return this.requestFirstName; }

    /**
     * Gets the quantity of the customer's order for this object
     * @return the quantity of the customer's order
     */
    public String getRequestQuantity(){ return String.valueOf(this.requestQuantity); }
}
