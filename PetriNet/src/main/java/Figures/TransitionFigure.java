package Figures;

import View.Grid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class TransitionFigure extends AbstractFigure {
    final public static int WIDTH = Grid.cellSize;
    final public static int HEIGHT = 10;
    private Rectangle2D rectangle;
    private String transitionId;
    public TransitionFigure(String transitionId, Point2D position) {
        this.transitionId = transitionId;
        this.position = position;
        this.label = new TextFigure(this);
        this.rectangle = (Rectangle2D) getBounds();
    }
    @Override
    public boolean contains(Point2D position) {
        return rectangle.contains(position);
    }
    @Override
    public RectangularShape getBounds() {
        return new Rectangle2D.Double(position.getX() - WIDTH / 2, position.getY() - HEIGHT / 2, WIDTH, HEIGHT);
    }
    @Override
    public void draw(Graphics2D g) {
        rectangle = (Rectangle2D) getBounds();
        drawFill(g);
        drawStroke(g);
    }
    @Override
    public void drawFill(Graphics2D g) {
        if (selected) {
            g.setPaint(selectedColor);
        } else {
            g.setPaint(fillColor);
        }
        g.fill(rectangle);

    }
    @Override
    public void drawStroke(Graphics2D g) {
        if (highlighted) {
            g.setPaint(highlightedColor);
        } else {
            g.setPaint(strokeColor);
        }
        g.setStroke(new java.awt.BasicStroke(2f));
        g.draw(rectangle);

    }

    @Override
    public void setPosition(Point2D newPosition) {
        position = newPosition;
        label.setRelativePosition(newPosition);
    }

    @Override
    public String getElementId() {
        return transitionId;
    }

    @Override
    public void setElementId(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}