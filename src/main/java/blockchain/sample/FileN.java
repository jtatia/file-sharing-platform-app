package blockchain.sample;

public class FileN {
    public String fileId;
    public String encryptedHash;
    public String encryptedKey;
    public String serializedAccessPolicy;
    public String version;
    public String owner;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getEncryptedHash() {
        return encryptedHash;
    }

    public void setEncryptedHash(String encryptedHash) {
        this.encryptedHash = encryptedHash;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public String getSerializedAccessPolicy() {
        return serializedAccessPolicy;
    }

    public void setSerializedAccessPolicy(String serializedAccessPolicy) {
        this.serializedAccessPolicy = serializedAccessPolicy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
