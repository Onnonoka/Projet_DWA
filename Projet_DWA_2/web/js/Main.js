import GameTab from "./GameTab.js";
import ProfileTab from "./ProfileTab.js";

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

    addGameTab(gameId, players) {
        let tab = new GameTab(this.model, players, gameId);
        this.tabs.push(tab);
        this.tabsTitle.push("Partie " + gameId);
        this.update();
    }

    removeGameTab(gameId) {
        const index = this.tabsTitle.indexOf("Partie " + gameId);
        if (index > -1) {
            this.tabs[index].closePopup();
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
        this.tabs.forEach( element => {
            if (element instanceof GameTab && element.gameId === gameId) {
                tab = element;
            }
        });
        return tab;
    }
}

export default Main;