package tim03we.bungeeforms.bungee.custom;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tim03we.bungeeforms.bungee.FormHandler;
import tim03we.bungeeforms.bungee.custom.elements.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomForm {

    private final String title;
    private final ArrayList<Element> elements;
    private final Consumer<ProxiedPlayer> closeCallback;
    private final BiConsumer<String, List<Object>> submitCallback;
    private final String id;

    public CustomForm(Builder b) {
        this.title = b.title;
        this.elements = b.elements;
        this.closeCallback = b.closeCallback;
        this.submitCallback = b.submitCallback;
        this.id = String.valueOf(new Random().nextInt(1000000));
    }


    public void send(ProxiedPlayer player) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        // perform a check to see if globally are no players
        if(networkPlayers == null || networkPlayers.isEmpty()) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(player.getName() + ";" + id + ";" + title + ";#");
        for (Element element : elements) {
            if(element instanceof ElementDropdown) {
                ElementDropdown dropdown = (ElementDropdown) element;
                stringBuilder.append("dropdown;" + dropdown.getText() + ";" + convertToString(dropdown.getOptions()) + ";" + dropdown.getDefaultOptionIndex());
            } else if(element instanceof ElementInput) {
                ElementInput input = (ElementInput) element;
                String text = "null";
                String placeholder = "null";
                String defaultText = "null";
                if(!input.getText().isEmpty()) text = input.getText();
                if(!input.getPlaceHolder().isEmpty()) placeholder = input.getPlaceHolder();
                if(!input.getDefaultText().isEmpty()) defaultText = input.getDefaultText();
                stringBuilder.append("input;" + input.getText() + ";" + input.getPlaceHolder() + ";" + input.getDefaultText());
            } else if(element instanceof ElementLabel) {
                ElementLabel label = (ElementLabel) element;
                stringBuilder.append("label;" + label.getText());
            } else if(element instanceof ElementSlider) {
                ElementSlider slider = (ElementSlider) element;
                stringBuilder.append("slider;" + slider.getText() + ";" + slider.getMin() + ";" + slider.getMax() + ";" + slider.getStep() + ";" + slider.getDefaultValue());
            } else if(element instanceof ElementStepSlider) {
                ElementStepSlider stepSlider = (ElementStepSlider) element;
                stringBuilder.append("stepslider;" + stepSlider.getText() + ";" + convertToString(stepSlider.getSteps()) + ";" + stepSlider.getDefaultStepIndex());
            } else if(element instanceof ElementToggle) {
                ElementToggle toggle = (ElementToggle) element;
                stringBuilder.append("toggle;" + toggle.getText() + ";" + toggle.isDefaultValue());
            }
            stringBuilder.append("#");
        }
        FormHandler.customPending.put(id, this);

        String string = stringBuilder.toString().substring(0, (stringBuilder.toString().length() - 1));

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("customform"); // the channel could be whatever you want
        out.writeUTF(string); // this data could be whatever you want
        System.out.println("INP: " + string);
        player.getServer().getInfo().sendData( "send:form", out.toByteArray() );
    }

    public void setClosed(ProxiedPlayer player) {
        if (closeCallback == null) return;
        closeCallback.accept(player);
    }

    public void setSubmitted(String id, List<Object> form) {
        submitCallback.accept(id, form);
    }

    private String convertToString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringList) {
            stringBuilder.append(s).append(",");
        }
        return stringBuilder.toString().substring(0, (stringBuilder.toString().length() - 1));
    }

    public static class Builder {

        private String title;
        private Consumer<ProxiedPlayer> closeCallback;
        private BiConsumer<String, List<Object>> submitCallback;
        private String id;
        private ArrayList<Element> elements = new ArrayList<>();

        public Builder(String title) {
            this.title = title;
            this.id = String.valueOf(new Random().nextInt(1000000));
        }

        public Builder addElement(Element element) {
            elements.add(element);
            return this;
        }

        public Builder setTitle(String s) {
            title = s;
            return this;
        }

        public Builder onClose(Consumer<ProxiedPlayer> cb) {
            this.closeCallback = cb;
            return this;
        }

        public Builder onSubmit(BiConsumer<String, List<Object>> cb) {
            this.submitCallback = cb;
            return this;
        }



        public CustomForm build() {
            return new CustomForm(this);
        }

    }

}
