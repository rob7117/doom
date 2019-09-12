public class Door {
    private int id;
    private int specialType;
    private String state;
    private String keyRequired;

    public Door(int id, int specialType, String state, String keyRequired) {
        this.id = id;
        this.specialType = specialType;
        this.state = state;
        this.keyRequired = keyRequired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecialType() {
        return specialType;
    }

    public void setSpecialType(int specialType) {
        this.specialType = specialType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKeyRequired() {
        return keyRequired;
    }

    public void setKeyRequired(String keyRequired) {
        this.keyRequired = keyRequired;
    }
}
