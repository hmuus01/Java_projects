/*
PLEASE NOTE: MY MAIN METHOD IS IN MY MAIN CLASS WHERE I CREATE AN INSTANCE OF THIS CLASS

THIS SPELLCHECKER SUGGESTION PROGRAM TAKES A FILE AND CHECKS IF EACH WORD IN THE FILE IS IN THE DICTIONARY, IF ITS NOT GIVES THE USER
A LIST OF SUGGESTIONS FOR THEM TO CHOOSE THEIR REPLACEMENT BASED ON WORDS THAT SOUND LIKE IT. USING A SOUND EX CODE.
ONCE THE FILE HAS BEEN SPELLCHECKED IT WILL SAVE THE NEW FILE AS "newSpellchecked+FILENAME+EXTENSION"
THE DICTIONARY THAT WAS PROVIDED TO US IS SAVED AS DICTIONARY.TXT AND THE SOUND EX CODE DICTIONARY IS SAVED AS DICTIONARY2.TXT
AND I HAVE A METHOD THAT TURNS THE DICTIONARY FILE INTO AN ARRAY LIST AND A METHOD THAT GENERATES SOUND EX CODE.
I HAVE A METHOD WHICH CHECKS IF THE WORD IS IN THE DICTIONARY AND IF THE WORD IS, IT RETURNS TRUE.
I HAVE A METHOD CALLED SPELLCHECKPROMPT() WHICH IS WHERE THE USERS FILE INPUT GETS TAKEN AND THE COMPARISON BETWEEN WORDS IN THE TXT FILE AND THE DICTIONARY HAPPENS.
I HAVE A METHOD WHICH ADDS THE WORD IN THE DICTIONARY AND SORTS AND UPDATES THAT WORD IN THE DICTIONARY ONCE THE PROGRAM FINISHES.
I HAVE A METHOD WHICH REPLACES THE WORD IN THE TXT FILE.
I HAVE A METHOD WHICH GETS SUGGESTIONS BY CREATING A SOUND EX CODE FOR A WORD
AND THEN LOOPING THROUGH THE SOUND EX DICTIONARY AND COMPARING THE SOUNDEX CODE WITH THE SAME ONE AS THE WORD, THEN RETURNS THE SUGGESTIONS
BY CREATING A NEW ARRAY LIST I CHECK EACH LINE IN THE ARRAY LIST AND IF THE LINE IS CORRECT IT ADDS IT TO THE NEW ARRAY LIST.
LASTLY I HAVE A METHOD WHICH PROMPTS THE USER TO ENTER EITHER ACCEPT A WORD AND ADD IT TO THE DICTIONARY OR PICK FROM A LIST OF SUGGESTIONS USING SOUND EX
AND THEN REPLACE THE WORD THEY PICK WITH THE INCORRECT WORD AND UPDATE THAT IN THE TXT FILE.
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
public class SpellcheckerSuggestion
{
        //Method that returns an ArrayList (Dictionary)
        private ArrayList<String> readDictToList(String filenameIn){
            //Initialize an ArrayList to store the dictionary
            ArrayList<String> dict = new ArrayList<>();
            //Initialize a newFile
            FileReader newFile = null;
            try {
                //Set the newFile to the txt file which is the dictionary
                newFile = new FileReader(filenameIn);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Create a buffering character Input stream
            BufferedReader reader =  new BufferedReader(newFile);
            //Initialize currentLine
            String currentLine = null;
            try {
                currentLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Loop while there is another word in the text file
            while(currentLine != null)
            {
                //Add each Line to the arrayList
                dict.add(currentLine);
                //Handle IO Exception Error
                try {
                    currentLine = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Return the Dictionary in an ArrayList
            return dict;
        }
        //Method to check if word is in Dictionary
        //????Use .contains()
        private boolean wordInDict(ArrayList<String> dict, String wordIn){
            String lowerWordIn = wordIn.toLowerCase();
            //Return true if the word is in the dictionary
            return dict.contains(lowerWordIn);
        }
        //Method to write Lines in an ArrayList to a file
        private ArrayList<String>  readFileToLines(String file){
            //Initialize an ArrayList to store the Lines
            ArrayList<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                //Initialize a string line
                String line;
                //Loop while the there is still a word in the file
                while ((line = br.readLine()) != null) {
                    //Add each line to the ArrayList
                    lines.add(line);
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("This File Does not exist");
                System.exit(0);
            }
            //Return the lines ArrayList
            return lines;
        }
        public void spellCheckPrompt(){
            //Initialize an ArrayList to store the dictionary
            ArrayList<String> dict = readDictToList("dictionary.txt");
            //Let the user know which directory we're in, and place the file to test in this directory
            System.out.println("–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
            System.out.println("WORKING DIRECTORY = " + System.getProperty("user.dir"));
            System.out.println("–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
            System.out.println("NOW RUNNING SPELLCHECKER SUGGESTION");
            //Initialize a scanner to get the user input
            Scanner scan = new Scanner(System.in);
            //Ask the user to enter a file
            System.out.println("Please Enter Filename with the extension");
            String filename = scan.nextLine();
            //Initialize two ArrayList's to store the lines in the txt file, in order to compare them later
            ArrayList<String> lines = readFileToLines(filename);
            ArrayList<String> lines2 = readFileToLines(filename);

            //Store the extension of the file with the use of lastIndexOf which takes everything after the "."
            String extension = filename.substring(filename.lastIndexOf("."));
            //Split the name of the file from its extension
            String[] newFile = filename.split(extension);
            //Name the file new and its original filename with the extension.
            String newFileName = "new"+newFile[0]+"2"+extension;

            //Loop through each line in ArrayList
            for(String currentLine : lines) {
                //Similar to String tokenizer, disregards punctuation in words and splits each line into string of words
               // String[] tokens = currentLine.replaceAll("[^a-zA-Z ]", "").split("\\s+");
                String[] tokens = currentLine.replaceAll("[^A-Za-z0-9 \\']+", "").split("\\s+");
                //Loop through each word in the text file
                for (String token : tokens) {
                    //check if the word is not in the dictionary
                    if (!wordInDict(dict, token)) {
                        try {
                            //if word is not in the dictionary call the userPrompt Method which asks to add the word to dictionary or replace it and store that in Line2
                            lines2 = userPrompt(dict, lines2, token);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            //if the two ArrayLists are different
            if (lines != lines2) {
                //write lines2 to new file
                FileWriter writer;
                try {
                    writer = new FileWriter(newFileName);
                    for (String str : lines2) {
                        writer.write(str+"\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Print that the file has finished spellchecking
            System.out.println("FILE HAS FINISHED SPELLCHECKING and is saved as "+newFileName);
        }
    //read the Dictionary File in into an ArrayList3
    private static void createSoundexDict(String dictFilenameIn){
        //Initialise a spellchecker object
        Spellchecker spellChecker  =  new Spellchecker();
        //Initialize an ArrayList to store the dictionary
        ArrayList<String> storeDict = spellChecker.readDictToList();
        //Initialize an ArrayList to store the soundEx dictionary
        ArrayList<String> soundexDict = new ArrayList<>();
        //Created a SoundEx Object
        Soundex soundex = new Soundex();
        //from the above ArrayList create a new ArrayList of SoundEx codes
        for(String word: storeDict){
            String soundexCode = soundex.soundexCode(word);
            soundexDict.add(soundexCode);
        }
        try {
            //write both ArrayLists into new file, <soundex_code> <word>
            File updateFile = new File("dictionary2.txt");
            //Print the words onto the new dictionary2
            PrintWriter printWords = new PrintWriter(new FileWriter(updateFile, false));
            //loop through the size of the dictionary
            for (int i = 0; i < storeDict.size(); i++) {
                //Separate the sound ex code and the its corresponding word with a space
                String word = soundexDict.get(i) + " " + storeDict.get(i);
                //print the words onto the dictionary
                printWords.println(word);
            }
            //when finished close the print writer
            printWords.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
        //Method to add a word to the dictionary
        private void addWordToDict(ArrayList<String> dictIn, String token) throws IOException {
            //Make the string token lowercase
            String newtoken = token.toLowerCase();
            //add the word to the dictionary
            dictIn.add(newtoken);
            //Sort the dictionary
            Collections.sort(dictIn);
            //Update the dictionary
            File updateFile = new File("dictionary.txt");
            //Write the all the words of the dictionary into a new file.
            PrintWriter printWords = new PrintWriter(new FileWriter(updateFile,false));
            //Loop through the dictionary
            for (String word:dictIn)
            {
                //Print(write) each word into the file
                printWords.println(word);
            }
            //When done close the print writer.
            printWords.close();
            //update dictionary2
            createSoundexDict("dictionary.txt");
        }
        //Method to replace incorrect word
        private ArrayList<String> replaceWordInFile(ArrayList<String> linesArray, String token, String correctWord) {
            //Initialize a new Array
            ArrayList<String> linesArray2 = new ArrayList<>();

            //Loop through the line array which store the words in the text file which will be replaced
            for(String line: linesArray) {
                //check if the line contains the incorrect word
                if (line.contains(token)) {
                    //if so replace all occurrence of that word
                    line = line.replaceAll(token, correctWord);
                }
                //Add that line to the ArrayList
                linesArray2.add(line);
            }
            //Return the ArrayList
            return linesArray2;
        }
        //Method which returns the suggestions
        private ArrayList<String> getSuggestions(String wordIn){
            ArrayList<String> suggestions = new ArrayList<>();
            //create a soundex object
            Soundex soundex = new Soundex();
            //use the soundex object above to soundexcode(token)
            String soundExToken = soundex.soundexCode(wordIn);
            //get soundex codes ArrayList
            ArrayList<String> soundexCodes = readDictToList("dictionary2.txt");

            //loop over SoundEx codes ArrayList
            for(String line:soundexCodes){
                //Split the the code an word in to two strings
                String[] tokens = line.split("\\s+");
                //Store the first string which is the sound ex code which was generated
                String soundToken = tokens[0];
                //Store the second string which is the word
                String word = tokens[1];

                //if token soundEx is found, add its corresponding word to suggestions
                if(soundExToken.equals(soundToken))
                {
                    //add the word to the suggestions ArrayList.
                    suggestions.add(word);
                }
            }
            //return the suggestions arrayList.
            return suggestions;
        }
        //Method to get the user to add a word to a dictionary or get suggestions
        private ArrayList<String> userPrompt(ArrayList<String> dictIn, ArrayList<String> linesIn, String token) throws IOException {
            //Initialize an ArrayList to store the words the txt file
            ArrayList<String> lines = linesIn;
            //Scanner to get user Input
            Scanner askUser = new Scanner(System.in);
            //Print the word that hasn't been found and ask the user to add it the dictionary or to see suggestions
            System.out.println("Word " + token + " not found. Type 'yes' to add it to the dictionary? or 'no' to see suggestions ");
            //Store the users answer in lowercase
            String userAnswer = askUser.nextLine().toLowerCase();
            //Check if the user types yes
            if(userAnswer.equals("yes"))
            {
                //if user types call the Method which adds a word to the dictionary
                addWordToDict(dictIn, token);
            }
            else{
                //Initialize an ArrayList to store the suggestions which is returned from the getSuggestions method
                ArrayList<String> suggestions = getSuggestions(token);
                //Loop through the suggestions
                System.out.println("suggestions are: ");
                for(int i = 0; i< suggestions.size(); i++){//enumerate by index
                    //get suggestion from index
                    String suggestion = suggestions.get(i);
                    //print index_suggestion
                    //print what the suggestions are
                    System.out.println("Code "+i+ ": " +suggestion);
                }
                //If there is no soundEx code generated for a word or a suggestion is not found, let the user know
                // ask the user to type the correct word.
                if(suggestions.size() == 0)
                {
                    //Let the user know there are no suggestions for the word in the file, maybe its a misspelling
                    System.out.println("We could not find any suggestions for " + token+ " Perhaps a misspelling? ");
                    //Initialize a new Scanner to get user to retype a replacement for the misspelling
                    Scanner misspelling = new Scanner(System.in);
                    //Ask the user to type the word again
                    System.out.println("Please type the correct word again");
                    //Store the users input
                    String spellAgain = misspelling.nextLine();
                    //replace the misspelling in the txt file with the users input
                    lines = replaceWordInFile(linesIn,token,spellAgain);
                    //return the Lines array
                    return lines;
                }
                //Initialize a scanner to ask a user to enter a correct word
                Scanner correctWord =  new Scanner(System.in);
                //Ask the user to enter a correct word
                System.out.println("Please Enter the correct CODE ");
                //Store the users answer
                String indexStr = correctWord.nextLine();
                //if the user does not enter a number, ask them to enter a number
                while(!isInt(indexStr))
                {
                    //ask the user to enter a number
                    System.out.println("Retry with a NUMBER not a word");
                    //store the users input
                    indexStr = correctWord.nextLine();
                }
                    //Convert the String entered into an integer and store in index
                    int index = Integer.parseInt(indexStr);

                //Check if the code for the suggestion the user enters is out of bounds.
                while(index>=suggestions.size()) {
                    //get the users input
                    correctWord = new Scanner(System.in);
                    //ask the user to enter
                    System.out.println("Please enter one of the CODES ABOVE ");
                    if(correctWord.hasNextInt())
                    {
                        //Convert the String entered into an integer and store in index
                         index = correctWord.nextInt();
                    }
                }
                //get the index of suggestion and store it in a string
                String suggestion = suggestions.get(index);
                //Call to the Method which replaces incorrect word and store all the replaced words in the lines arrayList
                lines = replaceWordInFile(linesIn, token, suggestion);
            }
            //Return the arrayList with all the incorrect words replaced
            return lines;
        }
        //Method to check if input is integer
        private boolean isInt(String wordIn)
        {
            //loop through the word and check if it is a number or not
            for(int i=0;i<wordIn.length();i++)
            {
                if(i==0 && wordIn.charAt(i) == '-') continue;
                if( !Character.isDigit(wordIn.charAt(i)) ) {
                    //if so return false
                    return false;
                }
            }
            //if the word is an integer return true
            return true;
        }
}
