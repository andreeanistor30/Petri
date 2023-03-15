package Business;

public class OutputArc extends Arc{
    public String txt = "1";
    public OutputArc(Place place, Transition transition){
        this.place = place;
        this.transition = transition;
        this.id = "output" + this.id;
    }
    public TokenSet execute() {
        return new TokenSet(txt);
    }
}
