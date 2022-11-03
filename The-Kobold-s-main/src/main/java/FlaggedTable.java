/**
 * A helper class to allow flagged titles to be displayed properly in a table
 */
public class FlaggedTable {

    private String flaggedTitleName;
    private int flaggedIssueNumber;
    private int flaggedPriceDollars;
    private int flaggedQuantity;
    private int titleId;
    private int flaggedNumRequests;


    /**
     * Constructor. Creates a FlaggedTable object from the values provided
     * @param titleId ID of the FlaggedTable to create
     * @param title name of the FlaggedTable to create
     * @param issue issue of the FlaggedTable to create
     * @param price price of the FlaggedTable to create
     * @param quantity quantity of the FlaggedTable to create
     * @param numRequests number of requests of the FlaggedTable to create
     */
    public FlaggedTable(int titleId, String title, int issue, int price, int quantity, int numRequests){
        this.titleId = titleId;
        this.flaggedTitleName = title;
        this.flaggedIssueNumber = issue;
        this.flaggedPriceDollars = price;
        this.flaggedQuantity = quantity;
        this.flaggedNumRequests = numRequests;
    }

    /**
     * Gets the ID of this FlaggedTable
     * @return the ID of this FlaggedTable
     */
    public int getTitleId(){ return this.titleId; }

    /**
     * Gets the name of the title for this Flagged Table
     * @return the name of the title for this FlaggedTable
     */
    public String getFlaggedTitleName(){ return this.flaggedTitleName; }

    /**
     * Gets the issue number for this FlaggedTable
     * @return the issue number of the title for this FlaggedTable
     */
    public int getFlaggedIssueNumber(){ return this.flaggedIssueNumber; }

    /**
     * Gets the price in cents of this FlaggedTable
     * @return the price in cents of this FlaggedTable
     */
    public int getFlaggedPriceCents() { return this.flaggedPriceDollars; }

    /**
     * Gets the price in dollars (as a String) of this FlaggedTable
     * @return the price in dollars of this FlaggedTable as a String
     */
    public String getFlaggedPriceDollars(){
        String total;
        int dollars = (flaggedPriceDollars / 100);
        int cents = (flaggedPriceDollars % 100);
        if ((cents / 10) == 0) {
            total = Integer.toString(dollars) + ".0" + Integer.toString(cents);
        }else{
            total = Integer.toString(dollars) + '.' + Integer.toString(cents);
        }

        return total;
    }

    /**
     * Gets the quantity for this FlaggedTable
     * @return the quantity for this FlaggedTable
     */
    public int getFlaggedQuantity(){ return this.flaggedQuantity; }

    /**
     * Gets the number of requests for this FlaggedTable
     * @return the number of requests for this FlaggedTable
     */
    public int getFlaggedNumRequests(){ return this.flaggedNumRequests; }
}
