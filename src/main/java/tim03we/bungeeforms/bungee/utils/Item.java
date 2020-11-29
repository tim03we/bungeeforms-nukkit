package tim03we.bungeeforms.bungee.utils;

public class Item {

    private String id;
    private String meta;
    private String count;
    private String customName;

    public Item(String id, String meta, String count) {
        this.id = id;
        this.meta = meta;
        this.count = count;
    }

    public Item(String id, String meta, String count, String customName) {
        this.id = id;
        this.meta = meta;
        this.count = count;
        this.customName = customName;
    }

    public int getId() {
        return Integer.parseInt(this.id);
    }

    public int getMeta() {
        return Integer.parseInt(this.meta);
    }

    public int getCount() {
        return Integer.parseInt(this.count);
    }

    public String getCustomName() {
        return customName;
    }
}
