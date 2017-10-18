/**
 * Arquivo: usuario.js
 * Author: Gabriel Zerbine
 * Description: Arquivo onde trataremos o modelo do projeto.
 * Definição dos esquemas para serem utilizadas na Base de Dados (MongoDB)
 * Data: 16/10/2017
 */

 var mongoose = require('mongoose');
 var Schema = mongoose.Schema;

 var UsuarioSchema = new Schema ({
     nome: String,
     login: String,
     senha: String
 });

 module.exports = mongoose.model('Usuario', UsuarioSchema);