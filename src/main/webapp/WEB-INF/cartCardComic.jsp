<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Gson gson = new Gson();
    String comicJson = gson.toJson(comic);
%>
<div class="card-container" id="cart-card-comic-<%=comic.getISBN()%>">
    <button class="onclick-button image-button" onclick="location.href = 'comic?isbn=<%=comic.getISBN()%>'" aria-label="Image Clickable" onkeydown="location.href = 'comic?isbn=<%=comic.getISBN()%>'">
        <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
    </button>
    <div class="text-section">
        <div>
            <h1><%=comic.getTitle()%></h1>
            <p id="desc-<%=comic.getISBN()%>" class="desc"><%=comic.getDesc()%></p>
        </div>
        <div class="price">
            <p><%=finalPrice%> €</p>
        </div>
    </div>
    <div class="operation-section">
        <div class="separation">
            <div class="quantity-section">
                <button class="click-quantity onclick-button" onclick='decrementQuantity("<%=comic.getISBN()%>", <%=comicJson%>, <%=comic.getFinalPrice()%>)' aria-label="Decrement Quantity" onkeydown='decrementQuantity(<%=comic.getISBN()%>, <%=comicJson%>, <%=comic.getFinalPrice()%>)'>
                    -
                </button>
                <span data-quantity="<%=cart.getQuantity(comic.getISBN())%>" id="quantity-cart-<%=comic.getISBN()%>"><%=cart.getQuantity(comic.getISBN())%></span>
                <button class="click-quantity onclick-button" onclick='incrementQuantity("<%=comic.getISBN()%>", <%=comicJson%>, <%=comic.getFinalPrice()%>)' aria-label="Increment Quantity" onkeydown='incrementQuantity(<%=comic.getISBN()%>, <%=comicJson%>, <%=comic.getFinalPrice()%>)'>
                    +
                </button>
            </div>
        </div>
        <div class="separation">
            <button onclick='remove("<%=comic.getISBN()%>", <%=comicJson%>, <%=finalPrice%>)'>Rimuovi</button>
        </div>
    </div>
</div>