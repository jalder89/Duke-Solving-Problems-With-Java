import edu.duke.URLResource;
/**
 * Write a description of class Part4 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 * 
 * Here are suggestions to get started.

1. Create a new Java Class named Part4 in the StringsFirstAssignments project and put your code in that class.

2. Use URLResource to read the file at http://www.dukelearntoprogram.com/course2/data/manylinks.html word by word.

3. For each word, check to see if “youtube.com” is in it. If it is, find the double quote to the left and right of the occurrence of “youtube.com” 
to identify the beginning and end of the URL.  Note, the double quotation mark is a special character in Java. 
To look for a double quote, look for (\”), since the backslash (\) character indicates we want the literal quotation marks (“) and not the Java character. 
The string you search for would be written “\”” for one double quotation mark.

4. In addition to the String method indexOf(x, num), you might want to consider using the String method lastIndexOf(s, num) that can be used with two parameters s and num. 
The parameter s is the string or character  to look for,  and num is the last position in the string to look for it. 
This method returns the last match from the start of the string up to the num position, so it is a good option for finding the opening quotation mark of 
a string searching backward from “youtube.com.” 
More information on String methods can be found in the Java documentation for Strings: http://docs.oracle.com/javase/7/docs/api/java/lang/String.html.

Caution: The word Youtube could appear in different cases such as YouTube, youtube, or YOUTUBE. 
You can find the URLs more easily by converting the string to lowercase. 
However, you will need the original string (with uppercase and lowercase letters) to view the YouTube URL to answer a quiz question because YouTube links are case sensitive. 
The link https://www.youtube.com/watch?v=ji5_MqicxSo  is different than the link https://www.youtube.com/watch?v=ji5_mqicxso, where all the letters are lowercase.
 * 
 */
public class Part4
{
    private void findYoutubeLink(URLResource weblinks) {
        for (String item : weblinks.words()) {
            //int indexOfYoutube = word.toLowerCase().indexOf("youtube.com");
            int pos = item.toLowerCase().indexOf("youtube.com");
            
            if (pos != -1) {
                //System.out.println(word);
                //int indexOfFirstQuote = word.indexOf("\"");
                //int indexOfLastQuote = word.indexOf("\"", indexOfFirstQuote + 1);
                //System.out.println("Indices of the URL: " + indexOfFirstQuote + ", " + indexOfLastQuote);
                //System.out.println("Youtube URL: " + word.substring(indexOfFirstQuote + 1, indexOfLastQuote));
                
                int beg = item.lastIndexOf("\"",pos);
                int end = item.indexOf("\"", pos+1);
                System.out.println(item.substring(beg+1,end));
            }
        }
    }
    
    public static void main (String[] args) {
        URLResource weblinks = new URLResource("https://www.dukelearntoprogram.com/course2/data/manylinks.html");
        new Part4().findYoutubeLink(weblinks); 
    }    
}
