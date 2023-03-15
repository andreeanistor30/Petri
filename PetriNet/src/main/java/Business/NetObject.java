package Business;

public abstract class NetObject {
    public static long LATEST_ID_PLACE = 1;
    public static long LATEST_ID_TRANSITION = 0;

    protected String id;
    protected String label = "";

    public NetObject() {
        if(this instanceof Place) {
            this.id = "" + LATEST_ID_PLACE;
            LATEST_ID_PLACE++;
        }
        else
        {
            this.id = "" + LATEST_ID_TRANSITION;
            LATEST_ID_TRANSITION++;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        if (label.equals("")) {
            label = id;
        }
        return label;
    }
}


