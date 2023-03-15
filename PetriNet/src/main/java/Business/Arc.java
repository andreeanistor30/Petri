package Business;

public class Arc extends NetObject {

    protected Place place;

    protected Transition transition;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Transition getTransition() {
        return transition;
    }

}