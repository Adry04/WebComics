<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style rel="stylesheet" href="Styles/admin/comicCard.css"></style>
<div class="comic-card" id="comic-card-<%=comic.getISBN()%>">
    <button onclick="location.href = 'comic?isbn=<%=comic.getISBN()%>'" aria-label="Immagine Fumetto" tabindex="0" onkeydown="location.href = 'comic?isbn=<%=comic.getISBN()%>'">
        <img src="<%=comic.getImmagine()%>" alt="<%=comic.getTitle()%>">
    </button>
    <p class="title">
        <%=comic.getTitle() %>
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
    <button onclick="location.href = 'admin-update-comic?isbn=<%=comic.getISBN()%>'" class="button-product" aria-label="Modifica" tabindex="0" onkeydown="location.href = 'admin-update-comic?isbn=<%=comic.getISBN()%>'">MODIFICA</button>
    <button class="trash-button" onclick="deleteComic('<%=comic.getISBN()%>')" aria-label="Delete Comic" tabindex="0" onkeydown="deleteComic('<%=comic.getISBN()%>')">
        <div class="trash-container">
            <svg aria-label="Thrash" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path fill="#AA5050" d="M20 6a1 1 0 0 1 .117 1.993L20 8h-.081L19 19a3 3 0 0 1-2.824 2.995L16 22H8c-1.598 0-2.904-1.249-2.992-2.75l-.005-.167L4.08 8H4a1 1 0 0 1-.117-1.993L4 6zm-6-4a2 2 0 0 1 2 2a1 1 0 0 1-1.993.117L14 4h-4l-.007.117A1 1 0 0 1 8 4a2 2 0 0 1 1.85-1.995L10 2z"></path></svg>
        </div>
    </button>
</div>
<script src="js/admin/comicCard.js"></script>