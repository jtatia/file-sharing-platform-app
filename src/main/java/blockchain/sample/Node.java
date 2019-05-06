package blockchain.sample;

import java.util.ArrayList;

public class Node {

    private String nodeId;

    private String name;

    private ArrayList<Attribute> attributePKList;
    private ArrayList<String> attributeSKNameList;

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

    public ArrayList<String> getAttributeSKNameList() {
        return attributeSKNameList;
    }

    public void setAttributeSKNameList(ArrayList<String> attributeSKNameList) {
        this.attributeSKNameList = attributeSKNameList;
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
