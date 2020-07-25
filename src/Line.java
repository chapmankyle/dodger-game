import java.awt.Color;
import java.awt.Graphics;

/**
 * Class representing the line that determines the lanes.
 *
 * @author Kyle Chapman (kyleichapman@gmail.com)
 */
public class Line extends Rect {

	private Color colour;

	/**
	 * Constructor for a line.
	 *
	 * @param x The x position.
	 * @param y The y position.
	 * @param width The width of the line.
	 * @param height The height of the line.
	 * @param colour The colour of the line.
	 */
	public Line(int x, int y, int width, int height, Color colour) {
		super(x, y, width, height);
		this.colour = colour;
	}

	/**
	 * Constructor for a line.
	 *
	 * @param x The x position.
	 * @param y The y position.
	 * @param width The width of the line.
	 * @param height The height of the line.
	 */
	public Line(int x, int y, int width, int height) {
		this(x, y, width, height, Color.BLACK);
	}

	/**
	 * Gets the colour of the line.
	 * @return The colour of the line.
	 */
	public Color getColour() {
		return this.colour;
	}

	/**
	 * Sets the colour of the line.
	 * @param colour The new colour of the line.
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * Paint a line.
	 * @param g The graphics object to use to paint.
	 */
	public void paint(Graphics g) {
		g.setColor(this.colour);
		g.fillRect(this.x, this.y, this.width, this.height);
	}
}
