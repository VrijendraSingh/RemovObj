package vidCapModule;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import org.opencv.core.Mat; 


public class VideoPanel extends JPanel
{ 
	private static final long serialVersionUID = 1L; 
	private BufferedImage image; 
	
	// Create a constructor method 
	public VideoPanel()
	{ 
		super(); 
	
	} 
	
	private BufferedImage getimage()
	{ 
		return image; 
	} 
	
	
	public void setImage(BufferedImage newimage)
	{ 
		image=newimage; 
		return; 
	}
	
	
	/** 
	* Converts/writes a Mat into a BufferedImage. 
	* 
	* @param matrix Mat of type CV_8UC3 or CV_8UC1 
	* @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY 
	*/ 
	public static BufferedImage matToBufferedImage(Mat matrix) 
	{ 
		int cols = matrix.cols(); 
		int rows = matrix.rows(); 
		int elemSize = (int)matrix.elemSize(); 
		byte[] data = new byte[cols * rows * elemSize]; 
		int type; 
		matrix.get(0, 0, data); 
		switch (matrix.channels()) { 
		case 1: 
		type = BufferedImage.TYPE_BYTE_GRAY; 
		break; 
		case 3: 
		type = BufferedImage.TYPE_3BYTE_BGR; 
		// bgr to rgb 
		byte b; 
		for(int i=0; i<data.length; i=i+3) { 
		b = data[i]; 
		data[i] = data[i+2]; 
		data[i+2] = b; 
		} 
		break; 
		default: 
		return null; 
		} 
		BufferedImage image2 = new BufferedImage(cols, rows, type); 
		image2.getRaster().setDataElements(0, 0, cols, rows, data); 
		return image2; 
	} 
	
	public void paintComponent(Graphics g)
	{ 
		BufferedImage temp=getimage(); 
		if (temp != null) {
			g.drawImage(temp,10,10,temp.getWidth(),temp.getHeight(), this); 
		}
	} 	
} 