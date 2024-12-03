package window.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import indicators.indicator;
import org.example.HistoricalData;
import window.login.adapters.ColorAdapter;
import window.login.adapters.IndicatorAdapter;
import window.login.adapters.LocalDateAdapter;
import window.trades.trade;
import window.watchlist.Element;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;

/**
 * fájlkezelő
 */
public class userHandler {
    /**
     * users.json filebol olvassa ki a nyers adatot
     * @return String
     * @throws IOException
     */
    private String readJson() throws IOException {
        StringBuilder json = new StringBuilder();

        FileReader fr = new FileReader("src/main/data/users.json");
        BufferedReader br = new BufferedReader(fr);
        while(true){
            String line;
            line = br.readLine();
            if(line == null) break;
            json.append(line);
        }
        br.close();
        return  json == null ? null : json.toString();
    }

    /**
     * users.json file-ba irja ki az adatokat
     * @param json json formátumu string
     * @throws IOException
     */
    private void writeJson(String json) throws IOException {
        FileWriter fw = new FileWriter("src/main/data/users.json");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(json);
        pw.close();

    }

    /**
     * kiolvassa a felhasználókat
     * @return
     */
    public User[] readUsers() {
        String json = null;
        try {
            json = readJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(json == null)
            return null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(indicator.class, new IndicatorAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .create();
        User[] users = gson.fromJson(json, User[].class);
        HistoricalData hd = new HistoricalData();
        for(User user : users) {
            for(trade Trade: user.getTrades()) {
                Trade.setUser(user);
            }
            for(Element e : user.getWatchlist()){
                e.setHd(hd);
            }
        }
        return users;
    }

    /**
     * elmenti a felhasználót
     * @param users felhasználó
     */
    public void writeUsers(User[] users) {
        if(users != null) {
            String json;
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(indicator.class, new IndicatorAdapter())
                    .registerTypeAdapter(Color.class, new ColorAdapter())
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            json = gson.toJson(users);
            try {
                writeJson(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
