
// Update locale
$('#selectLocale').val(selectedLocale);
$('#selectLocale').on('change', function() {
  window.location = window.location.origin + window.location.pathname + "?lang=" + this.value;  
});