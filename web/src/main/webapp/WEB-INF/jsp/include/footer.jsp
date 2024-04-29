<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- footer -->
<footer class="footer">
  <div class="container">
    <p class="text-muted" style="margin-bottom: 0">&copy; OpenKM External Search v${git.buildVersion} (Build: ${git.commitId}). All Rights Reserved.</p>
  </div>
</footer>

<script>
  // Need to set this vars here to be used in commons.js
  const selectedLocale = '<%=RequestContextUtils.getLocaleResolver(request).resolveLocale(request)%>';
</script>

<script src="<c:url value='/static/js/commons.js'/>"></script>
