      function applyZipCodeMask(compId){
          alert("hola");
        applyMask(compId, "99.999-999")
      }

      function applyPhoneMask(compId){
          alert("hola");
         applyMask(compId, "(99) 9999-9999");
      }

      function applyMask(compId, mask){
          alert("hola");
         compId = '#' + compId;
         jQuery(compId).mask(mask);
      }