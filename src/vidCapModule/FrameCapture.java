package vidCapModule;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.opencv.*;
import org.opencv.core.*;
import org.opencv.highgui.*;

public class FrameCapture implements Runnable {

	private VideoCapture cap =  null;
	private boolean runFlag = false;
	boolean isWindoMode = false;
	
	private Thread th = null;
	public  FileOutputStream fout = null;
	public VideoPanel prevWnd = null;
	public FrameCapture(boolean isWindo) throws FileNotFoundException {
		System.loadLibrary("opencv_java248");
		// TODO Auto-generated constructor stub
		th = new Thread(this);
		isWindoMode = isWindo;
		if (isWindoMode == true) {
			prevWnd = new VideoPanel();
		} else {
			fout = new FileOutputStream("capVideo.avi", true);
		}
	}
	
	
	
    // Return true if it is initialized alrealdy
	public boolean deviceState()
	{
		boolean isInit = false;
		if (cap != null) {
			isInit = cap.isOpened();
		}
		return isInit;
	}
	
	
	// Initialize selected device for index
	public void initdevice (int deviceIndex)
	{	 
		// Initialize device
		if (deviceState()) {
			System.out.println("Device is being used");
			return;
		}
		
		cap = new VideoCapture(0);
		if (cap != null) {
			cap.open(0);
		}					
	}
	
	
	public void captureImage()
	{
			// Image
			Mat img = new Mat();
			cap.read(img);
			
			Highgui.imwrite("capImage.jpg", img);
	}
	
	
	public Mat getFrame()
	{
		Mat img = new Mat();
		cap.read(img);
		return img;
	}

	
	public void startCap()
	{
		System.out.println("startCap :	");
		runFlag = true;
		if (th == null) {
			th = new Thread(this);
		}
		th.start();
	}
	
	public void stopCap()
	{
		if (th != null) {
			th = null;
		}
		runFlag = false;
	}



	@Override
	public void run() {
		System.out.println("Capture Thread started");
		FileOutputStream fout1 = null;
		try {
			fout1 = new FileOutputStream("capVideo.rgb", true);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int i = 0;
			while (runFlag) {
				// if isWindowMode ==true 
				// then show video in preview mode otherwise 
				// capture video and write into a file.
				Mat img = new Mat();
				cap.read(img);
				if (isWindoMode == true) {
					System.out.println("Capture Thread started 1");
					BufferedImage buffImg = matToBufferedImage(img);
					prevWnd.setImage(buffImg);	 
					prevWnd.repaint();
				} else {
					
					byte[] data = getRawData(img);
					try {
						if (fout1 == null) {
							fout1 = new FileOutputStream("capVideo.rgb", true);
						}
						if (data != null) {
						    fout1.write(data);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Highgui.imwrite("image", img)
				}
				
			}
				
	}	
	
	public static byte[] getRawData(Mat img)
	{
		byte[] rawData= null;
		int cols = img.cols(); 
		int rows = img.rows(); 
		int elemSize = (int)img.elemSize(); 
		rawData = new byte[cols * rows * elemSize]; 
		int type; 
		img.get(0, 0, rawData); 
		return rawData;
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

}
	


