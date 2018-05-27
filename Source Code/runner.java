   
import java.awt.Graphics;

public class runner extends runLoop{
	/**
	 * 
	 */
	private static final long serialVersionUID = 902468876551184020L;
	public static boolean uiDone = false;
	
	public void init(){
		//introUiStart();
		/*while(uiDone = false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//uiStarters();
		initializeItems();
		initializeLevels(2, 3, 1000, 40);
		setSize(scrW, scrH);
		initializeEnemies();
		Thread th = new Thread(this);
		th.start();
		offscreen = createImage(1920, 1050);
		d = offscreen.getGraphics();
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	@Override
	public void paint(Graphics g) {
		d.clearRect(0, 0, scrW, scrH);
		
		drawLevel();
		
		a.setHeight(sprH);
		a.setWidth(sprW);
		drawSprite(a);
		drawBots();
		drawMiniMap();
		drawHealthBar();
		drawItems();
		drawBotHealthBars();
		eraseCursor();
		drawCHair();
		drawProjectiles();
		g.drawImage(offscreen, 0, 0, this);
	}
	public void update(Graphics g){
		paint(g);
	}
}
