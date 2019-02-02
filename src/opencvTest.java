import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import remoMovObjFromImg.MovImgSubtractor;
import vidCapModule.*;
/*changed on Feb 2,2019*/
public class opencvTest {

	public opencvTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		JFrame frame = new JFrame("Video Capture Panel"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(800,800); 
		
/***********************Video Preview Test Code  For ******************************/
		FrameCapture cap = null;
		try {
			cap = new FrameCapture(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		cap.initdevice(0);
		cap.startCap();
		frame.setContentPane(cap.prevWnd); 
		frame.setVisible(true); 
/***********************Video Preview Test Code******************************/	
		
		
	// Runner code for ImageProcessing
	 MovImgSubtractor mov = new MovImgSubtractor();
	// mov.DoAllProcessing();
	// frame.setContentPane(mov.vidPanel);
 
		
	}

}
