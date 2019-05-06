package blockchain.sample;

import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Attr;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class RestHelper {

    public static final String URL = "http://localhost:3000/api";
    public static final String NODE = "resource:org.example.blockchain.Node#";
    public static final String AA = "resource:org.example.blockchain.AttributeAuthority#";
    public static final String ATTRIBUTE = "resource:org.example.blockchain.Attribute#";

    public static String addNode(Node node)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL+"/Node");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nodeId", node.getNodeId()));
        urlParameters.add(new BasicNameValuePair("nodeName", node.getName()));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();
    }
    public static String addAttributeAuthority(AttributeAuthority authority)throws Exception{
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL+"/AttributeAuthority");
        JsonArray attr = new JsonArray();
        for (Attribute a : authority.getAttributeList()) {
            attr.add(ATTRIBUTE+a.getAttrName());
        }
        System.out.println(attr);
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("aId", authority.getaId()));
        urlParameters.add(new BasicNameValuePair("attributes", attr.toString()));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();
    }

    public static String addAttribute(Attribute attribute)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL+"/Attribute");

        Gson g = new Gson();

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("attrName", attribute.getAttrName()));
        urlParameters.add(new BasicNameValuePair("pKey", g.toJson(attribute.getpKey())));
      //  urlParameters.add(new BasicNameValuePair("requesters", ""));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();
    }

    public static String addFileN(FileN fileN)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL+"/FileN");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("fileId", fileN.getFileId()));
        urlParameters.add(new BasicNameValuePair("encryptedHash", fileN.getEncryptedHash()));
        urlParameters.add(new BasicNameValuePair("encryptedKey", fileN.getEncryptedKey()));
        urlParameters.add(new BasicNameValuePair("serializedAccessPolicy", fileN.getSerializedAccessPolicy()));
        urlParameters.add(new BasicNameValuePair("version", fileN.getVersion()));
        urlParameters.add(new BasicNameValuePair("owner", NODE+fileN.getOwner()));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();
    }
    public static FileN getFileN(String fileId) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL+"/FileN/"+fileId);

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
        System.out.println(f.getEncryptedKey());
       // System.out.println("FIleN OBject"+gson.toJson(f));
        return f;
    }

    public static String requestAttributeTransaction(String nodeId, String aId, String attrName)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL+"/RequestAttributeTransaction");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("aAuthority", AA+aId));
        urlParameters.add(new BasicNameValuePair("node", NODE+nodeId));
        urlParameters.add(new BasicNameValuePair("attribute", ATTRIBUTE+attrName));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();

    }

    public static String grantAttributeTransaction(String nodeId, String aId, String sKey, String attribute)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL+"/GrantAttributeTransaction");

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("aAuthority", AA+aId));
        urlParameters.add(new BasicNameValuePair("node", NODE+nodeId));
        urlParameters.add(new BasicNameValuePair("attribute", attribute));
        urlParameters.add(new BasicNameValuePair("attributeSecretKey", sKey));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();
    }

    public static ArrayList<RAT> getRATransactions()throws Exception {
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
      //  System.out.println("GET::"+result.toString());
        Gson gson = new Gson();
        JsonParser jp = new JsonParser();
        JsonArray ja = (JsonArray) jp.parse(result.toString());
        ArrayList<RAT> res = new ArrayList<>();
        for (JsonElement j : ja) {
            RAT rat = gson.fromJson(j, RAT.class);
            res.add(rat);
        }
        return res;
    }
    public static String getGATransactions() {
        return null;
    }

    public static Node getNode(String nodeId)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL+"/Node/"+nodeId);

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
        Node f = gson.fromJson(jo, Node.class);
        return f;
    }

    public static Attribute getAttribute(String attr)throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL+"/Attribute/"+attr);

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
        Temp temp = gson.fromJson(jo, Temp.class);
        Attribute attribute = new Attribute();
        attribute.setAttrName(temp.getAttrName());
        attribute.setpKey(gson.fromJson(temp.getpKey(), PublicKey.class));
        // System.out.println("FIleN OBject"+gson.toJson(f));
        return attribute;
    }

    public class Temp {
        public String attrName;
        public String pKey;

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public String getpKey() {
            return pKey;
        }

        public void setpKey(String pKey) {
            this.pKey = pKey;
        }
    }
}
