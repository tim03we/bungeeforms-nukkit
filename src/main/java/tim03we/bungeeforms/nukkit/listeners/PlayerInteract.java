package tim03we.bungeeforms.nukkit.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.concurrent.CompletableFuture;

public class PlayerInteract implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        CompletableFuture.runAsync(() -> {
            Player player = event.getPlayer();
            Item item = event.getItem();
            Block block = event.getBlock();

            String itemString;
            String blockString;

            if(item == null) itemString = "null";
            else {
                if(item.hasCustomName()) itemString = item.getId() + ";:" + item.getDamage() + ";:" + item.getCount() + ";:" + item.getCustomName();
                else itemString = item.getId() + ";:" + item.getDamage() + ";:" + item.getCount() + ";:" + "null";
            }
            if(block == null) blockString = "null";
            else blockString = block.getId() + ";:" + block.getDamage();
            PlayerInteractEvent.Action action = event.getAction();

            ScriptCustomEventPacket customEventPacket = new ScriptCustomEventPacket();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            try {
                if(action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                    dataOutputStream.writeUTF("right_click_block");
                } else if(action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                    dataOutputStream.writeUTF("right_click_air");
                } else if(action == PlayerInteractEvent.Action.LEFT_CLICK_AIR) {
                    dataOutputStream.writeUTF("left_click_air");
                } else if(action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                    dataOutputStream.writeUTF("left_click_block");
                } else if(action == PlayerInteractEvent.Action.PHYSICAL) {
                    dataOutputStream.writeUTF("physical");
                }
                dataOutputStream.writeUTF(itemString);
                dataOutputStream.writeUTF(blockString);
                dataOutputStream.writeUTF(player.getName());
                customEventPacket.eventName = "interact:event";
                customEventPacket.eventData = outputStream.toByteArray();
                player.dataPacket(customEventPacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
