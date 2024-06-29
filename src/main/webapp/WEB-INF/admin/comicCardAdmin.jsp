<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style rel="stylesheet" href="Styles/admin/comicCard.css"></style>
<div class="comic-card">
    <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>" onclick="location.href = 'comic?isbn=<%=comic.getISBN()%>'">
    <p class="title">
        <%=comic.getTitle().length() > 20 ? comic.getTitle().substring(0, 20) + "..." : comic.getTitle() %>
    </p>
    <div class="price-section">
        <%
            if (comic.getSale() > 0) {
        %>
        <p class="sale-section">
            <span class="price">
                <%=price + " €"%>
            </span>
            <span class="sale">
                <%=comic.getSale() + "%"%>
            </span>
        </p>
        <%
            }
        %>
        <p class="final-price"><%=finalPrice + " €"%></p>
    </div>
    <button onclick="location.href = 'admin-update-comic?isbn=<%=comic.getISBN()%>'" class="button-product">MODIFICA</button>
</div>