package project;

import java.awt.*;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


public class CustomButton extends JButton implements MouseListener{
	
	Dimension size = new Dimension(250,150);
	
	boolean hover =false;
	boolean click = false;
	
	String text = "";

	public CustomButton(String text) {
		setVisible(true);
		setFocusable(true);
		setContentAreaFilled(false);
		setBorderPainted(false);
		this.text = text;
		addMouseListener(this);
		
	
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(click) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 255, 255);
		}
		g.setColor(new Color(0, 50, hover? 255:180));
		
		g.fillRect(0, 0, 250, 7);
		g.fillRect(0, 143, 250, 7);
		g.fillRect(0, 0, 7, 150);
		g.fillRect(243,0,7,150);
		
		g.setColor(new Color (230, 0 , 230));
		
		g.fillRect(14, 14, 222, 222);
		
		g.setColor(Color.WHITE);
		
		g.setFont(Font.decode("arial-BOLD-24"));
		
		FontMetrics metrics = g.getFontMetrics();
		
		int width = metrics.stringWidth(text);
		
		g.drawString(text, 125 - width /2, 75);
				
		
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	@Override
	public Dimension getMaximumSize() {
		return size;
	}
	@Override
	public Dimension getMinimumSize() {
		return size;
	}
	
	public void setButtonText(String text) {
		this.text = text;
	}
	public String getButtonText() {
		return text;
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		hover = true;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		click = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click= false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		hover = true;
	}


}
