package vendingmachine.config;

public enum Configuration {
    ;

    private final Object value;

    Configuration(Object value) {
        this.value = value;
    }

    public int getIntValue() {
        if (value instanceof Integer) {
            return (int) value;
        }
        throw new IllegalStateException("Value is not an integer: " + value);
    }

    public String getStringValue() {
        if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalStateException("Value is not a string: " + value);
    }
}
