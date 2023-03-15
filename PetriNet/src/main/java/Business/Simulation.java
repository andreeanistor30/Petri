package Business;

import View.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Simulation extends Thread {

    protected boolean step = false;
    protected boolean paused = false;
    protected boolean stop = false;
    public static int delay =100;
    protected GUI gui;

    public Simulation(boolean step, GUI gui) {
        this.step = step;
        this.gui = gui;
    }

    @Override
    public void run() {
        while (isFinished()==false && stop==false) {
            executeTransition();
        }

  }
    public boolean isFinished() {
        return NetModes.petriNet.deadNet();
    }

    protected void executeTransition() {
        enabledTransitionList();
        if (this.enabledTransitionList().isEmpty()!=true) {
            getRandomTransition().executeTransition(this.gui, 0);
            pauseResumeSimulation();

        }
    }

    public synchronized void pauseResumeSimulation() {
        if (step==true && stop==false) {
            paused = true;
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ex) {
               System.out.println("InterruptedException");
            }
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException l) {
            }
        }
    }

    public Transition getRandomTransition() {
        ArrayList enabledTransitions = this.enabledTransitionList();
        Random generator = new Random();
        int rand = generator.nextInt(enabledTransitions.size());
        return (Transition) enabledTransitions.get(rand);
    }

    public ArrayList enabledTransitionList() {
        Iterator iterator = NetModes.petriNet.getTransitions().iterator();
        ArrayList enabledTransitions = new ArrayList();
        while (iterator.hasNext()) {
            Transition transition = (Transition) iterator.next();
            if (transition.enabled(0)) {
                enabledTransitions.add(transition);
            }
        }
        return enabledTransitions;
    }

    public boolean isPaused() {
        return paused;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setStop(boolean stop) {
        if (this.paused) {
            synchronized (this) {
                this.notify();
            }
        }
        this.stop = stop;
    }
}