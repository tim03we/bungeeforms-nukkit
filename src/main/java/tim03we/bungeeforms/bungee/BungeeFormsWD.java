package tim03we.bungeeforms.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import tim03we.bungeeforms.bungee.events.ProxiedPlayerInteractEvent;
import tim03we.bungeeforms.bungee.utils.Block;
import tim03we.bungeeforms.bungee.utils.InteractType;
import tim03we.bungeeforms.bungee.utils.Item;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class BungeeFormsWD extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onMessage(PluginMessageEvent event) {
        try {
            if (event.getTag().equalsIgnoreCase("send:form")) {
                DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(event.getData()));
                String type = inputStream.readUTF().toLowerCase();
                System.out.println("TYPE: " + type);
                if(type.equals("simpleform")) {
                    String id = inputStream.readUTF();
                    String playerName = inputStream.readUTF();
                    String response = inputStream.readUTF();
                    FormHandler.handleSimple(id, response);
                } else if(type.equals("customform")) {
                    String id = inputStream.readUTF();
                    String playerName = inputStream.readUTF();
                    String response = inputStream.readUTF();
                    FormHandler.handleCustom(id, response);
                }
            } else if(event.getTag().equalsIgnoreCase("interact:event")) {
                DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(event.getData()));
                String type = inputStream.readUTF();
                String[] itemString = inputStream.readUTF().split(";:");
                String[] blockString = inputStream.readUTF().split(";:");
                String playerName = inputStream.readUTF();
                Item item = new Item(itemString[0], itemString[1], itemString[2], itemString[3]);
                Block block = new Block(blockString[0], blockString[1]);
                if(type.equals("right_click_block")) {
                    ProxyServer.getInstance().getPluginManager().callEvent(new ProxiedPlayerInteractEvent(ProxyServer.getInstance().getPlayer(playerName), InteractType.RIGHT_CLICK_BLOCK, item, block));
                } else if(type.equals("right_click_air")) {
                    ProxyServer.getInstance().getPluginManager().callEvent(new ProxiedPlayerInteractEvent(ProxyServer.getInstance().getPlayer(playerName), InteractType.RIGHT_CLICK_AIR, item, block));
                } else if(type.equals("left_click_air")) {
                    ProxyServer.getInstance().getPluginManager().callEvent(new ProxiedPlayerInteractEvent(ProxyServer.getInstance().getPlayer(playerName), InteractType.LEFT_CLICK_AIR, item, block));
                } else if(type.equals("left_click_block")) {
                    ProxyServer.getInstance().getPluginManager().callEvent(new ProxiedPlayerInteractEvent(ProxyServer.getInstance().getPlayer(playerName), InteractType.LEFT_CLICK_BLOCK, item, block));
                } else if(type.equals("physical")) {
                    ProxyServer.getInstance().getPluginManager().callEvent(new ProxiedPlayerInteractEvent(ProxyServer.getInstance().getPlayer(playerName), InteractType.PHYSICAL, item, block));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
