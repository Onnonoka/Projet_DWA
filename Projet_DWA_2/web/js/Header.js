import AuthPopup from "./AuthPopup.js";
import { RequestBuilder } from "./RequestBuilder.js";
import Model from "./Model.js";

class Header {
    container;

    constructor(model) {
        this.model = model;
        this.container = document.getElementById("header");
        this.update();
    }

    update() {
        this.container.innerHTML = "";
        switch(this.model.loginStatus) {
            case Model.CONNECTED :
                this.container.innerHTML += `<div>${this.model.userData.username}</div>`;
                break;
            case Model.CONNECTING :
                this.container.innerHTML += "Trying...";
                break;
            case Model.DISCONECTED :
            default : 
                this.container.innerHTML += `<button id="registerButton">S'inscrire</button>`;
                this.container.innerHTML += `<button id="logginButton">Connexion</button>`;
                break;
        }
        this.updateEventListener();
    }

    updateEventListener() {
        let button = document.getElementById("logginButton");
        if (button !== null) {
            button.onclick = () => {
                new AuthPopup(this.model, RequestBuilder.AUTH_LOGIN);
            };
        }
        button = document.getElementById("registerButton");
        if (button !== null) {
            button.onclick = () => {new AuthPopup(this.model, RequestBuilder.AUTH_REGISTER)};
        }
    }

}

export default Header;