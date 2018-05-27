import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class sprite {

	
	int sprTime = 1000;
	int sprCD = 1000;
	int xPos = 0;
	int yPos = 0;
	int height = 100;
	int width = 50;
	int h2 = 100;
	int w2 = 50;
	int h3 = 100;
	int w3 = 100;
	int HP;
	int damage;
	BufferedImage sprPNG3 = null;
	BufferedImage sprPNG2 = null;
	BufferedImage sprPNG = null;
	String sprType = "";

	public sprite(){
		
	}
	
	
	public void setHP(int newHP){
		HP = newHP;
	}
	public void sumXPos(int increment){
		xPos += increment;
	}
	public void minXPos(int increment){
		xPos -= increment;
	}
	public void minYPos(int increment){
		yPos -= increment;
	}
	public void sumYPos(int increment){
		yPos += increment;
	}
	public void setImage(String fileLoc){
		try {
			sprPNG = ImageIO.read(new File(fileLoc));
			height = sprPNG.getHeight();
			width = sprPNG.getWidth();
		} catch (IOException e) {
			System.out.println("ERR: sprPng not found in file location");
			System.out.println("     File Location: " + fileLoc);
			System.out.println("sprPNG now equals null");
		}
	}
	public void setImage2(String fileLoc){
		try {
			sprPNG2 = ImageIO.read(new File(fileLoc));
			h2 = sprPNG.getHeight();
			w2 = sprPNG.getWidth();
		} catch (IOException e) {
			System.out.println("ERR: sprPng not found in file location");
			System.out.println("     File Location: " + fileLoc);
			System.out.println("sprPNG now equals null");
		}
	}
	public void setImage3(String fileLoc){
		try {
			sprPNG3 = ImageIO.read(new File(fileLoc));
			h3 = sprPNG.getHeight();
			w3 = sprPNG.getWidth();
		} catch (IOException e) {
			System.out.println("ERR: sprPng not found in file location");
			System.out.println("     File Location: " + fileLoc);
			System.out.println("sprPNG now equals null");
		}
	}
	public void setSprType(String a){
		sprType = a;
	}
	public void setHeight(int a){
		height = a;
	}
	public void setHeight2(int a){
		h2 = a;
	}
	public void setHeight3(int a){
		h3 = a;
	}
	public void setWidth(int a){
		width = a;
	}
	public void setWidth2(int a){
		w2 = a;
	}
	public void setWidth3(int a){
		w3 = a;
	}
	public int getHP(){
		return HP;
	}
	public int getHeight(){
		return height;
	}
	public int getHeight2(){
		return h2;
	}
	public int getHeight3(){
		return h3;
	}
	public int getWidth(){
		return width;
	}
	public int getWidth2(){
		return w2;
	}
	public int getWidth3(){
		return w3;
	}
	public int getSprintTime(){
		return sprTime;
	}
	public int getSprintCooldown(){
		return sprCD;
	}
	public String getSprType(){
		return sprType;
	}
	public String getAction1(){
		if(sprType.equals("turtle")){
			return "defense 30";
		} else {
			return "default";
		}
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public void setX(int a){
		xPos = a;
	}
	public void setY(int a){
		yPos = a;
	}
	public BufferedImage getPNG(){
		return sprPNG;
	}
	public BufferedImage getPNG2(){
		return sprPNG2;
	}
	public BufferedImage getPNG3(){
		return sprPNG3;
	}
	public void setSprintTime(int a){
		sprTime = a;
	}
	public void setSprintCooldown(int a){
		sprCD = a;
	}
}

