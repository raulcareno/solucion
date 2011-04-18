function applyCelular(compId){
   applyMask(compId, "999-999-999")
}

function applyTelefono(compId){
   // alert("HOLA");
   applyMask(compId, "9999-999");
}

function applyMask(compId, mask){
   compId = '#' + compId;
   jQuery(compId).mask(mask);
 // jQuery(compId).Watermark(compId,"#369");
   //$("#suffix").Watermark("Suffix","#369");
}
function animar(compId){
   compId = '#' + compId;

   jQuery(compId).animar();
    
   //$("#suffix").Watermark("Suffix","#369");
}