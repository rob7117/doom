public enum Actions {
    SHOOT("shoot"),
    FORWARD("forward"),
    BACKWARD("backward"),
    LEFT("turn-left"),
    RIGHT("turn-right"),
    USE("use"),
    STRAFE_RIGHT("strafe-right"),
    STRAFE_LEFT("strafe-left");

    public final String action;

    private Actions(String action) {
        this.action = action;
    }
}
