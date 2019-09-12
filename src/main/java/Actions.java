public enum Actions {
    SHOOT("shoot"),
    FORWARD("forward"),
    BACKWARD("backward"),
    LEFT("turn-left"),
    RIGHT("turn-right"),
    USE("use");

    public final String action;

    private Actions(String action) {
        this.action = action;
    }
}
