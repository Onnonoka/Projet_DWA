/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

import model from "./model.js";
import vue from "./vue.js";
import controler from "./controler.js";

const go = () => {
    console.log("go");
    
    const m = new model();
    const v = new vue(m);
    const c = new controler(m, v);    
}

window.addEventListener('load', go);


