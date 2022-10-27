package dp.pa.model;

/**Represents a subset of a Part to be handled by the Inventory app.
 * Contains a machine id variable.
 * Has a unique id but all other fields can be non-unique.
 * */
public class InHouse extends Part {

    /**The machineID of the part*/
    private int machineId;

    /**The constructor. Inherits from superclass Part.\
     @param id the part id
     @param name the part name
     @param price the part price
     @param stock the part inv
     @param min the part min inv
     @param max the part max inv
     @param machineId the part machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
