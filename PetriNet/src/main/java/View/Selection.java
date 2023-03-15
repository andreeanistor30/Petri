package View;

import Business.NetModes;
import Business.NetObject;
import Business.Place;
import Figures.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;

public class Selection extends javax.swing.JComponent implements MouseListener, MouseMotionListener {
    private boolean selectionEnabled = false;
    private HashMap selectedFigures = new HashMap();
    private Canvas canvas;
    private boolean snapToGrid = true;

    public Selection(Canvas canvas) {
        this.canvas = canvas;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void updateBounds() {
        if (selectionEnabled) {
            setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    @Override
    public void paintComponent(java.awt.Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        this.updateBounds();
        //draw selected figures
        if (!selectedFigures.isEmpty()) {
            Iterator it = selectedFigures.values().iterator();
            while (it.hasNext()) {
                AbstractFigure figure = (AbstractFigure) it.next();
                if (!(figure instanceof TextFigure)) {// prevent double painting
                    figure.draw(g2);
                }
            }
        }
        canvas.repaint();
    }


    public void addSelectedFigure(AbstractFigure figure) {
        figure.setSelected(true);
        selectedFigures.put(figure.getElementId(), figure);
    }

    public void removeSelectedFigures() {
        if (!selectedFigures.isEmpty()) {
            Iterator i = selectedFigures.values().iterator();
            while (i.hasNext()) {
                AbstractFigure figure = (AbstractFigure) i.next();
                canvas.removeFigure(figure);
            }
        }

        this.selectedFigures = new HashMap();
        this.repaint();
    }
    public void updateOffsets(Point2D offset) {
        Iterator it = selectedFigures.values().iterator();
        while (it.hasNext()) {
            AbstractFigure element = (AbstractFigure) it.next();
            if (element.getPosition() != null) {
                Point2D newOffset = new Point2D.Double(element.getPosition().getX() - offset.getX(), element.getPosition().getY() - offset.getY());
                element.setOffset(newOffset);
            }
        }
    }

    public void setSelectionEnabled(boolean selectionEnabled) {
        this.selectionEnabled = selectionEnabled;
        if (selectionEnabled) {
            // to enable listeners in parent component
            updateBounds();
            canvas.add(this);
        }
        else {
            removeSelectedFigures();
            canvas.remove(this);
        }
    }

    public void snapToGrid(Point2D e) {
        if(snapToGrid){
            int val = Grid.cellSize / 5;// number of parts per cell
            Iterator it = selectedFigures.values().iterator();
            while (it.hasNext()) {
                AbstractFigure figure = (AbstractFigure) it.next();
                if (!(figure instanceof PathPoint) && figure.getPosition() != null) {// ie: AbstractArcFigure doesn't have position by itself
                    int x = (int) (((int) (e.getX() + figure.getOffset().getX()) / val) * val + val / 2);
                    int y = (int) (((int) (e.getY() + figure.getOffset().getY()) / val) * val + val / 2);
                    Point2D point = new Point2D.Double(x, y);
                    figure.setPosition(point);
                }
            }}
    }


    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        switch (NetModes.mode) {
            case NetModes.selectMode:
                AbstractFigure figure = canvas.selectFigure(e.getPoint());
                if (figure != null  ) {
                    if (!selectedFigures.containsKey(figure.getElementId())) {
                        addSelectedFigure(figure);
                    }
                    updateOffsets(e.getPoint());
                    // right click
                    if (e.getButton() == MouseEvent.BUTTON3 && figure instanceof PlaceFigures) {
                        NetObject object = NetModes.petriNet.getNetElement(canvas.getFigureKey(figure));
                        canvas.showForm(object);

                    }

                }
                break;
        }
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        switch (NetModes.mode) {
            case NetModes.selectMode:
                if (selectedFigures.isEmpty()!=true) {
                    Iterator it = selectedFigures.values().iterator();
                    while (it.hasNext()) {
                        AbstractFigure figure = (AbstractFigure) it.next();
                        if (figure instanceof AbstractArcFigure) {
                            AbstractArcFigure arcFigure = (AbstractArcFigure) figure;
                            arcFigure.setPosition(e.getPoint());
                        } else {
                            Point2D offset = figure.getOffset();
                            Point2D newPosition = new Point2D.Double(e.getPoint().getX() + offset.getX(), e.getPoint().getY() + offset.getY());
                            figure.setPosition(newPosition);
                        }
                    }
                    snapToGrid(e.getPoint());

                }
                break;
        }

        this.repaint();
    }

    public void mouseMoved(MouseEvent e) {
        requestFocus();
    }

}