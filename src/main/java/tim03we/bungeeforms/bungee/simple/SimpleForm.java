package tim03we.bungeeforms.bungee.simple;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tim03we.bungeeforms.bungee.FormHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleForm {

    private final LinkedHashMap<ElementButton, Consumer<ProxiedPlayer>> buttons;
    private final String title, content, id;
    private final Consumer<ProxiedPlayer> closeCallback;
    private final BiConsumer<String, String> submitCallback;

    public SimpleForm(Builder b) {
        this.buttons = b.buttons;
        this.id = String.valueOf(new Random().nextInt(1000000));
        this.title = b.title;
        this.content = b.content;
        this.closeCallback = b.closeCallback;
        this.submitCallback = b.submitCallback;
    }

    public void send(ProxiedPlayer player) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        // perform a check to see if globally are no players
        if(networkPlayers == null || networkPlayers.isEmpty()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(player.getName() + ";" + id + ";" + title + ";" + content + ";#");
        for (ElementButton elementButton : buttons.keySet()) {
            stringBuilder.append(elementButton.getText() + ";image:");
            if(elementButton.getImage() != null) {
                stringBuilder.append(elementButton.getImage().getType() + ":");
                stringBuilder.append(elementButton.getImage().getData() + ",");
            } else stringBuilder.append("null:null,");
        }
        //System.out.println("Buttons: " + stringBuilder.toString());
        FormHandler.simplePending.put(id, this);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("simpleform"); // the channel could be whatever you want
        out.writeUTF(stringBuilder.toString()); // this data could be whatever you want
        player.getServer().getInfo().sendData( "send:form", out.toByteArray() );
    }

    public void setClosed(ProxiedPlayer player) {
        if (closeCallback == null) return;
        closeCallback.accept(player);
    }

    public void setSubmitted(String p, String r) {
        if (submitCallback == null) return;
        submitCallback.accept(p, r);
    }

    public HashMap<ElementButton, Consumer<ProxiedPlayer>> getButtons() {
        return buttons;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static class Builder {

        private final LinkedHashMap<ElementButton, Consumer<ProxiedPlayer>> buttons = new LinkedHashMap<>();
        private String title;
        private String content;
        private String id;
        private Consumer<ProxiedPlayer> closeCallback;
        private BiConsumer<String, String> submitCallback;


        public Builder(String title, String content) {
            this.title = title;
            this.content = content;
            this.id = String.valueOf(new Random().nextInt(1000000));
        }

        public Builder onClose(Consumer<ProxiedPlayer> close) {
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

        public Builder addButton(ElementButton button, Consumer<ProxiedPlayer> callback) {
            buttons.put(button, callback);
            return this;
        }

        public Builder onSubmit(BiConsumer<String, String> r) {
            submitCallback = r;
            return this;
        }

        public SimpleForm build() {
            return new SimpleForm(this);
        }

    }

}
