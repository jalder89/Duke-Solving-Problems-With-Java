import edu.duke.FileResource;
/**
 * Write a description of class Part2 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Part2
{
    private void findSimpleGene(String dna,
    String startCodon, String endCodon) {
        // Chech if dna string is uppercase or lowercase
        boolean isUpper;
        if (dna.equals(dna.toUpperCase())) {
                isUpper = true;
        } else isUpper = false;
        
        // Set codon casing based on isUpper
        if (isUpper) {
            startCodon = startCodon.toUpperCase();
            endCodon = endCodon.toUpperCase();
        } else {
            startCodon = startCodon.toLowerCase();
            endCodon = endCodon.toLowerCase();
        }
        
        // Searhc for genes in dna string and print the results
        int startGeneIndex = dna.indexOf(startCodon);
        int endGeneIndex = dna.indexOf(endCodon, startGeneIndex);
        if (startGeneIndex == -1 && endGeneIndex == -1) {
            System.out.println("No start or end genes found");
        }else if (startGeneIndex == -1) {
            System.out.println("No start gene found");
        } else if (endGeneIndex == -1) {
            System.out.println("No end gene found");
        } else if (dna.substring(startGeneIndex, endGeneIndex + 3).length() % 3 == 0) {
            System.out.println("Gene found: " + dna.substring(startGeneIndex, endGeneIndex + 3));
        } else {
            System.out.println("No gene found");
        }
    }

    private void testSimpleGene(FileResource file) {
        // Set the start and end codons for searching the dna string
        String startCodon = "ATG";
        String stopCodon = "TAA";
        // Loop through each dna string in the file line by line
        for (String dnaString : file.lines()) {
            System.out.println("DNA String: " + dnaString);
            new Part2().findSimpleGene(dnaString, startCodon, stopCodon);
        }        
    }

    public static void main(String[] args) {
        // Fetch dna file
        FileResource file = new FileResource();
        new Part2().testSimpleGene(file);
    }
}