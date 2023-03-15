package Business;

public class Place extends NetObject {
    private TokenSet tokens = new TokenSet();

    private int capacity = 0;

    public Place() {
        this.id = "P" + this.id;
    }

    public TokenSet getTokens() {
        return tokens;
    }

    public void addToken(TokenSet tokenSet) {
        tokens.addAll(tokenSet);
    }

    public void removeTokens(TokenSet tokenSet) {
        tokens.removeAll(tokenSet);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}