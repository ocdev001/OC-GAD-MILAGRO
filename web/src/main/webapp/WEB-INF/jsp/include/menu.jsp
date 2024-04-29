<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cloudinary.*" %>
<%@ page import="com.cloudinary.utils.*" %>
<%@ page import="com.cloudinary.utils.ObjectUtils" %>

  <div class="container-fluid" style="background-color: #FFFFFF !important; height: 150px; !important;">
    <div class="navbar-header" id="ps-header" style="padding: 0; display: flex; align-items: center;"> <!-- Flexbox para mantener alineación -->
      <a class="navbar-brand" href="<c:url value="/"/>" style="padding: 0; display: flex; align-items: center;">
        <img src="${pageContext.request.contextPath}/static/images/logo.png"
             style="vertical-align: middle;"> <!-- Elimina inline-block y usa flex -->
        <span class="visible-lg visible-md"
              style="margin-left: 10px; line-height: 40px; font-size: 30px; font-weight: bold;">
           <%= (request.getParameter("title") != null) ? request.getParameter("title") : "BIBLIOTECA DIGITAL" %>
        </span>
      </a>
    </div>
  </div>
