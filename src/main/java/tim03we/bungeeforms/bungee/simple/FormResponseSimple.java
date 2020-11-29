package tim03we.bungeeforms.bungee.simple;

public class FormResponseSimple {

    private final int clickedButtonId;
    private final ElementButton clickedButton;

    public FormResponseSimple(int clickedButtonId, ElementButton clickedButton) {
        this.clickedButtonId = clickedButtonId;
        this.clickedButton = clickedButton;
    }

    public int getClickedButtonId() {
        return this.clickedButtonId;
    }

    public ElementButton getClickedButton() {
        return this.clickedButton;
    }
}
