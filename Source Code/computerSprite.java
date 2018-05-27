import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class computerSprite {
	int xPos = 0;
	int yPos = 0;
	int height = 100;
	int width = 50;
	int dmg = 0;
	int hp = 0;
	int vel;
	int xSum;
	int ySum;
	int maxHP;
	String inLevel;
	String file;
	BufferedImage compSprPNG = null;
	String sprType = "";
	ArrayList<BufferedImage> aniFr = new ArrayList<BufferedImage>();
	ArrayList<String> fileLocAnimFr = new ArrayList<String>();
	boolean isMoving;
	int aninterval;
	boolean isAlive = true;
	
	
	public computerSprite() {

	}
	 
	public void kill(){
		isAlive = false;
	}
	public void setAnimInterv(int a){
		aninterval = a;
	}
	public void setMoving(){
		isMoving = true;
	}
	public void setStill(){
		isMoving = false;
	}
	public void setXSum(int x){
		xSum = x;
	}
	public void setYSum(int y){
		ySum = y;
	}
	public int getXSum(){
		return xSum;
	}
	public int getYSum(){
		return ySum;
	}
	public void setLevel(String levelThatTheBotsAreIn){
		inLevel = levelThatTheBotsAreIn;
	}
	public void setDamage(int newDamage){
		dmg = newDamage;
	}
	public void setMaxHP(int newMaxHP){
		maxHP = newMaxHP;
		hp = newMaxHP;
	}
	public void setHP(int newHP){
		hp = newHP;
	}
	public void setVelocity(int velo){
		vel = velo;
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

	public void setAnimFrames(ArrayList<String> fileLoc){
		fileLocAnimFr = fileLoc;
		for(int i = 0; i < fileLoc.size(); i++){
			try {
				aniFr.add(ImageIO.read(new File((fileLoc.get(i) + ".png"))));
			} catch (IOException e1){

			}
		}
	}
	public void setImage(String fileLoc){
		file = fileLoc;
		try {
			compSprPNG = ImageIO.read(new File(fileLoc));
			height = compSprPNG.getHeight();
			width = compSprPNG.getWidth();
		} catch (IOException e) {
			System.out.println("ERR: sprPng not found in file location");
			System.out.println("     File Location: " + fileLoc);
			System.out.println("compSprPNG now equals null");
		}
	}


	public void setSprType(String a){
		sprType = a;
	}
	public void setHeight(int a){
		height = a;
	}
	public void setWidth(int a){
		width = a;
	}


	public String getLevel(){
		return inLevel;
	}
	public String getFileLoc(){
		return file;
	}
	public int getDamage(){
		return dmg;
	}
	public int getHP(){
		return hp;
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public String getSprType(){
		return sprType;
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
	public int getVelocity(){
		return vel;
	}
	public BufferedImage getPNG(){
		return compSprPNG;
	}
	public boolean getMotionStatus(){
		return isMoving;
	}
	public ArrayList<BufferedImage> getAnimFrames(){
		return aniFr;
	}
	public ArrayList<String> getAnimFileLoc(){
		return fileLocAnimFr;
	}
	public int getAnimInterval(){
		return aninterval;
	}
	public boolean getLifeStatus(){
		return isAlive;
	}
	public int getMaxHP(){
		return maxHP;
	}
}
