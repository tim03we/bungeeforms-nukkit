package tim03we.bungeeforms.nukkit.forms;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;

public class FormAPI {

    public static Plugin instance;

    public static void load(Plugin plugin) {
        instance = plugin;
        Server.getInstance().getPluginManager().registerEvents(new FormListener(), instance);
    }
}
