<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title> WebComics - Login </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="Styles/form.css">
    <link rel="shortcut icon" href="assets/favicon.ico" type="image/x-icon">
</head>
<body>
    <form action="" method="POST" onsubmit="return validateForm()">
        <button class="onclick-button" onclick="location.href='./'" aria-label="Back Button" onkeydown="location.href='./'" type="button">
            <svg class="back" aria-label="Back" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path fill="#321b41" d="m2.36 11.23l8.31 7.57c.61.56 1.6.12 1.6-.71v-3.63c0-.35.29-.64.64-.64h15.86c.66 0 1.19-.53 1.19-1.19V8.42c0-.66-.53-1.19-1.19-1.19H12.91c-.35 0-.64-.29-.64-.64V2.96c0-.83-.99-1.27-1.6-.71L2.36 9.82a.946.946 0 0 0 0 1.41m13.97 17.53l-2.54-6.95c-.16-.49-.62-.81-1.13-.81c-.51 0-.97.32-1.12.79l-2.57 6.98c-.18.48.07 1 .55 1.18c.1.03.21.05.32.05c.37 0 .73-.23.86-.6l.41-1.13c.04.01.08.01.12.01h2.87c.03 0 .07 0 .1-.01l.41 1.12c.18.48.7.73 1.18.55c.47-.17.71-.7.54-1.18m-4.54-2.32l.87-2.38l.86 2.38zm-3.56-2.73c0 .53-.15 1.02-.41 1.43a2.7 2.7 0 0 1 1.07 2.15c0 1.38-1.05 2.54-2.4 2.69c-.04.01-.09.02-.13.01c-.06.01-.12.01-.18.01H2.92a.92.92 0 0 1-.92-.93v-7.16c0-.5.41-.91.92-.91h2.6c.1 0 .19 0 .27.01a7.905 7.905 0 0 0 .19.03c1.28.22 2.25 1.34 2.25 2.67m-2.7-.87H3.84v1.74h1.68c.48 0 .88-.39.88-.87a.87.87 0 0 0-.87-.87m-1.69 3.57v1.74h2.35a.87.87 0 0 0 0-1.74zm18.76 1.94a3.308 3.308 0 0 1-6.17-1.66v-2.38c0-1.83 1.48-3.31 3.31-3.31c1.18 0 2.28.64 2.87 1.65c.26.44.1 1-.33 1.26c-.44.26-1 .11-1.26-.33a1.5 1.5 0 0 0-1.28-.74c-.8 0-1.47.66-1.47 1.47v2.38c0 .81.67 1.47 1.47 1.47c.53 0 1.01-.28 1.28-.74c.26-.43.81-.59 1.26-.33c.42.26.58.82.32 1.26m4.63-3.48l2.6 3.68c.29.42.2.99-.23 1.29c-.42.29-.99.19-1.28-.22l-2.41-3.42l-.69.69v2.2c0 .51-.41.92-.92.92s-.92-.41-.92-.92v-7.17c0-.51.41-.92.92-.92s.92.41.92.92v2.37l3.01-3.02c.36-.36.94-.36 1.3 0c.36.36.36.94 0 1.3z"></path></svg>
        </button>
        <p class="title-form"> Login </p>
        <div class="input-container">
            <div>
                <label for="email-input"> Email </label>
                <input type="email" placeholder="Inserisci la tua email" id="email-input" name="email" required/>
            </div>
            <div>
                <label for="password-input"> Password </label>
                <div class="container-password">
                    <input type="password" placeholder="Inserisci la tua password" id="password-input" name="password" required/>
                    <button id="openEye" class="" onclick="displayPassword()" onkeydown="displayPassword()" tabindex="0" aria-label="Display Password" type="button">
                        <svg aria-label="Eye icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><g fill="black"><path d="M15 12a3 3 0 1 1-6 0a3 3 0 0 1 6 0"></path><path d="M21.894 11.553C19.736 7.236 15.904 5 12 5c-3.903 0-7.736 2.236-9.894 6.553a1 1 0 0 0 0 .894C4.264 16.764 8.096 19 12 19c3.903 0 7.736-2.236 9.894-6.553a1 1 0 0 0 0-.894M12 17c-2.969 0-6.002-1.62-7.87-5C5.998 8.62 9.03 7 12 7c2.969 0 6.002 1.62 7.87 5c-1.868 3.38-4.901 5-7.87 5"></path></g></svg>
                    </button>
                    <button id="closeEye" class="no-display" onclick="hidePassword()" onkeydown="hidePassword()" tabindex="0" aria-label="Hide Password" type="button">
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><g fill="none" stroke="black" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"><path d="M10.585 10.587a2 2 0 0 0 2.829 2.828"></path><path d="M16.681 16.673A8.7 8.7 0 0 1 12 18q-5.4 0-9-6q1.908-3.18 4.32-4.674m2.86-1.146A9 9 0 0 1 12 6q5.4 0 9 6q-1 1.665-2.138 2.87M3 3l18 18"></path></g></svg>
                    </button>
                </div>
            </div>
            <p class="error-text <%=request.getAttribute("error") == null ? "remove-item" : ""%>"><%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%></p>
            <button type="submit"> Login </button>
            <div class="link-container">
                <p> Non hai ancora un account? </p>
                <a href="registration"> Registrati </a>
            </div>
        </div>
    </form>
    <script src="js/formValidation.js"></script>
    <script src="js/form.js"></script>
</body>
</html>