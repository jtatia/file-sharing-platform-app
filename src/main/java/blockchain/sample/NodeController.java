package blockchain.sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sg.edu.ntu.sce.sands.crypto.dcpabe.DCPABE;
import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;
import sg.edu.ntu.sce.sands.crypto.dcpabe.PublicKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class NodeController  {

    @FXML
    public TextArea textAccessPolicy;

    @FXML
    public Button uploadButton;

    @FXML
    public TextField uploadFileId;

    @FXML
    public TextField downloadFileId;

    @FXML
    public Button downloadButton;

    @FXML
    public Button requestButton;

    @FXML
    public TextField textAAId;

    @FXML
    public TextField textAttribute;

    @FXML
    public Label labelNodeId;

    @FXML
    public Label labelName;

    @FXML
    public TextArea textAttributeList;

    @FXML
    public Button refreshButton;

    private Node node;

    @FXML
    public void uploadFile(ActionEvent actionEvent)throws Exception {
        //Generating AES Key
        SymmetricKey symmetricKey = new SymmetricKey("block",16, "AES");
        FileChooser fileChooser = new FileChooser();
        //Choosing File
        File file = fileChooser.showOpenDialog(null);
        //Encrypting File
        String symm = symmetricKey.encryptFile(file);
        ExecuteCommands executeCommands = new ExecuteCommands();
        //Obtaining HashValue
        String ret = executeCommands.execute("ipfs add "+file.getPath());
        //Encrypting Hash and Key
        /**
        String accessPolicy = textAccessPolicy.getText();
        //building access policy
        AccessStructure as = AccessStructure.buildFromPolicy(accessPolicy);
        GlobalParameters gp = DCPABE.globalSetup(160); //should be formed globally
        PublicKeys publicKeys = new PublicKeys();
         */
        ret = ret.substring(ret.indexOf(" "));
        String hash = ret.substring(ret.indexOf(" ")+1,ret.substring(ret.indexOf(" ")+1)
                .indexOf(file.getName()));
        FileN fileN = new FileN();
        fileN.setEncryptedHash(hash);
        fileN.setEncryptedKey(symm);
        fileN.setFileId(String.valueOf((int)(Math.random()*1000)));
        fileN.setSerializedAccessPolicy("NA");
        fileN.setOwner(node.getNodeId());
        fileN.setVersion("1");
        RestHelper.addFileN(fileN);
        uploadFileId.setText(fileN.getFileId());
    }

    @FXML
    public void downloadFile(ActionEvent actionEvent) throws Exception {
        FileN fileN = RestHelper.getFileN(downloadFileId.getText());
        ExecuteCommands executeCommands = new ExecuteCommands();
        String string = executeCommands.execute("ipfs get "+fileN.getEncryptedHash());
        File f= new File(fileN.getEncryptedHash());
        SymmetricKey symmetricKey = new SymmetricKey("block",16,"AES");
        byte[] aesKey = Base64.getDecoder().decode(fileN.getEncryptedKey());
        symmetricKey.decryptFile(f,aesKey);
        System.out.println("Completed Process");
    }
    @FXML
    public void requestAttribute(ActionEvent actionEvent)throws Exception {
        RestHelper.requestAttributeTransaction(node.getNodeId(), textAAId.getText(), textAttribute.getText());
        textAAId.clear();textAttribute.clear();
    }

    @FXML
    public void refresh(ActionEvent actionEvent)throws Exception {
        Node node1 = RestHelper.getNode(node.getNodeId());
        try {
            node.setAttributeList(node1.getAttributeList());
            node.setAttributeKeyList(node1.getAttributeKeyList());
            textAttributeList.setText(node.getAttributeKeyList().toString());
        }catch (Exception e) {
            System.out.println("EXCEPTION");
            e.printStackTrace();
        }
    }

    public void initialize(Node node1) {
        this.node = node1;
        labelNodeId.setText(node.getNodeId());
        labelName.setText(node.getName());
    }
}
