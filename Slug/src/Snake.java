import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;


public class Snake {
	public Point head;
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public Direction direction;
	public int tailLength = 5;
	
	public static final Color JADE = new Color(18013070);
	
	public boolean noTailAt(int x, int y)
	{
		for (Point point : snakeParts)
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	}
	
	public void move() {
		
		if (direction == Direction.UP) {
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			head = new Point(head.x, head.y - 1);
		}

		if (direction == Direction.DOWN) {
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			head = new Point(head.x, head.y + 1);
		}

		if (direction == Direction.LEFT) {
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			head = new Point(head.x - 1, head.y);
		}

		if (direction == Direction.RIGHT) {
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			head = new Point(head.x + 1, head.y);
		}
		
	}

	
	/** Determine whether the snake will hit a 
	 *  wall or itself in the next time step. 
	 *  
	 * @return whether snake will hit a wall or itself.
	 */
	public boolean hitWall() {
		if (direction == Direction.UP) {
			if (head.y - 1 >= 1 && noTailAt(head.x, head.y - 1)) {
				return false;
			}
		}

		if (direction == Direction.DOWN) {
			if (head.y + 1 < 69 && noTailAt(head.x, head.y + 1)) {
				return false;
			}
		}

		if (direction == Direction.LEFT) {
			if (head.x - 1 >= 1 && noTailAt(head.x - 1, head.y)) {
				return false;
			}
		}

		if (direction == Direction.RIGHT) {
			if (head.x + 1 < 79 && noTailAt(head.x + 1, head.y)) {
				return false;
			}
		}
		
		return true;
	}

	protected void draw(Graphics g) {
		for (Point point : snakeParts)
		{
			g.setColor(JADE);
			g.fillRect(point.x * Game.SCALE, point.y * Game.SCALE, Game.SCALE, Game.SCALE);
		}
		
		g.setColor(Color.BLUE);
		g.fillRect(head.x * Game.SCALE, head.y * Game.SCALE, Game.SCALE, Game.SCALE);
	}
}
