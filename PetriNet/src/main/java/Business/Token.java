package Business;

public class Token {
    private String initialMarkingExpression = "";
    private Object object;
    private long timestamp = 0;
    public Token(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object obj) {
        Token objToken = (Token) obj;
        if (objToken.getTimestamp() == timestamp &&
                objToken.getObject().getClass().getName().equals(this.object.getClass().getName()) &&
                this.object.equals(objToken.getObject())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.object != null ? this.object.hashCode() : 0);
        hash = 47 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        String s = "";
        if (!initialMarkingExpression.equals("")) {
            s = initialMarkingExpression;
        } else {
            s = object.toString();
            if (timestamp != 0) {
                s += " " + timestamp;
            }
        }
        return s;
    }
    public Object getObject() {
        return object;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


}
