import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class ShowImage extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7132677479063908234L;
	Image image;
	
	public ShowImage(Image image){
		super();
		this.image = image;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int h = this.getHeight();
		int w = this.getWidth();
		g.drawImage(image, 10, 10, w-10, h-10, this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
}
