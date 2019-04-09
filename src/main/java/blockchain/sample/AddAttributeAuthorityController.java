package blockchain.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.base.Splitter;

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
        for (String a : attributes) {
            Attribute attribute = new Attribute();
            System.out.println("A==="+a);
            attribute.setAttrName(a);
            attribute.setpKey(a);
            attribute.setsKey("k");  //Temporary
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
