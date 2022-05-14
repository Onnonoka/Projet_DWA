import { RequestBuilder } from "./RequestBuilder.js";


class PlayerStatus {
    static PLAYER_WAITING = 0;
    static PLAYER_READY = 1;
    static PLAYER_REFUSE = 2;
}

class MatchFoundPopup {

    model;
    container;

    gameId;

    status;

    asReply;

    constructor(model, nbPlayer, gameId) {
        this.model = model;
        this.gameId = gameId;
        this.status = [];
        for (let i = 0; i < nbPlayer; i++) {
            this.status.push(PlayerStatus.PLAYER_WAITING);
        }
        this.asReply = false;
        this.container = document.getElementById("popup");
        this.container.style.display = "grid";
        this.update();
    }

    update() {
        let content = `<div class="matchmaking">
        <div class="title">Partie trouver</div>
        <div class="content">
        <div class="horizontal-list">`;
        this.status.forEach(element => {
            if (element === PlayerStatus.PLAYER_READY) {
                content += `<div class="statut">Pret</div>`;
            } else if (element === PlayerStatus.PLAYER_REFUSE) {
                content += `<div class="statut">Refuser</div>`;
            } else {
                content += `<div class="statut">En attente...</div>`;
            }
        });
        content += `</div><div classs="footer">`;
        if (!this.asReply) {
            content += `<button id="accept">Pret</button><button id="decline">Refuser</button>`;
        }
        content += `</div></div></div>`;
        this.container.innerHTML = content;
        this.updateEventListener();
    }

    updateEventListener() {
        if (!this.asReply) {
            let acceptButton = document.getElementById("accept");
            let declineButton = document.getElementById("decline");
            acceptButton.onclick = () => {this.sendRequest(true)};
            declineButton.onclick = () => {this.sendRequest(false)};
        }
    }

    setStatus(status) {
        this.status = status;
        this.update();
    }

    close() {
        this.container.innerHTML = "";
        this.container.style.display = "none";
    }

    sendRequest(accept) {
        let requestMessage = JSON.stringify({
            code: accept? RequestBuilder.GAME_READY : RequestBuilder.GAME_NOT_READY,
            data: {
                id: this.gameId
            }
        });
        this.model.ws.send(requestMessage);
        this.asReply = true;
        this.update();
    }
}

export default MatchFoundPopup;