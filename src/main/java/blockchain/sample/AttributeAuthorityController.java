package blockchain.sample;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class AttributeAuthorityController {

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private TextArea list;

    @FXML
    private TextField lregister;

    @FXML
    private TextField lrevoke;

    @FXML
    private Button bregister;

    @FXML
    private Button brevoke;

    private AttributeAuthority authority;

    @FXML
    public void registerAttribute(ActionEvent actionEvent) {}

    @FXML
    public void revokeAttribute(ActionEvent actionEvent) {}

    public void initialize(AttributeAuthority authority) {
        this.authority = authority;
        id.setText(authority.getaId());
        name.setText(authority.getaName());
        String attrList = "";
        for (Attribute a : authority.getAttributeList())
            attrList += a.getAttrName()+", ";
        list.setText(attrList);
        Thread th = new Thread(() -> {
            back();
        });
        th.setDaemon(true);
        th.start();
}

private int size =0;

public void back() {
    while (true) {
        try {
            ArrayList<RAT> arr = RestHelper.getRATransactions();
            for (int i=size;i<arr.size();i++) {
                String AA  = arr.get(i).aAuthority.substring(arr.get(i).aAuthority.indexOf("#")+1);
                String N = arr.get(i).node.substring(arr.get(i).node.indexOf("#")+1);
                String A = arr.get(i).attribute.substring(arr.get(i).attribute.indexOf("#")+1);
                String skey = "";
                System.out.println("HERE::::::;;;::"+AA+" $$ "+N+" $$ "+A+"\n");
                if (AA.equals(authority.getaId())) {
                    for (Attribute a : authority.getAttributeList()) {
                        if (A.equals(a.getAttrName())) {
                            skey = a.getsKey();
                            break;
                        }
                    }
                    RestHelper.grantAttributeTransaction(N, authority.getaId(), skey, A);
                }
                size = arr.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}

