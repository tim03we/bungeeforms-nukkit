package tim03we.bungeeforms.bungee.custom.elements;

import com.google.gson.annotations.SerializedName;

public class ElementToggle extends Element {

    private final String type;
    private String text;
    @SerializedName("default")
    private boolean defaultValue;

    public ElementToggle(String text) {
        this(text, false);
    }

    public ElementToggle(String text, boolean defaultValue) {
        this.type = "toggle";
        this.text = text;
        this.defaultValue = defaultValue;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }
}
