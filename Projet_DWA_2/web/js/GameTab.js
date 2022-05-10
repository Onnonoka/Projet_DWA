import MatchFoundPopup from "./MatchFoundPopup.js";
import GamePlayer from "./GamePlayer.js";


class GameTab {

    model;
    container;
    gameId;
    players;

    token;

    currentPlayer;

    popup;

    gameStarted;

    constructor(model, players, gameId) {
        this.model = model;
        this.players = [];
        players.forEach(e => {
            this.players.push(new GamePlayer(e));
        });
        this.gameId = gameId;
        this.currentPlayer = 0;

        this.token = 21;

        this.popup = new MatchFoundPopup(this.model, this.players.length, this.gameId);
        this.gameStarted = false;
    }

    update() {
        this.container = document.getElementById("main-content");
        let content = "";
        if (this.gameStarted) {
            content += `<div class="game">
            <div class="players">`;
            this.players.forEach(e => {
                content += `<div class="player ${e.username === this.players[this.currentPlayer].username? "current" : ""}">
                    <div class="username">${e.username}</div>
                    <div class="token">${e.token}</div>
                </div>`;
            });
            content += `</div><div class="center-token">Pot : ${this.token}</div>`;
            content += `<div class="message">A ${this.players[this.currentPlayer].username === this.model.userData.username? "toi" : this.players[this.currentPlayer].username} de jouer</div>`;
            content += `<div class="dices">
            <button id="btn-1" class="dice">1</button><button id="btn-2" class="dice">1</button><button id="btn-3" class="dice">1</button>
            </div>`;
            if (this.model.userData.username === this.players[this.currentPlayer].username) {
                content += `<div class="bottom"><button id="lancer" class="btn">Lancer</button><button id="terminer" class="btn">Terminer</button></div>`;
            }
            content += `</div>`;
        }
        this.container.innerHTML = content;
    }

    setStatus(status) {
        this.popup.setStatus(status);
    }

    closePopup() {
        this.popup.close();
    }

    setGameData(data) {
        this.currentPlayer = data.currentPlayer;
        this.token = data.remainToken;
        this.players.forEach(e => {
            const index = data.players.findIndex(el => {
                console.log(el, e);
                return e.username === el.username;
            });
            e.token = data.players[index].token;
        });
        this.update();
    }
}

export default GameTab;