import {RequestBuilder} from "./RequestBuilder.js";

class ProfileTab {

    model;
    container;
    player;

    history;
    

    constructor(model, player) {
        this.model = model;
        this.player = player;
        this.changeProfil = false;
        this.history = [];
        this.sendHistoryRequest();
    }

    update() {
        this.container = document.getElementById("main-content");
        let content = "";
        content += `<div class="profile">
            <div class="tetepseudo">
                <div class="pseudo">${this.player.pseudo}</div>`;
        if (this.model.userData.username === this.player.pseudo){
            content += `<div class="button">
                    ${ this.changeProfil ? '<button id="confirmProfil">Confirmer le changement</button>' : '<button id="modifProfil">Modifier le profil</button>'}
                </div>`;
        }
        content += `    
            </div>
            <hr />
            <div class="info">`;
        if (this.model.userData.username === this.player.pseudo ){
            content += `<div>Mdp : ${this.changeProfil ? '<input type="text" id="MdpProfil" minlenght="0" value=' + this.player.mdp + '>' : this.player.mdp}</div>`;
        }
        content += `
                <div>Sexe : ${this.changeProfil ? '<input type="text" id="SexProfil" value=' + this.player.sexe + '>' : this.player.sexe}</div>
                <div>Ville : ${this.changeProfil ? '<input type="text" id="VilleProfil" value=' + this.player.ville + '>' : this.player.ville}</div>
                <div>Age : ${this.changeProfil ? '<input type="text" id="AgeProfil" value=' + this.player.age + '>' : this.player.age}</div>
            </div>
            <hr />
            <div class="data">
                <div>Nombre de victoire : ${this.player.win}</div>
                <div>Nombre de partie jouer : ${this.player.played}</div>
                <div>Ratio de victoire : ${isNaN(this.player.win / this.player.played)? 0 : this.player.win / this.player.played}</div>
                <div>Nombre moyen de jeton charge : ${this.player.average1}</div>
                <div>Nombre moyen de jeton decharge : ${this.player.average2}</div>
            </div>
            <hr />
            <div class="history_part">
            <span>Historique</span>
            <div class="history">
                <table>
                <tr>
                    <td>id</td><td>Date</td><td>nb jetons charge</td><td>nb jetons décharge</td><td>Résultat</td>
                </tr>`;
        this.history.forEach((e, i) => {
            content += `<tr>
                <td>${e.id}</td><td>${e.date}</td><td>${e.nbJetonCharge}</td><td>${e.nbJetonDecharge}</td><td>${e.win? "Victoire" : "Defaite"}</td><td><button id="hist-${i}">voir replay</button></td>
            </tr>`;
        });

        content += `</table></div></div></div>`;
        this.container.innerHTML = content;
        this.updateEventListener();
    }
    
    updateEventListener() {
        let button1 = document.getElementById("modifProfil");
        if (button1 !== null) {
            button1.onclick = () => {
                this.changeProfil = true;
                this.update();
            };
        };
        let button2 = document.getElementById("confirmProfil");
        if (button2 !== null) {
            button2.onclick = () => {
                this.sendNewInfo();
                this.changeProfil = false;
                this.update();
                
            };
        }
        this.history.forEach((e, i) => {
            let histLine = document.getElementById("hist-" + i);
            histLine.onclick = () => {
                this.sendReplayDataRequest(e.id);
            }
        });
    }
    
    sendNewInfo() {
        let username = this.player.pseudo;
        let password = document.getElementById("MdpProfil").value;
        let sex = document.getElementById("SexProfil").value;
        let city = document.getElementById("VilleProfil").value;
        let age = document.getElementById("AgeProfil").value;
        let requestMessage = JSON.stringify({
            code: RequestBuilder.AUTH_UPDATE,
            data: {
                username: username,
                city: city,
                sex : sex,
                age : age,
                password: password
            }
        });
        this.model.ws.send(requestMessage);
    }

    GetNewInfo(data) {
        this.player.mdp = data.password
        this.player.sexe = data.sex;
        this.player.ville = data.city;
        this.player.age = data.age;
    }

    sendHistoryRequest() {
        const request = JSON.stringify({
            code: RequestBuilder.INFO_GET_HISTORY,
            data: {
                username: this.player.pseudo
            }
        });
        this.model.ws.send(request);
    }

    setHistory(data) {
        for(const e of data.history) {
            this.history.push(e);
        }
    }

    sendReplayDataRequest(id) {
        const request = JSON.stringify({
            code: RequestBuilder.REPLAY_NEW_REPLAY,
            data: {
                id: id
            }
        });
        this.model.ws.send(request);
    }
}

export default ProfileTab;