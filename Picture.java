import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method to set the red and green values to 0 */
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  
  /** Method to negate the pixels */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		int red = pixelObj.getRed();
		int green = pixelObj.getGreen();
		int blue = pixelObj.getBlue();
        pixelObj.setRed(255-red);
        pixelObj.setGreen(255-green);
        pixelObj.setBlue(255-blue);
      }
    }
  }
  
  /** Method to turn the picture into shades of grey */
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		int avg = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue())/3;
        pixelObj.setRed(avg);
        pixelObj.setGreen(avg);
        pixelObj.setBlue(avg);
      }
    }
  }
  
  /**  To pixelate by dividing area into size x size.
	* @param size Side length of square area to pixelate. */
  public void pixelate(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    int x = 0; int y = 0;
    
    
    for(int row = 0; row<pixels.length; row +=size){
		for(int col = 0; col < pixels[0].length; col+=size){
			
			int totalR = 0;
			int totalG = 0;
			int totalB = 0;
			int count = 0;
			
			for(int r = row; r < row+size; r++){
				for(int c = col; c < col+size; c++){
					if(r >= 0 && r < pixels.length && c >=0 && c < pixels[0].length){
						totalR += pixels[r][c].getRed();
						totalG += pixels[r][c].getGreen();
						totalB += pixels[r][c].getBlue();
						count++;
					}
				}
			}
			int avgR = totalR/count;
			int avgG = totalG/count;
			int avgB = totalB/count;

			for(int r = row; r < row+size; r++){
				for(int c = col; c < col+size; c++){
					if(r >= 0 && r < pixels.length && c >=0 && c < pixels[0].length){
						pixels[r][c].setRed(avgR);
						pixels[r][c].setGreen(avgG);
						pixels[r][c].setBlue(avgB);
					}

				}
			}
			
		}
	}
  }
  
	/** Method that blurs the picture
	* @param size Blur size, greater is more blur 
	* @return Blurred picture
	*/
  public Picture blur(int size){
      Pixel[][] pixels = this.getPixels2D();
      Picture result = new Picture(pixels.length, pixels[0].length);
      Pixel[][] resultPixels = result.getPixels2D();
      
      for(int row = 0; row < pixels.length; row++){
		  for(int col = 0; col < pixels[0].length; col++){
			  int totalRed = 0;
			  int totalGreen = 0;
			  int totalBlue = 0;
			  int count = 0;
			  for(int r = row - size/2; r <= row +size/2; r++){
				  for(int c = col - size/2; c<= col + size/2; c++){
						
					  if(r >= 0 && r < pixels.length && c >=0 && c < pixels[0].length){
						  totalRed += pixels[r][c].getRed();
						  totalGreen += pixels[r][c].getGreen();
						  totalBlue += pixels[r][c].getBlue();
						  count++;
					  }
				  }
				  
			  }
			  int avgRed = totalRed/count;
			  int avgBlue = totalBlue/count;
			  int avgGreen = totalGreen/count;
			  resultPixels[row][col].setColor(new Color(avgRed, avgGreen, avgBlue));
		  }
	  }
	  return result;
  }

  /** Method that enchances the picture
	* @param size enchance size, greater is less blur 
	* @return enchanced picture
	*/
  public Picture enhance(int size){
      Pixel[][] pixels = this.getPixels2D();
      Picture result = new Picture(pixels.length, pixels[0].length);
      Pixel[][] resultPixels = result.getPixels2D();
      
      for(int row = 0; row < pixels.length; row++){
		  for(int col = 0; col < pixels[0].length; col++){
			  int totalRed = 0;
			  int totalGreen = 0;
			  int totalBlue = 0;
			  int count = 0;
			  for(int r = row - size/2; r <= row +size/2; r++){
				  for(int c = col - size/2; c<= col + size/2; c++){
						
					  if(r >= 0 && r < pixels.length && c >=0 && c < pixels[0].length){
						  totalRed += pixels[r][c].getRed();
						  totalGreen += pixels[r][c].getGreen();
						  totalBlue += pixels[r][c].getBlue();
						  count++;
					  }
				  }
				  
			  }
			  int avgRed = totalRed/count;
			  int avgBlue = totalBlue/count;
			  int avgGreen = totalGreen/count;
			  
			  int newRed = 2*pixels[row][col].getRed() - avgRed;
			  int newGreen = 2*pixels[row][col].getGreen() - avgGreen;
			  int newBlue = 2*pixels[row][col].getBlue() - avgBlue;

			  if(newRed <0){
				  newRed = 0;
			  }
			  else if(newRed > 255){
				  newRed = 255;
			  }
			  if(newGreen <0){
				  newGreen = 0;
			  }
			  else if(newGreen > 255){
				  newGreen = 255;
			  }
			  if(newBlue <0){
				  newBlue = 0;
			  }
			  else if(newBlue > 255){
				  newBlue = 255;
			  }			  
			  
			  resultPixels[row][col].setColor(new Color(newRed, newGreen, newBlue));
		  }
	  }
	  return result;
  }
  
  
  /**Shifts the picture to the right and wraps around. */
  public Picture shiftRight(int percent){
	  Pixel[][] pixels = this.getPixels2D();
      Picture result = new Picture(this.getHeight(), this.getWidth());
      Pixel[][] resultPixels = result.getPixels2D();
      int colLength = this.getWidth();
	  int rowLength = this.getHeight();

	  int numShift = percent * colLength / 100;
	  
	  for (int row = 0; row < pixels.length; row++) {
		  for (int col = 0; col < colLength; col++) {
			  int newCol = (col + numShift) % colLength;
			  resultPixels[row][newCol].setColor(pixels[row][col].getColor());
		  }		
	  }
	 
	  return result;
  }
  
  /**Shifts the picture to the right and wraps around. Makes the picture jagged. */
  public Picture stairStep(int shiftCount, int steps){
	  Pixel[][] pixels = this.getPixels2D();
      Picture result = new Picture(this.getHeight(), this.getWidth());
      Pixel[][] resultPixels = result.getPixels2D();
      int colLength = this.getWidth();
	  int rowLength = this.getHeight();
	  
	  int rowsPerStep = rowLength/steps;
	  
	  for (int row = 0; row < rowLength; row++) {
		  int currentStep = row/rowsPerStep;
		  int pixelsToShift = shiftCount * currentStep;
		  for (int col = 0; col < colLength; col++) {
			  int newCol = (col + pixelsToShift) % colLength;
			  resultPixels[row][newCol].setColor(pixels[row][col].getColor());
		  }
	  }
	 
	  return result;
  }
  
  
  /**Rotates the picture 90 degrees clockwise */
  public Picture turn90(){
	  Pixel[][] pixels = this.getPixels2D();
	  int rowLength = this.getHeight();
	  int colLength = this.getWidth();
      Picture result = new Picture(colLength, rowLength);
      Pixel[][] resultPixels = result.getPixels2D();
      
      for(int row = 0; row < rowLength; row++){
		  for(int col = 0; col < colLength; col++){
			  resultPixels[col][row].setColor(pixels[rowLength - 1 - row][col].getColor());
		  }
	  }
      
      return result;
  }
  
  /**Zooms in to upper left corner */
  public Picture zoomUpperLeft(){
	  Pixel[][] pixels = this.getPixels2D();
	  int rowLength = this.getHeight();
	  int colLength = this.getWidth();
      Picture result = new Picture(rowLength, colLength);
      Pixel[][] resultPixels = result.getPixels2D();
      
      for(int row = 0; row < rowLength/2; row++){
		  for(int col = 0; col < colLength/2; col++){
			  int newCol = col*2;
			  int newRow = row*2;
			  resultPixels[newRow][newCol].setColor(pixels[row][col].getColor());
			  resultPixels[newRow+1][newCol].setColor(pixels[row][col].getColor());
			  resultPixels[newRow][newCol+1].setColor(pixels[row][col].getColor());			  
			  resultPixels[newRow+1][newCol+1].setColor(pixels[row][col].getColor());			  
			  
		  }
	  }
      
      return result;
  }
  
  /**Tiles a picture */
  public Picture tileMirror(){
	  Pixel[][] pixels = this.getPixels2D();
	  int rowLength = this.getHeight();
	  int colLength = this.getWidth();
      Picture result = new Picture(rowLength, colLength);
      Pixel[][] resultPixels = result.getPixels2D();
      
      for(int row = 0; row < rowLength; row++){
		  for(int col = 0; col < colLength; col++){
			  int newCol = col/2;
			  int newRow = row/2;
			  resultPixels[newRow][newCol].setColor(pixels[row][col].getColor());
			  resultPixels[rowLength - 1 -newRow][newCol].setColor(pixels[row][col].getColor());
		  }
	  }
      result.mirrorVertical();
      return result;
  }

  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
