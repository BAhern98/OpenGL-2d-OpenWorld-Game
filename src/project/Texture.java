
package project;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String filename) {//reads texture image
		BufferedImage bi;
		try {
			//bi = ImageIO.read(new File("./res/"+ filename));
			bi = ImageIO.read(new File("./resource/"+ filename));//
			width= bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int[width * height * 4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for(int i = 0; i< width ; i++) {
				for(int j = 0; j<height; j++) {
					int pixel = pixels_raw[i*width+j];
					pixels.put((byte)((pixel >> 16) & 0xFF));//red
					pixels.put((byte)((pixel >> 8) & 0xFF));//green

					pixels.put((byte)((pixel>> 0)  & 0xFF));//blue

					pixels.put((byte)((pixel >> 24) & 0xFF));//alpha

				}
			  	
			}
			pixels.flip();//flips buffer
			
			id = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D,id);//binding texures to id
			
			 
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);//nearest gives sharper texture
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);	
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void finalize() throws Throwable{
		glDeleteTextures(id);
		super.finalize();
	}
	
	public void bind(int sampler) {
		if(sampler >= 0 && sampler <=31 ) {
		glActiveTexture(GL_TEXTURE0 + sampler);//bind texture to first sampler
		glBindTexture(GL_TEXTURE_2D, id);//bind image
	}  


	}
}
