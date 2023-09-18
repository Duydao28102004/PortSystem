public class Container {

    private double Weight;
    private int Type;
    private int serialCode;

    // Define an enum for the container types

    // Constructor
    public Container(double weight, int type, int serialCode) {

        Weight = weight;
        Type = type;
        this.serialCode = serialCode;
    }

    // Setter & Getter
    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(int serialCode) {
        this.serialCode = serialCode;
    }
    // Getter and Setter for the Port

    public enum ContainerType {
        DRY_STORAGE(1),
        OPEN_TOP(2),
        OPEN_SIDE(3),
        REFRIGERATED(4),
        LIQUID(5);

        private final int value;

        ContainerType(int value) {
            this.value = value;
        }
        public static ContainerType fromValue(int value) {
            for (ContainerType type : ContainerType.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid ContainerType value: " + value);
        }
    }
    public String getContainerTypeName() {
        return ContainerType.fromValue(Type).name();
    }

    @Override
    public String toString() {
        return String.valueOf(serialCode);
    }
}
