import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DoomMain {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String BASE_URL = "http://localhost:6666/api/";

    private ArrayList<Door> passedDoors;

    public static void main(String[] args) throws Exception {
        DoomMain doomMain = new DoomMain();

        Player player = doomMain.getPlayer();
        System.out.println("ID: " + player.getId());
        System.out.println("Health: " + player.getHealth());

        // Get all doors for a level
        List<Door> doors = doomMain.getDoors();
        System.out.println(doors.toString());

        Door door = doomMain.getDoor(151);// First door in the level
        System.out.println("ID: " + door.getId());
        System.out.println("State: " + door.getState());

        doomMain.postAction(Actions.SHOOT.action);
        doomMain.postAction(Actions.LEFT.action);
        doomMain.postAction(Actions.FORWARD.action);
        doomMain.postAction(Actions.FORWARD.action);
        doomMain.postAction(Actions.FORWARD.action);
        doomMain.postAction(Actions.RIGHT.action);
        doomMain.postAction(Actions.FORWARD.action);
        doomMain.postAction(Actions.FORWARD.action);

        try{
            Door closestDoor = doomMain.getClosestDoor();
            System.out.println("ID: " + closestDoor.getId());
            System.out.println("State: " + closestDoor.getState());
        } catch (NoDoorsLeftException ex) {
            System.out.println("Out of doors, I'd better go find that button");
        }
    }

    private Player getPlayer() throws Exception {
        URL obj = new URL(BASE_URL + "player");
        StringBuffer response = getResponse(obj);

        Gson gson = new Gson();
        Player player = gson.fromJson(response.toString(), Player.class);
        return player;
    }

    private Door getDoor(int id) throws Exception {
        URL obj = new URL(BASE_URL + "world/doors/" + id);
        StringBuffer response = getResponse(obj);

        Gson gson = new Gson();
        Door door = gson.fromJson(response.toString(), Door.class);
        return door;
    }

    private Door getClosestDoor() throws IOException, NoDoorsLeftException {
        List<Door> doorList = getDoors();

        return doorList.stream()
                .filter(door -> !passedDoors.contains(door))
                .min(Comparator.comparing(Door::getDistance))
                .orElseThrow(NoDoorsLeftException::new);
    }

    private List<Door> getDoors() throws IOException {
        URL obj = new URL(BASE_URL + "world/doors");
        StringBuffer response = getResponse(obj);

        Type doorListType = new TypeToken<ArrayList<Door>>(){}.getType();
        return new Gson().fromJson(response.toString(), doorListType);
    }

    private String postAction(String action) throws Exception {
        URL obj = new URL(BASE_URL + "player/actions");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        String jsonInputString = "{\"type\": \"" + action + "\"}";

        OutputStream os = con.getOutputStream();
        byte[] input = jsonInputString.getBytes("utf-8");
        os.write(input, 0, input.length);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        return response.toString();
    }

    private StringBuffer getResponse(URL obj) throws IOException {
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }

    private class NoDoorsLeftException extends Exception {
        NoDoorsLeftException() {
            super();
        }
    }
}
