<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Gson gson = new Gson();
    String comicJson = gson.toJson(comic);
%>
<div class="card-container" id="cart-card-comic-<%=comic.getISBN()%>">
    <img onclick="location.href = 'comic?isbn=<%=comic.getISBN()%>'" src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
    <div class="text-section">
        <div>
            <h1><%=comic.getTitle()%></h1>
            <p id="desc"><%=comic.getDesc().length() > 800 ? comic.getDesc().substring(0, 800) + "..." : comic.getDesc()%></p>
        </div>
    </div>
    <div class="operation-section">
        <div class="separation">
            <div class="quantity-section">
                <span onclick='decrementQuantity(<%=comic.getISBN()%>, <%=comicJson%>, <%=comic.getFinalPrice()%>)' class="click-quantity">-</span>
                <span data-quantity="<%=cart.getQuantity(comic.getISBN())%>" id="quantity-cart-<%=comic.getISBN()%>"><%=cart.getQuantity(comic.getISBN())%></span>
                <span onclick='incrementQuantity(<%=comic.getISBN()%>, <%=comicJson%>, <%=comic.getFinalPrice()%>)' class="click-quantity">+</span>
            </div>
        </div>
        <div class="separation">
            <button onclick='remove(<%=comic.getISBN()%>, <%=comicJson%>, <%=cart.getQuantity(comic.getISBN())%>, <%=finalPrice%>)'>Rimuovi</button>
        </div>
    </div>
    <div class="price">
        <p><%=finalPrice%> €</p>
    </div>
</div>