package tim03we.bungeeforms.bungee.custom.elements;

public class ElementLabel extends Element {

    private final String type = "label";
    private String text = "";

    public ElementLabel(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
