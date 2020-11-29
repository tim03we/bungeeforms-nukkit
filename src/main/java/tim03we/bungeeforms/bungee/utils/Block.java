package tim03we.bungeeforms.bungee.utils;

public class Block {

    private String id;
    private String meta;

    public Block(String id, String meta) {
        this.id = id;
        this.meta = meta;
    }

    public int getId() {
        return Integer.parseInt(this.id);
    }

    public int getMeta() {
        return Integer.parseInt(this.meta);
    }
}
