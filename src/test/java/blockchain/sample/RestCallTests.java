package blockchain.sample;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RestCallTests {

    public static final String URL = "http://localhost:3000/api";
    public static final String NODE = "resource:org.example.blockchain.Node#";
    public static final String AA = "resource:org.example.blockchain.AttributeAuthority#";
    public static final String ATTRIBUTE = "resource:org.example.blockchain.Attribute#";



    @Test
    public void requestATransaction()throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL+"/RequestAttributeTransaction");

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        Gson gson = new Gson();
        JsonParser jp = new JsonParser();
        JsonArray jo = (JsonArray) jp.parse(result.toString());
        System.out.println(jo);
        System.out.println(jo.size());
        System.out.println(jo.get(jo.size()-1));
        RAT r = gson.fromJson(jo.get(0), RAT.class);
        System.out.println(r);
     }

     @Test
    public void getFileNRestTest()throws Exception {
         HttpClient client = HttpClientBuilder.create().build();
         HttpGet request = new HttpGet(URL+"/FileN/"+1);

         HttpResponse response = client.execute(request);

         System.out.println("Response Code : "
                 + response.getStatusLine().getStatusCode());

         BufferedReader rd = new BufferedReader(
                 new InputStreamReader(response.getEntity().getContent()));

         StringBuffer result = new StringBuffer();
         String line = "";
         while ((line = rd.readLine()) != null) {
             result.append(line);
         }
         System.out.println("GET::"+result.toString());
         Gson gson = new Gson();
         JsonParser jp = new JsonParser();
         JsonObject jo = (JsonObject)jp.parse(result.toString());
         FileN f = gson.fromJson(jo, FileN.class);
         System.out.println("FIleN OBject"+gson.toJson(f));
     }
}
