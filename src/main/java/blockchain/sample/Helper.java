package blockchain.sample;

import sg.edu.ntu.sce.sands.crypto.dcpabe.GlobalParameters;
import sg.edu.ntu.sce.sands.crypto.dcpabe.PublicKeys;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static GlobalParameters getGlobalParams()throws Exception {
        FileInputStream fi = new FileInputStream(new File("gpp.txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
        GlobalParameters pr1 = (GlobalParameters) oi.readObject();

        oi.close();
        fi.close();
        return  pr1;
    }

    public static String generateLengthMessage(int length) {
        String string = "";
        for (int i=0;i<length;i++)
            string += "D";
        return string;
    }

    public static PublicKeys getPublicKeys(Node node) {
        PublicKeys p = new PublicKeys();
        Map<String, PublicKey> keyMap = new HashMap<>();
        for (Attribute a : node.getAttributePKList()) {
            keyMap.put(a.getAttrName(), a.getpKey());
        }
        p.subscribeAuthority(keyMap);
        return p;
    }
}
