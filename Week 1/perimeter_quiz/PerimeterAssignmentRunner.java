import edu.duke.*;
import java.io.File;
import java.util.Stack;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Put code here
        int totalPoints = 0;
        
        for (Point currentPoint : s.getPoints()) {
            totalPoints++;
        }
        
        return totalPoints;
    }

    public double getAverageLength(Shape s) {
        // Put code here
        double totalDistance = 0.0;
        double averageLength = 0.0;
        double currentDistance = 0.0;
        int iteration = 0;
        Point previousPoint = s.getLastPoint();
        
        for (Point currentPoint : s.getPoints()) {
            iteration += 1;
            currentDistance = currentPoint.distance(previousPoint);
            totalDistance += currentDistance;
            previousPoint = currentPoint;
        }
        
        averageLength = totalDistance / iteration;
        return averageLength;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        double largestSide = 0.0;
        double currentDistance = 0.0;
        Point previousPoint = s.getLastPoint();
        
        for (Point currentPoint : s.getPoints()) {
            currentDistance = previousPoint.distance(currentPoint);
            
            if (currentDistance > largestSide) {
                largestSide = currentDistance;
            }
            previousPoint = currentPoint;
        }
        return largestSide;
    }

    public double getLargestX(Shape s) {
        // Put code here
        int pointX = 0;
        double largestPointX = 0;
        for (Point currentPoint : s.getPoints()) {
            pointX = currentPoint.getX();
            
            if (pointX > largestPointX) {
                largestPointX = pointX;
            }
        }
        
        return largestPointX;
    }

    public double getLargestPerimeterMultipleFiles(DirectoryResource directoryResource) {
        // Put code here
        double perimeter = 0.0;
        double largestPerimeter = 0.0;
        
        for (File file : directoryResource.selectedFiles()) {
            FileResource fileResource = new FileResource(file);
            Shape shape = new Shape(fileResource);
            perimeter = getPerimeter(shape);
            if (perimeter > largestPerimeter) {
                largestPerimeter = perimeter;
            }
        }
        return largestPerimeter;
    }

    public String getFileWithLargestPerimeter(DirectoryResource directoryResource) {
        // Put code here
        double perimeter = 0.0;
        double largestPerimeter = 0.0;
        File fileWithLargestPerimeter = null;
        
        for (File file : directoryResource.selectedFiles()) {
            FileResource fileResource = new FileResource(file);
            Shape shape = new Shape(fileResource);
            perimeter = getPerimeter(shape);
            if (perimeter > largestPerimeter) {
                largestPerimeter = perimeter;
                fileWithLargestPerimeter = file;
            }
            
        }
        return fileWithLargestPerimeter.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("Perimeter = " + length);
        int totalPoints = getNumPoints(s);
        System.out.println("Total points = " + totalPoints);
        double averageLength = getAverageLength(s);
        System.out.println("Average length of points = " + averageLength);
        double largestSide = getLargestSide(s);
        System.out.println("Largest side = " + largestSide);
        double largestPointX = getLargestX(s);
        System.out.println("Largest pointX = " + largestPointX);
    }
    
    public void testPerimeterMultipleFiles() {
        // Put code here
        DirectoryResource directoryResource = new DirectoryResource();
        double largestPerimeter = getLargestPerimeterMultipleFiles(directoryResource);
        System.out.println("The the largest perimeter of the selected files is = " + largestPerimeter);
        
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
        DirectoryResource directoryResource = new DirectoryResource();
        String fileWithLargestPerimeter = getFileWithLargestPerimeter(directoryResource);
        System.out.println("File with largest perimeter = " + fileWithLargestPerimeter);
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = " + peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        // pr.testPerimeter();
        // pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();
    }
}
