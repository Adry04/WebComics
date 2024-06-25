<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title> WebComics - Registrazione </title>
    <link rel="stylesheet" href="Styles/form.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <form action="" method="POST" style="margin: 5em 0" onsubmit="return validateForm()">
        <svg onclick="location.href='./'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path fill="#321b41" d="m2.36 11.23l8.31 7.57c.61.56 1.6.12 1.6-.71v-3.63c0-.35.29-.64.64-.64h15.86c.66 0 1.19-.53 1.19-1.19V8.42c0-.66-.53-1.19-1.19-1.19H12.91c-.35 0-.64-.29-.64-.64V2.96c0-.83-.99-1.27-1.6-.71L2.36 9.82a.946.946 0 0 0 0 1.41m13.97 17.53l-2.54-6.95c-.16-.49-.62-.81-1.13-.81c-.51 0-.97.32-1.12.79l-2.57 6.98c-.18.48.07 1 .55 1.18c.1.03.21.05.32.05c.37 0 .73-.23.86-.6l.41-1.13c.04.01.08.01.12.01h2.87c.03 0 .07 0 .1-.01l.41 1.12c.18.48.7.73 1.18.55c.47-.17.71-.7.54-1.18m-4.54-2.32l.87-2.38l.86 2.38zm-3.56-2.73c0 .53-.15 1.02-.41 1.43a2.7 2.7 0 0 1 1.07 2.15c0 1.38-1.05 2.54-2.4 2.69c-.04.01-.09.02-.13.01c-.06.01-.12.01-.18.01H2.92a.92.92 0 0 1-.92-.93v-7.16c0-.5.41-.91.92-.91h2.6c.1 0 .19 0 .27.01a7.905 7.905 0 0 0 .19.03c1.28.22 2.25 1.34 2.25 2.67m-2.7-.87H3.84v1.74h1.68c.48 0 .88-.39.88-.87a.87.87 0 0 0-.87-.87m-1.69 3.57v1.74h2.35a.87.87 0 0 0 0-1.74zm18.76 1.94a3.308 3.308 0 0 1-6.17-1.66v-2.38c0-1.83 1.48-3.31 3.31-3.31c1.18 0 2.28.64 2.87 1.65c.26.44.1 1-.33 1.26c-.44.26-1 .11-1.26-.33a1.5 1.5 0 0 0-1.28-.74c-.8 0-1.47.66-1.47 1.47v2.38c0 .81.67 1.47 1.47 1.47c.53 0 1.01-.28 1.28-.74c.26-.43.81-.59 1.26-.33c.42.26.58.82.32 1.26m4.63-3.48l2.6 3.68c.29.42.2.99-.23 1.29c-.42.29-.99.19-1.28-.22l-2.41-3.42l-.69.69v2.2c0 .51-.41.92-.92.92s-.92-.41-.92-.92v-7.17c0-.51.41-.92.92-.92s.92.41.92.92v2.37l3.01-3.02c.36-.36.94-.36 1.3 0c.36.36.36.94 0 1.3z"></path></svg>
        <p class="title-form"> Registrazione </p>
        <div class="input-container">
            <div>
                <label for="first-name-input"> Nome </label>
                <input type="text" placeholder="Inserisci il tuo nome" id="first-name-input" name="firstName" required>
            </div>
            <div>
                <label for="last-name-input"> Cognome </label>
                <input type="text" placeholder="Inserisci il tuo cognome" id="last-name-input" name="lastName" required>
            </div>
            <div>
                <label for="email-input"> Email </label>
                <input type="email" placeholder="Inserisci la tua email" id="email-input" name="email" required>
            </div>
            <div>
                <label for="password-input"> Password </label>
                <input type="password" placeholder="Inserisci la tua password" id="password-input" name="password" required>
            </div>
            <div>
                <label for="password-confirm"> Conferma Password </label>
                <input type="password" placeholder="Inserisci la tua password" id="password-confirm" name="passwordConfirm" required>
            </div>
            <p class="error-text <%=request.getAttribute("error") == null ? "remove-item" : ""%>"><%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></p>
            <button type="submit"> Registrati </button>
            <div class="link-container">
                <p> Hai già un account? </p>
                <a href="login"> Accedi </a>
            </div>
        </div>
    </form>
    <script src="js/formValidation.js"></script>
</body>
</html>