package tim03we.bungeeforms.bungee.custom.elements;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ElementDropdown extends Element {

    private final String type;
    private String text;
    private List<String> options;
    @SerializedName("default")
    private int defaultOptionIndex;

    public ElementDropdown(String text) {
        this(text, new ArrayList());
    }

    public ElementDropdown(String text, List<String> options) {
        this(text, options, 0);
    }

    public ElementDropdown(String text, List<String> options, int defaultOption) {
        this.type = "dropdown";
        this.text = "";
        this.defaultOptionIndex = 0;
        this.text = text;
        this.options = options;
        this.defaultOptionIndex = defaultOption;
    }

    public int getDefaultOptionIndex() {
        return this.defaultOptionIndex;
    }

    public void setDefaultOptionIndex(int index) {
        if (index < this.options.size()) {
            this.defaultOptionIndex = index;
        }
    }

    public List<String> getOptions() {
        return this.options;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addOption(String option) {
        this.addOption(option, false);
    }

    public void addOption(String option, boolean isDefault) {
        this.options.add(option);
        if (isDefault) {
            this.defaultOptionIndex = this.options.size() - 1;
        }

    }
}
