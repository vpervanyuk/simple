package ru.wsill.HangMan;

import java.util.ArrayList;

public class Game {
 final int maxFails =10;
 int fails;
 String[] wordList;
 String riddleWord;
 char[] answerWordLetters;
 ArrayList<Character> usedLetters;

 //конструктор
    Game(){
        fails = 0;
        wordList = new String[] {"ABRAKADABRA", "TRAKTOR", "COMPUTER"};
        usedLetters = new ArrayList<Character>();
        NewGame();
    }

 //загадать новое слово
    public void NewGame() {

     //word = wordList[(int)Math.random()*wordList.length];
        riddleWord = wordList[1];
        answerWordLetters = riddleWord.replaceAll("\\w", "-").toCharArray();
        usedLetters.clear();
        fails = 0;
    }

 //getter ?
 //   public String getRiddleWord() {
 //       return riddleWord;
 //   }

 //проверка, использована ли буква
    boolean letterIsUsed(char letter) {
       return usedLetters.contains(letter);
    }

 //обработка новой буквы
 // возвращает
 // -2 - проигрыш
 // -1 - буква уже была
 // 0  - нет буквы (увеличивает число ошибок)
 // 1  - есть буква (открывает в слове)
 // 2  - выигрыш
    public int newLetter(char letter){
      int res = 0;
      if (letterIsUsed(letter)) {
          res = -1;
      }else{
          usedLetters.add(letter);

          for (int i=0; i<riddleWord.length(); i++){
              if (riddleWord.charAt(i) == letter){
                  answerWordLetters[i] = letter;
                  res = 1;
              }
          }
          if (res == 0) fails++;
          else if (this.riddleWord.equals(String.valueOf(answerWordLetters))){
              res = 2;
          };
          if (fails > maxFails) res = -2;
      }
      return res;
    }

 //проверка слова
 //возвращает
 //-2 - не верно, проигрыш
 // 2 - верно, победа
    public int checkWord(String word){
     if (this.riddleWord.equals(word)) {
         return 2;
     }else{
         setMaxFails();
         return -2;
     }
    }


    public String getHint() {
        fails++;
        return "A";
    }

    public void setMaxFails(){
        fails = maxFails;
    }

    @Override
    public String toString() {
        return "{\"maxFails\":"+maxFails+", \"fails\":"+fails+", \"riddleWord\":"+riddleWord+", \"aswerWordLetters\":"+answerWordLetters.toString()+"}" ;
    }

}
