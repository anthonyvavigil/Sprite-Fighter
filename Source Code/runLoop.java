import java.applet.Applet;
import javax.swing.*;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

public class runLoop extends Applet implements Runnable, MouseMotionListener, MouseInputListener, KeyListener{
	private static final long serialVersionUID = 1L;
	public int saveCount = 0;
	public Image offscreen;
	public Graphics d;
	public int mouseX, mouseY, sprX, sprY, levX, levY, sprH, sprW, scrW, scrH, doorW, doorH, mapXPos, mapYPos, mapH, mapW, numBots, totBots, sprHP, maxSprHP, chSize, bulletTimer, botAnim, uiInt;
	public String sprite, uiEdit;
	public int frame = 0;
	public boolean up, down, left, right, sprint, click, door1, door2, door3, door4, playerIsMoving;
	public int projInterv = 0, thisInterv = 0;
	public int sens = 4, sprAnim = 0;
	public int damage;
	sprite a = new sprite();
	public level[][] lev = new level[4][5];
	public computerSprite[] bots = new computerSprite[300];
	public ArrayList<projectile> proj = new ArrayList();
	public ArrayList<computerSprite> curBots = new ArrayList();
	computerSprite[] botTypes;
	int mZoom = 3;
	computerSprite z = new computerSprite();
	public int sprintTimer = 0; 
	public int sprintCooldownTimer = 0;
	public boolean onSprintCooldown = false;
	public ArrayList<item> itemsOnLevel = new ArrayList();
	ArrayList<item> itemTypes = new ArrayList();
	public boolean hasKey = false;

