package blockchain.sample;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.SecretKey;

import java.util.ArrayList;
import java.util.Map;

public class AttributeAuthority {
    private String aId;
    private String aName;
    private ArrayList<Attribute> attributeList;
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
/**
    public Map<String, PublicKey> getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(Map<String, PublicKey> publicKeys) {
        this.publicKeys = publicKeys;
    }

    public Map<String, SecretKey> getSecretKeys() {
        return secretKeys;
    }

    public void setSecretKeys(Map<String, SecretKey> secretKeys) {
        this.secretKeys = secretKeys;
    }
 */
}
