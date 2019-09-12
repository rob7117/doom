import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
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
    private final Point startingPoint = new Point(1056, -3616, 0, 90);


    public static void main(String[] args) throws Exception {
        DoomMain doomMain = new DoomMain();

        ArrayList<Point> points = new ArrayList<>();

        final Point firstDoor = new Point(1519.973389, -2499.136963, 0, 358);
        final Point secondDoor = new Point(3004.020020, -3999.973877, -24, 270);
        final Point thirdDoor = new Point(3011.724121, -4615.993164, -24, 267);
        final Point finalButton = new Point(2928.022461, -4767.843262, -24, 175);

        points.add(firstDoor);
        points.add(secondDoor);
        points.add(thirdDoor);
        points.add(finalButton);


        Player player = doomMain.getPlayer();
        System.out.println("ID: " + player.getId());
        System.out.println("Health: " + player.getHealth());

        try{
//            Door closestDoor = doomMain.getClosestDoor(doors);
//            System.out.println("ID: " + closestDoor.getId());
//            System.out.println("State: " + closestDoor.getState());
            for (Point currentPoint : points) {
                while (playerDistanceToPointGreaterThanMin(player, currentPoint)) {
                    updatePlayerAngle(player, currentPoint, doomMain);
                    doomMain.postAction(Actions.FORWARD.action);
                }
            }


        } catch (NoDoorsLeftException ex) {
            System.out.println("Out of doors, I'd better go find that button");
        }

        // getAllDoors()

        // getClosestDoor()

        // doTrial()
            // moveForward()
            // getDistanceToClosestDoor()
            // moveBack()
            // rotate(45degrees)



    }

    private Player getPlayer() throws Exception {
        URL obj = new URL(BASE_URL + "player");
        StringBuffer response = getResponse(obj);

        Gson gson = new Gson();
        Player player = gson.fromJson(response.toString(), Player.class);
        return player;
    }

    private Door getDoor(int id) throws IOException {
        URL obj = new URL(BASE_URL + "world/doors/" + id);
        StringBuffer response = getResponse(obj);

        return new Gson().fromJson(response.toString(), Door.class);
    }

    private Door getClosestDoor(List<Door> doors) throws IOException, NoDoorsLeftException {
        return getDoor(doors.stream()
                .min(Comparator.comparing(Door::getDistance))
                .orElseThrow(NoDoorsLeftException::new).getId());
    }

    private List<Door> getDoors() throws IOException {
        URL obj = new URL(BASE_URL + "world/doors");
        StringBuffer response = getResponse(obj);

        Type doorListType = new TypeToken<ArrayList<Door>>(){}.getType();
        return new Gson().fromJson(response.toString(), doorListType);
    }

    private static boolean playerDistanceToPointGreaterThanMin(Player player, Point point) {
        return true;
    }

    private static void updatePlayerAngle(Player player, Point point, DoomMain doomMain) throws Exception {
        Long relativeAngle = Math.round((point.y - player.getPosition().get("y")) / point.x - (player.getPosition().get("x")));

        while(relativeAngle > 12) {
            doomMain.postAction(Actions.RIGHT.action);
            relativeAngle = Math.round((point.y - player.getPosition().get("y")) / point.x - (player.getPosition().get("x")));
        }
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
