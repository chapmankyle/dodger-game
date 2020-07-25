import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Traffic extends Rect {

	public int speed;
	public String path;
	public BufferedImage img;

	public Traffic(int x, int y, int width, int height, int speed, String path) {
		super(x, y, width, height);
		this.speed = speed;
		this.path = path;

		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Rect getTrafficBounds() {
		return new Rect(x, y, width, height);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
	}

	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
}
