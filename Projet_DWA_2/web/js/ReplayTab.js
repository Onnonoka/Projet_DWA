import Game from "./Game.js";

class ReplayTab {
    model;
    container;
    main;

    rolls;
    game; 
    gameId;
    currentRoll;
    turn;

    players;
    nbPlayer;
    playerPlayTurn;

    constructor(model, id, rolls, players, main) {
        this.model = model;
        this.main = main;
        console.log(main);
        this.rolls = [];
        rolls.charge.forEach(e => {
            this.rolls[e.number] = {username: e.username, dices: e.dices}
        });
        let startIndex = this.rolls.length;
        rolls.ordre.forEach(e => {
            this.rolls[e.number + startIndex] = {username: e.username, dices: e.dices}
        });
        startIndex = this.rolls.length;
        rolls.decharge.forEach(e => {
            this.rolls[e.number + startIndex] = {username: e.username, dices: e.dices}
        });
        this.players = players; 
        this.game = new Game(players);
        this.game.gameStatus = Game.GAME_CHARGE;
        this.currentRoll = 0;
        this.gameId = id;
        this.turn = 1;
        this.playerPlayTurn = [this.rolls[this.currentRoll].username];
        this.game.getPlayer(this.rolls[this.currentRoll].username).lastRoll = this.rolls[this.currentRoll].dices;
        this.nbPlayer = players.length;
    }

    update() {
        this.container = document.getElementById("main-content");
        let content = "";
        content += `<div class="game">
        <div class="players">
        <div class="player_points">`;
        this.game.players.forEach(e => {
            content += `<div class="player ${e.username === this.game.currentPlayer? "current" : ""}">
                <div class="username">${e.username}</div>
                <div class="token">: ${e.token}</div>
                <div class="lastTunr">${e.lastTurnToken}</div>
            </div>`;
        });
        content += `</div>`;
        content += `<div class="info_tour">`;
            content += `<div class="top">
                        <div class="turn">Tour numéro : ${this.turn}</div>
                        <div="phase">Phase de ${this.game.gameStatus === Game.GAME_CHARGE? "charge" : "Decharge"}</div>`;
            content += `</div>`;
            content += `</div>`;
        content += `<div class="game_side">`;
        content += `</div><div class="center-token">Pot : ${this.game.token}</div>`;
        if (this.game.gameStatus === Game.GAME_END) {
            content += `<div class="message">${this.winner} a gagné la partie!</div>`;
        } else {
            content += `<div class="message">${this.rolls[this.currentRoll].username} a jouer</div>`;
        }
        content += `<div class="dices">`;
        this.rolls[this.currentRoll].dices.forEach(e => {
            content += `<button class="dice">${e}</button>`;
        });
        content += `</div>`;
        content += `<div class="bottom">`;
        if (this.currentRoll > 0) {
            content += `<button id="previus" class="btn">Precedant</button>`;
        }
        if (this.currentRoll < this.rolls.length - 1) {
            content += `<button id="next" class="btn">Suivant</button>`;
        }
        content += `</div>`;
        if (this.game.gameStatus === Game.GAME_END){
            content += `<div class="shutpage"><button id="btnshutpage" class="btn">Fermer la page</button></div>`
        }
        content += `</div></div>`;
        this.container.innerHTML = content;
        this.updateEventListener();
    }

    updateEventListener() {
        const nextButton = document.getElementById("next");
        if (nextButton !== null) {
            nextButton.onclick = () => {
                this.next();
            }
        }
        const previusButton = document.getElementById("previus");
        if (previusButton !== null) {
            previusButton.onclick = () => {
                this.previus();
            }
        }
        const shutpage = document.getElementById("btnshutpage");
        if (shutpage !== null) {
            shutpage.onclick = () => {
                this.main.removeReplayTab(this);
            }
        }
    }

