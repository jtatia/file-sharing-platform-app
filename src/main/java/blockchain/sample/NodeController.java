package blockchain.sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sg.edu.ntu.sce.sands.crypto.dcpabe.*;
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

    @FXML
    public CheckBox pkcheckbox;

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
        ret = ret.substring(ret.indexOf(" "));
        String hash = ret.substring(ret.indexOf(" ")+1,ret.substring(ret.indexOf(" ")+1)
                .indexOf(file.getName()));
        //Encrypting Key
        String accessPolicy = textAccessPolicy.getText();
        //building access policy
        AccessStructure as = AccessStructure.buildFromPolicy(accessPolicy);
        GlobalParameters gp = Helper.getGlobalParams();
        PublicKeys pks = Helper.getPublicKeys(node);
        String paddedKey = Helper.generateLengthMessage(98)+symm;
        Message m = new Message(paddedKey.getBytes());
        Ciphertext ciphertext = DCPABE.encrypt(m, as, gp, pks);
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
        if (pkcheckbox.isSelected()) {
            node.getAttributePKList().add(RestHelper.getAttribute(textAttribute.getText()));
            textAAId.clear();
            textAttribute.clear();
        }
        else {
            RestHelper.requestAttributeTransaction(node.getNodeId(), textAAId.getText(), textAttribute.getText());
            textAAId.clear();
            textAttribute.clear();
        }
    }

    @FXML
    public void refresh(ActionEvent actionEvent)throws Exception {
        Node node1 = RestHelper.getNode(node.getNodeId());
        try {
            node.setAttributePKList(node1.getAttributePKList());
            node.setAttributeSKNameList(node1.getAttributeSKNameList());
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
