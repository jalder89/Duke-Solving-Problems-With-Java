import edu.duke.FileResource;
/**
 * Write a description of class Part3 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Part3
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
    
    private void printAllGenes(String dna) {
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
    
    /*
     * 3. Write the method named countGenes that has a String parameter named dna representing a string of DNA. 
     * This method returns the number of genes found in dna. 
     * For example the call countGenes(“ATGTAAGATGCCCTAGT”) returns 2, 
     * finding the gene ATGTAA first and then the gene ATGCCCTAG. 
     * Hint: This is very similar to finding all genes and printing them, except that instead of printing all the genes you will count them.
     */
    
    private int countGenes(String dna) {
        boolean firstLoop = true;
        int startIndex = 0;
        int indexOfClosestStop = 0;
        int geneCount = 0;
        
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
                    // System.out.println("DNA Strand: " + dna);
                    // System.out.println("Gene: " + gene);
                    geneCount++;
                } else break;
            }
        }
        
        return geneCount;
    }
    
    /* 
     * 4. Write the void method named testCountGenes that has no parameters.
     * This method calls countGenes with many example strings and prints the result for each.
     * You should create several examples with different numbers of genes to test your code.
     */
    
    public void testCountGenes() {
        int count = 0;
        FileResource dnaFile = new FileResource();
        for (String dnaString : dnaFile.lines()) {
            count = countGenes(dnaString);
            System.out.println(count);
        }
        
    }
}
