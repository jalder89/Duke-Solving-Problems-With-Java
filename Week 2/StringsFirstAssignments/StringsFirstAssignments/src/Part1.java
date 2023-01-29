import edu.duke.FileResource;
// import java.util.Stack;

public class Part1 {
    private void findSimpleGene(String dna) {
        int startGeneIndex = dna.indexOf("ATG");
        int endGeneIndex = dna.indexOf("TAA", startGeneIndex);

        if (startGeneIndex == -1) {
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
        // Stack<String> dnaStack = new Stack<String>();
        for (String dnaString : file.lines()) {
            // dnaStack.push(line);
            System.out.println("DNA String: " + dnaString);
            new Part1().findSimpleGene(dnaString);
        }        
    }

    public static void main(String[] args) {
        FileResource file = new FileResource("data.txt");
        new Part1().testSimpleGene(file);
    }
}