package tim03we.bungeeforms.bungee.simple;

import tim03we.bungeeforms.bungee.ElementButtonImageData;

public class ElementButton {

    private String text = "";
    private ElementButtonImageData image;

    public ElementButton(String text) {
        this.text = text;
    }

    public ElementButton(String text, ElementButtonImageData image) {
        this.text = text;
        if (!image.getData().isEmpty() && !image.getType().isEmpty()) {
            this.image = image;
        }

    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ElementButtonImageData getImage() {
        return this.image;
    }

    public void addImage(ElementButtonImageData image) {
        if (!image.getData().isEmpty() && !image.getType().isEmpty()) {
            this.image = image;
        }

    }
}
