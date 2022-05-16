import GameTab from "./GameTab.js";
import ProfileTab from "./ProfileTab.js";
import ReplayTab from "./ReplayTab.js";

class Main {

    model;
    container;

    tabsTitle = [];
    tabs = [];
    tabIndex = 0;

    constructor(model) {
        this.model = model;
        this.container = document.getElementById("main");
        this.update();
    }

    update() {
        this.container.innerHTML = "";  
        this.displayTabs();
        this.container.innerHTML += `<div id="main-content"></div>`;
        if (this.tabs.length > this.tabIndex) {
            this.tabs[this.tabIndex].update();
        }
        this.updateEventsListeners();
    }

    displayTabs() {
        let content = "";
        content += `<div id="header">`;
        this.tabsTitle.forEach((tab, index) => {
            content += `<div class="tab ${index === this.tabIndex? "selected" : ""}" id="tab-${index}">${tab}</div>`;
        });
        content += `</div>`;
        this.container.innerHTML += content;
    }

    updateEventsListeners() {
        for(let i = 0; i < this.tabsTitle.length; i++) {
            let element = document.getElementById("tab-" + i);
            element.onclick = () => {
                this.tabIndex = i;
                this.update();
            }
        }
    }

    addProfileTab(profile) {
        let tab = new ProfileTab(this.model, profile);
        this.tabs.push(tab);
        this.tabsTitle.push(profile.pseudo === this.model.userData.username? "Profile" : profile.pseudo);
        this.update();
    }

    removeProfileTab(username) {
        const index = this.tabsTitle.indexOf(username);
        if (index > -1) {
            this.tabsTitle.splice(index, 1);
            this.tabs.splice(index, 1);
        }
        if (this.tabIndex >= this.tabsTitle.length) {
            this.tabIndex--;
        }
        this.update();
    }
    
    getProfileTab(username) {
        let tab;
        const index = this.tabsTitle.indexOf(username === this.model.userData.username? "Profile" : username);
        if (index !== -1) {
            tab = this.tabs[index];
        }
        return tab;
    }

    addGameTab(gameId, players) {
        let tab = new GameTab(this.model, players, gameId, this);
        this.tabs.push(tab);
        this.tabsTitle.push("Partie " + gameId);
        this.update();
    }

    removeGameTab(gameId) {
        const index = this.tabsTitle.indexOf("Partie " + gameId);
        if (index > -1) {
            this.tabsTitle.splice(index, 1);
            this.tabs.splice(index, 1);
        }
        if (this.tabIndex >= this.tabsTitle.length) {
            this.tabIndex--;
        }
        this.update();
    }

    getGameTab(gameId) {
        let tab;
        const index = this.tabsTitle.indexOf("Partie " + gameId);
        if (index !== -1) {
            tab = this.tabs[index];
        }
        return tab;
    }

    addReplayTab(data) {
        console.log(data);
        let tab = new ReplayTab(this.model, data.id, data.rolls, data.players, this);
        this.tabs.push(tab);
        this.tabsTitle.push("Replay " + data.id);
        this.update();
    }

    removeReplayTab(data) {
        const index = this.tabsTitle.indexOf("Replay " + data.gameId);
        if (index > -1) {
            console.log(index);
            this.tabsTitle.splice(index, 1);
            this.tabs.splice(index, 1);
        }
        if (this.tabIndex >= this.tabsTitle.length) {
            this.tabIndex--;
        }
        this.update();
    }
}

export default Main;