

class GamePlayer {

    username;

    token;

    lastRoll;

    constructor(username) {
        this.username = username;
        this.token = 0;

        this.lastRoll = [1, 1, 1];
    }
}

export default GamePlayer;