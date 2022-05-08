

class ProfileTab {

    model;
    container;
    player;

    constructor(model, player) {
        this.model = model;
        this.player = player;
    }

    update() {
        this.container = document.getElementById("main-content");
        let content = "";
        content += `<div class="profile">
            <div class="pseudo">${this.player.pseudo}</div>
            <div class="info">
                <div>Sexe : ${this.player.sexe}</div>
                <div>Ville : ${this.player.ville}</div>
            </div>
            <div class="data">
                <div>Nombre de victoire : ${this.player.win}</div>
                <div>Nombre de partie jouer : ${this.player.played}</div>
                <div>Ratio de victoire : ${isNaN(this.player.win / this.player.played)? 0 : this.player.win / this.player.played}</div>
                <div>Nombre moyen de jeton charge : ${this.player.average1}</div>
                <div>Nombre moyen de jeton decharge : ${this.player.average2}</div>
            </div>
        </div>`;
        this.container.innerHTML = content;
    }
}

export default ProfileTab;