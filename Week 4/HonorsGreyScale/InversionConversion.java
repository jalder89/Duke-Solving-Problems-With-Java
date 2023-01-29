import edu.duke.*;
import java.io.File;

public class InversionConversion
{
    private ImageResource makeInverted(ImageResource inImage) {
        // Make a blank image of the same size
        ImageResource outImage = new ImageResource(inImage);
        
        // for each pixel in outImage
        for (Pixel outPixel : outImage.pixels()) {
            // look at the corresponding pixel in inImage
            Pixel inPixel = inImage.getPixel(outPixel.getX(), outPixel.getY());
            
            // Compute inPixel's red + blue + green total
            int inRedPixel = inPixel.getRed();
            int inGreenPixel = inPixel.getGreen();
            int inBluePixel = inPixel.getBlue();
            
            // Invert each pixel color
            int outRedPixel = 255 - inRedPixel;
            int outGreenPixel = 255 - inGreenPixel;
            int outBluePixel = 255 - inBluePixel;
            
            // Set pixel's red to average
            outPixel.setRed(outRedPixel);
            // Set pixel's green to average
            outPixel.setGreen(outGreenPixel);
            // Set pixel's blue to average
            outPixel.setBlue(outBluePixel);
        }
        
        outImage.draw();
        return outImage;
    }
    
    public void testMakeInverted() {
        String fileName = "";
        String newFileName = "";
        DirectoryResource directoryResource = new DirectoryResource();
        
        for (File imageFile : directoryResource.selectedFiles()) {
            ImageResource imageResource = new ImageResource(imageFile);
            imageResource = makeInverted(imageResource);
            fileName = imageFile.getName();
            newFileName = "inverted-" + fileName;
            imageResource.setFileName(newFileName);
            imageResource.save();
        }
    }
}
