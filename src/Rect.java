/**
 * Class representing a rectangle.
 * <p></p>
 * Can be used to represent most 2D objects.
 *
 * @author Kyle Chapman (kyleichapman@gmail.com)
 */
public class Rect {

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	/**
	 * Constructor for a simple rectangle.
	 *
	 * @param x The x position.
	 * @param y The y position.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Determines whether or not this {@code Rect} and the given {@code Rect}
	 * intersect. Two rectangles intersect if their intersection is nonempty.
	 *
	 * @param r The given {@code Rect} to test for intersection.
	 * @return {@code true} if the given {@code Rect} and this {@code Rect}
	 * intersect; {@code false} otherwise.
	 */
	public boolean intersects(Rect r) {
		int tw = this.width;
		int th = this.height;
		int rw = r.width;
		int rh = r.height;

		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}

		int tx = this.x;
		int ty = this.y;
		int rx = r.x;
		int ry = r.y;

		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;

		return (
			(rw < rx || rw > tx) &&
			(rh < ry || rh > ty) &&
			(tw < tx || tw > rx) &&
			(th < ty || th > ry)
		);
	}

	/**
	 * Gets the x position of the rectangle.
	 * @return The x position.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Gets the y position of the rectangle.
	 * @return The y position.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Gets the width of the rectangle.
	 * @return The width of the rectangle.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height of the rectangle.
	 * @return The height of the rectangle.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Sets the x position of the rectangle.
	 * @param x The new x position.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y position of the rectangle.
	 * @param y The new y position.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the width of the rectangle.
	 * @param width The new width of the rectangle.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height of the rectangle.
	 * @param height The new height of the rectangle.
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
