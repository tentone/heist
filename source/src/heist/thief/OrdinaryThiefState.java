package heist.thief;

import java.io.Serializable;

public class OrdinaryThiefState implements Serializable
{
    private static final long serialVersionUID = 937546L;
    
    /**
     * Thief unique id.
     */
    private int id;
    
    /**
     * Thief state.
     */
    private int state;
    
    /**
     * Thief crawling position.
     */
    private int position;
    
    /**
     * How far can the thief move in one step.
     */
    private int maximumDisplacement;
    
    /**
     * True if the thief carries a canvas.
     */
    private boolean hasCanvas;
    
    /**
     * Constructor for OrdinaryThief state.
     * @param id Thief id.
     * @param state Thief state.
     * @param position Thief position.
     * @param maximumDisplacement Thief maximum displacement.
     * @param hasCanvas  Thief has canvas.
     */
    public OrdinaryThiefState(int id, int state, int position, int maximumDisplacement, boolean hasCanvas)
    {
        this.id = id;
        this.state = state;
        this.position = position;
        this.maximumDisplacement = maximumDisplacement;
        this.hasCanvas = hasCanvas;
    }
}
