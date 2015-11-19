

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class CropImage {

	@SuppressWarnings("unused")
	public void run() {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


		// Load image

		//Mat image =

		File folder = new File("/Users/Delilah/Documents/finalproject/NewTest/");
		File[] listOfFiles = folder.listFiles();
		
		
		for (int j = 0; j < listOfFiles.length; j++) {
			System.out.println(listOfFiles[j]);
		}

		
		for (int i = 0; i < listOfFiles.length; i++){
			String picname = (listOfFiles[i]).toString();

			System.out.println("reading: " + listOfFiles[i].getName());
			//note this is saving over the original!!!!
			Mat image = Imgcodecs.imread(picname);

			// Define 3 regions
			Rect roi1 = new Rect(0,0,image.width()/3,image.height());
            Rect roi2 = new Rect(image.width()/3-1,0,image.width()/3,image.height());
            Rect roi3 = new Rect(2*image.width()/3-1,0,image.width()/3,image.height());
           
	
			// Chop images in 3 separate images (crop)
		Mat image2 = new Mat(image,roi1);
			Mat image3 = new Mat(image,roi2);
		Mat image4 = new Mat(image,roi3);
//
	String fileName = listOfFiles[i].getName();
//			
		Imgcodecs.imwrite("/Users/Delilah/Documents/finalproject/AllCroppedImages/"+fileName+"-crop1.jpg", image2);
		Imgcodecs.imwrite("/Users/Delilah/Documents/finalproject/AllCroppedImages/"+fileName+"-crop2.jpg", image3);
	Imgcodecs.imwrite("/Users/Delilah/Documents/finalproject/AllCroppedImages/"+fileName+"-crop3.jpg", image4);
//			
		}
//
//		// Example: load one image and display it
//		// This should be repeated for every slice

	System.out.println("Cropped files created. Now calling the method to classify them manually");
//		
	folder = new File("/Users/Delilah/Documents/finalproject/AllCroppedImages/");
	listOfFiles = folder.listFiles();
//
	for (int j = 0; j < listOfFiles.length; j++) {
		String fileName = listOfFiles[j].getName();
		System.out.println("Processing file "+fileName);
		selectAndSave(fileName);
//			
//			
	}
//		
//		
	}
//
//
	private void selectAndSave(String fn) {
	String directoryPathOriginals = "/Users/Delilah/Documents/finalproject/AllCroppedImages/";
	String directoryPathPositive = "/users/delilah/documents/finalproject/PosAndNeg/pos1/";
	String directoryPathNegative = "/users/delilah/documents/finalproject/PosAndNeg/neg1/";
//
	String fileName = fn;
	String fullPath = directoryPathOriginals + fileName;
//
	File file = new File(fullPath);
	BufferedImage imageBla;
//
	try {
		imageBla = ImageIO.read(file);

			JLabel label = new JLabel(new ImageIcon(imageBla));
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(label);
			f.pack();
			f.setLocation(200,200);
			f.setVisible(true);


			int reply = JOptionPane.showConfirmDialog(null, "Is it a walkable place?", "Please answer this question", JOptionPane.YES_NO_OPTION);

			if (reply == JOptionPane.YES_OPTION) {
				
				File outputfile = new File(directoryPathNegative+fileName);

				

				ImageIO.write((BufferedImage) imageBla, "png", outputfile);

				// JOptionPane.showMessageDialog(null, "Please check the next image");
			}

			if   (reply == JOptionPane.NO_OPTION) {

				File outputfile = new File(directoryPathPositive+fileName);

				ImageIO.write((BufferedImage) imageBla, "png", outputfile);

				// JOptionPane.showMessageDialog(null, "Please check the next image");

			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	public static void main (String[] args) {
		CropImage fd = new CropImage();
		fd.run();
	
	}
}