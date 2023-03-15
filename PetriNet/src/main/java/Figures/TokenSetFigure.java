package Figures;
import Business.NetModes;
import Business.Place;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class TokenSetFigure extends TextFigure{
    private String placeId;
    private Ellipse2D ellipse;
    private Point2D offsetToParent = new Point2D.Double(0, 0);
    final private static int DIAMETER = 30;

    public TokenSetFigure(PlaceFigures parent) {
        super(parent);
        this.position = new Point2D.Double(offsetToParent.getX() + parent.getPosition().getX(), offsetToParent.getY() + parent.getPosition().getY());
        this.placeId = parent.getElementId();
    }

    @Override
    public void draw(Graphics2D g) {
        this.ellipse = generateEllipse();
        drawFill(g);
        drawStroke(g);
    }
    @Override
    public void drawFill(Graphics2D g) {
        g.setPaint(fillColor);
    }
    @Override
    public void drawStroke(Graphics2D g) {
        super.drawStroke(g);
    //g.draw(ellipse);
    }
    public Ellipse2D generateEllipse() {
        return new Ellipse2D.Double(position.getX() - DIAMETER / 2, position.getY() - DIAMETER / 2, DIAMETER, DIAMETER);
    }
    @Override
    public void setRelativePosition(Point2D parentPosition) {
        position = new Point2D.Double(parentPosition.getX() +
                offsetToParent.getX(), parentPosition.getY() + offsetToParent.getY());
    }
    @Override
    public String getText() {
        Place place = (Place) NetModes.petriNet.getNetElement(placeId);
        return "" + place.getTokens().size();
    }
}
