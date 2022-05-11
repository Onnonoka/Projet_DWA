import PlayerOrderPopup from "./PlayerOrderPopup.js";
import { RequestBuilder } from "./RequestBuilder.js";


class UsersList {

    model;
    main;
    container;

    users;
    selectedUser;

    constructor(model, main) {
        this.model = model;
        this.main = main;
        this.container = document.getElementById("users");
        this.users = [];
        this.selectedUser = [];
        this.update();
    }

    update() {
        this.updateList();
        this.container.innerHTML = "";
        this.container.innerHTML += `<div id="header">Liste des joueurs</div>`;
        this.displayUsers();
        this.container.innerHTML += `<div id="footer"><button class="${this.selectedUser.length < 1? "disabled" : "enable"}" id="lunchGame">Commencer ${this.selectedUser.length} / 3</button></div>`;
        this.updateEventsListeners();
    }

    displayUsers() {
        let content = "";
        content += `<div id="list">`;
        this.users.forEach((user, index) => {
            content += `<button id="${"user-" + index}" class="user ${this.selectedUser.includes(user)? "selected" : ""}">${user}</button>`;
        });
        content += `</div>`;
        this.container.innerHTML += content;
    }

    updateEventsListeners() {
        this.users.forEach((user, index) => {
            let element = document.getElementById("user-" + index);
            element.onclick = () => {
                const index = this.selectedUser.indexOf(user);
                if (index > -1) {
                    this.selectedUser.splice(index, 1);
                    this.main.removeProfileTab(user);
                } else if (this.selectedUser.length < 3 ) {
                    this.selectedUser.push(user);
                    this.sendGetProfileRequest(user);
                }
                this.update();
            }
        });
        if (this.selectedUser.length > 0) {
            let button = document.getElementById("lunchGame");
            button.onclick = () => {
                this.sendNewGameRequest();
            }
        }
    }

    sendNewGameRequest() {
        new PlayerOrderPopup(this.model, [...this.selectedUser, this.model.userData.username]);
    }

    sendGetProfileRequest(user) {
        let request = JSON.stringify({
            code: RequestBuilder.INFO_GET_PROFILE,
            data: {
                username: user
            }
        });
        this.model.ws.send(request);
    }

    updateList() {
        this.users = this.model.userConnected;
        const index = this.users.indexOf(this.model.userData.username);
        if (index > -1) {
            this.users.splice(index, 1);
        }
        let stillSelected = [];
        this.selectedUser.forEach(user => {
            if (this.users.includes(user)) {
                stillSelected.push(user);
            }
        });
        this.selectedUser = stillSelected;
    }
}

export default UsersList;