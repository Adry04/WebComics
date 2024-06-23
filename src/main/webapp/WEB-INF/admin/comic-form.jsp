<html>
<head>
    <link rel="stylesheet" href="Styles/admin/comic-form.css">
</head>
<body>
    <div class="container-page">
        <%@include file="navadmin.jsp"%>
        <form action="" method="POST">
            <h1>Crea un nuovo fumetto</h1>
            <div class="input-container">
                <div>
                    <label for="ISBN-input"> ISBN </label>
                    <input type="text" placeholder="Inserisci l'isbn del fumetto" id="ISBN-input" name="ISBN" required>
                </div>
                <div>
                    <label for="autore-input"> Autore </label>
                    <input type="text" placeholder="Inserisci nome e cognome dell'autore" id="autore-input" name="autore" required>
                </div>
                <div>
                    <label for="prezzo-input"> Prezzo </label>
                    <input type="number" placeholder="Inserisci il prezzo del fumetto" id="prezzo-input" name="prezzo" required>
                </div>
                <div>
                    <label for="titolo-input"> Titolo </label>
                    <input type="text" placeholder="Inserisci il titolo del fumetto" id="titolo-input" name="titolo" required>
                </div>
                <div>
                    <label for="desc-input"> Descrizione </label>
                    <input type="text" placeholder="Inserisci la descrizione del fumetto" id="desc-input" name="desc" required>
                </div>
                <div>
                    <label for="catId-input"> Categoria </label>
                    <select id="catId-input">
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
                    <input type="text" placeholder="Inserisci lo sconto del fumetto" id="sconto-input" name="sconto">
                </div>
                <button type="submit">Aggiungi il nuovo fumetto</button>
            </div>
        </form>
    </div>
</body>
</html>