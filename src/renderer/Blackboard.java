
package renderer;

/**
 * Blackboard class is a global storage for the renderer
 * It stores the grid size and the anti-aliasing flag
 */
public class Blackboard {
    /**
     * The grid size
     */
    private int gridSize; // e.g., 9 for 9x9 grid
    /**
     * The anti-aliasing flag
     */
    private boolean isAntiAliasingEnabled;

    /**
     * Blackboard constructor
     *
     * @param gridSize the grid size
     */
    public Blackboard(int gridSize) {
        this.gridSize = gridSize;
        this.isAntiAliasingEnabled = false;
    }

    /**
     * getthe grid size
     *
     * @return the grid size
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * set the grid size
     *
     * @param gridSize the grid size
     * @return the Blackboard
     */
    public Blackboard setGridSize(int gridSize) {
        this.gridSize = gridSize;
        return this;
    }

    /**
     * get the anti-aliasing flag
     *
     * @return the anti-aliasing flag
     */
    public boolean isAntiAliasingEnabled() {
        return isAntiAliasingEnabled;
    }

    /**
     * set the anti-aliasing flag
     *
     * @param isAntiAliasingEnabled the anti-aliasing flag
     * @return the Blackboard
     */
    public Blackboard setAntiAliasingEnabled(boolean isAntiAliasingEnabled) {
        this.isAntiAliasingEnabled = isAntiAliasingEnabled;
        return this;
    }
}
