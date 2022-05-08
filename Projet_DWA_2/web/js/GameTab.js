import MatchFoundPopup from "./MatchFoundPopup.js";


class GameTab {

    model;
    container;
    gameId;
    players;

    popup;

    constructor(model, players, gameId) {
        this.model = model;
        this.players = players;
        this.gameId = gameId;

        this.popup = new MatchFoundPopup(this.model, this.players.length, this.gameId);
    }

    update() {
        this.container = document.getElementById("main-content");
        let content = "";
        content += `<div class="game">
        </div>`;
        this.container.innerHTML = content;
    }

    setStatus(status) {
        this.popup.setStatus(status);
    }
}

export default GameTab;