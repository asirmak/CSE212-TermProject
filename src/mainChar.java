import java.awt.Image;
import java.awt.Rectangle;

public class mainChar{
		
		// player class
	
		private int mainCharX = 600;;
		private int mainCharY = 600;;		
		private int mainCharWidth = 80;;
		private int mainCharHeight =80;;
		private int mainCharSpeed = 10;
		private int mainCharHealth = 3;
		private boolean collided = false; // to check player hit aliens or bullet hit player
		private int changeNum = 10; // number of change in image when collided
		
		Rectangle rectangle;
		Image playerImage;
				
		public Image getPlayerImage() {
			return playerImage;
		}

		public void setPlayerImage(Image playerImage) {
			this.playerImage = playerImage;
		}

		public Rectangle getRectangle() {
			return rectangle;
		}

		public void setRectangle(Rectangle rectangle) {
			this.rectangle = rectangle;
		}

		public boolean isCollided() {
			return collided;
		}

		public void setCollided(boolean collided) {
			this.collided = collided;
		}

		public int getMainCharHealth() {
			return mainCharHealth;
		}

		public void setMainCharHealth(int mainCharHealth) {
			this.mainCharHealth = mainCharHealth;
		}

		public int getMainCharX() {
			return mainCharX;
		}

		public int getMainCharY() {
			return mainCharY;
		}

		public int getMainCharWidth() {
			return mainCharWidth;
		}

		public int getMainCharHeight() {
			return mainCharHeight;
		}

		public int getMainCharSpeed() {
			return mainCharSpeed;
		}

		public void setMainCharX(int mainCharX) {
			this.mainCharX = mainCharX;
		}

		public void setMainCharY(int mainCharY) {
			this.mainCharY = mainCharY;
		}

		public void setMainCharWidth(int mainCharWidth) {
			this.mainCharWidth = mainCharWidth;
		}

		public void setMainCharHeight(int mainCharHeight) {
			this.mainCharHeight = mainCharHeight;
		}

		public void setMainCharSpeed(int mainCharSpeed) {
			this.mainCharSpeed = mainCharSpeed;
		}

		public int getChangeNum() {
			return changeNum;
		}

		public void setChangeNum(int changeNum) {
			this.changeNum = changeNum;
		}


	}