    next() {
        this.currentRoll++;
        const nextPlay = this.rolls[this.currentRoll + 1];
        if (this.game.gameStatus !== Game.GAME_DECHARGE) {
            this.playerPlayTurn.push(this.rolls[this.currentRoll].username);
        } else {
            if (nextPlay === undefined || nextPlay.username !== this.rolls[this.currentRoll].username) {
                this.playerPlayTurn.push(this.rolls[this.currentRoll].username);
            }
        }
        this.game.getPlayer(this.rolls[this.currentRoll].username).lastRoll = this.rolls[this.currentRoll].dices;
        if (nextPlay === undefined || this.playerPlayTurn.length === this.nbPlayer) {
            const player = this.game.getPlayer(this.playerPlayTurn[0]);
            let maxRoll = player.lastRoll;
            let minRoll = player.lastRoll;
            let winner = [player];
            let looser = [player];
            let nenette = [];

            this.playerPlayTurn.forEach((username, index) => {
                const player = this.game.getPlayer(username);
                if (index > 0) {
                    if (this.diceValue(player.lastRoll).code === this.diceValue(maxRoll).code) {
                        winner.push(player);
                    }
                    if (this.diceValue(player.lastRoll).code === this.diceValue(minRoll).code) {
                        looser.push(player);
                    }
                    if (this.diceCompare(player.lastRoll, maxRoll)) {
                        maxRoll = player.lastRoll;
                        winner = [player];
                    }
                    if (this.diceCompare(minRoll, player.lastRoll)) {
                        minRoll = player.lastRoll;
                        looser = [player];
                    }
                    if (this.diceValue(player.lastRoll).code === "221") {
                        nenette.push(player);
                    }
                }
            });
            switch(this.game.gameStatus) {
                case Game.GAME_CHARGE:
                    if (looser.length === 1) {
                        nenette.forEach(e => {
                            if (this.game.token >= 2) {
                                this.game.getPlayer(e).token += 2;
                                this.game.token -= 2;
                            } else {
                                this.game.getPlayer(e).token += this.game.token;
                                this.game.token = 0;
                            }
                        });
                        if (this.game.token >= this.diceValue(maxRoll).token) {
                            this.game.token -= this.diceValue(maxRoll).token;
                            looser[0].token += this.diceValue(maxRoll).token;
                        } else {
                            looser[0].token += this.game.token;
                            this.game.token = 0;
                        }
                        this.turn++;
                        this.playerPlayTurn = [];
                        if (this.game.token === 0) {
                            this.game.gameStatus = Game.GAME_ORDER;
                            this.turn = 0;
                        }
                    } else {
                        looser.forEach(e => {
                            const index = this.playerPlayTurn.indexOf(e);
                            this.playerPlayTurn.splice(index, 1);
                        });
                    }
                    break;
                case Game.GAME_ORDER:
                    if (winner.length === 1) {
                        this.turn++;
                        this.playerPlayTurn = [];
                        this.game.gameStatus = Game.GAME_DECHARGE;
                    } else {
                        winner.forEach(e => {
                            const index = this.playerPlayTurn.indexOf(e);
                            this.playerPlayTurn.splice(index, 1);
                        });
                    }
                    break;
                case Game.GAME_DECHARGE:
                    if (winner[winner.length-1].token >= this.diceValue(maxRoll).token) {
                        winner[winner.length-1].token -= this.diceValue(maxRoll).token;
                        looser[winner.length-1].token += this.diceValue(maxRoll).token;
                    } else {    
                        looser[winner.length-1].token += winner[winner.length-1].token;
                        winner[winner.length-1].token = 0;
                    }
                    if (winner[winner.length-1].token === 0) {
                        this.game.gameStatus = Game.GAME_END;
                        this.winner = winner[winner.length-1].username;
                    }
                    this.turn++;
                    this.playerPlayTurn = [];
                    break;
            }
        }
        this.update();
    }

    previus() {
        const currentStep = this.currentRoll;
        this.game = new Game(this.players);
        this.game.gameStatus = Game.GAME_CHARGE;
        this.currentRoll = 0;
        this.gameId = this.gameId;
        this.turn = 1;
        this.playerPlayTurn = [this.rolls[this.currentRoll].username];
        this.game.getPlayer(this.rolls[this.currentRoll].username).lastRoll = this.rolls[this.currentRoll].dices;
        while(this.currentRoll < currentStep - 1) {
            this.next();
        }
        this.update();
    }

    diceValue(dices) {
        dices.sort((a, b) => {return b - a});
        const diceCode = `${dices[0]}${dices[1]}${dices[2]}`;
        switch(diceCode) {
            case "421":
                return {
                    code: diceCode,
                    weight: 18,
                    token: 10
                }
            case "111":
                return {
                    code: diceCode,
                    weight: 17,
                    token: 7
                }
            case "611": 
                return {
                    code: diceCode,
                    weight: 16,
                    token: 6
                }
            case "666":
                return {
                    code: diceCode,
                    weight: 15,
                    token: 6
                }
            case "511":
                return {
                    code: diceCode,
                    weight: 14,
                    token: 5
                }
            case "555" :
                return {
                    code: diceCode,
                    weight: 13,
                    token: 5
                }
            case "411":
                return {
                    code: diceCode,
                    weight: 12,
                    token: 4
                }
            case "444":
                return {
                    code: diceCode,
                    weight: 11,
                    token: 4
                }
            case "311" :
                return {
                    code: diceCode,
                    weight: 10,
                    token: 3
                }
            case "333" :
                return {
                    code: diceCode,
                    weight: 9,
                    token: 3
                }
            case "211" :
                return {
                    code: diceCode,
                    weight: 8,
                    token: 2
                }
            case "222" :
                return {
                    code: diceCode,
                    weight: 7,
                    token: 2
                }
            case "654" :
                return {
                    code: diceCode,
                    weight: 6,
                    token: 2
                }
            case "543" :
                return {
                    code: diceCode,
                    weight: 5,
                    token: 2
                }
            case "432" :
                return {
                    code: diceCode,
                    weight: 4,
                    token: 2
                }
            case "321" :
                return {
                    code: diceCode,
                    weight: 3,
                    token: 2
                }
            case "221" :
                return {
                    code: diceCode,
                    weight: 2,
                    token: 2
                }
            default :
                return {
                    code: diceCode,
                    weight: 1,
                    token: 1
                }
        }
    }

    diceCompare(a, b) {
        const aValue = this.diceValue(a);
        const bValue = this.diceValue(b);

        if (aValue.weight === 1 && bValue.weight === 1) {
            if (a[0] === b[0]) {
                if (a[1] === b[1]) {
                    return a[2] > b[2];
                }
                return a[1] > b[1];
            }
            return a[0] > b[0];
        }
        return aValue.weight > bValue.weight;
    }

}

export default ReplayTab;