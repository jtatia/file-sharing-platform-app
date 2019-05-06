package blockchain.sample;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sg.edu.ntu.sce.sands.crypto.dcpabe.DCPABE;
import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.SecretKey;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;

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

    private Set<String> set = new LinkedHashSet<>();
    public void back() {
    while (true) {
        try {
            ArrayList<RAT> arr = RestHelper.getRATransactions();
            for (int i=0;i<arr.size();i++) {
                String AA  = arr.get(i).aAuthority.substring(arr.get(i).aAuthority.indexOf("#")+1);
                String N = arr.get(i).node.substring(arr.get(i).node.indexOf("#")+1);
                String A = arr.get(i).attribute.substring(arr.get(i).attribute.indexOf("#")+1);
                GlobalParameters gp = Helper.getGlobalParams();
                if (set.contains(A+"$"+N))
                    continue;
                System.out.println("HERE::::::;;;::"+AA+"$$"+N+"$$"+A+"\n");
                if (AA.equals(authority.getaId())) {
                    //Verifying if attribute belongs to this authority. if yes generating corresponding
                    //secret key for user N
                    for (Attribute a : authority.getAttributeList()) {
                        if (A.equals(a.getAttrName())) {
                            Gson g =new Gson();

                            PersonalKey s = DCPABE.keyGen(N, A,
                                    authority.getAuthorityKeys().getSecretKeys().get(A),
                                    gp);

                            System.out.println(s.getAttribute());
                            System.out.println(s.getKey());

                            RestHelper.grantAttributeTransaction(N, authority.getaId(), g.toJson(s), A);

                            set.add(A+"$"+N);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}

