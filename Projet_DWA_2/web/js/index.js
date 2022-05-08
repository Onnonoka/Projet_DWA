/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servconst/JavaScript.js to edit this template
 */

import Model from "./Model.js";
import Vue from "./Vue.js";
import Controler from "./Controler.js";

const DEBUG = true;

const go = () => {
    console.log("go");
    const m = new Model();
    const v = new Vue(m);
    const c = new Controler(m, v);
}

window.addEventListener('load', go);
