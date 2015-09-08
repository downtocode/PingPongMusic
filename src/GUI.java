import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import org.jfugue.*;

public class GUI extends JFrame {

	private JFrame mainArea;
	// Dimensions
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	
	private static int SNAP_HOME = 5; // steps to snap back home
	private static int GOING_AWAY = 15; // steps to get to "away"
	private ArrayList<String> journey1 = MyMusicApp.goingAway(GOING_AWAY, 0);
	private ArrayList<String> journey2 = MyMusicApp.comingHome(SNAP_HOME, 0,
			journey1);
	private ArrayList<String> v1 = MyMusicApp.composeArr(MyMusicApp.compose(
			journey1, journey2));
	private ArrayList<String> v2 = MyMusicApp.voice2(v1);
	private ArrayList<String> v3 = MyMusicApp.voice3(v1, v2, 5);
	private ArrayList<String> v4 = MyMusicApp.voice4(v1, v2, v3);
	private ArrayList<String> vMain = MyMusicApp.composeArr(MyMusicApp.combine(
			v1, v2, v3, v4));
	Pattern pattn = new Pattern(MyMusicApp.combine(v1, v2, v3, v4));

	// cycling through this at any time, starts with v1 of course
	ArrayList<String> current = v1;

	private static int pointer = 0;
	private Player plyr1 = new Player();
	
	private player ply;
	private Thread thr;
	

	public GUI() {
		super();
		// mainArea = this;
		makeFrame();
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

	}

	private void makeFrame() {

		setTitle("Click me");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JPanel main = new JPanel();
		add(main);
		addMouseListener(new Helper());

		// Centering jFrame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = dim.width / 2;
		int h = dim.height / 2;
		setLocation(w - WIDTH / 2, h - HEIGHT / 2);

	}

	class Helper extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
            if (plyr1.isPlaying()){
            	stopMzk();
            }
			int i = v1.size() - 1;
			if (i == pointer) {
				startMzk(current.get(pointer));
				//player2.close();
				pointer = 0;
				current = vMain;
			} else {
				startMzk(current.get(pointer));
				//player2.close();
				pointer++;
			}
		}

	}
	
	public void startMzk(String n){
		if(ply == null){
			ply = new player(n);
		}
		else{
			ply = null;
			ply = new player(n);
		}
		if(thr == null){
			thr = new Thread(ply);
		}
		else{
			thr = null;
			thr = new Thread(ply);
		}
		thr.start();
	}
	
	public void stopMzk(){
		thr.interrupt();
		thr= null;
		ply= null;
		plyr1.stop();
	}
	
	class player implements Runnable{
		private String str;
		public player(String n){
			str= n;
		}
		public void run(){
			try{
				plyr1.play(str);
			}
			catch (Exception exception){}
		}
	}

}
