import edu.duke.FileResource;
/**
 * Write a description of class Part1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Part1
{
    private int findStopCodon(String dna, int startIndex, String stopCodon) {
        // This method returns the index of the first occurrence of stopCodon that appears past startIndex and is a multiple of 3 away from startIndex. 
        // If there is no such stopCodon, this method returns the length of the dna strand.
        int currentIndex = 0;
        currentIndex = dna.indexOf(stopCodon, startIndex);
        
        while(currentIndex != -1) {
            if ((currentIndex - startIndex) % 3 == 0) {
                break;
            } else {
                currentIndex = dna.indexOf(stopCodon, currentIndex + stopCodon.length());
            }
        }
        
        if (currentIndex != -1) {
            return currentIndex;
        } else return dna.length();
    }
    
    // 5. Write the method findGene that has one String parameter dna, representing a string of DNA. In this method you should do the following:
    // Find the index of the first occurrence of the start codon “ATG”. If there is no “ATG”, return the empty string.
    // Find the index of the first occurrence of the stop codon “TAA” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”. 
    // Hint: call findStopCodon.
    // Find the index of the first occurrence of the stop codon “TAG” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”. 
    // Find the index of the first occurrence of the stop codon “TGA” after the first occurrence of “ATG” that is a multiple of three away from the “ATG”. 
    // Return the gene formed from the “ATG” and the closest stop codon that is a multiple of three away. 
    // If there is no valid stop codon and therefore no gene, return the empty string.
    private String findGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        
        if (startIndex == -1) {
            return "";
        } else {
            int indexOfTAA = findStopCodon(dna, startIndex, "TAA");
            int indexOfTAG = findStopCodon(dna, startIndex, "TAG");
            int indexOfTGA = findStopCodon(dna, startIndex, "TGA");
            int indexOfClosestStop = Math.min(Math.min(indexOfTAA, indexOfTAG), indexOfTGA);
            
            if (indexOfClosestStop != dna.length()) {
                return dna.substring(startIndex, indexOfClosestStop + 3);
            } else return "No gene found";
        }
    }
    
    /*
     * 6. Write the void method testFindGene that has no parameters. You should create five DNA strings. 
     * The strings should have specific test cases such as DNA with no “ATG”, DNA with “ATG” and one valid stop codon, DNA with “ATG” and multiple valid stop codons, 
     * DNA with “ATG” and no valid stop codons. 
     * Think carefully about what would be good examples to test. For each DNA string you should: 
     *      Print the DNA string. 
     *      Calculate the gene by sending this DNA string as an argument to findGene. 
     *      If a gene exists following our algorithm above, then print the gene, otherwise print the empty string.
     */
    
    public void testFindGene() {
        FileResource dnaFile = new FileResource();
        
        for (String dna : dnaFile.lines()) {
            String gene = findGene(dna);
            System.out.println("DNA Strand: " + dna);
            System.out.println("Gene: " + gene);
        }
    }
    
    /*  
     * 7. Write the void method printAllGenes that has one String parameter dna, representing a string of DNA. 
     * In this method you should repeatedly find genes and print each one until there are no more genes. 
     * Hint: remember you learned a while(true) loop and break.
     */
    
    public void printAllGenes(String dna) {
        boolean firstLoop = true;
        int startIndex = 0;
        int indexOfClosestStop = 0;
        
        while(true) {
            if (firstLoop) {
                startIndex = dna.indexOf("ATG");
                firstLoop = false;
            } else {
                startIndex = dna.indexOf("ATG", indexOfClosestStop + 3);
            }
            
            
            if (startIndex == -1) {
                break;
            } else {
                int indexOfTAA = findStopCodon(dna, startIndex, "TAA");
                int indexOfTAG = findStopCodon(dna, startIndex, "TAG");
                int indexOfTGA = findStopCodon(dna, startIndex, "TGA");
                indexOfClosestStop = Math.min(Math.min(indexOfTAA, indexOfTAG), indexOfTGA);
                
                if (indexOfClosestStop != dna.length()) {
                    String gene = dna.substring(startIndex, indexOfClosestStop + 3);
                    System.out.println("DNA Strand: " + dna);
                    System.out.println("Gene: " + gene);
                } else break;
            }
        }
    }
    
    // Write the void method testFindStopCodon that calls the method findStopCodon with several examples and prints out the results to check 
    // if findStopCodon is working correctly. Think about what types of examples you should check. 
    // For example, you may want to check some strings of DNA that have genes and some that do not. What other examples should you check?
    public void testFindStopCodon() {
        FileResource dnaFile = new FileResource();
        for (String dna : dnaFile.lines()) {
            int stopCodonIndex = findStopCodon(dna, 0, "TAA");
            System.out.println("DNA length: " + dna.length());
            System.out.println(stopCodonIndex);
        }
    }
}
