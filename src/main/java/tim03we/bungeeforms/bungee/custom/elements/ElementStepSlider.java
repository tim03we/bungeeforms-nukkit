package tim03we.bungeeforms.bungee.custom.elements;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ElementStepSlider extends Element {

    private final String type;
    private String text;
    private List<String> steps;
    @SerializedName("default")
    private int defaultStepIndex;

    public ElementStepSlider(String text) {
        this(text, new ArrayList());
    }

    public ElementStepSlider(String text, List<String> steps) {
        this(text, steps, 0);
    }

    public ElementStepSlider(String text, List<String> steps, int defaultStep) {
        this.type = "step_slider";
        this.text = "";
        this.defaultStepIndex = 0;
        this.text = text;
        this.steps = steps;
        this.defaultStepIndex = defaultStep;
    }

    public int getDefaultStepIndex() {
        return this.defaultStepIndex;
    }

    public void setDefaultOptionIndex(int index) {
        if (index < this.steps.size()) {
            this.defaultStepIndex = index;
        }
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getSteps() {
        return this.steps;
    }

    public void addStep(String step) {
        this.addStep(step, false);
    }

    public void addStep(String step, boolean isDefault) {
        this.steps.add(step);
        if (isDefault) {
            this.defaultStepIndex = this.steps.size() - 1;
        }

    }
}
