package itrex;

import java.util.Arrays;

public class ItRexFirstTestTask {

    public static void main(String[] args) {
        System.out.println(task1algorithm1("success"));// suksess
        System.out.println(task1algorithm2("ooo"));// uo
        System.out.println(task1algorithm2("oou"));// u
        System.out.println(task1algorithm2("iee"));// i
        System.out.println(task1algorithm3("The"));// Th
        System.out.println(task1algorithm3("e"));// e
        System.out.println(task1algorithm3(task1algorithm2(task1algorithm1(task1algorithm4("cacao and coffee\n" +
                "success")))));
        // kakao and kofi
        // sukses
    }

    /*
    1) Remove "c" from the text
            If the text contains “ci” and “ce”, change it to “si” and “se”.
            If “ck” then change it to “k”.
            In the other case replace “c” with “k”.
            All the changes should be made in a strong order left-to-right.
            For example:
                The word “success” first of all will be the word “sukcess”. Then “suksess”.
     */
    private static String task1algorithm1(String example){
        StringBuffer word = new StringBuffer(example);
        int wordIndex = 0; // todo RENAME charIndex
        while(word.length() > 1 && wordIndex < word.length() - 1){
            if(word.charAt(wordIndex) == 'c'){
                if(word.charAt(wordIndex + 1) == 'e' || word.charAt(wordIndex + 1) == 'i'){
                    word.setCharAt(wordIndex, 's');
                }else if(word.charAt(wordIndex + 1) == 'k'){
                    word.deleteCharAt(wordIndex--);
                }else {
                    word.setCharAt(wordIndex, 'k');
                }
            }
            wordIndex++;
        }
        // If 'c' is last char in word
        if(word.length() >= 1 && word.charAt(word.length() - 1) == 'c'){
            word.setCharAt(word.length() - 1, 'k');
        }
        return word.toString();
    }

    /*
    2) Remove a double letter
        If the text contains “ee” then replace it by simple “i”.
        If “oo” then change it by “u”.
        In the other case any double letter should be changed by one letter.
        For example:
            “ooo” will be “uo”
            “oou” will be “u”
            “iee” will be “i”
     */
    private static String task1algorithm2(String example){
        StringBuffer word = new StringBuffer(example);
        int originalSize = word.length();
        int charIndex = 0;
        while(word.length() > 1 && charIndex < word.length() - 1){
            char currentChar = word.charAt(charIndex);
            if(currentChar == word.charAt(charIndex + 1)){
                if(currentChar == 'e'){
                    word.deleteCharAt(charIndex + 1);
                    word.setCharAt(charIndex, 'i');
                }else if(currentChar == 'o'){
                    word.deleteCharAt(charIndex + 1);
                    word.setCharAt(charIndex, 'u');
                }else {
                    word.deleteCharAt(charIndex + 1);
                }
            }
            charIndex++;
        }
        if(word.length() != originalSize){
            return task1algorithm2(word.toString());
        }
        return word.toString();
    }

    /*
    3) Remove the letter “e” at the end of each word
        Remove the letter “e” at the end of each word if the word length > 1.
        For example:
            “The” will be “Th”.
     */
    private static String task1algorithm3(String testString){
        StringBuffer word = new StringBuffer(testString);
        if(word.length() > 1 && word.charAt(word.length() - 1) == 'e'){
            word.deleteCharAt(word.length() - 1);
        }
        return word.toString();
    }

    /*
    4) Remove articles
        Remove the articles “a”, “an” or “the” from the text.
        They should be removed only if they were the words a, an, the in the original text.
        For example:
            Input text: “the table”. After the first three steps it will be changed to “th tabl”. After the fourth step: “tabl”
     */
    private static String task1algorithm4(String textString){
        StringBuffer resultString  = new StringBuffer();
        Arrays.stream(textString.split(" "))
                .filter(str -> !str.trim().equals("the") && !str.trim().equals("a") && !str.trim().equals("an"))
                .forEach(text -> resultString.append(text.trim() + " "));
        return resultString.toString().trim();
    }

}
