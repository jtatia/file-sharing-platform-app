package blockchain.sample;

import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.SecretKey;

public class Attribute {

    private  String attrName;
    private PublicKey pKey;

    public Attribute() {
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public PublicKey getpKey() {
        return pKey;
    }

    public void setpKey(PublicKey pKey) {
        this.pKey = pKey;
    }


}
