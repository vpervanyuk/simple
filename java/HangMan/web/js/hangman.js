function newWord(e) {
    console.log("NewGame strated");
    request("command=1");
    document.getElementById("fails_img").setAttribute("img", "resources/0.jpg")
    e.target.disabled = true;
    document.getElementById("hint_btn").disabled = false;
    document.getElementById("giveup_btn").disabled = false;
}

function giveUp(e){
    console.log("GiveUp pressed");
    request("command=3");

}

function showHint(e){
    console.log("Hint pressed");
    request("command=2");
}

function help(e){
    console.log("Help pressed");
    alert("No Help :)");
}

function newLetter(e) {
    var letter = document.getElementById("input_letter").value;
    len = String(letter).length;
    console.log("New Letter pressed (" + letter + ")");
    if(len==0){          // no letter
        writeMessage("Select a letter or give up!")
    }else if(len==1) {   // letter
        request("command=4&letter=" + letter)
    }else{               // word
        request("command=5&word=" + letter)
    };
}

function writeMessage(text) {
    document.getElementById("message_txt").innerText = text
}

function writeWord(text) {
    document.getElementById("word_txt").innerText = text
}

function writeFails(text) {
    if (text>=10){
        document.getElementById("fails_txt").innerText = "game over!"
        document.getElementById("fails_img").setAttribute("src", "resources/gameover.jpg")
        document.getElementById("giveup_btn").disabled = true;
        document.getElementById("hint_btn").disabled = true;
        document.getElementById("newword_btn").disabled = false;
    }else {
        document.getElementById("fails_txt").innerText = text
        document.getElementById("fails_img").setAttribute("src", "resources/" + text + ".jpg")
    }
}

function parseAnswer(jsonObj) {
    var answer = JSON.parse(jsonObj);
    writeMessage(answer.message);
    writeWord(answer.wordLetters);
    writeFails(answer.fails);
}

function request(params) {
    var servletURL = "http://localhost:8080/hangman";
    var request = new XMLHttpRequest();
    console.log(servletURL+"?"+params);

    request.open("GET", servletURL+"?"+params, false);
    request.send();

    var status = request.status;
    if(status==200)
        //writeMessage(request.responseText);
        parseAnswer(request.responseText);
    else if(status==404)
        writeMessage("Ресурс не найден");
    else
        writeMessage(request.statusText);
}

//initialization
document.getElementById("newword_btn").onclick = newWord;
document.getElementById("hint_btn").onclick = showHint;
document.getElementById("help_btn").onclick = help;
document.getElementById("giveup_btn").onclick = giveUp;
document.getElementById("letter_btn").onclick = newLetter;
request("command=1");