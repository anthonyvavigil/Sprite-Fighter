import java.awt.image.BufferedImage;
import java.util.HashMap;

public class item {
	
	String itemType;
	
	String[] playerEffects;
	/*
	 * array of all effects that this item  has on the player
	 */
	
	int xPos;
	int yPos;
	int height;
	int width;
	String type;
	HashMap<String, String> itemHMap;
	BufferedImage png;
	
	public item() {
		
	}
	public void setItemHash(HashMap<String, String> newItemHash){
		itemHMap = newItemHash;
	}
	public void setX(int newX){
		xPos = newX;
	}
	public void setY(int newY){
		yPos = newY;
	}
	public void setHeight(int newHeight){
		height = newHeight;
	}
	public void setWidth(int newWidth){
		width = newWidth;
	}
	public void setIcon(BufferedImage itemIcon){
		png = itemIcon;
		
	}
	public void setPlayerEffects(String[] effects){
		playerEffects = new String[effects.length];
		playerEffects = effects;
	}
	public void setItemType(String newItemType){
		itemType = newItemType;
	}
	
	public HashMap<String, String> getItemHash(){
		return itemHMap;
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public BufferedImage getIcon(){
		return png;
	}
	public String[] getPlayerEffects(){
		return playerEffects;
	}
	public String getItemType(){
		return itemType;
	}
}
