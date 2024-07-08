<%@ page import="Model.ComicDAO" %>
<%@ page import="Model.Comic" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.CartDAO" %>
<%@ page import="Model.Cart" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    int sizeWishes = 0;
    int sizeCartComics = 0;
    List<Comic> wishComics = new ArrayList<>();
    List<Comic> cartComics = new ArrayList<>();
    Cart cart = new Cart();
    if(session.getAttribute("userId") != null) {
      wishComics = ComicDAO.getWishes((int) session.getAttribute("userId"));
      sizeWishes = wishComics != null ? wishComics.size() : 0;
    }
    if (session.getAttribute("cart") != null) {
      cart = (Cart) session.getAttribute("cart");
      System.out.println("Arrivo dentro");
      System.out.println(cart.getComics());
      cartComics = cart.getComics();
      sizeCartComics = cart.getTotalQuantity();
    } else if (session.getAttribute("userId") != null) {
      cart = CartDAO.getCart((int) session.getAttribute("userId"));
      cartComics = Objects.requireNonNull(cart).getComics();
      sizeCartComics = cart.getTotalQuantity();
    }
%>
<div class="top-nav">
    <p class="title-top-nav">
      <a href="service">
        SERVIZIO CLIENTI
      </a>
    </p>
    <div class="link-top-nav">
      <div class="link-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="1.5em" height="1.5em" viewBox="0 0 24 24"><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M5 20v-1a7 7 0 0 1 7-7v0a7 7 0 0 1 7 7v1m-7-8a4 4 0 1 0 0-8a4 4 0 0 0 0 8"></path></svg>
        <p>
          <a href=<%=session.getAttribute("nome") != null ? "account" : "login"%>>
            <%=session.getAttribute("nome") != null ? session.getAttribute("nome") : "ACCEDI" %>
          </a>
        </p>
      </div>
      <div class="link-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="1.5em" height="1.5em" viewBox="0 0 256 256"><path fill="currentColor" d="M178 40c-20.65 0-38.73 8.88-50 23.89C116.73 48.88 98.65 40 78 40a62.07 62.07 0 0 0-62 62c0 70 103.79 126.66 108.21 129a8 8 0 0 0 7.58 0C136.21 228.66 240 172 240 102a62.07 62.07 0 0 0-62-62m-50 174.8c-18.26-10.64-96-59.11-96-112.8a46.06 46.06 0 0 1 46-46c19.45 0 35.78 10.36 42.6 27a8 8 0 0 0 14.8 0c6.82-16.67 23.15-27 42.6-27a46.06 46.06 0 0 1 46 46c0 53.61-77.76 102.15-96 112.8"></path></svg>
        <p>
          <a href="wishlist">
            LISTA DEI DESIDERI: <span data-wishes="<%=sizeWishes%>" id="counter-wishes"><%=sizeWishes%></span>
          </a>
        </p>
      </div>
      <div class="link-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="1.5em" height="1.5em" viewBox="0 0 512 512"><circle cx="176" cy="416" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><circle cx="400" cy="416" r="16" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32"></circle><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M48 80h64l48 272h256"></path><path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="32" d="M160 288h249.44a8 8 0 0 0 7.85-6.43l28.8-144a8 8 0 0 0-7.85-9.57H128"></path></svg>
        <p>
          <a href="cart">
            CARRELLO: <span data-carts="<%=sizeCartComics%>" id="counter-carts"><%=sizeCartComics%></span>
          </a>

        </p>
      </div>
    </div>
</div>
<nav>
    <a href="./">
      <img src="assets/logo.png" alt="Logo WebComics">
    </a>
    <div class="section-container">
      <ul>
        <li id="category-manga">
          <a>MANGA
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" d="M11.178 19.569a.998.998 0 0 0 1.644 0l9-13A.999.999 0 0 0 21 5H3a1.002 1.002 0 0 0-.822 1.569z"></path></svg>
          </a>
          <div id="list-category-manga" class="subcontainer">
            <p onclick="location.href = ''">NUOVE USCITE</p>
            <p onclick="location.href = ''">I PIU' LETTI</p>
            <p onclick="location.href = 'search?parameter=shonen'">SHONEN</p>
            <p onclick="location.href = 'search?parameter=shinen'">SEINEN</p>
            <p onclick="location.href = 'search?parameter=josei'">JOSEI</p>
            <p onclick="location.href = 'search?parameter=comodo'">CODOMO</p>
          </div>
        </li>
        <li id="category-fumetti">
          <a>FUMETTI
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" d="M11.178 19.569a.998.998 0 0 0 1.644 0l9-13A.999.999 0 0 0 21 5H3a1.002 1.002 0 0 0-.822 1.569z"></path></svg>
          </a>
          <div id="list-category-fumetti" class="subcontainer">
            <p onclick="location.href = ''">NUOVE USCITE</p>
            <p onclick="location.href = ''">I PIU' LETTI</p>
            <p onclick="location.href = 'search?parameter=fantasy'">FANTASY</p>
            <p onclick="location.href = 'search?parameter=commedia'">COMMEDIA</p>
            <p onclick="location.href = 'search?parameter=supereroi'">SUPEREROI</p>
            <p onclick="location.href = 'search?parameter=horror'">HORROR</p>
          </div>
        </li>
      </ul>
    </div>
    <div class="search-container" id="search-container" onmouseleave="hideSearchBar()">
        <div onclick="displaySearchBar()">
            CERCA
            <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" d="m19.6 21l-6.3-6.3q-.75.6-1.725.95T9.5 16q-2.725 0-4.612-1.888T3 9.5t1.888-4.612T9.5 3t4.613 1.888T16 9.5q0 1.1-.35 2.075T14.7 13.3l6.3 6.3zM9.5 14q1.875 0 3.188-1.312T14 9.5t-1.312-3.187T9.5 5T6.313 6.313T5 9.5t1.313 3.188T9.5 14"></path></svg>
        </div>
    <form method="GET" action="search" id="form-search-bar" class="">
        <input type="text" name="parameter" placeholder="Cerca Prodotto" id="input-search-bar">
        <button type="submit">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path fill="black" d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5A6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5S14 7.01 14 9.5S11.99 14 9.5 14"/></svg>
        </button>
    </form>
    </div>
</nav>
  <div id="check-box" class="">
    <p id="check-box-text"></p>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path fill="white" d="m9 20.42l-6.21-6.21l2.83-2.83L9 14.77l9.88-9.89l2.83 2.83z"></path></svg>
  </div>
  <div id="check-error-box" class="">
    <p id="check-error-box-text"></p>
    <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 14 14"><path fill="white" fill-rule="evenodd" d="M1.707.293A1 1 0 0 0 .293 1.707L5.586 7L.293 12.293a1 1 0 1 0 1.414 1.414L7 8.414l5.293 5.293a1 1 0 0 0 1.414-1.414L8.414 7l5.293-5.293A1 1 0 0 0 12.293.293L7 5.586z" clip-rule="evenodd"></path></svg>
  </div>