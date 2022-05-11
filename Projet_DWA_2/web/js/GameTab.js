import MatchFoundPopup from "./MatchFoundPopup.js";
import GamePlayer from "./GamePlayer.js";
import Game from "./Game.js";
import { RequestBuilder } from "./RequestBuilder.js";


class GameTab {

    model;
    container;
    gameId;

    game;

    popup;

    gameStarted;

    dicesSelected;

    message;

    constructor(model, players, gameId) {
        this.model = model;
        this.gameId = gameId;

        this.game = new Game(players);

        this.popup = new MatchFoundPopup(this.model, players.length, this.gameId);
        this.gameStarted = false;
        this.dicesSelected = [false, false, false];

        this.message = "";
    }

    update() {
        const mTurn = this.model.userData.username === this.game.currentPlayer;
        this.container = document.getElementById("main-content");
        let content = "";
        if (this.gameStarted) {
            content += `<div class="game">
            <div class="players">`;
            this.game.players.forEach(e => {
                content += `<div class="player ${e.username === this.game.currentPlayer? "current" : ""}">
                    <div class="username">${e.username}</div>
                    <div class="token">${e.token}</div>
                </div>`;
            });
            content += `</div><div class="center-token">Pot : ${this.game.token}</div>`;
            content += `<div class="message">${this.message}</div>`;
            content += `<div class="dices">`;
            this.dicesSelected.forEach((e, i) => {
                content += `<button id="dice-${i}" class="dice ${e? "selected" : ""}">${this.game.dices[i]}</button>`;
            });
            content += `</div>`;
            if (mTurn) {
                content += `<div class="bottom"><button id="roll" class="btn">Lancer</button>`;
                if (this.game.gameStatus === Game.GAME_DECHARGE) {
                    content += `<button id="end" class="btn">Terminer</button>`;
                }
                content += `</div>`;
            }
            content += `</div>`;
        }
        this.container.innerHTML = content;
        this.updateEventListener();
    }

    updateEventListener() {
        let button = document.getElementById("roll");
        if (button !== null) {
            button.onclick = () => {
                this.game.rollDice(...this.dicesSelected);
                this.sendRoll();
                if (this.game.gameStatus === Game.GAME_CHARGE) {
                    this.sendEndRoll();
                }
                this.update();
            }
        }
        button = document.getElementById("end");
        if (button !== null) {
            button.onclick = () => {
                this.game.rollDice(...this.dicesSelected);
                this.sendEndRoll();
                this.update();
            }
        }
        if (this.game.gameStatus === Game.GAME_DECHARGE && this.model.userData.username === this.game.currentPlayer) {
            this.dicesSelected.forEach((e, i) => {
                button = document.getElementById("dice-" + i);
                button.onclick = () => {
                    this.dicesSelected[i] = !e;
                    this.update();
                }
            });
        }

    }

    setStatus(status) {
        this.popup.setStatus(status);
    }

    closePopup() {
        this.popup.close();
    }

    setGameData(data) {
        this.game.currentPlayer = data.currentPlayer;
        this.game.token = data.remainToken;
        this.game.players.forEach(e => {
            const index = data.players.findIndex(el => {
                return e.username === el.username;
            });
            e.token = data.players[index].token;
        });
        this.game.gameStatus = data.status;
        this.message = `A ${this.model.userData.username === this.game.currentPlayer? "toi" : this.game.currentPlayer} de jouer`;
        this.update();
    }

    sendRoll() {
        let requestMessage = JSON.stringify({
            code: RequestBuilder.GAME_LUNCH_DICE,
            data: {
                id: this.gameId,
                dices : this.game.dices
            }
        });
        this.model.ws.send(requestMessage);
        this.update();

    }

    sendEndRoll() {
        let requestMessage = JSON.stringify({
            code: RequestBuilder.GAME_END_ROLL,
            data: {
                id: this.gameId,
            }
        });
        this.model.ws.send(requestMessage);
        this.update();
    }
}

export default GameTab;