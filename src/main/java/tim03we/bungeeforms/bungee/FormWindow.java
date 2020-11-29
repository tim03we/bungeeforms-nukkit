package tim03we.bungeeforms.bungee;

import com.google.gson.Gson;

public abstract class FormWindow {

    private static final Gson GSON = new Gson();
    protected boolean closed = false;

    public FormWindow() {
    }

    public String getJSONData() {
        return GSON.toJson(this);
    }

    public abstract void setResponse(String var1);

    public abstract FormResponse getResponse();

    public boolean wasClosed() {
        return this.closed;
    }
}
