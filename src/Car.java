import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class representing a single car.
 *
 * @author Kyle Chapman (kyleichapman@gmail.com)
 */
public class Car extends Rect {

	private int speed;
	private int lane;

	private String path;
	private BufferedImage img;

	/**
	 * Constructor for a car represented by an image.
	 *
	 * @param x The x position.
	 * @param y The y position.
	 * @param width The width of the car.
	 * @param height The height of the car.
	 * @param speed The speed that the car will travel at.
	 * @param path The path to the image for the car.
	 */
	public Car(int x, int y, int width, int height, int speed, String path) {
		super(x, y, width, height);
		this.speed = speed;
		this.path = path;
		this.lane = 0;

		try {
			this.img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Determines whether or not this {@code Car} and the given {@code Car}
	 * collide. Two cars collide if their bounds intersect.
	 *
	 * @param c The given {@code Car} to test for collision.
	 * @return {@code true} if the given {@code Car} and this {@code Car} collide;
	 * {@code false} otherwise.
	 */
	public boolean collidesWith(Car c) {
		return c.getBounds().intersects(this.getBounds());
	}

	/**
	 * Gets the speed of the car.
	 * @return The speed of the car.
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Gets the current lane of the car.
	 * @return The lane the car is in.
	 */
	public int getLane() {
		return this.lane;
	}

	/**
	 * Gets the path to the image for the car.
	 * @return The car image path.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Gets the image used for the car.
	 * @return The image used for the car.
	 */
	public BufferedImage getImage() {
		return this.img;
	}

	/**
	 * Gets the bounds of the car.
	 * @return The bounds as a {@link Rect}.
	 */
	public Rect getBounds() {
		return new Rect(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Sets the speed of the car.
	 * @param speed The new speed.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Sets the lane of the car.
	 * @param lane The new lane of the car.
	 */
	public void setLane(int lane) {
		this.lane = lane;
	}

	/**
	 * Sets the path to the image of the car.
	 * @param path The new image path.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Sets the image of the car.
	 * @param img The new car image.
	 */
	public void setImage(BufferedImage img) {
		this.img = img;
	}

	/**
	 * Paints the car using the graphics object.
	 * @param g The graphics object to paint with.
	 */
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
}
