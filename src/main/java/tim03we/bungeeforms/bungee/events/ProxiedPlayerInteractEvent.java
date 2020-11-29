package tim03we.bungeeforms.bungee.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;
import tim03we.bungeeforms.bungee.utils.Block;
import tim03we.bungeeforms.bungee.utils.InteractType;
import tim03we.bungeeforms.bungee.utils.Item;

public class ProxiedPlayerInteractEvent extends Event {

    private ProxiedPlayer proxiedPlayer;
    private InteractType type;
    private Item item;
    private Block block;

    public ProxiedPlayerInteractEvent(ProxiedPlayer proxiedPlayer, InteractType type, Item item, Block block) {
        this.proxiedPlayer = proxiedPlayer;
        this.type = type;
        this.item = item;
        this.block = block;
    }

    public ProxiedPlayer getProxiedPlayer() {
        return proxiedPlayer;
    }

    public InteractType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public Block getBlock() {
        return block;
    }
}
