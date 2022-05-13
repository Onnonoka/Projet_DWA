
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
        this.container.style.display = "block";
        this.update();
    }

    update() {
        let content = `<div class="auth">`;
        if (this.requestType === RequestBuilder.AUTH_REGISTER) {
            content += `<div class="form">`;
            content += `<h2>S'inscrire<h2>`;
        } else {
            content += `<div class="form">`;
            content += `<h2>Connexion<h2>`;
        }
        content += `<div class="form-element">`;
        content += `<label for="username">Nom d'utilisateur</label>`;
        content += `<input type="text" id="username" placeholder="nom de compte"/>`;
        content += `</div>`;
        content += `<div class="form-element">`;
        content += `<label for="password">Mot de passe</label>`;
        content += `<input type="password" id="password" name="password" placeholder="mot de passe"/>`;
        if (this.error !== undefined) {
            if (this.error === RequestBuilder.TIMED_OUT) {
                content += `<span class="error">Erreur de connexion le server ne repond pas.</span>`;
            } else if (this.requestType === RequestBuilder.AUTH_REGISTER) {
                content += `<span class="error">Erreur de connexion le nom d'utilisateur est deja utilisé.</span>`;
            } else {
                content += `<span class="error">Erreur d'enregistrement le nom d'utilisateur ou le mot de passe est incorrect.</span>`;
            }
        }
        content += `</div>`;
        if (this.requestType === RequestBuilder.AUTH_REGISTER) {
            content += `<div class="form-element">`;
            content += `<label for="ville">Ville</label>`;
            content += `<input type="text" id="ville" placeholder="ville"/>`;
            content += `</div>`;
            content += `<div class="form-element">`;
            content += `<label for="sex">Sexe</label>`;
            content += `<select name="sexe" id="sex">
                            <option value="">--Choississez une option--</option>
                            <option value="F">F</option>
                            <option value="H">H</option>
                            <option value="O">O</option>
                        </select>`;
            content += `</div>`;
            content += `<div class="form-element">`;
            content += `<button id="sendButton">S'inscrire</button>`;
            content += `</div>`;
        } else {
            content += `<div class="form-element">`;
            content += `<button id="sendButton">Connexion</button>`;
            content += `</div>`;
        }
        content += `</div>`;
        if (this.requestType === RequestBuilder.AUTH_REGISTER) {
            
            content += `<span id="loginButton">Connexion</span>`;
        } else {
            content += `<span id="registerButon">S'inscrire</span>`;
        }
        content += `</div>`;
        this.container.innerHTML = content;
        this.updateEventListener();
    }
    
    updateEventListener() {
        let button1 = document.getElementById("sendButton");
        if (button1 !== null) {
            button1.onclick = () => {
                this.sendRequest();
            }
        }
        
         let button = document.getElementById("loginButton");
        if (button !== null) {
            button.onclick = () => {
                new AuthPopup(this.model, RequestBuilder.AUTH_LOGIN);
            };
        }
        button = document.getElementById("registerButon");
        if (button !== null) {
            button.onclick = () => {new AuthPopup(this.model, RequestBuilder.AUTH_REGISTER)};
        }
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
        })
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