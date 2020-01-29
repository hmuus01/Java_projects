/* THIS CLASS TAKES A STRING LOOPS THROUGH THE CHARACTERS OF THE STRING AND CHECKS IF EACH CHARACTER IS A CONSONANT, IF IT IS IT REPLACES IT WITH A NUMBER.
   IF LETTERS SEPARATED BY A VOWEL ARE REPEATED THEN THEY ARE ENCODED TWICE AND IT GENERATES A 4 DIGIT CODE KNOWN AS SOUNDEX CODE
   WHICH IS USUALLY USED TO FIND WORDS THAT SOUND ALIKE.
    I USE THIS CLASS IN MY SPELLCHECKER SUGGESTION CLASS TO RETURN SUGGESTIONS
*/
public class Soundex {
    //Method which returns a string and takes a word as a parameter
    public String soundexCode(String wordIn)
    {
        //create an array which stores the characters of the word
        char[] wordChars = wordIn.toUpperCase().toCharArray();
        //Initialize the first character which will be the first character of the soundEx code returned
        char firstChar = wordChars[0];

        //Convert characters to a number code by looping through the character array and if the letter is found replace it with a number
        for (int i = 0; i < wordChars.length; i++) {
            //Initialize the current index
            char charI=wordChars[i];
            //check if the current character is any of these letters
            if (charI == 'B' || charI == 'F' || charI == 'P' || charI == 'V')
                {
                    //if so replace with 1
                    wordChars[i] = '1';
                }
            //check if the current character is an of these letters
             else if(charI == 'C' || charI == 'G' || charI == 'J' || charI == 'K' || charI == 'Q' || charI == 'S' || charI == 'X' || charI == 'Z')
                 {
                     //if so replace with 2
                    wordChars[i] = '2';
                 }
             //check if the current character is any of these letters
             else if(charI == 'D' || charI == 'T')
                 {
                     //if so replace with 3
                    wordChars[i] = '3';
                 }
             //check if the current character is any of these letters
             else if(charI == 'L')
                 {
                     //if so replace with 4
                    wordChars[i] = '4';
                 }
             //check if the current character is any of these letters
                else if(charI == 'M' || charI == 'N')
                 {
                     //if so replace with 5
                    wordChars[i] = '5';
                 }
                //check if the current character is any of these letters
                else if(charI == 'R')
                 {
                     //if so replace with 6
                    wordChars[i] = '6';
                 }
                //if the character is none of the above or a vowel
                else{
                    wordChars[i] = '0';
                 }
        }
        //Initialize a string which will be returned as the output and concatenate with the first Character
        String soundExCode = "" + firstChar;
        //Check if any characters are similar by looping through the character array
        for (int i = 1; i < wordChars.length; i++) {
            //if the characters and not the same or is not 0
            if (wordChars[i] != wordChars[i - 1] && wordChars[i] != '0')
            {
                //append the current character to the output string
                soundExCode += wordChars[i];
            }
        }
        //Return the Output string (Only the first 4)
        soundExCode = soundExCode + "0000";
        return soundExCode.substring(0, 4);

    }

}
