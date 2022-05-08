
class RequestBuilder {
    static UNDEFINED_CODE = -999;
    
    static TIMED_OUT = -900;
    static USER_DISCONECTED = -20;
    
    static INFO_WRONG_USERNAME = -3;
    
    static AUTH_USERNAME_ALREADY_USED = -2;
    static AUTH_WRONG_CREDENTIALS = -1;
    
    static AUTH_LOGIN = 0;
    static AUTH_REGISTER = 1;
    static AUTH_UPDATE = 2;
    
    static INFO_GET_PROFILE = 3;
    static INFO_GET_HISTORY = 4;
    static INFO_PLAYER_LIST = 5;
    
    static GAME_NEW_GAME = 6;
    static GAME_STARTING = 7;
    static GAME_READY = 8;
    static GAME_NOT_READY = 9;
    static GAME_START = 10;
    static GAME_CANCEL = 11;

}

export {RequestBuilder};