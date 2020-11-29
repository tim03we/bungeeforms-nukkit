package tim03we.bungeeforms.bungee;

public class ElementButtonImageData {

    public static final String IMAGE_DATA_TYPE_PATH = "path";
    public static final String IMAGE_DATA_TYPE_URL = "url";
    private String type;
    private String data;

    public ElementButtonImageData(String type, String data) {
        if (type.equals("url") || type.equals("path")) {
            this.type = type;
            this.data = data;
        }
    }

    public String getType() {
        return this.type;
    }

    public String getData() {
        return this.data;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }
}
