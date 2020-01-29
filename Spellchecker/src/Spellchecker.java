/*
PLEASE NOTE: MY MAIN METHOD IS IN MY MAIN CLASS WHERE I CREATE AN INSTANCE OF THIS CLASS

THIS SPELLCHECKER PROGRAM TAKES A FILE AND CHECKS IF EACH WORD IN THE FILE IS IN THE DICTIONARY.
ONCE THE FILE HAS BEEN SPELLCHECKED IT WILL SAVE THE NEW FILE AS "new+FILENAME+EXTENSION"
THE DICTIONARY THAT WAS PROVIDED TO US IS SAVED AS DICTIONARY.TXT AND I HAVE A METHOD THAT TURNS THE DICTIONARY FILE INTO AN ARRAY LIST
I HAVE A METHOD WHICH CHECKS IF THE WORD IS IN THE DICTIONARY AND IF THE WORD IS, IT RETURNS TRUE
I HAVE A METHOD CALLED SPELLCHECKPROMPT() WHICH IS WHERE THE USERS FILE INPUT GETS TAKEN AND THE THE COMPARISON BETWEEN WORDS IN THE TXT FILE AND THE DICTIONARY HAPPENS
I HAVE A METHOD WHICH ADDS THE WORD IN THE DICTIONARY AND SORTS AND UPDATES THAT WORD IN THE DICTIONARY ONCE THE PROGRAM FINISHES
I HAVE A METHOD WHICH REPLACES THE WORD IN THE TXT FILE.
BY CREATING A NEW ARRAY LIST I CHECK EACH LINE IN THE ARRAY LIST AND IF THE LINE IS CORRECT IT ADDS IT THE NEW ARRAY LIST.
LASTLY I HAVE A METHOD WHICH PROMPTS THE USER TO EITHER ACCEPT A WORD AND ADD IT TO THE DICTIONARY OR REPLACE THE WORD AND UPDATE THAT IN THE TXT FILE.
*/
import java.io.*;
import java.util.*;
import java.io.IOException;
public class Spellchecker{

    //Method that returns an ArrayList (Dictionary)
    public ArrayList<String> readDictToList(){
        //Initialize an ArrayList to store the dictionary
        ArrayList<String> dict = new ArrayList<>();
        //Initialize a newFile
        FileReader newFile = null;
        try {
            //Set the newFile to read the txt file which is the dictionary
            newFile = new FileReader("dictionary.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Create a buffering character Input stream
        BufferedReader reader =  new BufferedReader(newFile);
        //Initialize currentLine to null
        String currentLine = null;
        try {
            //Read the current line of the file
            currentLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Loop while there is still another word in the text file
        while(currentLine != null)
        {
            //Add each Line(word) to the arrayList
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
    public boolean wordInDict(ArrayList<String> dict, String wordIn){
        String lowerWordIn = wordIn.toLowerCase();
        //Return true if the word is in the dictionary
        return dict.contains(lowerWordIn);
    }
    //Method to write Lines in an ArrayList to a file
    public ArrayList<String> readLinesToFile(String file){
        //Initialize an ArrayList to store the Lines
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            //Initialize a string line
            String line;
            //Loop while the there is still a word in the file
            while ((line = reader.readLine()) != null) {
                //Add each line to the ArrayList
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Return the lines ArrayList
        return lines;
    }
    public void spellCheckPrompt(){
        //Initialize an ArrayList to store the dictionary
        ArrayList<String> dict = readDictToList();
        //Let the user know which directory we're in, and place the file to test in this directory
        System.out.println("–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
        System.out.println("SPELLCHECKER WITH REPLACEMENT RUNNING");
        //Initialize a scanner to get the user input
        Scanner scan = new Scanner(System.in);
        //Ask the user to enter a file
        System.out.println("Please Enter Filename with the extension");
        //Store the users input in filename
        String filename = scan.nextLine();
        //Initialize two ArrayList's to store the lines in the txt file, in order to compare them later
        ArrayList<String> lines = readLinesToFile(filename);
        ArrayList<String> lines2 = readLinesToFile(filename);

        //Get the extension of the file with the use of lastIndexOf which takes everything after the "."
        String extension = filename.substring(filename.lastIndexOf("."));
        //Split the name of the file from its extension
        String[] newFile = filename.split(extension);
        //Name the file new and its original filename with the extension.
        String newFileName = "new"+newFile[0]+extension;
        //Loop through each line in ArrayList
        for(String currentLine : lines) {
            //Similar to String tokenizer, disregards all punctuation in words and splits each line into string of words
            //String[] tokens = currentLine.replaceAll("[^a-zA-Z ]", "").split("\\s+");
            String[] tokens = currentLine.replaceAll("[^A-Za-z0-9 \\']+", "").split("\\s+");
            //Loop through each word in the text file
            for (String token : tokens) {
                //check if the word is not in the dictionary
                if (!wordInDict(dict, token)) {
                    //System.out.println("Word not found: " + token);
                    try {
                        //if word is not in the dictionary call the userPrompt Method which asks to add the word to dictionary or replace it and stores that in Lines2
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
                //Make a new file with 2 at the end of the original file
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
        //Write all the words of the dictionary into a new file.
        PrintWriter printWords = new PrintWriter(new FileWriter(updateFile,false));
        //Loop through the dictionary
        for (String word:dictIn) {
            //Print(write) each word into the file
            printWords.println(word);
        }
        //When done close the print writer.
        printWords.close();
    }
    //Method to replace incorrect word
    private ArrayList<String> replaceWordInFile(ArrayList<String> linesArray, String token, String correctWord) {
        //Initialize a new Array
        ArrayList<String> linesArray2 = new ArrayList<>();

        //for each line in array
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
    //Method to get the user to add a word to a dictionary or replace the word
    public ArrayList<String> userPrompt(ArrayList<String> dictIn, ArrayList<String> linesIn, String token) throws IOException {
        //Initialize an ArrayList to store the words the txt file
        ArrayList<String> lines = linesIn;
        //Scanner to get user Input
        Scanner answer = new Scanner(System.in);
        //Print the word that hasn't been found and ask the user to add it the dictionary or replace the word
        System.out.println("Word " + token + " not found in the dictionary. Type 'yes' to add it to the dictionary? or 'no' to enter a replacement ");
        //Loop to make sure the users input is correct
        while(true)
        {
            //If the user enters a string
            if(answer.hasNext())
            {
                //Store the users answer in lowercase
                String userAnswer = answer.nextLine().toLowerCase();
                //check if the user types yes
                if(userAnswer.equals("yes"))
                {
                    //if user types yes call the Method which adds a word to the dictionary
                    addWordToDict(dictIn, token);
                    //then break out of the loop
                    break;
                }
                //check if the user enters no, then ask the user to enter the replacement
                else if(userAnswer.equals("no"))
                    {
                    //Initialize a scanner to ask a user to enter a correct word
                     Scanner correctWord = new Scanner(System.in);
                     System.out.println("Please Enter the replacement ");
                     //Store the users answer
                     String storeAnswer = correctWord.nextLine();
                     //Call to the Method which replaces incorrect word and store all the replaced words in the lines arrayList
                     lines = replaceWordInFile(linesIn, token, storeAnswer);
                     //break out of the loop
                     break;
                    }
                else
                    {
                        //Ask the user to ONLY enter yes or no
                    System.out.println("Please ONLY enter yes or no");
                    //store the users input and go through the loop again
                    answer = new Scanner(System.in);
                    }

                }
            }
        //Return the spell checked file as an ArrayList
        return lines;
        }
}
