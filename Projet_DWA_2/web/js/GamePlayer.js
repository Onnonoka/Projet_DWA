

class GamePlayer {

    username;

    token;

    lastRoll;

    lastTurnToken;

    constructor(username) {
        this.username = username;
        this.token = 0;
        this.lastTurnToken = "";

        this.lastRoll = [1, 1, 1];
    }
}

export default GamePlayer;