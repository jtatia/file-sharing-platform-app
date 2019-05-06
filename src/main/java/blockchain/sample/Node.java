package blockchain.sample;

import java.util.ArrayList;

public class Node {

    private String nodeId;

    private String name;

    private ArrayList<Attribute> attributePKList;
    private ArrayList<String> attributeList;

    private ArrayList<String> attributeKeyList;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributList(ArrayList<String> attributeList) {
        this.attributeList = attributeList;
    }

    public ArrayList<String> getAttributeKeyList() {
        return attributeKeyList;
    }

    public void setAttributeKeyList(ArrayList<String> attributeKeyList) {
        this.attributeKeyList = attributeKeyList;
    }

    public ArrayList<Attribute> getAttributePKList() {
        return attributePKList;
    }

    public void setAttributePKList(ArrayList<Attribute> attributePKList) {
        this.attributePKList = attributePKList;
    }
}
