package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class PetriNetElements extends NetObject {
    private ArrayList<Place> places = new ArrayList();
    private ArrayList<Transition> transitions = new ArrayList();
    private ArrayList<InputArc> inputArcs = new ArrayList();
    private ArrayList<OutputArc> outputArcs = new ArrayList();
    private HashMap netElements = new HashMap();

    public PetriNetElements() {
        this.label = this.id;
        this.id = "net" + this.id;
    }

    public boolean deadNet() {
        boolean deadFlag = false;
        Iterator i = getTransitions().iterator();
        while (deadFlag==false && i.hasNext()) {
            Transition transition = (Transition) i.next();
            if(transition.enabled(0)==false)
                deadFlag=true;
        }
        return deadFlag;
    }

    public void addPlace(Place place) {
        this.places.add(place);
        this.netElements.put(place.getId(), place);
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
        this.netElements.put(transition.getId(), transition);
    }

    public void addInputArc(InputArc inputArc) {
        this.inputArcs.add(inputArc);
        this.netElements.put(inputArc.getId(), inputArc);
    }

    public void addOutputArc(OutputArc outputArc) {
        this.outputArcs.add(outputArc);
        this.netElements.put(outputArc.getId(), outputArc);
    }

    public void removePlace(Place place) {
        this.removeInputArcs(place.getId());
        this.removeOutputArcs(place.getId());
        this.places.remove(place);
        this.netElements.remove(place.getId());
    }

    public void removeTransition(Transition transition) {
        this.removeInputArcs(transition.getId());
        this.removeOutputArcs(transition.getId());
        this.transitions.remove(transition);
        this.netElements.remove(transition.getId());
    }

    public void removeInputArc(InputArc inputArc) {
        this.inputArcs.remove(inputArc);
        this.netElements.remove(inputArc.getId());
    }

    public void removeOutputArc(OutputArc outputArc) {
        this.outputArcs.remove(outputArc);
        this.netElements.remove(outputArc.getId());
    }

    public void removeInputArcs(String id) {
        Iterator it = getInputArcs().iterator();
        while (it.hasNext()) {
            InputArc inputArc = (InputArc) it.next();
            if (id.equals(inputArc.getPlace().getId())) {
                it.remove();
            }
        }
    }

    public void removeOutputArcs(String id) {
        Iterator it = getOutputArcs().iterator();
        while (it.hasNext()) {
            OutputArc outputArc = (OutputArc) it.next();
            if (id.equals(outputArc.getPlace().getId())) {
                it.remove();
            }
        }
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public ArrayList<InputArc> getInputArcs() {
        return inputArcs;
    }

    public ArrayList<OutputArc> getOutputArcs() {
        return outputArcs;
    }

    public NetObject getNetElement(String id) {
        return (NetObject) this.netElements.get(id);
    }

}
