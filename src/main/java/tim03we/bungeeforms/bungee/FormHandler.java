package tim03we.bungeeforms.bungee;

import tim03we.bungeeforms.bungee.custom.CustomForm;
import tim03we.bungeeforms.bungee.simple.SimpleForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormHandler {

    public static HashMap<String, SimpleForm> simplePending = new HashMap<>();
    //public static HashMap<String, ModalForm> modalPending = new HashMap<>();
    public static HashMap<String, CustomForm> customPending = new HashMap<>();

    public static void handleSimple(String id, String input) {
        if (simplePending.containsKey(id)) {
            SimpleForm sform = simplePending.get(id);
            simplePending.remove(id);

            /*if (form.getResponse() == null) {
                sform.setClosed(player);
                return;
            }*/

            /*ElementButton clickedButton = form.getResponse().getClickedButton();

            for (Map.Entry<ElementButton, Consumer<ProxiedPlayer>> map : sform.getButtons().entrySet()) {
                if (map.getKey().getText().equalsIgnoreCase(clickedButton.getText())) {
                    if (map.getValue() != null) map.getValue().accept(player);
                    break;
                }
            }*/

            sform.setSubmitted(id, input);
        }
    }

    /*public static void handleModal(Player player, FormWindowModal form) {
        if (modalPending.containsKey(player.getName())) {
            ModalForm mform = modalPending.get(player.getName());
            modalPending.remove(player.getName());

            if (form.getResponse() == null) {
                mform.setClosed(player);
                return;
            }

            String clickedButton = form.getResponse().getClickedButtonText();
            if (clickedButton.equalsIgnoreCase(mform.getYes())) mform.setYes(player);
            if (clickedButton.equalsIgnoreCase(mform.getNo())) mform.setNo(player);

            mform.setSubmitted(player, form.getResponse());
        }
    }*/

    public static void handleCustom(String id, String input) {
        if (customPending.containsKey(id)) {
            CustomForm cform = customPending.get(id);
            customPending.remove(id);

            /*if (form.getResponse() == null) {
                cform.setClosed(player);
                return;
            }*/
            List<Object> list = new ArrayList<>();
            for (String s : input.split(";#")) {
                list.add(s);
            }
            cform.setSubmitted(id, list);
        }
    }
}
