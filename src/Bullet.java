import java.awt.Image;
import java.awt.Rectangle;

public class Bullet {
	
	// bullet class
	
	private Image bulletImage;
	private Rectangle rectangle;
	
	public Bullet(Image bulletImage, Rectangle rectangle) {
		this.bulletImage = bulletImage;
		this.rectangle = rectangle;
	}	
	public Image getBulletImage() {
		return bulletImage;
	}
	public void setBulletImage(Image enemyImage) {
		this.bulletImage = enemyImage;
	}
	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}	
		
}
