import java.awt.Color;

/**
 * Class representing all constants used in the game.
 *
 * @author Kyle Chapman (kyleichapman@gmail.com)
 */
public class Consts {

	public static final int CANVAS_WIDTH = 1280;
	public static final int CANVAS_HEIGHT = 720;
	public static final Color CANVAS_BG_COLOR = Color.WHITE;

	public static final int PAUSE_WIDTH = 400;
	public static final int PAUSE_HEIGHT = 500;

	public static final int PLAYER_WIDTH = 150;
	public static final int PLAYER_HEIGHT = 70;
	public static final int LINE_WIDTH = CANVAS_WIDTH;
	public static final int LINE_HEIGHT = 10;

	public static enum MOVES {
		UP, DOWN;
	}
}
