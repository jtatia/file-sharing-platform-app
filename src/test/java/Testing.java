import blockchain.sample.Helper;
import blockchain.sample.SymmetricKey;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sg.edu.ntu.sce.sands.crypto.dcpabe.*;
import sg.edu.ntu.sce.sands.crypto.dcpabe.ac.AccessStructure;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PersonalKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.PublicKey;
import sg.edu.ntu.sce.sands.crypto.dcpabe.key.SecretKey;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(JUnit4.class)
public class Testing {
    @Test
    public void testDCPABE2() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        GlobalParameters gp = DCPABE.globalSetup(160);
        PublicKeys publicKeys = new PublicKeys();
        AuthorityKeys authority1 = DCPABE.authoritySetup("a1", gp, "a", "b");
        publicKeys.subscribeAuthority(authority1.getPublicKeys());
        AuthorityKeys authority2 = DCPABE.authoritySetup("a2", gp, "c", "d");
        publicKeys.subscribeAuthority(authority2.getPublicKeys());
        PublicKey p = authority1.getPublicKeys().get("a");
        PersonalKeys pkeys = new PersonalKeys("user");
        SecretKey k = authority1.getSecretKeys().get("a");
        pkeys.addKey(DCPABE.keyGen("user", "a", authority1.getSecretKeys().get("a"), gp));
        pkeys.addKey(DCPABE.keyGen("user", "d", authority2.getSecretKeys().get("d"), gp));

        AccessStructure as = AccessStructure.buildFromPolicy("and a or d and b c");

        Message message = DCPABE.generateRandomMessage(gp);
        SymmetricKey symmetricKey = new SymmetricKey("block",16, "AES");
        String key = symmetricKey.getKey();
     //   System.out.println(key.length());
        key = Helper.generateLengthMessage(98) + key;
     //   System.out.println(new String(key.getBytes()));
        Message message1 = new Message(key.getBytes());
      //  System.out.println(new String(message1.m));
       // System.out.println(message1.m.length);
        Ciphertext ct = DCPABE.encrypt(message1, as, gp, publicKeys);
        Gson g= new Gson();
        String x = g.toJson(ct);
        Ciphertext ciphertext = g.fromJson(x, Ciphertext.class);
        System.out.println(ciphertext.toString());
        Message dmessage = DCPABE.decrypt(ct, pkeys, gp, as);
        //System.out.println(new String(dmessage.m).su.bstring(98));
        ;
    }

    @Test
    public void generateTest() {
        GlobalParameters gp = DCPABE.globalSetup(160);
        try {
            FileOutputStream f = new FileOutputStream(new File("gpp.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(gp);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    @Test
    public void testDCPABE1() {
        GlobalParameters gp = DCPABE.globalSetup(160);

        PublicKeys publicKeys = new PublicKeys();

        AuthorityKeys authority0 = DCPABE.authoritySetup("a1", gp, "a", "b", "c", "d");
        publicKeys.subscribeAuthority(authority0.getPublicKeys());

        AccessStructure as = AccessStructure.buildFromPolicy("and a or d and b c");

        PersonalKeys pkeys = new PersonalKeys("user");
        PersonalKey k_user_a = DCPABE.keyGen("user", "a", authority0.getSecretKeys().get("a"), gp);
        PersonalKey k_user_d = DCPABE.keyGen("user", "d", authority0.getSecretKeys().get("d"), gp);
        pkeys.addKey(k_user_a);
        pkeys.addKey(k_user_d);

        Message message = DCPABE.generateRandomMessage(gp);
        Ciphertext ct = DCPABE.encrypt(message, as, gp, publicKeys);

        Message dMessage = DCPABE.decrypt(ct, pkeys, gp);

        System.out.println("M(" + message.m.length + ") = " + Arrays.toString(message.m));
        System.out.println("DM(" + dMessage.m.length + ") = " + Arrays.toString(dMessage.m));

        assertArrayEquals(message.m, dMessage.m);
    }

    @Test
    public void testAS() {
        AccessStructure as1 = AccessStructure.buildFromPolicy("and a or d and b c");
        as1.printPolicy();
        as1.printMatrix();

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("a");
        attributes.add("d");

        AccessStructure as2 = AccessStructure.buildFromPolicy("and or d and b c a");
        as2.printPolicy();
        as2.printMatrix();

        AccessStructure as3 = AccessStructure.buildFromPolicy("and or a b and c d");
        as3.printPolicy();
        as3.printMatrix();
    }

    @Test
    public void testBilinearity() {
        SecureRandom random = new SecureRandom("12345".getBytes());
        Pairing pairing = PairingFactory.getPairing(new TypeACurveGenerator(random, 181, 603, true).generate());

        Element g1 = pairing.getG1().newRandomElement().getImmutable();
        Element g2 = pairing.getG2().newRandomElement().getImmutable();

        Element a = pairing.getZr().newRandomElement().getImmutable();
        Element b = pairing.getZr().newRandomElement().getImmutable();

        Element ga = g1.powZn(a);
        Element gb = g2.powZn(b);

        Element gagb = pairing.pairing(ga, gb);

        Element ggab = pairing.pairing(g1, g2).powZn(a.mulZn(b));

        assertTrue(gagb.isEqual(ggab));
    }

}
