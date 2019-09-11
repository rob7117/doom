import java.util.HashMap;

public class Player {
    private int id;
    private HashMap<String, Double> position;
    private int angle;
    private int height;
    private int health;
    private int typeId;
    private String type;
    private HashMap<String, Boolean> flags;
    private int armor;
    private int kills;
    private int items;
    private int secrets;
    private int weapon;
    private HashMap<String, Boolean> keyCards;
    private HashMap<String, Boolean> cheatFlags;

    public Player(int id, HashMap<String, Double> position,
                  int angle, int height, int health, int typeId,
                  String type, HashMap<String, Boolean> flags,
                  int armor, int kills, int items, int secrets,
                  int weapon, HashMap<String, Boolean> keyCards,
                  HashMap<String, Boolean> cheatFlags) {
        this.id = id;
        this.position = position;
        this.angle = angle;
        this.height = height;
        this.health = health;
        this.typeId = typeId;
        this.type = type;
        this.flags = flags;
        this.armor = armor;
        this.kills = kills;
        this.items = items;
        this.secrets = secrets;
        this.weapon = weapon;
        this.keyCards = keyCards;
        this.cheatFlags = cheatFlags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<String, Double> getPosition() {
        return position;
    }

    public void setPosition(HashMap<String, Double> position) {
        this.position = position;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Boolean> getFlags() {
        return flags;
    }

    public void setFlags(HashMap<String, Boolean> flags) {
        this.flags = flags;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public int getSecrets() {
        return secrets;
    }

    public void setSecrets(int secrets) {
        this.secrets = secrets;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public HashMap<String, Boolean> getKeyCards() {
        return keyCards;
    }

    public void setKeyCards(HashMap<String, Boolean> keyCards) {
        this.keyCards = keyCards;
    }

    public HashMap<String, Boolean> getCheatFlags() {
        return cheatFlags;
    }

    public void setCheatFlags(HashMap<String, Boolean> cheatFlags) {
        this.cheatFlags = cheatFlags;
    }
}

