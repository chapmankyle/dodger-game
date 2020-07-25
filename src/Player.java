import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Rect {

	public String path;
	public BufferedImage img;

	public Player(int x, int y, int width, int height, String path) {
		super(x, y, width, height);
		this.path = path;

		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Rectangle getPlayerBounds() {
		return new Rectangle(x, y, width, height);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}

}
