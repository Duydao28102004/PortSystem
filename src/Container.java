public class Container {

    private int ID;
    private float Weight;
    private ContainerType Type;
    private Port p_number;

    // Define an enum for the container types
    enum ContainerType {
        DRY_STORAGE,
        OPEN_TOP,
        OPEN_SIDE,
        REFRIGERATED,
        LIQUID
    }

    // Constructor
    public Container(int ID, float weight, ContainerType type) {
        this.ID = ID;
        Weight = weight;
        Type = type;
    }

    // Setter & Getter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getWeight() {
        return Weight;
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    public ContainerType getType() {
        return Type;
    }
    public void setType(ContainerType type) {
        this.Type = type;
    }

    @Override
    public String toString() {
        return "Container{" +
                "ID=" + ID +
                ", Weight=" + Weight +
                ", Type=" + Type +
                '}';
    }

}
