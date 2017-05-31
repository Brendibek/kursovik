package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by ilya on 25.05.17.
 */
public class DBConnector {
    private static final String url = "http://airport.pe.hu/kursovik.php?command=";

    private static JSONParser parser = new JSONParser();

    public static void addUser(String email, String pass, String pass2){
        try {
            String request = DBConnector.url + "sign_up&email=" + email + "&pass=" + pass + "&rpass=" + pass2;
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "utf-8");
            InputStream response = conn.getInputStream();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static ArrayList<String[]> getQuestionsByTestId(int testId){
        String[] keys = {"id", "testId", "imageName", "answers"};
        try {
            return getData(new URL(DBConnector.url + "get_questions_data&testId=" + testId), keys);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> getQuestions(){
        String[] keys = {"id", "testId", "imageName", "answers"};
        try {
            return getData(new URL(DBConnector.url + "get_questions_data"), keys);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> getTests(){
        String[] keys = {"id", "name", "description", "creatorId"};
        try {
            return getData(new URL(DBConnector.url + "get_tests_data"), keys);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> getUserById(int id){
        String[] keys = {"id", "email", "admin", "password"};
        try {
            return getData(new URL(DBConnector.url + "get_user_byid&userId=" + id), keys);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> getUserByEmail(String email){
        String[] keys = {"id", "email", "admin", "password", "confirm"};
        try {
            return getData(new URL(DBConnector.url + "get_user_byemail&email=" + email), keys);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String[]> getUsers(){
        String[] keys = {"id", "email", "admin", "password"};
        try {
            return getData(new URL(DBConnector.url + "get_users_data"), keys);
        }catch (MalformedURLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String[]> getData(URL url, String[] keys){
        try {
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String data = reader.readLine();

            data = data.substring(1);//костыль

            reader.close();

            JSONArray jsonArray = (JSONArray) parser.parse(data);

            ArrayList<String[]> rezult = new ArrayList<>();

            for(int i = 0; i<jsonArray.size(); i++){
                JSONObject line = (JSONObject) jsonArray.get(i);
                String[] arr = new String[keys.length];

                for(int j = 0; j<keys.length; j++)
                    arr[j] = line.get(keys[j]).toString();

                rezult.add(arr);
            }

            return rezult;
        }catch (IOException ex){
            ex.printStackTrace();
        }catch (ParseException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
