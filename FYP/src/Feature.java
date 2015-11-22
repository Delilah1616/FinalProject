import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

	import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

	import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;

	import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

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

	public class Feature {
		public static String matToCSVString(MatOfFloat m) {
			//Precondition: m is a matrix x*y*1 (gray image)
			Size size = m.size();
			StringBuilder stringBuilder = new StringBuilder();
			float[] d = new float[(int) (size.width * size.height)];
			m.get(0, 0, d);
			for (int x = 0; x < size.height; x++) {
				for (int y = 0; y < size.width; y++) {
					if (stringBuilder.length() > 0)
						stringBuilder.append(",");
					stringBuilder.append(d[(int) (y * size.height + x)]);
					}
			}
			return stringBuilder.toString();
		}
		
		PrintWriter printwriter;
		
		public void run() throws FileNotFoundException, UnsupportedEncodingException {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			
			printwriter = new PrintWriter("/Users/Delilah/Documents/finalproject/test7.csv", "UTF-8");
			printwriter.print("Class");
			
			for (int i = 0; i<2592; i++){
				printwriter.print(","+ i);
			}

			printwriter.println();
						
			generateAndPrint("/Users/Delilah/Documents/finalproject/PosAndNeg/pos1/", "N");
			generateAndPrint("/Users/Delilah/Documents/finalproject/PosAndNeg/neg1/", "Y");
			printwriter.close();
		}
	

		public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
			Feature fd = new Feature();
			fd.run();
		}
		public void generateAndPrint(String path, String type) throws FileNotFoundException, UnsupportedEncodingException
		{
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			
			for (int i = 0; i < listOfFiles.length; i++)
				 //for(int i = 0; i < 2; i++){
				{
					String picname = (listOfFiles[i]).getName(); // filename in input
																	// folder												// folder
					String picPathName = path + picname; // pic filename is the
															// foldername and the
									
					Mat image = Imgcodecs.imread(picPathName);
					Mat grayImage = new Mat();
					Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_RGB2GRAY);
					
					Mat smallGrayImage = new Mat();
					Imgproc.resize(grayImage, smallGrayImage, new Size(378, 630));

					Size windowSize = smallGrayImage.size(); // for example suppose the image is 192x192
					Size cellSize = new Size(63,63); // 192 is a multiple of 32
					Size blockSize = new Size(3*cellSize.height, 3*cellSize.width);
					Size blockStride = new Size(cellSize.height, cellSize.width);
					int nbins = 9;
					
					HOGDescriptor hog = new HOGDescriptor(windowSize, blockSize, blockStride, cellSize, nbins);
					MatOfFloat feature = new MatOfFloat();
					//System.out.println("Before");
					hog.compute(smallGrayImage, feature);

					printwriter.println(type + ","
						+ matToCSVString(feature));
				}
		}
	
	}

