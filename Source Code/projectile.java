import java.awt.image.BufferedImage;

public class projectile {

	int xPos;
	int yPos;
	int xDest;
	int yDest;
	int xSum;
	int ySum;
	double slope;
	BufferedImage icon;
	int vel;
	int dmg;
	int height;
	int width;
	int interval;
	boolean active = false;
	String projType;
	
	
	public projectile() {
		
	}
	public void setYSum(int y){
		ySum = y;
	}
	public void setInterval(int a){
		interval = a;
	}
	public void setXSum(int x){
		xSum = x;
	}
	public void setXPosition(int x){
		xPos = x;
	} 
	public void setYPosition(int y){
		yPos = y;
	}
	public void setXDestination(int x){
		xDest = x;
	}
	public void setActive() {
		active = true;
	}
	public void setInactive() {
		active = false;
	}
	public void setYDestination(int y){
		yDest = y;
	}
	public void setImage(BufferedImage img){
		icon = img;
	}
	public void setVelocity(int speed){
		vel = speed;
	}
	public void setDamage(int damage){
		dmg = damage;
	}
	public void setHeight(int h){
		height = h;
	}
	public void setWidth(int w){
		width = w;
	}
	public void setProjectileType(String type){
		projType = type;
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public int getXDestination(){
		return xDest;
	}
	public int getYDestination(){
		return yDest;
	}
	public BufferedImage getImage(){
		return icon;
	}
	public double getSlope(){
		double p1 = yPos - yDest;
		double p2 = xPos - xDest;
		double sl = p1/p2;
		return sl;
	}
	public int getVelocity(){
		return vel;
	}
	public int getDamage(){
		return dmg;
	}
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public String getProjectileType(){
		return projType;
	}	
	public int getXSum(){
		return xSum;
	}
	public int getYSum(){
		return ySum;
	}
	public int getInterval(){
		return interval;
	}
	public boolean isActive(){
		return active;
	}
}
