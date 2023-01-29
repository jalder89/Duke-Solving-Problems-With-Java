import edu.duke.FileResource;
/**
 * Write a description of class Part2 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Part2
{
    /*
     * 1. Create a new Java Class named Part2in the StringsSecondAssignments project.
     * 2. Write the method named howMany that has two String parameters named stringa and stringb. 
     * This method returns an integer indicating how many times stringa appears in stringb, where each occurrence of stringa must not overlap with another occurrence of it. 
     * For example, the call howMany(“GAA”, “ATGAACGAATTGAATC”) returns 3 as GAA occurs 3 times. 
     * The call howMany(“AA”, “ATAAAA”) returns 2. Note that the AA’s found cannot overlap.
     * 3.Write the void method testHowMany has no parameters. Add code in here to call howMany with several examples and print the results. 
     * Think carefully about what types of examples would be good to test to make sure your method works correctly.
     */
    
    private int howMany(String stringa, String stringb) {
        int indexOfAppearance = 0;
        int currentIndex = 0;
        int count = 0;
        
        while(indexOfAppearance != -1) {
            indexOfAppearance = stringb.indexOf(stringa, currentIndex);
            
            if (indexOfAppearance != -1) {
                currentIndex = indexOfAppearance + stringa.length();
                count++;
            }
        }
        
        if (count > 0) {
            return count;
        } else return -1;
    }
    
    public void testHowMany() {
        FileResource stringaFile = new FileResource();
        FileResource stringbFile = new FileResource();
        
        for (String stringb : stringbFile.lines()) {
            for (String stringa : stringaFile.lines()) {
                System.out.println("How many times String A appears in String B: " + howMany(stringa, stringb));
            }
        }
    }
}
