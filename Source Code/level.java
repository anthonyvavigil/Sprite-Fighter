import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;


public class level {



	/*
	 * door1 - door on the left side of the screen
	 * door2 - door on the top of the screen
	 * door3 - door on the right side
	 * door4 - door on the bottom of the screen
	 */

	File cfg = new File("maplayout.txt");
	int[] door;
	BufferedImage lvlbg;
	int[] items;
	String curLev = "";
	Scanner scn;

	public level(){
	}
	
	public void setLev(String a){
		curLev = a;
	}
	public void setDoors(int[] a){
		door = a;
	}
	public void setItems(int[] a){
		items = a;
	}
	public void setPNG(BufferedImage a){
		lvlbg = a;
	}

	
	public int[] getDoors(){
		return door;
	}
	public int[] getItems(){
		return items;
	}
	public String getLevel(){
		return curLev;
	}
	public BufferedImage getPNG(){
		return lvlbg;
	}
}
