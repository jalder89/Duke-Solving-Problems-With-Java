import edu.duke.*;
import java.io.File;

public class GrayScaleConversion
{
    // Start with the image to convert
    private ImageResource makeGrey(ImageResource inImage) {
        // Make a blank image of the same size
        ImageResource outImage = new ImageResource(inImage);
        
        // for each pixel in outImage
        for (Pixel outPixel : outImage.pixels()) {
            // look at the corresponding pixel in inImage
            Pixel inPixel = inImage.getPixel(outPixel.getX(), outPixel.getY());
            
            // Compute inPixel's red + blue + green total
            int totalRGB = inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen();
            
            // Divide sum by 3 for average
            int pixelAverage = totalRGB / 3;
            
            // Set pixel's red to average
            outPixel.setRed(pixelAverage);
            // Set pixel's green to average
            outPixel.setGreen(pixelAverage);
            // Set pixel's blue to average
            outPixel.setBlue(pixelAverage);
        }
        
        outImage.draw();
        return outImage;
    }
    
    public void testMakeGrey() {
        String fileName = "";
        String newFileName = "";
        DirectoryResource directoryResource = new DirectoryResource();
        
        for (File imageFile : directoryResource.selectedFiles()) {
            ImageResource imageResource = new ImageResource(imageFile);
            imageResource = makeGrey(imageResource);
            fileName = imageFile.getName();
            newFileName = "gray-" + fileName;
            imageResource.setFileName(newFileName);
            imageResource.save();
        }
    }
}
