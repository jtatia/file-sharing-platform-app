package blockchain.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AddNodeController {


    @FXML
    private TextField labelId;

    @FXML
    private TextField labelName;

    @FXML
    private Button submit;

    @FXML
    public void createNode(ActionEvent actionEvent) throws Exception {
        String url = "http://localhost:3000/api/Node";
        Node node = new Node();
        node.setNodeId(labelId.getText());
        node.setName(labelName.getText());
        node.setAttributePKList(new ArrayList<>());
        node.setAttributeKeyList(new ArrayList<>());
        node.setAttributList(new ArrayList<>());
        RestHelper.addNode(node);
        //Move to next scene
        Main.showNode(node);
    }
}
