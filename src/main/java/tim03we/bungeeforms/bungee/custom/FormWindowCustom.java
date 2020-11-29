package tim03we.bungeeforms.bungee.custom;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tim03we.bungeeforms.bungee.ElementButtonImageData;
import tim03we.bungeeforms.bungee.FormWindow;
import tim03we.bungeeforms.bungee.custom.elements.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FormWindowCustom extends FormWindow {

    private final String type;
    private String title;
    private ElementButtonImageData icon;
    private List<Element> content;
    private FormResponseCustom response;

    public FormWindowCustom(String title) {
        this(title, new ArrayList());
    }

    public FormWindowCustom(String title, List<Element> contents) {
        this(title, contents, (ElementButtonImageData)null);
    }

    public FormWindowCustom(String title, List<Element> contents, String icon) {
        this(title, contents, icon.isEmpty() ? null : new ElementButtonImageData("url", icon));
    }

    public FormWindowCustom(String title, List<Element> contents, ElementButtonImageData icon) {
        this.type = "custom_form";
        this.title = "";
        this.title = title;
        this.content = contents;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Element> getElements() {
        return this.content;
    }

    public void addElement(Element element) {
        this.content.add(element);
    }

    public ElementButtonImageData getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        if (!icon.isEmpty()) {
            this.icon = new ElementButtonImageData("url", icon);
        }

    }

    public void setIcon(ElementButtonImageData icon) {
        this.icon = icon;
    }

    public FormResponseCustom getResponse() {
        return this.response;
    }

    public void setResponse(String data) {
        if (data.equals("null")) {
            this.closed = true;
        } else {
            List<String> elementResponses = (List)(new Gson()).fromJson(data, (new TypeToken<List<String>>() {
            }).getType());
            int i = 0;
            HashMap<Integer, FormResponseData> dropdownResponses = new HashMap();
            HashMap<Integer, String> inputResponses = new HashMap();
            HashMap<Integer, Float> sliderResponses = new HashMap();
            HashMap<Integer, FormResponseData> stepSliderResponses = new HashMap();
            HashMap<Integer, Boolean> toggleResponses = new HashMap();
            HashMap<Integer, Object> responses = new HashMap();
            HashMap<Integer, String> labelResponses = new HashMap();

            for(Iterator var11 = elementResponses.iterator(); var11.hasNext(); ++i) {
                String elementData = (String)var11.next();
                if (i >= this.content.size()) {
                    break;
                }

                Element e = (Element)this.content.get(i);
                if (e == null) {
                    break;
                }

                if (e instanceof ElementLabel) {
                    labelResponses.put(i, ((ElementLabel)e).getText());
                    responses.put(i, ((ElementLabel)e).getText());
                } else {
                    Object answer;
                    if (e instanceof ElementDropdown) {
                        answer = (String)((ElementDropdown)e).getOptions().get(Integer.parseInt(elementData));
                        dropdownResponses.put(i, new FormResponseData(Integer.parseInt(elementData), (String) answer));
                        responses.put(i, answer);
                    } else if (e instanceof ElementInput) {
                        inputResponses.put(i, elementData);
                        responses.put(i, elementData);
                    } else if (e instanceof ElementSlider) {
                        answer = Float.parseFloat(elementData);
                        sliderResponses.put(i, (Float) answer);
                        responses.put(i, answer);
                    } else if (e instanceof ElementStepSlider) {
                        answer = (String)((ElementStepSlider)e).getSteps().get(Integer.parseInt(elementData));
                        stepSliderResponses.put(i, new FormResponseData(Integer.parseInt(elementData), (String) answer));
                        responses.put(i, answer);
                    } else if (e instanceof ElementToggle) {
                        answer = Boolean.parseBoolean(elementData);
                        toggleResponses.put(i, (Boolean) answer);
                        responses.put(i, answer);
                    }
                }
            }

            this.response = new FormResponseCustom(responses, dropdownResponses, inputResponses, sliderResponses, stepSliderResponses, toggleResponses, labelResponses);
        }
    }

    public void setElementsFromResponse() {
        if (this.response != null) {
            this.response.getResponses().forEach((i, response) -> {
                Element e = (Element)this.content.get(i);
                if (e != null) {
                    if (e instanceof ElementDropdown) {
                        ((ElementDropdown)e).setDefaultOptionIndex(((ElementDropdown)e).getOptions().indexOf(response));
                    } else if (e instanceof ElementInput) {
                        ((ElementInput)e).setDefaultText((String)response);
                    } else if (e instanceof ElementSlider) {
                        ((ElementSlider)e).setDefaultValue((Float)response);
                    } else if (e instanceof ElementStepSlider) {
                        ((ElementStepSlider)e).setDefaultOptionIndex(((ElementStepSlider)e).getSteps().indexOf(response));
                    } else if (e instanceof ElementToggle) {
                        ((ElementToggle)e).setDefaultValue((Boolean)response);
                    }
                }

            });
        }

    }
}
