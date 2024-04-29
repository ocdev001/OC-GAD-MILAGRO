<%@ taglib prefix="o" uri="http://openkm.com/tags/utils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cloudinary.*" %>
<%@ page import="com.cloudinary.utils.*" %>
<%@ page import="com.cloudinary.utils.ObjectUtils" %>


<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/header"/>
  <script type="text/javascript">
    $(document).ready(function () {

      const searchButton = $('#searchButtonIndex');
      const contentInput = $('#Content');
      const warningMessage = $('#warningMessage');

      // Deshabilitar el botón de búsqueda al inicio
      searchButton.prop('disabled', true);

      // Evento cuando se escribe en el campo de entrada
      contentInput.on('input', function() {
        const textLength = $(this).val().length;
        const minCharacters = 3;

        // Habilitar o deshabilitar el botón según la longitud del texto
        searchButton.prop('disabled', textLength < minCharacters);

        // Mostrar u ocultar el mensaje de advertencia
        if (textLength < minCharacters) {
          warningMessage.show();
        } else {
          warningMessage.hide();
        }
      });

      // Evento cuando se escribe en el campo de entrada
      $('#Content').on('input', function() {
        const text = $(this).val(); // Obtener el valor del campo de entrada
        // Habilitar el botón si el texto tiene al menos 3 letras, de lo contrario, deshabilitar
        $('#searchButtonIndex').prop('disabled', text.length < 3);
      });
      $('#searchButtonIndex').on('click', function () {
        if (contentInput.val().length < 3) {
          warningMessage.show(); // Asegurarse de que el mensaje de advertencia esté visible
        }else{
          $("#searchButtonIndex").html('Searching...');
          $("#srcIndexForm1").submit();
        }
      });

      $('#searchExternalButtonIndex').on('click', function () {
        $("#searchExternalButtonIndex").html('Searching...');
        $("#srcIndexForm2").submit();
      });

      $('#cleanButton').on('click', function () {
        $(location).attr('href', "${pageContext.request.contextPath}/browser/");
      });
    });
  </script>
</head>
<jsp:include page="/menu">
  <jsp:param name="title" value="BIBLIOTECA DIGITAL" />
</jsp:include>
<body class="body-index" style="background-image: url('${pageContext.request.contextPath}/static/images/background.png');">
<div class="container-fluid">
  <div class="row">
    <!-- Jumbotron izquierda con margen variable -->
    <div class="col-md-4 col-sm-6" style="margin-top: 200px; margin-left: 50px;"> <!-- Márgenes adicionales para dispositivos más grandes -->
      <div class="jumbotron text-left" style="background-color: rgba(196, 166, 55, 0.8);border: 4px solid #1D6B9E; border-radius: 30px; box-shadow: 20px 20px rgba(227, 228, 230,0.8) ;"> <!-- Fondo amarillo con transparencia -->
        <h2 style="text-align: center; color: #3c763d; font-weight: bold;">BIBLIOTECA EXTERNA</h2>
        <form role="form" id="srcIndexForm2" action="${pageContext.request.contextPath}/searchExternal/" method="post" class="form-background" style="border-radius: 20px">
          <select id="selectExternal" name="selectExternal" class="form-control" required style="border-radius: 20px">
            <option value="">Elija...</option>
            <c:forEach var="varokg" items="${selectOptions}">
              <c:set var="varokgvalue" value="${varokg.valueName}"/>
              <option value="${varokgvalue}">${varokg.labelName}</option>
            </c:forEach>
          </select>
        </form>
        <div class="form-group" style="margin-top: 20px; text-align: center;">
          <button id="searchExternalButtonIndex" type="button" class="btn">
            BUSCAR
          </button>
        </div>
      </div>
    </div>

    <!-- Columna vacía para espaciado -->
    <div class="col-md-2 d-none d-md-block" style="margin-left: 140px;" > <!-- Visible solo en dispositivos medianos o más grandes -->
      <!-- Espacio extra entre jumbotrons -->
    </div>

    <!-- Jumbotron derecha con margen variable -->
    <div class="col-md-4 col-sm-6" style="margin-top: 200px; margin-left: 50px;"> <!-- Márgenes adicionales para dispositivos más grandes -->
      <div class="jumbotron text-left" style="background-color: rgba(196, 166, 55, 0.8); border: 4px solid #1D6B9E; border-radius: 30px; box-shadow: 20px 20px rgba(227, 228, 230, 0.8);"> <!-- Fondo amarillo con transparencia -->
        <h2 style="text-align: center; color: #3c763d; font-weight: bold;">BIBLIOTECA INTERNA</h2>
        <form role="form" id="srcIndexForm1" action="${pageContext.request.contextPath}/searchInternal/findContent" method="post" class="form-background" style="border-radius: 20px">
          <div class="form-group">
            <input class="form-control" name="content" id="Content" style="border-radius: 20px">
          </div>
        </form>
        <div class="form-group" style="text-align: center;">
          <button id="searchButtonIndex" type="button" class="btn" >
            BUSCAR
          </button>
        </div>
        <div class="flex-container">
          <a href="${pageContext.request.contextPath}/searchInternal/" class="advanced-search">Búsqueda Avanzada</a>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
<jsp:include page="/footer"/>
</html>
