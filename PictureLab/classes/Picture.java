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
 * @cooler author Jay Rixie
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
  
  /** Method to set the red and green to 0 */
  public void onlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(0);
        pixelObj.setRed(0);
      }
    }
  }
  
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    }
  }
  
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    int avColor;
    
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        avColor = (pixelObj.getGreen() + pixelObj.getRed() + pixelObj.getBlue()) / 3;
        pixelObj.setGreen(avColor);
        pixelObj.setRed(avColor);
        pixelObj.setBlue(avColor);
      }
    }
  }
  
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    int avColor;
    
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(pixelObj.getRed() * 5);
      }
    }
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
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVerticalRightToLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        rightPixel = pixels[row][col];
        leftPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int height = pixels.length;
    for (int row = 0; row < pixels.length / 2; row++)
    {
      for (int col = 0; col < pixels[row].length; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[height - 1 - row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
  public void mirrorDiagonal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int height;
    
    if(pixels.length < pixels[0].length)
    {
        height = pixels.length;
    }
    else
    {
        height = pixels[0].length;
    }
    
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < row; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[height - row - 1][height - col - 1];
        bottomPixel.setColor(topPixel.getColor());
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
  
  public void mirrorSnowman()
  {
    int mirrorPoint = 194;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 159; row < 191; row++)
    {
      // loop through the columns
      for (int col = 106; col < 294; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[mirrorPoint - row + mirrorPoint][col];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  public void mirrorSeagull()
  {
    int mirrorPoint = 350;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 234; row < 322; row++)
    {
      // loop through the columns
      for (int col = 237; col < 344; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  public void cropAndCopy( Picture sourcePicture, int startSourceRow, 
         int endSourceRow, int startSourceCol, int endSourceCol,
         int startDestRow, int startDestCol )
  {
    Pixel originalPixel = null;
    Pixel replacementPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    
     // loop through the rows
    for (int row = startSourceRow; row < endSourceRow; row++)
    {
      // loop through the columns
      for (int col = startSourceCol; col < endSourceCol; col++)
      {
          originalPixel = pixels[row][col];
          replacementPixel = pixels[startDestRow + row][startDestCol + col];
          replacementPixel.setColor(originalPixel.getColor());
      }
    }
  }

  public Picture scaleByHalf()
  {
    Pixel[][] pixels = this.getPixels2D();
      
    Picture newPic = new Picture((pixels.length/2), (pixels[0].length/2));
    Pixel[][] newPixels = newPic.getPixels2D();
    

    
    for (int row = 0; row < newPixels.length; row++)
    {
      for (int col = 0; col < newPixels[0].length; col++)
      {
          newPixels[row][col].setColor(new Color(
                                                                ((pixels[row*2][col*2].getRed()
                                                                 + pixels[row*2 + 1][col*2].getRed()
                                                                 + pixels[row*2][col*2 + 1].getRed()
                                                                 + pixels[row*2 + 1][col*2 + 1].getRed()) /4), 
                                                                 
                                                                 ((pixels[row*2][col*2].getGreen()
                                                                 + pixels[row*2 + 1][col*2].getGreen()
                                                                 + pixels[row*2][col*2 + 1].getGreen()
                                                                 + pixels[row*2 + 1][col*2 + 1].getGreen()) /4), 
                                                                 
                                                                 ((pixels[row*2][col*2].getBlue()
                                                                 + pixels[row*2 + 1][col*2].getBlue()
                                                                 + pixels[row*2][col*2 + 1].getBlue()
                                                                 + pixels[row*2 + 1][col*2 + 1].getBlue()) /4)));
      }
    }
    
    return newPic;
  }
  
  public Picture scale(int scaleFactor)
  {
    Pixel[][] pixels = this.getPixels2D();
      
    Picture newPic = new Picture((pixels.length*scaleFactor), (pixels[0].length*scaleFactor));
    Pixel[][] newPixels = newPic.getPixels2D();
    
    for(int row = 0; row < pixels.length; row++)
    {
        for(int col = 0; col < pixels[row].length; col++)
        {
            for(int i = 0; i < scaleFactor; i++)
            {
                for(int j = 0; j < scaleFactor; j++)
                {
                    newPixels[row*scaleFactor+i][col*scaleFactor+j].setColor(pixels[row][col].getColor());
                }
            }
        }
    }
    
    return newPic;
  }
  
  public void rainbow()
  {
    Pixel[][] pixels = this.getPixels2D();
    int r;
    int g;
    int b;
    

    double length = pixels[0].length / 6;
    
      for(int row = 0; row < pixels.length; row++)
    {
        for(int col = 0; col < pixels[row].length; col++)
        {
           if (col < length)
           {
               r = 255;
               g = (int)((col % length) / length * 255); 
               b = 0;
           }
           
           else if (col < length * 2)
           {
               g = 255;
               r = 255 - (int)((col % length) / length * 255);
               b = 0;
           }
           
           else if (col < length * 3)
           {
               g = 255;
               b = (int)((col % length) / length * 255);
               r = 0;
           }
           
           else if (col < length * 4)
           {
               b = 255;
               g = 255 - (int)((col % length) / length * 255);
               r = 0;
           }
           
           else if (col < length * 5)
           {
               b = 255;
               r = (int)((col % length) / length * 255);
               g = 0;
           }

           else
           {
               r = 255;
               b = 255 - (int)((col % length) / length * 255);
               g = 0;
           }
           
           pixels[row][col].setColor(new Color(((pixels[row][col].getRed() + r)/2),((pixels[row][col].getGreen() + g))/2,((pixels[row][col].getBlue() + b))/2));
        }
    }
  }
  
  public void testQuestionMark()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setGreen(pixelObj.getGreen()<128?0:255);
        pixelObj.setRed(pixelObj.getRed()<128?0:255);
        pixelObj.setBlue(pixelObj.getBlue()<128?0:255);
      }
    }
  }
  
  public void testQuestionMark2()
  {
    Pixel[][] pixels = this.getPixels2D();
    int countR = 0;
    int countG = 0;
    int countB = 0;
    
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
          countR += pixelObj.getRed();
          countG += pixelObj.getGreen();
          countB += pixelObj.getBlue();
      }
      
      for (Pixel pixelObj : rowArray)
      {
          pixelObj.setColor(new Color(countR/pixels[0].length, countG/pixels[0].length, countB/pixels[0].length));
      }
      
      countR = 0;
      countG = 0;
      countB = 0;
    }
    
    
  }
  
  public void posterize()
  {
    Pixel[][] pixels = this.getPixels2D();
    int r;
    int g;
    int b;
    
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        r = pixelObj.getRed();
        g = pixelObj.getGreen();
        b = pixelObj.getBlue();
        if(r < 63)
        {
            r = 31;
        }
        
        else if(r < 127)
        {
            r = 95;
        }
        
        else if(r < 191)
        {
            r = 158;
        }
        
        else
        {
            r = 222;
        }
        
        if(g < 63)
        {
            g = 31;
        }
        
        else if(g < 127)
        {
            g = 95;
        }
        
        else if(g < 191)
        {
            g = 158;
        }
        
        else
        {
            g = 222;
        }
        
        if(b < 63)
        {
            b = 31;
        }
        
        else if(b < 127)
        {
            b = 95;
        }
        
        else if(b < 191)
        {
            b = 158;
        }
        
        else
        {
            b = 222;
        }
        
        pixelObj.setColor(new Color(r,g,b));
      }
    }
  }
  
  public void pixelate(int blurAmount)
  {
    Pixel[][] pixels = this.getPixels2D();

    int r;
    int g;
    int b;
    
    for (int row = 0; row < pixels.length / blurAmount; row++)
    {
      for (int col = 0; col < pixels[0].length / blurAmount; col++)
      {
          r = 0;
          g = 0;
          b = 0;
          for (int i = 0; i < blurAmount; i++)
          {
              for (int j = 0; j < blurAmount; j++)
              {
                   r += pixels[row*blurAmount + i][col*blurAmount + i].getRed();
                   g += pixels[row*blurAmount + i][col*blurAmount + i].getGreen();
                   b += pixels[row*blurAmount + i][col*blurAmount + i].getBlue();
              }
          }
          
          for (int i = 0; i < blurAmount; i++)
          {
              for (int j = 0; j < blurAmount; j++)
              {
                  pixels[row*blurAmount + i][col*blurAmount + j].setColor(new Color(r/(blurAmount*blurAmount), g/(blurAmount*blurAmount), b/(blurAmount*blurAmount)));
              }
          }
      }
    }
  }
  
  public void testQuestionMark3()
  {
    Pixel[][] pixels = this.getPixels2D();
      

    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
          pixels[row][col].setColor(new Color(pixels[row][col].getBlue(), pixels[row][col].getRed(), pixels[row][col].getGreen()));
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
