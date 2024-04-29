<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <jsp:include page="/header"/>
</head>
<body>
  <jsp:include page="/menu"/>
  <div class="container">
    <div class="alert alert-danger" role="alert">
      <h3>Error</h3>
      <p>Please, contact with support.</p>
      <p>&nbsp;</p>
      <div class="well well-sm" style="margin-bottom: 0;">
        <p><strong>Path:</strong> ${path}</p>
        <p><strong>Timestamp:</strong> ${timestamp}</p>
        <p><strong>Exception:</strong> ${exception}</p>
        <p><strong>Message:</strong> ${message}</p>
        <!--
          Path: ${path}
          Timestamp: ${timestamp}
          Message: ${message}
          <%--
          <c:forEach items="${exception.stackTrace}" var="st">
            ${st}
          </c:forEach>
          --%>
        -->
      </div>
    </div>
  </div>
</body>
</html>
