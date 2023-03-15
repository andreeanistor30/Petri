package Figures;

public interface ConnectionFigure {
    public void setConnectionStart(AbstractFigure start);
    public AbstractFigure getStartConnector();
    public void setConnectionEnd(AbstractFigure end);
    public AbstractFigure getEndConnector();
}
