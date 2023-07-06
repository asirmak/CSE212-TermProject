import java.awt.Image;
import java.awt.Rectangle;

public class Enemy {
	
	// enemy class
	
	private Image enemyImage;
	private int health;
	private Rectangle rectangle;
	private boolean collided = false; // check enemy collided to player, not to hit same enemy more than once
	private boolean isDamaged; // to check player's bullet hit to enemy
	private boolean flag = false; // control flag for animation
	private boolean flagg = false; // control flag for animation
	private boolean flag2 = false;	// control flag to control enemy that moves horizontally
	private boolean bulletHas = false; // check enemy throw bullet
	
	public boolean isFlagg() {
		return flagg;
	}

	public void setFlagg(boolean flagg) {
		this.flagg = flagg;
	}
	
	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isDamaged() {
		return isDamaged;
	}

	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

	public Enemy(int health) {
		this.health = health;
	}
	
	public Enemy(Image enemyImage, Rectangle rectangle) {
		this.enemyImage = enemyImage;
		this.rectangle = rectangle;
	}
	
	public Enemy(Image enemyImage, int health, Rectangle rectangle) {
		this.enemyImage = enemyImage;
		this.health = health;
		this.rectangle = rectangle;
	}
	
	public void takeDamage() {
		health--;
	}
	
	public boolean isDestroyed() {
		return health <= 0;
	}
	

	public boolean isCollided () {
		return collided ;
	}

	public void setCollided (boolean collided ) {
		this.collided  = collided ;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}


	public void setEnemyImage(Image enemyImage) {
		this.enemyImage = enemyImage;
	}


	public Image getEnemyImage() {
		return enemyImage;
	}

	public boolean isBulletHas() {
		return bulletHas;
	}

	public void setBulletHas(boolean bulletHas) {
		this.bulletHas = bulletHas;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}