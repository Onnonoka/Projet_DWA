import GamePlayer from "./GamePlayer.js";

class Game {

    static GAME_ABORT = -2;
    static GAME_CREATING = -1;
    static GAME_CHECKING = 0;
    static GAME_CHARGE = 1;
    static GAME_ORDER = 2;
    static GAME_DECHARGE = 3;
    static GAME_END = 4;

    
    dices;

    players;

    token;

    currentPlayer;

    turn;

    nbRoll;

    gameStatus;

    constructor(players) {
        this.dices = [1, 1, 1];
        this.currentPlayer = 0;

        this.players = [];
        players.forEach(e => {
            this.players.push(new GamePlayer(e));
        });
        this.token = 21;

        this.turn = 0;
        this.nbRoll = 0;
        this.gameStatus = Game.GAME_CHECKING;
    }

    rollDice(roll1, roll2, roll3) {
        const rolls = [roll1, roll2, roll3];
        this.dices = this.dices.map((e, i) => {
            return rolls[i] || this.nbRoll === 0? Math.floor(Math.random() * 6  + 1) : e;
        });
        this.nbRoll++;
    }

    setDices(dices) {
        this.dices = dices;
    }

    endRoll() {
        this.getPlayer(this.currentPlayer).lastRoll = this.dices;
        this.nbRoll = 0;
    }

    getPlayer(username) {
        const index = this.players.findIndex(el => {
            return username === el.username;
        });
        return this.players[index];
    }

}

export default Game;