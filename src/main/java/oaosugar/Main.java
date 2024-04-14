package oaosugar;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Start program!");
        String server = "https://android-for-students.ru";
        String serverPath = "/materials/practical/hello.php";
        HashMap<String,String> map = new HashMap();
        map.put("name","Milovantseva");
        map.put("group","RIBO-01-22");
        HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath,map);
        Thread th = new Thread(httpRunnable);
        th.start();
        try {
            th.join();
        } catch (InterruptedException ex){
        }finally {
           /* try {
                FileWriter fw = new FileWriter("C:\\Users\\User\\Desktop\\tet\\resp.html");
                fw.write(httpRunnable.getResponseBody());
                fw.close();
                System.out.println("Success save response from server:"+server);
            } catch (IOException ex){
                System.out.println("Error response saving: " + ex.getMessage());
            }
            System.out.println("Response from server:"+server);
            System.out.println(httpRunnable.getResponseBody());*/
            //System.out.println(httpRunnable.getResponseBody());
            try {
                JSONObject jSONObject = new JSONObject(httpRunnable.getResponseBody());
                int result = jSONObject.getInt("result_code");
                System.out.println("Result: " + result);
                System.out.println("Type: " + jSONObject.getString("message_type"));
                System.out.println("Text: " + jSONObject.getString("message_text"));
                switch (result) {
                    case 1:
                        JSONArray jSONArray = jSONObject.getJSONArray("task_list");
                        System.out.println("Task list: ");
                        for (int i = 0; i < jSONArray.length(); i++) {
                            System.out.println((i + 1) + ")" + jSONArray.get(i));
                        }
                        break;
                    case 0:
                        //
                        break;
                    default:
                        break;
                }
            } catch (JSONException jex){
                System.out.println("Error: " + jex.getMessage());
            }
        }
    }
}