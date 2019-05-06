package blockchain.sample;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BackgroundTask implements Runnable {

    public static final String URL = "http://localhost:3000/api";
    private AttributeAuthority attributeAuthority;
    private Thread t;
    public BackgroundTask(AttributeAuthority authority) {
        atomicInteger = new AtomicInteger(0);
        this.attributeAuthority = authority;
        t = new Thread();
        t.start();
    }

    private AtomicInteger atomicInteger;
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("listening");
                int size = atomicInteger.get();
                ArrayList<RAT> arr = RestHelper.getRATransactions();
                for (int i=size;i<arr.size();i++) {
                    String AA  = arr.get(i).aAuthority.substring(arr.get(i).aAuthority.indexOf("#")+1);
                    String N = arr.get(i).node.substring(arr.get(i).node.indexOf("#")+1);
                    String A = arr.get(i).attribute.substring(arr.get(i).attribute.indexOf("#")+1);
                    String skey = "";
                    if (AA.equals(attributeAuthority.getaId())) {
                        for (Attribute a : attributeAuthority.getAttributeList()) {
                            if (A.equals(a.getAttrName())) {
                                skey = "";//CHAHCHACHA
                                break;
                            }
                        }
                        RestHelper.grantAttributeTransaction(N, attributeAuthority.getaId(), skey, "");
                    }
                }
                size = arr.size();
                atomicInteger.set(arr.size());
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
