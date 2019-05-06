package blockchain.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.base.Splitter;
import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.DCPABE;
import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;

public class AddAttributeAuthorityController {

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField attr;

    @FXML
    public void createAA(ActionEvent actionEvent) throws Exception {
        AttributeAuthority authority = new AttributeAuthority();
        authority.setaId(id.getText());
        authority.setaName(name.getText());
        Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();
        Iterable<String> attributes =  SPLITTER.split(attr.getText());
        ArrayList<String> stringlist = new ArrayList<>();
        for (String a : attributes) {
            stringlist.add(a);
        }
        /**
         * Creating AKeys
         */
        GlobalParameters globalParameters = Helper.getGlobalParams();
        AuthorityKeys authorityKeys = DCPABE.authoritySetup(authority.getaId(),
                globalParameters, stringlist);
        authority.setAuthorityKeys(authorityKeys);
        for (String a : attributes) {
            /**
             * Creating corresponding attributes objects
             */
            Attribute attribute = new Attribute();
            attribute.setAttrName(a);
            attribute.setpKey(authorityKeys.getPublicKeys().get(a));
            RestHelper.addAttribute(attribute);
            if (authority.getAttributeList().isEmpty()) {
                ArrayList<Attribute> arrayList = new ArrayList<>(Arrays.asList(attribute));
                authority.setAttributeList(arrayList);
            } else {
                ArrayList<Attribute> arrayList = authority.getAttributeList();
                arrayList.add(attribute);
                authority.setAttributeList(arrayList);
            }
        }
//        authority.setAttributeList(new ArrayList<>(Arrays.asList(attr.getText())));
        RestHelper.addAttributeAuthority(authority);
        Main.showAttributeAuthority(authority);
    }
}
