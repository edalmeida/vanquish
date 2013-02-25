<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>


  <form action="/load" method="post">
    <div>Load Time (ms)<textarea name="time" rows="3" cols="60"></textarea></div>
    <div>Timed Bulk NewOrder Load<input type="submit" value="Go" /></div>
    <input type="hidden" name="itemName" value="${fn:escapeXml(productName)}"/>
  </form>
    <form action="/load" method="post">
    <div>Load Size (transactions)<textarea name="trans" rows="3" cols="60"></textarea></div>
    <div>Sized Bulk NewOrder Load<input type="submit" value="Go" /></div>
    <input type="hidden" name="itemName" value="${fn:escapeXml(productName)}"/>
  </form>
  <form action="/gdsbench" method="post">
    <div>Single NewOrder Transaction<input type="submit" value="Go" /></div>
  </form>
  <form action="/clean" method="post">
    <div>Truncate Database<input type="submit" value="Go" /></div>
  </form>
  <form action="/stat" method="post">
    <div>Get Statistics<input type="submit" value="Go" /></div>
  </form>
  <form action="/model" method="post">
    <div>New Model<input type="submit" value="Go" /></div>
  </form>

  
  
  </body>
</html>