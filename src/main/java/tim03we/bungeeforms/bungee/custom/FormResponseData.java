package tim03we.bungeeforms.bungee.custom;

public class FormResponseData {

    private final int elementID;
    private final String elementContent;

    public FormResponseData(int id, String content) {
        this.elementID = id;
        this.elementContent = content;
    }

    public int getElementID() {
        return this.elementID;
    }

    public String getElementContent() {
        return this.elementContent;
    }
}
