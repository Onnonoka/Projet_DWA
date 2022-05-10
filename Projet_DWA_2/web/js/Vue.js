/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

import Header from "./Header.js";
import AuthPopup from "./AuthPopup.js";
import UsersList from "./UsersList.js";
import Main from "./Main.js";

class Vue {
    
    model;                          // the model of the app
    mainContainer;                  // the container
    
    constructor(model) {
        this.model = model;
        this.container = document.getElementById("app");

    }

    init() {
        this.container.innerHTML = `
        <div id="header"></div>
        <div id="main"></div>
        <div id="users"></div>
        <div id="popup"></div>
        `;
        this.header = new Header(this.model);
        new AuthPopup(this.model, 0);
        this.main = new Main(this.model);
        this.userList = new UsersList(this.model, this.main);
    }

    update() {
        this.header.update();
        this.userList.update();
        this.main.update();
    }
    
}

export default Vue;