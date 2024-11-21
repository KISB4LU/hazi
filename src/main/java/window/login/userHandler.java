package window.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class userHandler {
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
    private void writeJson(String json) throws IOException {
        FileWriter fw = new FileWriter("src/main/data/users.json");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(json);
        pw.close();

    }
    public User[] readUsers() {
        String json = null;
        try {
            json = readJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(json == null)
            return null;
        Gson gson = new GsonBuilder().create();
        User[] users = gson.fromJson(json, User[].class);
        return users;
    }
    public void writeUsers(User[] users) {
        if(users != null) {
            String json;
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
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
