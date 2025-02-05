<!DOCTYPE html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" uri="http://openkm.com/tags/utils" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="es">
<head>
  <jsp:include page="/header"/>
  <style type="text/css">
    /* For select picker */
    .bootstrap-select {
      width: 115px !important;
    }
  </style>
  <script type="text/javascript">


    function showPreview() {
      document.getElementById('preview-doc').style.display = 'inline';
      calculatePreviewHeight(); // Redimensionar el iframe de la vista previa
    }



    // Funcipara calcular la altura del iframe de la vista previa
    function calculatePreviewHeight() {
      var position = $("#preview-doc").offset();
      $("#preview-doc").height($(window).height() - (position.top + 27));
    }

    // Llamar a la funcide redimensionamiento al cargar y redimensionar la ventana
    window.onload = function() {
      showPreview(); // Mostrar la vista previa por defecto al cargar la p
    };
    window.onresize = function() {
      if (document.getElementById('preview-doc').style.display === 'inline') {
        calculatePreviewHeight();
      }
    };

    function changePreview(uuid) {
      $.get('${pageContext.request.contextPath}/propertyGroup/getPreviewURL',
              {
                uuid: uuid
              },
              function (data, status, xhr) {
                if (status === "success") {
                  $('#preview-doc').attr('src', data);
                }

              }
      );
    }
    // Función para limpiar la tabla
    function clearTableBrowser() {
      const table = document.getElementById("documentTable");
      if (table) {
        const tbody = table.querySelector("tbody");
        if (tbody) {
          tbody.innerHTML = ""; // Borra el contenido del cuerpo de la tabla
        }
      }
    }

    // Función para limpiar el contenido del iframe de vista previa
    function clearPreviewBrowser() {
      const previewFrame = document.getElementById("preview-doc");
      if (previewFrame) {
        previewFrame.src = ""; // Borra la URL del iframe para limpiar la vista previa
      }
    }

    // Función que llama a ambas acciones de limpieza
    function cleanAllBrowser() {
      clearTableBrowser(); // Limpiar la tabla
      clearPreviewBrowser(); // Limpiar la vista previa
    }


    $(document).ready(function () {

      var pagination = new Pagination('#documentTable', 10);
      pagination.generatePagination();
      pagination.handlePaginationClicks();
      const searchButton = $('#searchButton');
      const inputFields = $('#chgPrjForm input.form-control'); // Todos los campos de entrada en el formulario

      // Deshabilitar el botón de búsqueda al inicio
      searchButton.prop('disabled', true);

      // Función para comprobar si hay al menos un campo con texto
      function checkFields() {
        let hasContent = false;

        inputFields.each(function() {
          if ($(this).val().trim() !== '') {
            hasContent = true; // Al menos un campo tiene valor
          }
        });

        // Habilitar o deshabilitar el botón según el estado de los campos
        searchButton.prop('disabled', !hasContent);
      }

      // Verificar los campos al iniciar
      checkFields();

      // Evento para cada cambio en los campos de entrada
      inputFields.on('input', checkFields);


      $('#searchButton').on('click', function () {
        $("#searchButton").html('Searching...');
        $("#chgPrjForm").submit();
      });

    });
  </script>
</head>

<body class="body-index">
<jsp:include page="/menu">
  <jsp:param name="title" value="BIBLIOTECA INTERNA" />
</jsp:include>

<div class="container-fluid" style="background-color:#f1f1f1; padding: 0; margin-top: -17px; margin-left:10px !important;">
  <div class="row"> <!-- Añadimos fila para la distribución -->
    <div class="col-sm-6"> <!-- Sección izquierda con tabla y pestañas -->
      <form role="form" id="chgPrjForm"
            action="${pageContext.request.contextPath}/searchInternal/find" method="post"
            class="col-md-12 form-background" style="margin:10px 0px 10px 0px; background-color: rgb(225,225,225) !important;">

        <!-- Los elementos del formulario están alineados verticalmente -->
        <div class="form-group row" style="padding: 10px 5px;">
          <!-- El label y el input están en la misma línea -->
          <label class="col-sm-2 col-form-label label-style">AUTOR:</label>
          <div class="col-sm-6">
            <input class="form-control" name="author" id="Author">
          </div>
        </div>

        <div class="form-group row" style="padding: 10px 5px;">
          <label class="col-sm-2 col-form-label label-style">TITULO:</label>
          <div class="col-sm-6">
            <input class="form-control" name="title" id="Title">
          </div>
        </div>

        <div class="form-group row" style="padding: 10px 5px;">
          <label class="col-sm-2 col-form-label label-style">EDICIÓN:</label>
          <div class="col-sm-6">
            <input class="form-control" name="edit" id="Edit">
          </div>
        </div>

        <div class="form-group row align-items-center" style="padding: 10px 5px;">
          <label class="col-sm-2 col-form-label label-style">AÑO:</label>
          <div class="col-sm-6">
            <input class="form-control" name="year" id="Year">
          </div>
        </div>
        <!-- Mantenemos la sección para errores -->
        <div class="help-block with-errors"></div>
      </form>
        <div class="form-group" style="padding: 10px 0px 0px 15px;">
          <button id="searchButton" type="button">
            BUSCAR
          </button>
          <button id="cleanButton" type="button" onclick="cleanAllBrowser()">
            LIMPIAR
          </button>
        </div>


      <div class="col-sm-12" style="margin:0; padding:0;">
        <table id="documentTable" class="table table-striped" style="font-size: 13px;">
          <thead>
          <tr class="info">
            <th>Título</th>
            <th>Tamaño</th>
            <th>Autor</th>
            <th>Edición</th>
            <th>Año</th>
            <th>Acción</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="documentsInfo" items="${documentsInfo}">
            <tr>
              <td><a href="#" onclick="javascript:changePreview('${documentsInfo.uuid}');">${documentsInfo.shortenFileName}</a></td>
              <td>${documentsInfo.size} KB</td>
              <td>${documentsInfo.author}</td>
              <td>${documentsInfo.edit}</td>
              <td>${documentsInfo.year}</td>
              <td>
                <a href="<c:url value='/download?node=${documentsInfo.uuid}&attachment=false' />"
                   class="btn btn-default" role="button" style="font-size:10px; height:26px;">Descargar</a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    <div class="text-right">
      <ul class="pagination" id="pagination"></ul>
    </div>
  </div>
    <div class="col-sm-6" style="padding: 10px 2px 2px 2px; background-color: #808080;">
      <iframe id="preview-doc" style="width: 100%; border: 0px; height: 97%; display: inline; background-color: white;" src="${previewUrl}"></iframe>
    </div>
</div>
</div>
<jsp:include page="/footer"/>
</body>
</html>