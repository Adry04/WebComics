<%@ page import="Model.Comic" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Comic comic = (Comic) request.getAttribute("comic");
%>
<html>
<head>
    <title> Admin - Update comic</title>
    <link rel="stylesheet" href="Styles/admin/navadmin.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="Styles/admin/comic-form.css" />
</head>
<body>
    <%@include file="navadmin.jsp"%>
    <div class="container-page">
        <form method="POST" action="admin-update-comic" enctype="multipart/form-data">
            <div class="input-container">
                <div>
                    <label for="autore">Autore</label>
                    <input type="text" name="autore" id="autore" placeholder="Autore Fumetto" value="<%=comic.getAuthor()%>" required>
                </div>
                <div>
                    <label for="prezzo">Prezzo</label>
                    <input type="number" name="prezzo" id="prezzo" placeholder="Prezzo Fumetto" value="<%=comic.getPrice()%>" required min="0" step="0.01">
                </div>
                <div>
                    <label for="titolo">Titolo</label>
                    <input type="text" name="titolo" id="titolo" placeholder="Titolo Fumetto" value="<%=comic.getTitle()%>" required>
                </div>
                <div>
                    <label for="descrizione">Descrizione</label>
                    <textarea name="descrizione" id="descrizione" placeholder="Descrizione Fumetto"><%=comic.getDesc()%></textarea>
                </div>
                <div>
                    <label for="categoria">Categoria</label>
                    <select id="categoria" name="categoria">
                        <option value="shonen" <%=comic.getCategory().equals("shonen") ? "selected" : ""%>>SHONEN</option>
                        <option value="seinen" <%=comic.getCategory().equals("seinn") ? "selected" : ""%>>SEINEN</option>
                        <option value="josei" <%=comic.getCategory().equals("josei") ? "selected" : ""%>>JOSEI</option>
                        <option value="codomo" <%=comic.getCategory().equals("codomo") ? "selected" : ""%>>CODOMO</option>
                        <option value="fantasy" <%=comic.getCategory().equals("fantasy") ? "selected" : ""%>>FANTASY</option>
                        <option value="commedia" <%=comic.getCategory().equals("commedia") ? "selected" : ""%>>COMMEDIA</option>
                        <option value="supereroi" <%=comic.getCategory().equals("supereroi") ? "selected" : ""%>>SUPEREROI</option>
                        <option value="horror" <%=comic.getCategory().equals("horror") ? "selected" : ""%>>HORROR</option>
                    </select>
                </div>
                <div>
                    <label for="sconto">Sconto</label>
                    <input type="number" name="sconto" id="sconto" placeholder="Sconto Fumetto" value="<%=comic.getSale()%>">
                </div>
                <div>
                    <label for="immagine">Carica immagine</label>
                    <input type="file" name="immagine" id="immagine">
                </div>
                <input type="hidden" name="isbn" value="<%=comic.getISBN()%>">
                <p class="error-text <%=request.getAttribute("error-form") == null ? "remove-item" : ""%>"><%=request.getAttribute("error-form") != null ? request.getAttribute("error-form") : ""%></p>
                <button type="submit">Salva</button>
            </div>
        </form>
    </div>
</body>
</html>