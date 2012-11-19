/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 
var CronoID = null
var CronoEjecutandose = false
var decimas, segundos, minutos

function DetenerCrono (){
    if(CronoEjecutandose)
        clearTimeout(CronoID)
    CronoEjecutandose = false
}

function InicializarCrono () {
    //inicializa contadores globales
    decimas = 0
    segundos = 0
    minutos = 0

    //pone a cero los marcadores
    //document.crono.display.value = '00:00:0'
    document.getElementById('forma:display').value = '00:00:0';

}

function MostrarCrono () {

    //incrementa el crono
    decimas++
    if ( decimas > 9 ) {
        decimas = 0
        segundos++
        if ( segundos > 59 ) {
            segundos = 0
            minutos++
            if ( minutos > 99 ) {
                alert('Fin de la cuenta')
                DetenerCrono()
                return true
            }
        }
    }

    //configura la salida
    var ValorCrono = ""
    ValorCrono = (minutos < 10) ? "0" + minutos : minutos
    ValorCrono += (segundos < 10) ? ":0" + segundos : ":" + segundos
    ValorCrono += ":" + decimas

    //document.crono.display.value = ValorCrono
    document.getElementById('forma:display').value = ValorCrono;
    CronoID = setTimeout("MostrarCrono()", 100)
    CronoEjecutandose = true
    return true
}

function IniciarCrono () {
    DetenerCrono()
    InicializarCrono()
    MostrarCrono()
}


function verificar(permiso){
    if(permiso == true){
        return true;
    }else{
        alert("No tiene permisos para realizar esta Acción...");
        return false;
    }

}

function cargar(){
    try{
        deshabilitar(document.getElementById('forma:agregar'),true);
        document.getElementById('forma:agregar').disabled=false;
        document.getElementById('forma:arbol').disabled=false;
    }catch(errsor){

    }
}
  
function numeros(campo2,Rinicial,Rfinal){
    //window.alert("hola"+campo+"final"+Rfinal+"inicial"+Rinicial);
    campo = campo2.value;
    var ubicacion
    var caracteres = "1234567890."
    var contador = 0
    var flag
    for (var i=0; i < campo.length; i++) {
        ubicacion = campo.substring(i, i + 1)
        if (caracteres.indexOf(ubicacion) != -1) {
            contador++
        //flag = true;
        }else {
            //alert("ERROR: SOLO SE ACEPTAN CARACTERES NUMERICOS \n CORRIJA EL ERROR O NO PODRÁ ALMACENAR EL REGISTRO")
            flag = false;
        }
    }
    if (campo == null || campo == "" || campo==0 || flag ==false) {
        //alert("ERROR: NO SE PERMITEN CAMPOS VACIOS");
        return 0;
    }else if(campo > Rfinal || campo < Rinicial){
        alert("RANGO DE NOTA INGRESADO ESTÁ FUERA DE RANGOS: ["+Rinicial+" A "+Rfinal+"]");
        campo2.focus();
        return 0;
    }else{
        return campo;
    }

}
function numerosCero(campo){
    if (campo == null || campo == "" || campo==0) {
        return "";
    }else{
        return campo;
    }
}
function tabular(e,obj) {
    tecla=(document.all) ? e.keyCode : e.which;
    if(tecla!=13) return;
    frm=obj.form;
    for(i=0;i<frm.elements.length;i++)
        if(frm.elements[i]==obj) {
            if (i==frm.elements.length-1) i=-1;
            break
        }
    if (frm.elements[i+1].disabled ==true )
        tabular(e,frm.elements[i+1]);
    else{
        frm.elements[i+1].focus();
        frm.elements[i+1].select();
    }
    return false;
}

        