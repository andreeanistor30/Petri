package Figures;

import View.Grid;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

public class PlaceFigures extends AbstractFigure{

    private String placeId;
    private Ellipse2D ellipse;
    final public static int DIAMETER = Grid.cellSize;
    protected TokenSetFigure tokenFigure;

    public PlaceFigures(String placeId, Point2D position) {
        this.placeId = placeId;
        this.position = position;
        this.label = new TextFigure(this);
        this.tokenFigure = new TokenSetFigure(this);
        this.ellipse = generateEllipse();
    }

    @Override
    public boolean contains(Point2D position) {
        return this.ellipse.contains(position);
    }

    @Override
    public RectangularShape getBounds() {
        return new Ellipse2D.Double(position.getX() - DIAMETER / 2, position.getY() - DIAMETER / 2, DIAMETER, DIAMETER);
    }

    @Override
    public void draw(Graphics2D g) {
        this.ellipse = generateEllipse();
        drawFill(g);
        drawStroke(g);
        tokenFigure.draw(g);
    }

    @Override
    public void drawFill(Graphics2D g) {
        if (selected) {
            g.setPaint(selectedColor);
        } else {
            g.setPaint(fillColor);
        }
        g.fill(ellipse);
    }

    @Override
    public void drawStroke(Graphics2D g) {
        g.setStroke(new java.awt.BasicStroke(2f));
        strokeColor = new Color(105,105,105);
        if (highlighted) {
            g.setPaint(highlightedColor);
        } else {
            g.setPaint(strokeColor);
        }
        g.draw(ellipse);
    }

    public Ellipse2D generateEllipse() {
        return new Ellipse2D.Double(position.getX() - DIAMETER / 2, position.getY() - DIAMETER / 2, DIAMETER, DIAMETER);
    }

    @Override
    public void setPosition(Point2D newPosition) {
        position = newPosition;
        label.setRelativePosition(newPosition);
        tokenFigure.setRelativePosition(newPosition);
    }

    @Override
    public String getElementId() {
        return this.placeId;
    }

    @Override
    public void setElementId(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

