      function applyZipCodeMask(compId){
        applyMask(compId, "99.999-999")
      }

      function applyPhoneMask(compId){
          applyMask(compId, "999-999-999");
      }

      function applyMask(compId, mask){
          compId = '#' + compId;
         jQuery(compId).mask(mask);
      }
      function aparecer(compId){
         compId = '#' + compId;
         jQuery(compId).hide().fadeIn();

      }