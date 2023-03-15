package View;

import Business.*;
import Figures.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Canvas extends javax.swing.JPanel
            implements MouseListener, MouseMotionListener, MouseWheelListener{
        private HashMap figures = new HashMap();
        private AbstractArcFigure arcFigure;
        private Grid grid;
        private boolean enabledGrid = true;
        private Selection selection;

        public Canvas() {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.addMouseWheelListener(this);
            selection = new Selection(this);
            initComponents();
        }

        public Canvas(GUI window) {
            initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

            setBackground(new java.awt.Color(255, 255, 255));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 400, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 300, Short.MAX_VALUE)
            );
        }// </editor-fold>

        @Override
        public void paintComponent(java.awt.Graphics graphics) {
            graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) graphics;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            grid = new Grid(this.getWidth(), this.getHeight());
            if (this.enabledGrid) {
                grid.drawGrid(g2);
            }

            Iterator it = figures.values().iterator();
            while (it.hasNext()) {
                AbstractFigure element = (AbstractFigure) it.next();
                element.draw(g2);
            }

            if (arcFigure != null) {
                arcFigure.draw(g2);
            }

        }
        public String getFigureKey(AbstractFigure figure) {
            String id = "";
            for (Object o : figures.keySet()) {
                if (figures.get(o).equals(figure)) {
                    id = (String) o;
                }
            }
            return id;
        }
    public void showForm(NetObject netObject){
        if (netObject != null) {
            PlacesCapacity frmPlace = new PlacesCapacity(netObject);
            frmPlace.setVisible(true);
            repaint();
        }
    }
        public void addFigure(int element, Point2D position) {
            switch (element) {
                case NetModes.placeMode:
                    Place place = new Place();
                    NetModes.petriNet.addPlace(place);
                    PlaceFigures placeFigure = new PlaceFigures(place.getId(), position);
                    figures.put(place.getId(), placeFigure);
                    figures.put(place.getId() + "label", placeFigure.getLabel());
                    break;
                case NetModes.transitionMode:
                    Transition transition = new Transition();
                    NetModes.petriNet.addTransition(transition);
                    TransitionFigure transitionFigure = new TransitionFigure(transition.getId(), position);
                    figures.put(transition.getId(), transitionFigure);
                    figures.put(transition.getId() + "label", transitionFigure.getLabel());
                    break;
                case NetModes.arcMode:
                    AbstractFigure start = arcFigure.getStartConnector();
                    AbstractFigure end = arcFigure.getEndConnector();
                    String id;
                    if (NetModes.petriNet.getNetElement(start.getElementId()) instanceof Place) {
                        Place p = (Place) NetModes.petriNet.getNetElement(start.getElementId());
                        Transition t = (Transition) NetModes.petriNet.getNetElement(end.getElementId());
                        InputArc arc = new InputArc(p, t);
                        NetModes.petriNet.addInputArc(arc);
                        id = arc.getId();
                    } else {
                        Place p = (Place) NetModes.petriNet.getNetElement(end.getElementId());
                        Transition t = (Transition) NetModes.petriNet.getNetElement(start.getElementId());
                        OutputArc arc = new OutputArc(p, t);
                        NetModes.petriNet.addOutputArc(arc);
                        id = arc.getId();
                    }
                    figures.put(id, arcFigure);
                    arcFigure.setElementId(id);
                    Iterator it = arcFigure.getPoints().iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        PathPoint pathPoint = (PathPoint) it.next();
                        if (i != 0 && i != arcFigure.getPoints().size() - 1) {
                            pathPoint.setElementId(arcFigure.getElementId() + "_pathpoint_" + i);
                            figures.put(pathPoint.getElementId(), pathPoint);
                        }
                        i++;
                    }
                    break;
            }


            }

        public AbstractFigure selectFigure(Point2D position){
            Iterator iterator = figures.values().iterator();
            AbstractFigure figure = null;
            while(iterator.hasNext())
            {
                AbstractFigure figures = (AbstractFigure) iterator.next();
                if(figures.contains(position)){
                    figure = figures;
                }
            }

            if(figure instanceof AbstractArcFigure){
                AbstractArcFigure abstractArcFigure = (AbstractArcFigure) figure;
                Point2D point = abstractArcFigure.containsPoint(position);
                abstractArcFigure.setSelectedPoint(point);
            }
            return figure;
        }

        public Point2D snapPointToGrid(Point2D e) {
            int val = Grid.cellSize / 5;// number of parts per cell
            int x = (int) (((int) (e.getX()) / val) * val + val / 2);
            int y = (int) (((int) (e.getY()) / val) * val + val / 2);
            return new Point2D.Double(x, y);
        }

        public boolean duplicateArc(AbstractArcFigure abstractArcFigure){
            boolean duplicate = false;
            Iterator iterator = figures.values().iterator();
            while(!duplicate && iterator.hasNext()){
                AbstractFigure figure = (AbstractFigure) iterator.next();
                if (figure instanceof AbstractArcFigure) {
                    AbstractArcFigure tmp = (AbstractArcFigure) figure;
                    if (tmp.getStartConnector().equals(arcFigure.getStartConnector()) &&
                            tmp.getEndConnector().equals(arcFigure.getEndConnector())) {
                        duplicate = true;
                    }

                }
            }
            return duplicate;
        }

        public void removeFigure(AbstractFigure figure)
        {
            if (figure instanceof PlaceFigures) {
                Place place = (Place) NetModes.petriNet.getNetElement(figure.getElementId());
                NetModes.petriNet.getNetElement(place.getId());
                figures.remove(place.getId());
                figures.remove(place.getId() + "label");
                removeArcFigures(place.getId());
                NetModes.petriNet.removePlace(place);
            } else if (figure instanceof TransitionFigure) {
                Transition transition = (Transition) NetModes.petriNet.getNetElement(figure.getElementId());
                NetModes.petriNet.getNetElement(transition.getId());
                figures.remove(transition.getId());
                figures.remove(transition.getId() + "label");
                removeArcFigures(transition.getId());
                NetModes.petriNet.removeTransition(transition);
            } else if (figure instanceof ArcFigure) {
                Arc arc = (Arc) NetModes.petriNet.getNetElement(figure.getElementId());
                if (arc instanceof InputArc) {
                    InputArc inputArc = (InputArc) arc;
                    figures.remove(arc.getId());
                    NetModes.petriNet.removeInputArc(inputArc);
                } else if (arc instanceof OutputArc) {
                    OutputArc outputArc = (OutputArc) arc;
                    figures.remove(outputArc.getId());
                    NetModes.petriNet.removeOutputArc(outputArc);
                }
                AbstractArcFigure arcFigure = (AbstractArcFigure) figure;
                removePathPoints(arcFigure);
            } else if (figure instanceof PathPoint) {
                PathPoint pathPoint = (PathPoint) figure;
                StringTokenizer parts = new StringTokenizer(pathPoint.getElementId(), "_");
                String arcId = parts.nextToken();
                ArcFigure normalArc = (ArcFigure) figures.get(arcId);
                if (normalArc != null) {
                    normalArc.removePoint(pathPoint);
                }
                figures.remove(figure.getElementId());

            }
        }

    public void removeArcFigures(String id) {
        Object[] f = figures.values().toArray();
        for (int i = 0; i < f.length; i++) {
            AbstractFigure figure = (AbstractFigure) f[i];
            if (figure instanceof AbstractArcFigure) {
                AbstractArcFigure arcFigure = (AbstractArcFigure) figure;
                if (arcFigure.getStartConnector().getElementId().equals(id) || arcFigure.getEndConnector().getElementId().equals(id)) {
                    removePathPoints(arcFigure);
                    figures.remove(figure.getElementId());
                }
            }
        }

    }

    public void removePathPoints(AbstractArcFigure arcFigure) {
        Iterator it = arcFigure.getPoints().iterator();
        while (it.hasNext()) {
            PathPoint pathPoint = (PathPoint) it.next();
            figures.remove(pathPoint.getElementId());
            it.remove();
        }
    }

        public void addArc(Point2D position)
        {
            AbstractFigure figure = selectFigure(position);
            if (figure != null) {
                if (figure instanceof PlaceFigures || figure instanceof TransitionFigure) {
                    if (arcFigure == null) {
                        //start Point
                        arcFigure = new ArcFigure();
                        arcFigure.addPoint(position);
                        arcFigure.setConnectionStart(figure);
                    } else {
                        if (arcFigure.getStartConnector() instanceof PlaceFigures && figure instanceof TransitionFigure || arcFigure.getStartConnector() instanceof TransitionFigure &&
                                figure instanceof PlaceFigures) {
                            //end point
                            arcFigure.setConnectionEnd(figure);
                            arcFigure.addPoint(position);
                            if (!duplicateArc(arcFigure)) {
                                addFigure(NetModes.arcMode, position);
                            }
                        }
                        arcFigure = null;
                    }
                }
            } else {
                if (arcFigure != null)
                    arcFigure.addPoint(position);
                }
            repaint();
            }

        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            switch (NetModes.mode) {
                case NetModes.placeMode:
                    addFigure(NetModes.placeMode, snapPointToGrid(e.getPoint()));
                    break;
                case NetModes.transitionMode:
                    addFigure(NetModes.transitionMode,snapPointToGrid(e.getPoint()));
                    break;
                    case NetModes.arcMode:
                        addArc(e.getPoint());
                        break;
                default:
                    break;
            }
            repaint();
        }



        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        public HashMap getFigures() {
            return figures;
        }

        public void setFigures(HashMap figures) {
            this.figures = figures;
        }

        public Selection getSelection() {
            return selection;
        }

        public void colorArcs(ArrayList list, String transitionId, boolean highlighted, boolean delay) {
            if (Simulation.delay > 0) {
                boolean any = false;
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Arc arc = (Arc) it.next();
                    if (arc.getTransition().getId().equals(transitionId)) {
                        any = true;
                        AbstractFigure abstractFigure = (AbstractFigure) figures.get(arc.getId());
                        abstractFigure.setHighlighted(highlighted);
                    }
                }
                if (any && delay) {// delay only when highlighted is ON
                    sleepRepaint();
                }
            }
        }

        public void colorTransition(String id, boolean highlighted, boolean delay) {
            if (Simulation.delay > 0) {
                TransitionFigure transitionFigure = (TransitionFigure) figures.get(id);
                transitionFigure.setHighlighted(highlighted);
                if (delay) {// delay only when highlighted is ON
                    sleepRepaint();
                }
            }
        }

    public void colorPlaces(ArrayList list, String transitionId, boolean highlighted, boolean delay) {
        if (Simulation.delay > 0) {
            boolean any = false;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Arc arc = (Arc) it.next();
                if (arc.getTransition().getId().equals(transitionId)) {
                    any = true;
                    AbstractFigure abstractFigure = (AbstractFigure) figures.get(arc.getPlace().getId());
                    abstractFigure.setHighlighted(highlighted);
                }
            }
            if (any && delay) {
                sleepRepaint();
            }
        }
    }
        public void sleepRepaint() {
            this.repaint();
            try {
                Thread.sleep(Simulation.delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Transition.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


