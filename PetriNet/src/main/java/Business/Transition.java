package Business;

import View.GUI;

import java.util.Iterator;

public class Transition extends NetObject {

    private long globalClock;

    public Transition() {
        this.id = "T" + this.id;
    }

    public Transition(String id) {
        this.id = id;
    }

    public Transition(String id, String guardText) {
        this.id = id;
    }

    public void executeTransition(GUI gui, long globalClock) {
        this.globalClock = globalClock;

        //places ON
        gui.getCanvas().colorPlaces(NetModes.petriNet.getInputArcs(), id, true, false);

        //inputArcs ON
        gui.getCanvas().colorArcs(NetModes.petriNet.getInputArcs(), id, true, true);

        //remove tokens from previous places
        Iterator it = NetModes.petriNet.getInputArcs().iterator();
        while (it.hasNext()) {
            InputArc arc = (InputArc) it.next();
            if (arc.getTransition().getId().equals(getId())) {
                arc.getPlace().removeTokens(arc.execute());
            }
        }

        //places OFF
        gui.getCanvas().colorPlaces(NetModes.petriNet.getInputArcs(), id, false, false);

        //inputArcs OFF
        gui.getCanvas().colorArcs(NetModes.petriNet.getInputArcs(), id, false, false);

        //transition ON
        gui.getCanvas().colorTransition(id, true, true);

        //transition OFF
        gui.getCanvas().colorTransition(id, false, false);

        //outputArcs ON
        gui.getCanvas().colorArcs(NetModes.petriNet.getOutputArcs(), id, true, false);


        //places ON
        gui.getCanvas().colorPlaces(NetModes.petriNet.getOutputArcs(), id, true, true);

        //create all tokens to output places
        it = NetModes.petriNet.getOutputArcs().iterator();
        while (it.hasNext()) {
            OutputArc arc = (OutputArc) it.next();
            if (arc.getTransition().getId().equals(getId())) {
               TokenSet tokenSet = arc.execute();
               tokenSet.incrementTime(globalClock);// set time of all new tokens of the tokenSet
                arc.getPlace().addToken(tokenSet);

            }
            //outputArcs OFF
            gui.getCanvas().colorArcs(NetModes.petriNet.getOutputArcs(), id, false, false);

            //places OFF
            gui.getCanvas().colorPlaces(NetModes.petriNet.getOutputArcs(), id, false, false);

            gui.getCanvas().repaint();
        }
    }

    public boolean enabled(long time) {
        boolean enable= true;

        if (enable==true && NetModes.petriNet.getInputArcs().isEmpty()==false) {
            Iterator it = NetModes.petriNet.getInputArcs().iterator();
            while (enable==true && it.hasNext()) {
                InputArc arc = (InputArc) it.next();
                if (arc.getTransition().getId().equals(getId())) {
                    TokenSet tokensList = arc.getPlace().getTokens();
                    enable = tokensList.containsTime(time);
                    if(arc.evaluate()==false)
                        enable = false;
                }
            }
        }


        if (enable==true && NetModes.petriNet.getOutputArcs().isEmpty()==false) {
            Iterator it = NetModes.petriNet.getOutputArcs().iterator();
            while (enable==true && it.hasNext()) {
                OutputArc arc = (OutputArc) it.next();
                if (arc.getTransition().getId().equals(getId())) {
                    TokenSet tokensList = arc.getPlace().getTokens();
                    if (arc.getPlace().getCapacity() != 0) {
                        if(arc.getPlace().getCapacity()>tokensList.size())
                            enable=false;
                    }
                }
            }
        }

        return enable;
    }
}
