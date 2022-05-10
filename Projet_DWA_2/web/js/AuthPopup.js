
import {RequestBuilder} from "./RequestBuilder.js";
import Model from "./Model.js";

class AuthPopup {

    container;
    model;
    requestType;
    error;

    constructor(model, requestType, error) {
        this.model = model;
        this.requestType = requestType;
        this.error = error;
        this.container = document.getElementById("popup");
        this.container.innerHTML = "";
        this.container.style.display = "grid";
        this.update();
    }

    update() {
        let content = `<div class="auth">`;
        if (this.requestType === RequestBuilder.AUTH_REGISTER) {
            content += `<div class="title">S'inscrire</div>`;
        } else {
            content += `<div class="title">Connexion</div>`;
        }
        content += `<div class="content">`;
        content += "Nom d'utilisateur : ";
        content += `<input id="username" />`;
        content += "Mot de passe : ";
        content += `<input id="password" type="password" />`;
        if (this.error !== undefined) {
            if (this.error === RequestBuilder.TIMED_OUT) {
                content += `<span class="error">Erreur de connexion le server ne repond pas.</span>`;
            } else if (this.requestType === RequestBuilder.AUTH_REGISTER) {
                content += `<span class="error">Erreur de connexion le nom d'utilisateur est deja utilisé.</span>`;
            } else {
                content += `<span class="error">Erreur d'enregistrement le nom d'utilisateur ou le mot de passe est incorrect.</span>`;
            }
        }
        if (this.requestType === RequestBuilder.AUTH_REGISTER) {
            content += `<button id="sendButton">S'inscrire</button>`;
        } else {
            content += `<button id="sendButton">Connexion</button>`;
        }
        content += `</div>`;
        content += `</div>`;
        this.container.innerHTML = content;
        let button = document.getElementById("sendButton");
        button.onclick = () => {this.sendRequest()};
    }

    sendRequest() {
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;
        let requestMessage = JSON.stringify({
            code: this.requestType,
            data: {
                username: username,
                password: password
            }
        });
        this.container.style.display = "none";
        this.model.ws.send(requestMessage);
        if (this.requestType === RequestBuilder.AUTH_LOGIN) {
            this.model.loginStatus = Model.CONNECTING;
            setTimeout( () => {
                if (this.model.loginStatus === Model.CONNECTING) {
                    new AuthPopup(this.model, this.requestType, RequestBuilder.TIMED_OUT);
                }
            }, 5000);
        } 
    }
}

export default AuthPopup;