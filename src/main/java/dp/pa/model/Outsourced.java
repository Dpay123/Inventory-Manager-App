package dp.pa.model;

/**Represents a subset of a Part to be handled by the Inventory app.
 * Contains a company name variable.
 * Has a unique id but all other fields can be non-unique.
 * */
public class Outsourced extends Part {

    /**The company name*/
    private String companyName;

    /**The constructor. Inherits from superclass Part.\
     @param id the part id
     @param name the part name
     @param price the part price
     @param stock the part inv
     @param min the part min inv
     @param max the part max inv
     @param companyName the part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
