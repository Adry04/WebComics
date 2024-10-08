<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/admin/comic-form.css">
    <title>Admin Page - <%=session.getAttribute("nome")%></title>
    <link rel="stylesheet" href="Styles/admin/navadmin.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <%@include file="navadmin.jsp"%>
    <div class="container-page">
        <form action="" method="POST" onsubmit="return validateComic()" enctype="multipart/form-data">
            <h1>Crea un nuovo fumetto</h1>
            <div class="input-container">
                <div>
                    <label for="ISBN-input"> ISBN </label>
                    <input type="text" placeholder="Inserisci l'isbn del fumetto" id="ISBN-input" name="ISBN" required maxlength=13>
                </div>
                <div>
                    <label for="autore-input"> Autore </label>
                    <input type="text" placeholder="Inserisci nome e cognome dell'autore" id="autore-input" name="autore" required>
                </div>
                <div>
                    <label for="prezzo-input"> Prezzo </label>
                    <input type="number" placeholder="Inserisci il prezzo del fumetto" id="prezzo-input" name="prezzo" required min="0" step="0.01">
                </div>
                <div>
                    <label for="titolo-input"> Titolo </label>
                    <input type="text" placeholder="Inserisci il titolo del fumetto" id="titolo-input" name="titolo" required>
                </div>
                <div>
                    <label for="desc-input"> Descrizione </label>
                    <textarea placeholder="Inserisci la descrizione del fumetto" id="desc-input" name="descrizione" required></textarea>
                </div>
                <div>
                    <label for="catId-input"> Categoria </label>
                    <select id="catId-input" name="categoria">
                        <option value="shonen">SHONEN</option>
                        <option value="seinen">SEINEN</option>
                        <option value="josei">JOSEI</option>
                        <option value="codomo">CODOMO</option>
                        <option value="fantasy">FANTASY</option>
                        <option value="commedia">COMMEDIA</option>
                        <option value="supereroi">SUPEREROI</option>
                        <option value="horror">HORROR</option>
                    </select>
                </div>
                <div>
                    <label for="sconto-input"> Sconto </label>
                    <input type="number" placeholder="Inserisci lo sconto del fumetto" id="sconto-input" name="sconto" min="0" step="1">
                </div>
                <div>
                    <label for="image-input">Carica immagine</label>
                    <input type="file" name="image" id="image-input" required>
                </div>
                <p class="error-text <%=request.getAttribute("error-form") == null ? "remove-item" : ""%>"><%=request.getAttribute("error-form") != null ? request.getAttribute("error-form") : ""%></p>
                <button type="submit">Aggiungi il nuovo fumetto</button>
            </div>
        </form>
    </div>
    <script src="js/comicValidation.js"></script>
    <script src="js/form.js"></script>
</body>
</html>