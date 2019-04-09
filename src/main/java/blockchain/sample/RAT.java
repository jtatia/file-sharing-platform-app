package blockchain.sample;

public class RAT {
    public String aAuthority;
    public String node;
    public String attribute;
    public RAT(){}
    @Override
    public String toString() {
        return aAuthority+"\n"+node+"\n"+attribute+"\n";
    }
}