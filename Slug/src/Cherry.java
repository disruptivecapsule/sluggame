import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class Cherry extends Point {
	public static final String img_file = "cherry.png";
	public Point pos;
	private static BufferedImage img;
	
	//Place cherry in new random place
	public void spawn(){
		Random random = new Random();
		pos = new Point(
				random.nextInt(79), random.nextInt(69));
//		System.out.println("spawn" + pos.x + pos.y);
	}
	
	public void draw(Graphics g) {
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		g.drawImage(img, pos.x * Game.SCALE, pos.y * Game.SCALE, Game.SCALE, Game.SCALE, null);
//		System.out.println("draw cherry" + pos.x + pos.y);
	}
}
