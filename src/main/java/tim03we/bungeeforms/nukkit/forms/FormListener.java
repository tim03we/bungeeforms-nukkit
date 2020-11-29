package tim03we.bungeeforms.nukkit.forms;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;

public class FormListener implements Listener {

    @EventHandler
    public void onForm(PlayerFormRespondedEvent event) {
        if (event.getWindow() instanceof FormWindowSimple) FormHandler.handleSimple(event.getPlayer(), (FormWindowSimple) event.getWindow());
        if (event.getWindow() instanceof FormWindowCustom) FormHandler.handleCustom(event.getPlayer(), (FormWindowCustom) event.getWindow());
    }

    /*@EventHandler
    public void onChat(PlayerChatEvent event) {
        SimpleForm form = new SimpleForm.Builder("Hallo", " ")
                .addButton(new ElementButton("HalloButton"))
                .onClose((player -> player.sendMessage("Geschlossen")))
                .onSubmit(((player, formResponseSimple) -> player.sendMessage(formResponseSimple.getClickedButton().getText())))
                .build();
        form.send(event.getPlayer());
    }*/
}
