package tim03we.bungeeforms.nukkit;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.element.*;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;
import cn.nukkit.plugin.PluginBase;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import tim03we.bungeeforms.nukkit.forms.FormAPI;
import tim03we.bungeeforms.nukkit.forms.custom.CustomForm;
import tim03we.bungeeforms.nukkit.forms.simple.SimpleForm;
import tim03we.bungeeforms.nukkit.listeners.PlayerInteract;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BungeeFormsNK extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        FormAPI.load(this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    }

    public void handleSimpleForm(String input) {
        String[] ex = input.split(";");
        System.out.println(input);
        Player player = Server.getInstance().getPlayer(ex[0]);
        String id = ex[1];
        String title = ex[2];
        String content = ex[3];
        String buttonString = input.split("#")[1];
        String[] buttons = buttonString.split(",");
        SimpleForm.Builder form = new SimpleForm.Builder(title, content);
        for (String button : buttons) {
            System.out.println("BUTTON: " + button);
            String[] b = button.split(";");
            String[] image = b[1].split(":");
            if(image[1].equals("null") && image[2].equals("null")) {
                form.addButton(new ElementButton(b[0]));
            } else {
                form.addButton(new ElementButton(b[0], new ElementButtonImageData(image[1], image[2])));
            }
        }
        form.onSubmit((p, response) -> {

            ScriptCustomEventPacket customEventPacket = new ScriptCustomEventPacket();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            try {
                dataOutputStream.writeUTF("simpleform");
                dataOutputStream.writeUTF(id);
                dataOutputStream.writeUTF(p.getName());
                dataOutputStream.writeUTF(response.getClickedButton().getText());
                customEventPacket.eventName = "send:form";
                customEventPacket.eventData = outputStream.toByteArray();
                player.dataPacket(customEventPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        form.build().send(player);
    }

    public void handleCustomForm(String input) {
        String[] inp = input.split(";");
        Player player = Server.getInstance().getPlayer(inp[0]);
        CustomForm.Builder form = new CustomForm.Builder(inp[2]);
        for (String s : input.split("#")) {
            String[] data = s.split(";");
            if(data[0].equals("dropdown")) {
                List<String> list = new ArrayList<>();
                for (String s2 : data[2].split(",")) {
                    list.add(s2);
                }
                form.addElement(new ElementDropdown(data[1], list, Integer.parseInt(data[3])));
            } else if(data[0].equals("input")) {
                form.addElement(new ElementInput(data[1], data[2], data[3]));
            } else if(data[0].equals("label")) {
                form.addElement(new ElementLabel(data[1]));
            } else if(data[0].equals("slider")) {
                form.addElement(new ElementSlider(data[1], Float.parseFloat(data[2]), Float.parseFloat(data[3]), Integer.parseInt(data[4]), Float.parseFloat(data[5])));
            } else if(data[0].equals("stepslider")) {
                List<String> list = new ArrayList<>();
                for (String s2 : data[2].split(",")) {
                    list.add(s2);
                }
                form.addElement(new ElementStepSlider(data[1], list, Integer.parseInt(data[3])));
            } else if(data[0].equals("toggle")) {
                form.addElement(new ElementToggle(data[1], Boolean.parseBoolean(data[2])));
            }
        }
        form.onSubmit((p, response) -> {
            StringBuilder resultBuilder = new StringBuilder();
            for (int i : response.getResponses().keySet()) {
                resultBuilder.append(response.getResponse(i) + ";#");
            }
            String resultString = resultBuilder.toString().substring(0, (resultBuilder.toString().length() - 2));

            ScriptCustomEventPacket customEventPacket = new ScriptCustomEventPacket();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            try {
                dataOutputStream.writeUTF("customform");
                dataOutputStream.writeUTF(inp[1]);
                dataOutputStream.writeUTF(p.getName());
                dataOutputStream.writeUTF(resultString);
                customEventPacket.eventName = "send:form";
                customEventPacket.eventData = outputStream.toByteArray();
                player.dataPacket(customEventPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        form.build().send(player);
    }

    @EventHandler
    public void on(DataPacketReceiveEvent event) {
        DataPacket packet = event.getPacket();
        if(packet instanceof ScriptCustomEventPacket) {
            if(((ScriptCustomEventPacket) packet).eventName.equals("send:form")) {
                ByteArrayDataInput input = ByteStreams.newDataInput(((ScriptCustomEventPacket) packet).eventData);
                String type = input.readUTF();
                String response = input.readUTF();
                if(type.equals("simpleform")) {
                    handleSimpleForm(response);
                } else if(type.equals("customform")) {
                    handleCustomForm(response);
                }
            }
        }
    }
}
