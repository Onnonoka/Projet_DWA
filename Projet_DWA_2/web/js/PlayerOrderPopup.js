import {RequestBuilder} from "./RequestBuilder.js";

class PlayerOrderPopup {

    model;
    players;
    playersIndex;

    nextIndex ;
    
    container;

    constructor(model, players) {
        this.model = model;
        this.players = players;
        this.playersIndex = this.players.map(() => {return 0});
        this.nextIndex = 1;
        this.container = document.getElementById("popup");
        this.container.innerHTML = "";
        this.container.style.display = "grid";
        console.log(this);
        this.update();
    }

    update() {
        let content = `<div class="player-order">
            <div class="title"> Selectionner l'ordre des joueurs</div>
            <div class="content">
            <div class="list">`;
        this.players.forEach((username, index) => {
            content += `<button id="${"userOrder-" + index}" class="user ${this.playersIndex[index] !== 0? "selected" : ""}">
            <div class="username">${username}</div><div class="index">${this.playersIndex[index] !== 0? this.playersIndex[index] : ""}</button>`;
        });
        content += `</div>`;
        content += `<div class="footer"><button class="${this.playersIndex.reduce((check, a) => {return (a !== 0? check : false)}, true)? "enable" : "disabled"}" id="sendNewGame">Commencer</button></div>`;
        content += `</div></div>`;
        this.container.innerHTML = content;
        this.updateEventListener();
    }

    updateEventListener() {
        if (this.playersIndex.reduce((check, a) => {return (a !== 0? check : false)}, true)) {
            let button = document.getElementById("sendNewGame");
            button.onclick = () => {this.sendRequest()};
        }
        this.players.forEach((username, index) => {
            let buttonPlayer = document.getElementById("userOrder-" + index);
            buttonPlayer.onclick = () => {this.setIndex(index)};
        });
    }

    setIndex(index) {
        if (this.playersIndex[index] === 0) {
            this.playersIndex[index] = this.nextIndex;
            this.nextIndex++;
        } else {
            let temp = this.playersIndex[index];
            this.playersIndex = this.playersIndex.map( e => {
                console.log(e, temp);
                if (e >= temp) {
                    return --e;
                }
                return e;
            });
            this.playersIndex[index] = 0;
            this.nextIndex--;
        }
        this.update();
    }

    sendRequest() {
        let playersOrder = [];
        this.playersIndex.forEach((e, index) => {
            playersOrder[e - 1] = this.players[index];
        });
        let request = JSON.stringify({
            code: RequestBuilder.GAME_NEW_GAME,
            data: {
                nbPlayer: this.playersIndex.length,
                playersOrder: playersOrder
            }
        });
        this.model.ws.send(request);
        this.container.style.display = "none";
    }

}

export default PlayerOrderPopup;