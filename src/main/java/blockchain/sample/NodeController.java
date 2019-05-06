package blockchain.sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import sg.edu.ntu.sce.sands.crypto.dcpabe.*;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
        String paddedKey = Helper.generateLengthMessage(61)+symm;
        System.out.println(paddedKey);

        Message message = new Message(paddedKey.getBytes());

        Ciphertext ciphertext = DCPABE.encrypt(message, as, gp, pks);

        Gson g = new Gson();
        FileN fileN = new FileN();
        fileN.setEncryptedHash(hash);
        fileN.setEncryptedKey(g.toJson(ciphertext));
        fileN.setFileId(String.valueOf((int)(Math.random()*1000)));
        fileN.setSerializedAccessPolicy(textAccessPolicy.getText());
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

        Gson g = new Gson();

        GlobalParameters gp = Helper.getGlobalParams();

        AccessStructure as = AccessStructure.buildFromPolicy(fileN.getSerializedAccessPolicy());

        Ciphertext ct = g.fromJson(fileN.getEncryptedKey(), Ciphertext.class);

        PersonalKeys pkeys = new PersonalKeys(node.getNodeId());

        for (String secretAttrKey : node.getAttributeKeyList()) {
            System.out.println(secretAttrKey);
            pkeys.addKey(g.fromJson(secretAttrKey, PersonalKey.class));
        }

        Message dmessage = DCPABE.decrypt(ct, pkeys, gp, as);
        String  padded = new String(dmessage.m);
        String decryptedKey = padded.substring(padded.length()-24);

        System.out.println(decryptedKey);
        byte[] aesKey = Base64.getDecoder().decode(decryptedKey);
        symmetricKey.decryptFile(f,aesKey);
        System.out.println("Completed Process");
    }
    @FXML
    public void requestAttribute(ActionEvent actionEvent)throws Exception {
        if (pkcheckbox.isSelected()) {
            if (node.getAttributePKList().isEmpty()) {
                ArrayList<Attribute> arrayList = new ArrayList<>();
                arrayList.add(RestHelper.getAttribute(textAttribute.getText()));
                node.setAttributePKList(arrayList);
            } else {
                node.getAttributePKList().add(RestHelper.getAttribute(textAttribute.getText()));
            }
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
          //  node.setAttributePKList(node1.getAttributePKList());
            node.setAttributList(node1.getAttributeList());
            node.setAttributeKeyList(node1.getAttributeKeyList());
            textAttributeList.setText(node.getAttributeList().toString());
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
