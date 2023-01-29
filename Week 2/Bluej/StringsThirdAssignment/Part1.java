import edu.duke.*;
import java.util.ArrayList;
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
    
    private StorageResource getAllGenes(String dna, StorageResource resource) {
        boolean firstLoop = true;
        String dnaUpper = dna.toUpperCase();
        int startIndex = 0;
        int indexOfClosestStop = 0;
        String gene = "";
        
        while(true) {
            if (firstLoop) {
                startIndex = dnaUpper.indexOf("ATG");
                firstLoop = false;
            } else {
                startIndex = dnaUpper.indexOf("ATG", indexOfClosestStop + 3);
            }
            
            
            if (startIndex == -1) {
                break;
            } else {
                int indexOfTAA = findStopCodon(dnaUpper, startIndex, "TAA");
                int indexOfTAG = findStopCodon(dnaUpper, startIndex, "TAG");
                int indexOfTGA = findStopCodon(dnaUpper, startIndex, "TGA");
                indexOfClosestStop = Math.min(Math.min(indexOfTAA, indexOfTAG), indexOfTGA);
                
                if (indexOfClosestStop != dnaUpper.length()) {
                    gene = dna.substring(startIndex, indexOfClosestStop + 3);
                    resource.add(gene);
                    System.out.println("Store size: " + resource.size());
                    System.out.println("Logging gene after resource add: " + gene);
                } else break;
            }
        }
        
        return resource;
    }
    
    private float cgRatio(String dna) {
        int charCount = 0;
        float ratio = 0.0f;
        char[] dnaCharArray = dna.toLowerCase().toCharArray();
        
        for (char character : dnaCharArray) {
            if (character == 'c' || character == 'g') {
                charCount++;
            }
        }
        
        ratio = (float) charCount / dna.length();
        System.out.println("Ratio of C & G in dna strand " + dna + " is " + ratio);
        
        return ratio;
    }
    
    private int countCTG(String dna) {
        boolean firstLoop = true;
        int indexOfCTG = 0;
        int count = 0;
        
        while (true) {
            if (firstLoop) {
                indexOfCTG = dna.indexOf("CTG", indexOfCTG);
                firstLoop = false;
            } else {
                indexOfCTG = dna.indexOf("CTG", indexOfCTG + 3);
            }
            
            
            if (indexOfCTG != -1) {
                count++;
            } else break;
        }
        
        return count;
    }
    
    private void processGenes(StorageResource stringResource) {
        int largeGeneCount = 0;
        int ratioCount = 0;
        int longestGeneLength = 0;
        String longestGene = "";
        
        for (String dna : stringResource.data()) {
            // print all the Strings in sr that are longer than 9 characters
            if (dna.length() > 60) {
                System.out.println("This gene is longer than 60: " + dna);
                largeGeneCount++;
            }
            
            // print the Strings in sr whose C-G-ratio is higher than 0.35
            if (cgRatio(dna) > 0.35f) {
                System.out.println("This gene has a C-G Ratio higher than 0.35: " + dna);
                ratioCount++;
            }
            
            if (dna.length() > longestGeneLength) {
                longestGene = dna;
                longestGeneLength = dna.length();
            }
        }
        
        // print the number of Strings in sr that are longer than 9 characters
        System.out.println("Number of genes longer than 60: " + largeGeneCount);
        // print the number of strings in sr whose C-G-ratio is higher than 0.35
        System.out.println("Number of genes with a C-G ratio higher than 0.35: " + ratioCount);
        // print the length of the longest gene in sr
        System.out.println("The longest gene is: " + longestGene);
    }
    
    public void testFindStopCodon() {
        FileResource dnaFile = new FileResource();
        for (String dna : dnaFile.lines()) {
            int stopCodonIndex = findStopCodon(dna, 0, "TAA");
            System.out.println("DNA length: " + dna.length());
            System.out.println(stopCodonIndex);
        }
    }
    
    public void testFindGene() {
        FileResource dnaFile = new FileResource();
        
        for (String dna : dnaFile.lines()) {
            String gene = findGene(dna);
            System.out.println("DNA Strand: " + dna);
            System.out.println("Gene: " + gene);
        }
    }
    
    public void testGetAllGenes() {
        FileResource dnaFile = new FileResource();
        StorageResource allGenesResource = new StorageResource();
        
        for (String dna : dnaFile.lines()) {
            allGenesResource = getAllGenes(dna, allGenesResource);
        }

        for (String gene : allGenesResource.data()) {
                System.out.println("Gene: " + gene);
        }
    }
    
    public void testCGRatio() {
        FileResource dnaFile = new FileResource();
        float ratio = 0.0f;
        
        for(String dna : dnaFile.lines()) {
            ratio = cgRatio(dna);
            System.out.println("Ratio of C & G in dna strand " + dna + " is " + ratio);
        }
    }
    
    public void testCountCTG() {
        FileResource dnaFile = new FileResource();
        int ctgCount = 0;
        
        for(String dna : dnaFile.lines()) {
            ctgCount = countCTG(dna);
            System.out.println("The number of times CTG appears in dna strand " + dna + " is " + ctgCount);
        }
    }
    
    /*
     * Write a method testProcessGenes. This method will call your processGenes method on different test cases. 
     * Think of five DNA strings to use as test cases. 
     * These should include: 
     * one DNA string that has some genes longer than 9 characters, 
     * one DNA string that has no genes longer than 9 characters, 
     * one DNA string that has some genes whose C-G-ratio is higher than 0.35, 
     * and one DNA string that has some genes whose C-G-ratio is lower than 0.35. 
     * Write code in testProcessGenes to call processGenes five times with StorageResources made from each of your five DNA string test cases.
     * We have some real DNA for you to test your code on. 
     * Download the file brca1line.fa from the DukeLearnToProgram Project Resources page. 
     * Make sure you save it in your BlueJ project so that your code can access it. 
     * You can use a FileResource to open the file and the FileResource method asString to convert the contents of the file to a single string so that 
     * you can use it like the other DNA strings you have been using. 
     * Here is an example: 12
     * FileResource fr = new FileResource("brca1line.fa");
     * String dna = fr.asString();
     * Modify your processGenes method so that it prints all the Strings that are longer than 60 characters and prints the number of Strings that 
     * are longer than 60 characters (you do not need to make changes to the rest of the method).
     * Modify the method testProcessGenes to call processGenes with a StorageResource of the genes found in the file brca1line.fa.
     */
    
    public void testProcessGenes() {
        StorageResource storageResource = new StorageResource();
        FileResource dnaFile = new FileResource();
        String dna = dnaFile.asString();
        
        // for(String dna : dnaFile.lines()) {
        storageResource = getAllGenes(dna, storageResource);
        // }
        
        processGenes(storageResource);
    }
}
