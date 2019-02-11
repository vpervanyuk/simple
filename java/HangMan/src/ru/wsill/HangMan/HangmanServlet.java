package ru.wsill.HangMan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/hangman")
public class HangmanServlet extends HttpServlet {

    final int commandNewGame = 1;
    final int commandHint    = 2;
    final int commandGiveUp  = 3;
    final int commandNewLetter  = 4;
    final int commandWord  = 5;
    final int commandBad  = 10;
    Game game = new Game();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int result;
        int command = -1;     //управление игрой
        String word = null;   //введенное слово
        char letter = '#';   //введенная буква
        StringBuilder answerBuilder;
        String message = null;

        //сначала провряем управление
        try {
            command = Integer.parseInt(req.getParameter("command"));
        }
        catch(Exception ex){
            command = commandBad;
        }

        switch (command) {
            case commandBad:
                message = "Bad command!";
                break;
            case commandNewGame:
                game.NewGame(); //
                message = "Let's go!";
                break;
            case commandHint:
                message = game.getHint();
                message = "... so what?";
                break;
            case commandGiveUp:
                message = "the word was :" + game.riddleWord;
                game.setMaxFails();
                break;
            case commandNewLetter:
                letter = req.getParameter("letter").toUpperCase().charAt(0);
                result = (letter == '#') ? -10 : game.newLetter(letter);
                switch (result) {
                    case -10:
                        message = "no letter?";
                        break;
                    case -2:
                        message = "game over!";
                        break;
                    case -1:
                        message = "the letter " + letter + " was used already.";
                        break;
                    case 0:
                        message = "no letter " + letter + " in this word!";
                        break;
                    case 1:
                        message = "you right, there is a " + letter + " in his word";
                        break;
                    case 2:
                        message = "bingo, you win!";
                        break;
                    default:
                        message = "impossible result :)";
                }
                break;
            case commandWord:
                word = req.getParameter("word").toUpperCase();
                result = (word == null) ? -10 : game.checkWord(word);
                message = (result == -2) ? "game over!" : "bingo, you win!";
                break;
            default:
                message = "impossible result :)";
        }


    answerBuilder = new StringBuilder(256);
    answerBuilder.append("{");
    //answerBuilder.append("\"riddleWord\": \"" + game.riddleWord + "\",\n");
    answerBuilder.append("\"message\": \"" + message + "\",\n");
    //answerBuilder.append("\"usedLetters\": " + game.usedLetters.toString() + ",\n");
    answerBuilder.append("\"wordLetters\": \"" + String.valueOf(game.answerWordLetters) + "\",\n");
    answerBuilder.append("\"fails\": " + game.fails + "}");

    PrintWriter writer = resp.getWriter();
    writer.println(answerBuilder.toString());
    }
}





