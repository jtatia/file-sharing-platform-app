package blockchain.sample;

import sg.edu.ntu.sce.sands.crypto.dcpabe.AuthorityKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.SecretKey;

import java.util.ArrayList;
import java.util.Map;

public class AttributeAuthority {
    private String aId;
    private String aName;
    private ArrayList<Attribute> attributeList;
    private AuthorityKeys authorityKeys;
    //private Map<String, PublicKey> publicKeys;
    //private Map<String, SecretKey> secretKeys;

    public AttributeAuthority() {
        attributeList = new ArrayList<>();
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public ArrayList<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(ArrayList<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public AuthorityKeys getAuthorityKeys() {
        return authorityKeys;
    }

    public void setAuthorityKeys(AuthorityKeys authorityKeys) {
        this.authorityKeys = authorityKeys;
    }
}
