package Figures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

public abstract class AbstractFigure {
    
    protected Point2D position;
    private Point2D offset;
    protected TextFigure label;
    protected Color fillColor = new Color(224, 224, 224);
    protected Color strokeColor = new Color(0, 0, 0);
    protected Color selectedColor = new Color(255, 230, 255);
    protected Color highlightedColor = new Color(255, 153, 255);
    protected boolean selected = false;
    protected boolean highlighted = false;
    public abstract boolean contains(Point2D position);
    public abstract void draw(Graphics2D g);
    public abstract void drawFill(Graphics2D g);
    public abstract void drawStroke(Graphics2D g);
    public abstract String getElementId();
    public abstract void setElementId(String id);
    public abstract RectangularShape getBounds();

    public Point2D getPosition() {
        return position;
    }
    public void setPosition(Point2D position) {
        this.position = position;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    public Point2D getOffset() {
        return offset;
    }
    public void setOffset(Point2D offset) {
        this.offset = offset;
    }
    public TextFigure getLabel() {
        return label;
    }
}


