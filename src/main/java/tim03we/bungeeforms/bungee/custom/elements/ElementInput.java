package tim03we.bungeeforms.bungee.custom.elements;

import com.google.gson.annotations.SerializedName;

public class ElementInput extends Element {

    private final String type;
    private String text;
    private String placeholder;
    @SerializedName("default")
    private String defaultText;

    public ElementInput(String text) {
        this(text, "");
    }

    public ElementInput(String text, String placeholder) {
        this(text, placeholder, "");
    }

    public ElementInput(String text, String placeholder, String defaultText) {
        this.type = "input";
        this.text = "";
        this.placeholder = "";
        this.defaultText = "";
        this.text = text;
        this.placeholder = placeholder;
        this.defaultText = defaultText;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlaceHolder() {
        return this.placeholder;
    }

    public void setPlaceHolder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getDefaultText() {
        return this.defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }
}
