package Business;

public class InputArc extends Arc{

    public String txt = "1";
    public InputArc(Place place,Transition transition)
    {
        this.place = place;
        this.transition = transition;
        this.id = "input"+this.id;
    }

    public boolean evaluate() {
        return getTokenSet().size() > 0;
    }

    public TokenSet getTokenSet() {
        return this.getPlace().getTokens();
    }

    public TokenSet execute() {
        return new TokenSet(txt);
    }
}
