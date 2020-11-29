# BungeeForms
Send Simple Forms and Custom Forms from Bungeecord to Nukkit. Simplify your systems and much more. 

## Important
Note that the plugin must be installed on both servers. So once on the Bungeecord and Nukkit server. 

## Example
Please note that you take the package `tim03we.bungeeforms.bungee` and not the one from Nukkit.
```java
public class WaterdogTest extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void on(ProxiedPlayerInteractEvent event) {
        ProxiedPlayer proxiedPlayer = event.getProxiedPlayer();
        if(event.getType() == InteractType.RIGHT_CLICK_AIR) {
            if(event.getItem().getId() == 345) {
                proxiedPlayer.sendMessage("Item: Compass");
            }
        }
    }

    public void sendSimpleForm(ProxiedPlayer proxiedPlayer) {
        SimpleForm.Builder form = new SimpleForm.Builder("My title", "My content");
        form.addButton(new ElementButton("Button 1"));
        form.addButton(new ElementButton("Button 2"));
        form.onSubmit((p, response) -> {
            proxiedPlayer.sendMessage("Clicked button: " + response);
        });
        form.build().send(proxiedPlayer);
    }

    public void sendCustomForm(ProxiedPlayer proxiedPlayer) {
        CustomForm.Builder form = new CustomForm.Builder("My title");

        List<String> dropdownList = new ArrayList<>();
        dropdownList.add("Select 1");
        dropdownList.add("Select 2");

        form.addElement(new ElementDropdown("Dropdown Menu", dropdownList, 0)); // index 0
        form.addElement(new ElementInput("Input Menu", "Input placeholder", "Input default text")); // index 1
        form.onSubmit((p, response) -> {
            proxiedPlayer.sendMessage("Dropdown: " + response.get(/* index */ 0));
            proxiedPlayer.sendMessage("Input: " + response.get(/* index */ 1));
        });
        form.build().send(proxiedPlayer);
    }
}
```