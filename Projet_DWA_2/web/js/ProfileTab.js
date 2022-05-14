import {RequestBuilder} from "./RequestBuilder.js";

class ProfileTab {

    model;
    container;
    player;
    

    constructor(model, player) {
        this.model = model;
        this.player = player;
        this.changeProfil = false;
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
        </div>`;
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
}

export default ProfileTab;