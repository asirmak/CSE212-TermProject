import java.awt.Image;

public class hitAnim {

	// when player's bullet hit enemies this class is used
	
	public Image enemyImage;
	public int hitWidth;
	public int hitHeight;
	public int hitX;
	public int hitY;
	
	public hitAnim(Image enemyImage, int hitWidth, int hitHeight, int hitX, int hitY) {
		this.enemyImage = enemyImage;
		this.hitWidth = hitWidth;
		this.hitHeight = hitHeight;
		this.hitX = hitX;
		this.hitY = hitY;
	}
	
	public Image getEnemyImage() {
		return enemyImage;
	}
	public void setEnemyImage(Image enemyImage) {
		this.enemyImage = enemyImage;
	}
	public int getHitWidth() {
		return hitWidth;
	}
	public void setHitWidth(int hitWidth) {
		this.hitWidth = hitWidth;
	}
	public int getHitHeight() {
		return hitHeight;
	}
	public void setHitHeight(int hitHeight) {
		this.hitHeight = hitHeight;
	}
	public int getHitX() {
		return hitX;
	}
	public void setHitX(int hitX) {
		this.hitX = hitX;
	}
	public int getHitY() {
		return hitY;
	}
	public void setHitY(int hitY) {
		this.hitY = hitY;
	}	
}
