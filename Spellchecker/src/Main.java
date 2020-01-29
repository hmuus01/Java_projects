/* I have separated my Main class file which contains my main method from my other classes.

I create an instance of both the spellchecker class and the spellcheckerSuggestion I have uncommented both instances

To test each one PLEASE COMMENT OUT THE OTHER OR THE CLASSES WILL RUN BACK TO BACK.

IF YOU ARE TESTING MY PROGRAM GO IN TO MY SPELLCHECKER DIRECTORY AND THEN INTO TO SRC FOLDER AND THEN RUN MAIN

TO TEST IF THE SPELLCHECKER WORKS PLEASE RUN ANY OF THE FILES "testLines.txt,check.jav, test.html". THANK YOU.
*/
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String [] args)throws IOException{
        Spellchecker sp1 = new Spellchecker();
        sp1.spellCheckPrompt();

        SpellcheckerSuggestion spc1 = new SpellcheckerSuggestion();
        spc1.spellCheckPrompt();

    }
}
