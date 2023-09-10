public class Container {

    private int c_number;
    private double Weight;
    private int Type;

    // Define an enum for the container types

    // Constructor
    public Container(int c_number, double weight, int type) {
        this.c_number = c_number;
        Weight = weight;
        Type = type;
    }

    // Setter & Getter
    public int getC_number() {
        return c_number;
    }
    public void setC_number(int c_number) {
        this.c_number = c_number;
    }
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
        public int getValue() {
            return value;
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
}
