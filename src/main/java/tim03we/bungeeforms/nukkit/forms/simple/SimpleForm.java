package tim03we.bungeeforms.nukkit.forms.simple;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import tim03we.bungeeforms.nukkit.forms.FormAPI;
import tim03we.bungeeforms.nukkit.forms.FormHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleForm {

    private final LinkedHashMap<ElementButton, Consumer<Player>> buttons;
    private final String title, content;
    private final Consumer<Player> closeCallback;
    private final BiConsumer<Player, FormResponseSimple> submitCallback;

    public SimpleForm(Builder b) {
        this.buttons = b.buttons;
        this.title = b.title;
        this.content = b.content;
        this.closeCallback = b.closeCallback;
        this.submitCallback = b.submitCallback;
    }

    public void send(Player player) {
        FormWindowSimple form = new FormWindowSimple(title, content);
        buttons.keySet().forEach((form::addButton));

        FormHandler.simplePending.put(player.getName(), this);

        player.showFormWindow(form);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title + ";" + content + ";");
        for (ElementButton elementButton : buttons.keySet()) {
            stringBuilder.append(elementButton.getText() + ";image:");
            if(elementButton.getImage() != null) {
                stringBuilder.append(elementButton.getImage().getType() + ":");
                stringBuilder.append(elementButton.getImage().getData() + ",");
            } else stringBuilder.append("null:null,");
        }
        //System.out.println("Buttons: " + stringBuilder.toString());
        Server.getInstance().getScheduler().scheduleDelayedTask(FormAPI.instance, () -> player.sendExperience(player.getExperience()), 20);
    }

    public void setClosed(Player player) {
        if (closeCallback == null) return;
        closeCallback.accept(player);
    }

    public void setSubmitted(Player p, FormResponseSimple r) {
        if (submitCallback == null) return;
        submitCallback.accept(p, r);
    }

    public HashMap<ElementButton, Consumer<Player>> getButtons() {
        return buttons;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static class Builder {

        private final LinkedHashMap<ElementButton, Consumer<Player>> buttons = new LinkedHashMap<>();
        private String title;
        private String content;
        private Consumer<Player> closeCallback;
        private BiConsumer<Player, FormResponseSimple> submitCallback;


        public Builder(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public Builder onClose(Consumer<Player> close) {
            closeCallback = close;
            return this;
        }

        public Builder addButton(ElementButton button) {
            buttons.put(button, null);
            return this;
        }

        public Builder setTitle(String s) {
            title = s;
            return this;
        }

        public Builder setContent(String s) {
            content = s;
            return this;
        }

        public Builder addButton(ElementButton button, Consumer<Player> callback) {
            buttons.put(button, callback);
            return this;
        }

        public Builder onSubmit(BiConsumer<Player, FormResponseSimple> r) {
            submitCallback = r;
            return this;
        }

        public SimpleForm build() {
            return new SimpleForm(this);
        }

    }

}
