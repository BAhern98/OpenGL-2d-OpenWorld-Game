package project;

import java.awt.*;
import javax.swing.JPanel;

import world.TileRenderer;

import javax.swing.BoxLayout;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.*;

//public class Menu extends JPanel{
public class Menu {
	public void renderMenu(Graphics g){
		Font font1 = new Font("arial",Font.BOLD,50);
		
		g.setFont(font1);
		g.setColor(Color.white);
		g.drawString("FYP GAME", 100 , 100 );
		
	}
	
		
}
			
		
//		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
//		
//		
//		
//		add(Box.createVerticalStrut(280));
//
//		
//		CustomButton buttom 
	


