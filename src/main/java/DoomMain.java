import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoomMain {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String BASE_URL = "http://localhost:6666/api/";

    public static void main(String[] args) throws Exception {
        DoomMain doomMain = new DoomMain();

        Player player = doomMain.getPlayer();
        System.out.println("ID: " + player.getId());
        System.out.println("Health: " + player.getHealth());

        System.out.println(doomMain.postAction(Actions.SHOOT.action));
    }

    private Player getPlayer() throws Exception {
        URL obj = new URL(BASE_URL + "player");
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

        Gson gson = new Gson();
        Player player = gson.fromJson(response.toString(), Player.class);
        return player;
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

}