	public void run() {
		JFrame fr = new JFrame();
		JLabel charPick = new JLabel();
		charPick.setText("Pick Your Character");




		while(true){

			//initializeProjectiles();

			sprHP = a.getHP();
			xMotion();
			yMotion();

			zombieMotion();

			projTest();
			projMotion();
			projInBounds();

			itemTest();
			testCollisions();

			printSpriteInfo();
			printLevelInfo();
			printBotInfo();
			printProjInfoLong();
			//printProjInfoShort();



			if(sprint == true){
				sprintTimer+=20;
				if(sprintTimer >= a.getSprintTime()){
					onSprintCooldown = true;
					sprintCooldownTimer=0;
					sprintTimer=0;
				}
			}
			if(onSprintCooldown){
				sprintCooldownTimer+=20;
				if(sprintCooldownTimer >= a.getSprintCooldown()){
					onSprintCooldown = false;
				}
			}

			System.out.println("\nFrame: " + frame + "\n");
			frame++;

			repaint();
			projInterv++;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();		
			}
		}
	}






	public void loadNewLevel(){
		getBotsOnLevel(("lv" + levX + levY));
		getItemsOnLevel();
		proj.clear();
	}


	/*
	 * DOORS
	 */



	public void itemTest(){
		for(int i = 0; i < itemsOnLevel.size(); i++){
			int itemX = itemsOnLevel.get(i).getX();
			int itemY = itemsOnLevel.get(i).getY();
			int itemW = itemsOnLevel.get(i).getWidth();
			int itemH = itemsOnLevel.get(i).getHeight();
			sprX = a.getX();
			sprY = a.getY();
			if(sprX + sprW > itemX && sprX < itemX + itemW && sprY + sprH > itemY && sprY < itemY + itemH){
				String itemType = itemsOnLevel.get(i).getItemType();
				if(itemType.equals("speed boost")){
					sens += 2;
				}
				if(itemType.equals("health increase (1.1x)")){
					a.setHP((a.getHP()*110)/100);
					maxSprHP = (maxSprHP*110)/100;
				}
				if(itemType.equals("health increase (1.2x)")){
					a.setHP((a.getHP()*120)/100);
					maxSprHP = (maxSprHP*120)/100;
				}
				if(itemType.equals("key")){
					hasKey = true;
				}
				else {
					damage = (damage*110)/100;
				}
				itemsOnLevel.remove(i);
			}
		}
	}
	public void doorTest(int x, int y){
		if(door1 && x < (doorW) && y  > (scrH/2)-(doorH/2) - a.getHeight() && y < (scrH/2) + (doorH/2)){
			if(levX == 1){
				levX = 4;
				loadNewLevel();
			}else{
				levX--;
				loadNewLevel();
			}
			d.clearRect(0, 0, scrW, scrH+30);
			a.setX(scrW-a.getWidth()-doorW-1);
		}if(door2 && y < doorW && x < (scrW/2) + (a.getWidth()) && x > (scrW/2) - ((doorH/2)+ a.getWidth())){
			if(levY == 5){
				levY = 1;
				loadNewLevel();
			} else {
				levY++;
				loadNewLevel();
			}
			d.clearRect(0, 0, scrW, scrH + 30);
			a.setY((scrH-(doorH-1))-a.getHeight());
		}if(door3 && x > ((scrW-(doorW))-a.getWidth()) && y < (scrH/2)+(doorH/2) && y > (scrH/2) - ((doorH/2) + a.getHeight())){
			if(levX == 4){
				levX = 1;
				loadNewLevel();
			} else {
				levX++;
				loadNewLevel();
			}
			d.clearRect(0, 0, scrW, scrH + 30);
			a.setX(a.getWidth());
		}if(door4 && y + a.getHeight() > (scrH - doorW) && x < ((scrW/2) + (doorH/2)) && x > ((scrW/2) - ((doorH/2) + a.getWidth()))){
			if(levY == 1){
				levY = 5;
				loadNewLevel();
			} else {
				levY--;
				loadNewLevel();
			}
			d.clearRect(0, 0, scrW, scrH + 30);
			a.setY(doorW+1);
		}

	}

	public void printDoorInfo(){
		System.out.println("door 1: " + door1);
		System.out.println("door 2: " + door2);
		System.out.println("door 3: " + door3);
		System.out.println("door 4: " + door4);
	}
	public void printSpriteInfo(){
		System.out.println("----------------SPRITE INFO---------------------");
		System.out.println("hgt: " + a.getHeight());
		System.out.println("wdt: " + a.getWidth());
		System.out.println("sprType: " + a.getSprType());
		System.out.println("act1: " + a.getAction1());
		System.out.println("X: " + a.getX() + " Y: " + a.getY());
	}
	public void printLevelInfo(){
		level currentLevel = lev[levX-1][levY-1];
		System.out.println("---------------LEVEL INFO-----------------------");
		printDoorInfo();
		System.out.print("items: ");
		for(int i = 0; i < currentLevel.getItems().length; i++){
			System.out.print(currentLevel.getItems()[i] + ", ");
		}
		System.out.println();
		System.out.println("level ID: " + currentLevel.getLevel());
	}
	public void printBotInfo(){
		System.out.println("---------------BOT INFO-------------------------");
		System.out.println("SIZE: " + curBots.size());
		for(int i = 0; i < curBots.size(); i++){
			System.out.println("BOT " + i + "======================");
			System.out.println("DMG: " + curBots.get(i).getDamage());
			System.out.println("HP: " + curBots.get(i).getHP());
			System.out.println("X: " + curBots.get(i).getX());
			System.out.println("Y: " + curBots.get(i).getY());
			System.out.println("Height: " + curBots.get(i).getHeight());
			System.out.println("Width: " + curBots.get(i).getWidth());
		} 
	}
	public void printProjInfoShort(){
		System.out.println("--------------SHORT PROJ INFO-------------------");
		System.out.println("Projectiles: " + proj.size());
		System.out.println("Interval Counter: " + projInterv);
	}
	public void printProjInfoLong() {
		System.out.println("-------------LONG PROJ INFO---------------------");
		System.out.println("Projectiles: " + proj.size());
		System.out.println("Interval Counter: " + projInterv);
		for(int i = 0; i < proj.size(); i++){
			System.out.println("Projectile " + i + "===================");
			System.out.println("X: " + proj.get(i).getX());
			System.out.println("Y: " + proj.get(i).getY());
			System.out.println("xDest: " + proj.get(i).getXDestination());
			System.out.println("yDest: " + proj.get(i).getYDestination());
		}
	}








	/*
	 * DRAW METHODS
	 */




	public void drawStaminaInfo(){

		//use textattribute class to make these strings look pretty in later builds

		if(onSprintCooldown){
			double cdNum = (double) sprintCooldownTimer/1000;
			cdNum = 1-cdNum;
			BigDecimal bd = new BigDecimal(cdNum);
			bd = bd.round(new MathContext(2));
			cdNum = bd.doubleValue();
			d.drawString("Cooldown Time Left: " + cdNum + "s", 500, 500);
		}else if(sprint){
			double cdNum = (double) sprintTimer/1000;
			cdNum = 2-cdNum;
			BigDecimal bd = new BigDecimal(cdNum);
			bd = bd.round(new MathContext(2));
			cdNum = bd.doubleValue();
			d.drawString("Sprint Time Left: " + cdNum + "s", 500, 500);
		}
	}
	public void drawBotHealthBars(){
		BufferedImage hBarp1 = null;
		BufferedImage hBarp2 = null;

		try {
			hBarp1 = ImageIO.read(new File("enmBar.png"));
			hBarp2 = ImageIO.read(new File("enmRed.png"));
		} catch (IOException e1){
			System.out.println("ERR: Bot Health Bars Could Not Be Resolved!");
		}
		for(int i = 0; i < curBots.size(); i++){
			if(curBots.get(i).getHP() < curBots.get(i).getMaxHP()){
				d.drawImage(hBarp1, curBots.get(i).getX() + 5, curBots.get(i).getY() - 35, 50, 10, this);
				double pctHP = (double) curBots.get(i).getHP() / (double) curBots.get(i).getMaxHP();
				double dw = (50 * pctHP);
				int grW = (int) dw;
				d.drawImage(hBarp2, curBots.get(i).getX() + 5, curBots.get(i).getY() - 35, grW, 10, this);
			}
		}
	}

	public void drawProjectiles(){
		if(!proj.isEmpty()){
			for(int i = 0; i < proj.size(); i++){
				d.drawImage(proj.get(i).getImage(), proj.get(i).getX(), proj.get(i).getY(), proj.get(i).getWidth(), proj.get(i).getHeight(),this);
			}
		}
	}

	public void drawLevel(){
		d.drawImage(lev[levX-1][levY-1].getPNG(), 0, 0, scrW, scrH, this);
		drawDoors();
	}
	public void drawItemsOnLevel(){

	}
	public void drawDoors() {
		door1 = false;
		door2 = false;
		door3 = false;
		door4 = false;
		if(lev[levX-1][levY-1].getDoors()[0] == 1){
			d.drawRect(0, ((scrH-doorW)/2)-(doorH/2), doorW, doorH);
			door1 = true;
		} else {
			door1 = false;
		}
		if(lev[levX-1][levY-1].getDoors()[1] == 1){
			d.drawRect((scrW/2)-(doorH/2), 0, doorH, doorW);
			door2 = true;
		} else {
			door2 = false;
		}
		if(lev[levX-1][levY-1].getDoors()[2] == 1){
			d.drawRect(scrW-doorW, ((scrH-doorW)/2)-(doorH/2), doorW, doorH);
			door3 = true;
		} else { 
			door3 = false;
		}
		if(lev[levX-1][levY-1].getDoors()[3] == 1){
			d.drawRect((scrW/2)-(doorH/2), (scrH-doorW), doorH, doorW);
			door4 = true;
		} else {
			door4 = false;
		}
	}
	public void eraseCursor(){
		Toolkit tk= getToolkit();
		Cursor transparent = tk.createCustomCursor(tk.getImage(""), new Point(), "trans");
		setCursor(transparent);
	}
	public void drawCHair(){
		if(!((mouseX < chSize) && (mouseY < chSize))){
			BufferedImage cHair = null;
			try {
				cHair = ImageIO.read(new File("crosshair2.png"));
			} catch(IOException e1){
				System.out.println("ERR: Crosshair could not be resolved");
			}
			d.drawImage(cHair, mouseX, mouseY, chSize, chSize, this);
		}
	}

	public void drawHealthBar(){
		BufferedImage hpBack = null;
		BufferedImage hpGreen = null;
		String hpStr = a.getHP() + "/" + maxSprHP;
		try {
			hpBack = ImageIO.read(new File("hplyr1.png"));
			hpGreen = ImageIO.read(new File("hpgreen.png"));
		} catch (IOException e1){
			System.out.println("ERR: HP Files not resolved");
		}
		d.drawImage(hpBack, (mapXPos + mapW + 50), 15, 400, 50, this);
		if(sprHP > 0){
			double pctHP = (double) sprHP / (double) maxSprHP;
			double dw = (342 * pctHP);
			int grW = (int) dw;
			d.drawImage(hpGreen, (mapXPos + mapW + 100), 18, grW, 40, this);
			d.drawString(hpStr, (mapXPos + mapW + 225), 44);
		} else {
			d.drawString("LOL U DED", (mapXPos + mapW + 232), 44);
		}
	}


	public void drawSprite(sprite Sprite){
		if(!playerIsMoving){
			d.drawImage(Sprite.getPNG(), Sprite.getX(), Sprite.getY(), Sprite.getWidth(), Sprite.getHeight(), this);
			sprAnim = 0;
		}
		else {
			sprAnim++;
			boolean rig = (mouseX > sprX);
			if(rig){
				Sprite.setImage("hn1r.png");
				Sprite.setImage2("hn2r.png");
				Sprite.setImage3("hn3r.png");
				Sprite.setWidth(sprW);
				Sprite.setHeight(sprH);
			} else {
				Sprite.setImage("hmn1.png");
				Sprite.setImage2("hmn2.png");
				Sprite.setImage3("hmn3.png");
				Sprite.setWidth(sprW);
				Sprite.setHeight(sprH);
			}
			if(sprAnim <= 4){
				d.drawImage(Sprite.getPNG3(), Sprite.getX(), Sprite.getY(), Sprite.getWidth(), Sprite.getHeight(), this);
			} else if ( sprAnim <= 8){
				d.drawImage(Sprite.getPNG2(), Sprite.getX(), Sprite.getY(), Sprite.getWidth(), Sprite.getHeight(), this);
			} else if( sprAnim <= 12){
				d.drawImage(Sprite.getPNG(), Sprite.getX(), Sprite.getY(), Sprite.getWidth(), Sprite.getHeight(), this);
				if(sprAnim >= 12){
					sprAnim = 0;
				}
			}
		}
	}



	//used when entering a level
	public void getBotsOnLevel(String lev){
		curBots.clear();
		numBots = 0; 
		for(int i = 0; i < totBots; i++){
			computerSprite cor = bots[i];
			String c = cor.getLevel();
			if(c.equals(lev)){
				curBots.add(bots[i]);
				numBots++;
			}
		}
	}
	public void getItemsOnLevel(){
		itemsOnLevel.clear();
		for(int i = 0; i < lev[levX][levY].getItems().length; i++){
			if(lev[levX][levY].getItems()[i] == 1){
				itemsOnLevel.add(itemTypes.get(i));
			}
		}
	}

	public void drawBots(){
		botAnim++;
		if(!(curBots.isEmpty())){
			for(int i = 0; i < curBots.size(); i++){
				if(curBots.get(i).getMotionStatus()){
					if(botAnim <= 8){
						d.drawImage(curBots.get(i).getAnimFrames().get(1), curBots.get(i).getX(), curBots.get(i).getY(), curBots.get(i).getWidth(), curBots.get(i).getHeight(), this);
					} else if(botAnim <= 16){
						d.drawImage(curBots.get(i).getAnimFrames().get(2), curBots.get(i).getX(), curBots.get(i).getY(), curBots.get(i).getWidth(), curBots.get(i).getHeight(), this);
					} else if(botAnim <= 24){
						d.drawImage(curBots.get(i).getAnimFrames().get(0), curBots.get(i).getX(), curBots.get(i).getY(), curBots.get(i).getWidth(), curBots.get(i).getHeight(), this);
					} else {
						botAnim = 0;
					} 
				} else {
					d.drawImage(curBots.get(i).getAnimFrames().get(0), curBots.get(i).getX(), curBots.get(i).getY(), curBots.get(i).getWidth(), curBots.get(i).getHeight(), this);
				}
			}
		}
	}


	public void drawItems(){
		for(int i = 0; i < itemsOnLevel.size(); i++){
			item a = itemsOnLevel.get(i);
			d.drawImage(a.getIcon(), a.getX(), a.getY(), a.getWidth(), a.getHeight(), this);
		}
	}

	public void drawMiniMap(){
		BufferedImage mini = null;
		try {
			mini = ImageIO.read(new File("miniMapDefault.png"));
		} catch (IOException e) {
			System.out.println("ERR: Mini Map File could not be found!!!!");
		}
		BufferedImage proc = null;
		int ediX = 51;
		int ediY = 64;
		if(((levX)*125)-150 > 0 && ((6-levY)*120)-150 > 0){
			proc = mini.getSubimage(((levX)*125)-150, ((6-levY)*120)-150, 200, 200);
		} else if(((6-levY)*120)-150 <= 0 && ((levX)*125)-150 > 0){
			proc = mini.getSubimage(((levX)*125)-150, 0, 200, 200);
			ediY = 28;
		} else if(((6-levY)*120)-150 > 0 && ((levX)*125)-150 <= 0){
			proc = mini.getSubimage(0, ((6-levY)*120)-150, 200, 200);
			ediX = 25;
		} else {
			proc = mini.getSubimage(0, 0, 200, 200);
			ediX = 25;
			ediY = 28;
		}

		double scaledX = sprX * (90.0/(double)scrW);
		double scaledY = sprY * (95.0/(double) scrH);




		d.drawRect(mapXPos-1, mapYPos-1, mapW+1, mapH+1);
		d.drawImage(proc, mapXPos, mapYPos, 200, 200, this);
		for(int i = 0; i < curBots.size(); i++){
			int ediX1 = 51;
			int ediY1 = 64;
			if(((levX)*125)-150 > 0 && ((6-levY)*120)-150 > 0){
				proc = mini.getSubimage(((levX)*125)-150, ((6-levY)*120)-150, 200, 200);
			} else if(((6-levY)*120)-150 <= 0 && ((levX)*125)-150 > 0){
				proc = mini.getSubimage(((levX)*125)-150, 0, 200, 200);
				ediY1 = 28;
			} else if(((6-levY)*120)-150 > 0 && ((levX)*125)-150 <= 0){
				proc = mini.getSubimage(0, ((6-levY)*120)-150, 200, 200);
				ediX1 = 25;
			} else {
				proc = mini.getSubimage(0, 0, 200, 200);
				ediX1 = 25;
				ediY1 = 28;
			}
			BufferedImage enmIcon = null;
			try {
				enmIcon = ImageIO.read(new File("enmI.png"));
			} catch (IOException e1){
				System.out.println("ERR: Enemy Icon not resolved");
			}

			double scaledX1 = curBots.get(i).getX() * (90.0/(double)scrW);
			double scaledY1 = curBots.get(i).getY() * (95.0/(double) scrH);
			d.drawImage(enmIcon, ((int) ediX1 + (int) scaledX1), ((int) ediY1+ (int) scaledY1), 10, 10, this);
		}
		d.fillOval((ediX+(int) scaledX),(ediY+(int) scaledY),10,10);

	}












	/*
	 * MOTION/PLAYER INTERACTION
	 */









	public void testCollisions() {
		if(!proj.isEmpty()){
			for(int i = 0; i < curBots.size(); i++){
				for(int j = 0; j < proj.size(); j++){
					int botX = curBots.get(i).getX();
					int botY = curBots.get(i).getY();
					int botW = curBots.get(i).getWidth();
					int botH = curBots.get(i).getHeight();
					int prjX = proj.get(j).getX();
					int prjY = proj.get(j).getY();
					int prjW = proj.get(j).getWidth();
					int prjH = proj.get(j).getWidth();


					if(((prjX + prjW) > botX) && ((prjY + prjH) > botY) && (prjX < (botX + botW)) && (prjY < (botY + botH))){
						curBots.get(i).setHP((curBots.get(i).getHP()-proj.get(j).getDamage()));
						if(curBots.get(i).getHP() <= 0){
							curBots.remove(i);

						} 
						proj.remove(j);
					}
					else if(prjX < prjW || prjX > scrW || prjY < prjH || prjY > scrH){
						proj.remove(j);
					}
				}
			}
		}
	}

	public void projTest(){

		if(proj.isEmpty()){
			thisInterv = 0;
		} else {
			thisInterv = proj.get(0).getInterval();
		}
		if(click == true && projInterv >= thisInterv) {
			projectile newProj = new projectile();
			newProj.setHeight(30);
			newProj.setWidth(30);
			newProj.setDamage(damage);
			newProj.setVelocity(10);
			newProj.setXDestination(mouseX + (chSize/2));
			newProj.setYDestination(mouseY + (chSize/2));
			newProj.setActive();
			newProj.setXPosition(sprX);
			newProj.setYPosition(sprY);
			newProj.setInterval(25);
			thisInterv = 25;
			BufferedImage proj1 = null;
			try {
				proj1 = ImageIO.read(new File("prj1.png"));
			}
			catch(IOException e1){
				System.out.println("ERR: Bullet 1 could not be resolved");
			}
			newProj.setImage(proj1);	

			Point p = linearMotion(newProj.getX(), newProj.getY(), newProj.getXDestination(), newProj.getYDestination(), newProj.getVelocity());

			System.out.println(p.toString());


			newProj.setXSum((int) p.getX());
			newProj.setYSum((int) p.getY());
			proj.add(newProj);
			projInterv = 0;
		}

	}

	public void projMotion() { 
		if(!proj.isEmpty()){
			for(int i = 0; i < proj.size(); i++){
				proj.get(i).setXPosition((proj.get(i).getX() + proj.get(i).getXSum()));
				proj.get(i).setYPosition((proj.get(i).getY() + proj.get(i).getYSum()));			
			}
		}
	}
	public void projInBounds() { 
		if(!proj.isEmpty()){
			for(int i = 0; i < proj.size(); i++){
				if(proj.get(i).getX() > scrW) {
					proj.remove(i);
				}
				else if(proj.get(i).getX() <= -40) {
					proj.remove(i);
				}
				else if(proj.get(i).getY() <= -40) {
					proj.remove(i);
				}
				else if(proj.get(i).getY() > scrH) {
					proj.remove(i);
				}
			}
		}
	}
	public void xMotion(){
		if(sprint == true && onSprintCooldown != true){
			sens = 6;
		} else {
			sens = 4;
		}
		if(left == true && a.getX() - sens > 0){
			a.minXPos(sens);
		} else if(left == true && a.getX() - sens <=0){ 
			a.setX(0);
		}
		else if(right == true && a.getX() + sens < scrW-a.getWidth()){
			a.sumXPos(sens);
		} else if(right == true && a.getX() + sens >= scrW-a.getWidth()){
			a.setX(scrW-a.getWidth());
		}
		doorTest(a.getX(), a.getY());
		sprX = a.getX();
	}
	public void yMotion(){
		if(up == true && a.getY() - sens > 0){
			a.minYPos(sens);
		} else if(up == true && a.getY() - sens <= 0){ 
			a.setY(0);
		}
		else if(down == true && a.getY() + sens < scrH-a.getHeight()){
			a.sumYPos(sens);
		} else if(down == true && a.getY() + sens >= scrH-a.getHeight()){
			a.setY(scrH-a.getHeight());
		}
		doorTest(a.getX(), a.getY());
		sprY = a.getY();
	}


	public void damagePlayer(int dmgAmt){
		a.setHP((a.getHP()-dmgAmt));
	}

	public Point linearMotion(int xPos, int yPos, int xDest, int yDest, int vel){


		double triW = Math.abs(xDest) - Math.abs(xPos);
		double triH = Math.abs(yDest) - Math.abs(yPos);

		if(yPos > yDest){
			triH = Math.abs(yPos)-Math.abs(yDest);
		}
		if(xPos > xDest){
			triW = Math.abs(xPos)-Math.abs(xDest);
		}


		double angle = Math.atan((triH/triW));

		double hypotenuse = Math.sqrt((Math.pow(triH, 2) + Math.pow(triW, 2)));

		int xVel = (int) Math.ceil((vel * Math.cos(angle)));
		int yVel = (int) Math.ceil((vel * Math.sin(angle)));

		if(yPos > yDest){
			yVel = -yVel;
		}
		if(xPos > xDest){
			xVel = -xVel;
		}

		return new Point(xVel, yVel);
	}

	public void zombieMotion() {
		int botX;
		int botY;

		for(int i = 0; i < curBots.size(); i++){
			botX = curBots.get(i).getX();
			botY = curBots.get(i).getY();



			Point n = linearMotion(botX, botY, sprX, sprY, curBots.get(i).getVelocity());
			if(curBots.get(i).isMoving) {
				botX+= n.getX();
				botY+= n.getY();	
			}



			if((botX + curBots.get(i).getWidth()) > sprX && botX < (sprX + a.getWidth()) && (botY + curBots.get(i).getHeight()) > sprY && botY < (sprY + a.getHeight())){
				damagePlayer(curBots.get(i).getDamage());
				curBots.get(i).setStill();
			}
			else {
				curBots.get(i).setMoving();
			}

			if(botX >= 0 && botX - 100 < scrW && botY >= 0 && botY - 100 < scrH){
				curBots.get(i).setX(botX);
				curBots.get(i).setY(botY);
			}
		}
	}

	public void exponMotion() {
		/*
		 * 
		 * 
		 * exponential motion until within 25 units of the player
		 * bot travels 4/11 * the distance between the bot and the player
		 * 
		 * 
		 */

		int botX = 0;
		int botY = 0;
		for(int i = 0; i < curBots.size(); i++){
			botX = curBots.get(i).getX();
			botY = curBots.get(i).getY();

			int distX = botX - sprX;
			int distY = botY - sprY;

			if(Math.abs(distX) < 25){
				botX += 6;
			}else{
				botX += ((distX * 4)/11);
			}
			if(Math.abs(distY) < 25){
				botY += 6;
			}else{
				botY += ((distY * 4)/11);
			}
			curBots.get(i).setX(botX);
			curBots.get(i).setY(botY);
		}
	}

	public level getLevelAt(String curLev){
		level returner = null;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 5; j++){
				level a = lev[i][j];
				if(a.getLevel().equals(curLev)){

					returner = lev[i][j];
				}
			}
		}
		return returner;
	}







	/*
	 * INITIALIZERS
	 */


	public void introUiStart(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					introUi frame = new introUi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void uiStarters() {
		if (introUi.resolution != null) {
			uiEdit = introUi.resolution;
			uiEdit = uiEdit.substring(0, (uiEdit.lastIndexOf('X') - 1));
			scrW = Integer.parseInt(uiEdit);
			uiEdit = uiEdit.substring((uiEdit.lastIndexOf('X') + 1));
			scrH = Integer.parseInt(uiEdit);
		} else {
			scrW = 1300;
			scrH =900;
		}

	}






	public void initializeEnemies(){
		Scanner scn1 = null;
		try {
			scn1 = new Scanner(new File("bottypes"));
		} catch (FileNotFoundException e1) {
			System.out.println("ERR: bottypes.txt could not be resolved");
		}
		int count = 0;
		botTypes = new computerSprite[6];
		while(scn1.hasNextLine()){
			String currLine = scn1.nextLine();
			botTypes[count] = new computerSprite();
			botTypes[count].setSprType(currLine.substring(0,4));
			botTypes[count].setMaxHP(Integer.valueOf(currLine.substring(5,7)));
			botTypes[count].setDamage(Integer.valueOf(currLine.substring(8,10)));
			botTypes[count].setImage(currLine.substring(14, 18));
			botTypes[count].setVelocity(Integer.valueOf(currLine.substring(11,13)));
			botTypes[count].setHeight(Integer.valueOf(currLine.substring(34, 37)));
			botTypes[count].setWidth(Integer.valueOf(currLine.substring(38, 41)));
			String p1 = currLine.substring(14,18);
			String p2 = currLine.substring(19, 23);
			String p3 = currLine.substring(24, 28);
			ArrayList<String> temp = new ArrayList();
			temp.add(p1); temp.add(p2); temp.add(p3);
			System.out.println(temp);
			System.out.println();
			System.out.println();
			System.out.println();



			botTypes[count].setAnimFrames(temp);
			System.out.println("test_1");
			count++;
		}
		int totIter = 0;
		for(int x = 1; x < 5; x++){
			for(int y = 1; y < 6; y++){
				Random rand = new Random();
				int botsOnLevel = rand.nextInt(9);
				for(int i = 0; i < botsOnLevel; i++){
					int r = rand.nextInt(6);
					bots[totIter] = new computerSprite();
					bots[totIter].setAnimFrames(new ArrayList<String>());
					if(r >= 5){
						bots[totIter].setDamage(botTypes[5].getDamage());
						bots[totIter].setMaxHP(botTypes[5].getHP());
						bots[totIter].setVelocity(botTypes[5].getVelocity());
						bots[totIter].setImage("enm6.png");
						bots[totIter].setAnimFrames(botTypes[5].getAnimFileLoc());
						bots[totIter].setWidth(botTypes[5].getHeight());
						bots[totIter].setWidth(botTypes[5].getHeight());
					}
					else if(r == 4){
						bots[totIter].setDamage(botTypes[4].getDamage());
						bots[totIter].setMaxHP(botTypes[4].getHP());
						bots[totIter].setVelocity(botTypes[4].getVelocity());
						bots[totIter].setImage("enm5.png");
						bots[totIter].setAnimFrames(botTypes[4].getAnimFileLoc());
						bots[totIter].setWidth(botTypes[4].getHeight());
						bots[totIter].setWidth(botTypes[4].getHeight());
					}
					else if(r == 3){
						bots[totIter].setDamage(botTypes[3].getDamage());
						bots[totIter].setMaxHP(botTypes[3].getHP());
						bots[totIter].setVelocity(botTypes[3].getVelocity());
						bots[totIter].setImage("enm4.png");
						bots[totIter].setWidth(botTypes[3].getHeight());
						bots[totIter].setWidth(botTypes[3].getHeight());
						bots[totIter].setAnimFrames(botTypes[3].getAnimFileLoc());
					}
					else if(r == 2){
						bots[totIter].setSprType("band");
						bots[totIter].setDamage(botTypes[2].getDamage());
						bots[totIter].setMaxHP(botTypes[2].getHP());
						bots[totIter].setVelocity(botTypes[2].getVelocity());
						bots[totIter].setImage("enm3.png");
						bots[totIter].setWidth(botTypes[2].getHeight());
						bots[totIter].setWidth(botTypes[2].getHeight());
						bots[totIter].setAnimFrames(botTypes[2].getAnimFileLoc());
					}
					else if(r == 1){
						bots[totIter].setSprType("tank");
						bots[totIter].setDamage(botTypes[0].getDamage());
						bots[totIter].setMaxHP(botTypes[0].getHP());
						bots[totIter].setVelocity(botTypes[0].getVelocity());
						bots[totIter].setImage("enm2.png");
						bots[totIter].setWidth(botTypes[1].getHeight());
						bots[totIter].setWidth(botTypes[1].getHeight());
						bots[totIter].setAnimFrames(botTypes[1].getAnimFileLoc());
					} 
					else if (r == 0) {
						bots[totIter].setSprType("hunt");
						bots[totIter].setDamage(botTypes[0].getDamage());
						bots[totIter].setVelocity(botTypes[0].getVelocity());
						bots[totIter].setMaxHP(botTypes[0].getHP());
						bots[totIter].setImage("enm1.png");
						bots[totIter].setWidth(botTypes[0].getHeight());
						bots[totIter].setWidth(botTypes[0].getHeight());
						bots[totIter].setAnimFrames(botTypes[0].getAnimFileLoc());
					}
					bots[totIter].setLevel(("lv" + x + y));


					bots[totIter].setX(rand.nextInt(scrW));
					bots[totIter].setY(rand.nextInt(scrH));
					System.out.println(bots[totIter].getLevel());
					System.out.println(totIter);
					totIter++;
				}
			}
		}
		totBots = totIter;

	}



	public void initializeItems(){

		File inFile = new File("itemTypes.txt");
		Scanner scn = null;
		try {
			scn = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scrW = 1500;
		scrH = 1000;

		while(scn.hasNextLine()){

			item toAdd = new item();
			String a = scn.nextLine();
			String[] w = a.split(":");
			for(int i = 0; i < w.length; i++){
				toAdd.setItemType(w[0]);
				//toAdd.setHeight(Integer.valueOf(w[1]));
				toAdd.setHeight(scrH/11);
				System.out.println("H: " + toAdd.getHeight());
				//toAdd.setWidth(Integer.valueOf(w[2]));
				toAdd.setWidth(scrW/11);
				BufferedImage curImg = null;
				try {
					curImg = ImageIO.read(new File((w[3] + ".png")));
				} catch (IOException e1){
					System.out.println(w[3] + ".png could not be found");
				}  

				toAdd.setX((int)((scrW*(i+1))/7));
				toAdd.setY((int)(scrH/2));
				toAdd.setIcon(curImg);
			}
			itemTypes.add(toAdd);
		}
	}

	public void loadSavedGame(int gameSaveSlot){

		/*
		 * THIS METHOD INITIALIZES THE LEVELS AND ENEMIES
		 */

		File in = new File("game_save_slot" + gameSaveSlot + ".txt");
		Scanner scn = null;
		try {
			scn = new Scanner(in);
		} catch (IOException e1){
			e1.printStackTrace();
		}
		initializeLevels(Integer.valueOf(scn.nextLine().split(":")[1]), Integer.valueOf(scn.nextLine().split(":")[1]), Integer.valueOf(scn.nextLine().split(":")[1]), Integer.valueOf(scn.nextLine().split(":")[1]));
		initializeEnemies();
	}

	public void saveGame(){

		/*
		 * FIX THIS SO THE USER CAN CHANGE THE OUTFILE LOCATION 
		 * LET THEM SET THIS IN THE OPENING MENU
		 */

		saveCount++;

		PrintWriter outPrinter = null;
		try {
			//outPrinter = new PrintWriter("C:\\Users\\Anthony Vigil\\workspace\\Post IB Exam Project Build 1.1.7\\src\\game_save_slot" + saveCount + ".txt");
			outPrinter = new PrintWriter("C:\\Users\\Anthony Vigil\\workspace\\Post IB Exam Project Build 1.1.7\\src\\game_save_slot" + saveCount + ".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * 
		 * saved variables are
		 * 
		 * player health
		 * level x/y
		 * player damage
		 * 
		 * everything else resets, as it is static
		 * 
		 */
		outPrinter.println("levX:" + levX);
		outPrinter.println("levY:" + levY);
		outPrinter.println("playerHP:" + a.getHP());
		outPrinter.println("damage:" + damage);


		System.out.println("RUNNING");
		outPrinter.close();
	}

	public void initializeLevels(int ilevX, int ilevY, int iplayerHP, int idamage){	




		levX = ilevX; 
		levY = ilevY;
		doorW = 20;
		doorH = 60;

		damage = idamage;

		sprH = 100;
		sprW = 50;
		scrH -= a.getHeight();
		mapXPos = 10;
		mapYPos = 10;
		mapH = 200;
		mapW = 200;
		sprHP = 1000;
		a.setSprintTime(8000);
		a.setSprintCooldown(1000);
		a.setHP(iplayerHP);
		maxSprHP = iplayerHP;
		a.setX((scrW/2)-sprW);
		a.setY((scrH/2)-sprH);
		a.setImage("hmn1.png");
		a.setImage2("hmn2.png");
		a.setImage3("hmn3.png");
		chSize = 40;



		Scanner scn = null;
		try {
			scn = new Scanner(new File("maplayout.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("ERR: maplayout.txt could not be resolved");

		}
		int[] door;
		int[] items;
		int x = 1;
		int y = 1;
		int count = 0;
		level cur;
		while(count < 20){
			count++;
			door = new int[4];
			items = new int[5];


			String currLine = scn.nextLine();
			System.out.println(currLine);

			x = Integer.valueOf(currLine.substring(2, 3));
			y = Integer.valueOf(currLine.substring(3, 4));
			lev[x-1][y-1] = new level();
			cur = new level();
			cur.setLev(currLine.substring(0, 4));
			if(currLine.substring(5, 6).equals("0")){
				door[0] = 0;
			} else {
				door[0] = 1;
			}if(currLine.substring(6, 7).equals("0")){
				door[1] = 0;
			} else {
				door[1] = 1;
			}if(currLine.substring(7,8).equals("0")){
				door[2] = 0;
			} else {
				door[2] = 1;
			}if(currLine.substring(8,9).equals("0")){
				door[3] = 0;
			} else {
				door[3] = 1;
			}
			cur.setDoors(door);
			if(currLine.substring(10, 11).equals("0")){
				items[0] = 0;
			} else if(currLine.substring(10, 11).equals("2")){
				items[0] = (((int) Math.random()));
			} else {
				items[0] = 1;
			}	
			if(currLine.substring(11, 12).equals("0")){
				items[1] = 0;
			} else if(currLine.substring(11, 12).equals("2")){
				items[1] = (((int) Math.random()));
			} else {
				items[1] = 1;
			}	
			if(currLine.substring(12, 13).equals("0")){
				items[2] = 0;
			} else if(currLine.substring(12, 13).equals("2")){
				items[2] = (((int) Math.random()));
			} else {
				items[2] = 1;
			}	
			if(currLine.substring(13, 14).equals("0")){
				items[3] = 0;
			} else if(currLine.substring(13, 14).equals("2")){
				items[0] = (((int) Math.random()));
			} else {
				items[3] = 1;
			}
			if(currLine.substring(14, 15).equals("0")){
				items[3] = 0;
			} else if(currLine.substring(14, 15).equals("2")){
				items[2] = (((int) Math.random()));
			} else {
				items[4] = 1;
			}

			cur.setItems(items);

			try {
				cur.setPNG(ImageIO.read(new File((currLine.substring(16) + ".png"))));
			} catch (IOException e) {
				//System.out.println("ERR: Could not resolve background");
				//System.out.println("Requested File: " + currLine.substring(16) + ".png");
			}
			lev[x-1][y-1].setItems(items);
			lev[x-1][y-1].setDoors(door);
			lev[x-1][y-1].setPNG(cur.getPNG());
			lev[x-1][y-1].setLev(cur.getLevel());

		}
	}






	/*
	 * Mouse/Keys
	 */





	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		click = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		click = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A' || e.getKeyCode() == 37){
			left = true;
			playerIsMoving = true;
		}
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W' || e.getKeyCode() == 38){
			up = true;
			playerIsMoving = true;
		}
		if(e.getKeyChar() == 's' || e.getKeyChar() == 'S' || e.getKeyCode() == 40){
			down = true;
			playerIsMoving = true;
		}
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D' || e.getKeyCode() == 39){
			right = true;
			playerIsMoving = true;
		}
		if(e.getKeyCode() == 16 || e.getKeyCode() == 17){
			sprint = true;
			playerIsMoving = true;
		}

	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A' || e.getKeyCode() == 37){
			left = false;
			playerIsMoving = false;
		}
		if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W' || e.getKeyCode() == 38){
			up = false;
			playerIsMoving = false;
		}
		if(e.getKeyChar() == 's' || e.getKeyChar() == 'S' || e.getKeyCode() == 40){
			down = false;
			playerIsMoving = false;
		}
		if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D' || e.getKeyCode() == 39){
			right= false;
			playerIsMoving = false;
		}
		if(e.getKeyCode() == 16 || e.getKeyCode() == 17){
			sprint = false;
			playerIsMoving = false;
		}
		if(e.getKeyChar() == 'h' || e.getKeyChar() == 'H'){
			saveGame();
		}
	}
	public void keyTyped(KeyEvent e) {

	}
}