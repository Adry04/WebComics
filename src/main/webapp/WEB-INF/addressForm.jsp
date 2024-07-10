<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> Aggiunta indirizzi di spedizione - <%=session.getAttribute("nome")%> </title>
    <link rel="stylesheet" href="Styles/form.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <form method="POST" action="" onsubmit="return addressFormValidation()">
        <svg onclick="location.href='./'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path fill="#321b41" d="m2.36 11.23l8.31 7.57c.61.56 1.6.12 1.6-.71v-3.63c0-.35.29-.64.64-.64h15.86c.66 0 1.19-.53 1.19-1.19V8.42c0-.66-.53-1.19-1.19-1.19H12.91c-.35 0-.64-.29-.64-.64V2.96c0-.83-.99-1.27-1.6-.71L2.36 9.82a.946.946 0 0 0 0 1.41m13.97 17.53l-2.54-6.95c-.16-.49-.62-.81-1.13-.81c-.51 0-.97.32-1.12.79l-2.57 6.98c-.18.48.07 1 .55 1.18c.1.03.21.05.32.05c.37 0 .73-.23.86-.6l.41-1.13c.04.01.08.01.12.01h2.87c.03 0 .07 0 .1-.01l.41 1.12c.18.48.7.73 1.18.55c.47-.17.71-.7.54-1.18m-4.54-2.32l.87-2.38l.86 2.38zm-3.56-2.73c0 .53-.15 1.02-.41 1.43a2.7 2.7 0 0 1 1.07 2.15c0 1.38-1.05 2.54-2.4 2.69c-.04.01-.09.02-.13.01c-.06.01-.12.01-.18.01H2.92a.92.92 0 0 1-.92-.93v-7.16c0-.5.41-.91.92-.91h2.6c.1 0 .19 0 .27.01a7.905 7.905 0 0 0 .19.03c1.28.22 2.25 1.34 2.25 2.67m-2.7-.87H3.84v1.74h1.68c.48 0 .88-.39.88-.87a.87.87 0 0 0-.87-.87m-1.69 3.57v1.74h2.35a.87.87 0 0 0 0-1.74zm18.76 1.94a3.308 3.308 0 0 1-6.17-1.66v-2.38c0-1.83 1.48-3.31 3.31-3.31c1.18 0 2.28.64 2.87 1.65c.26.44.1 1-.33 1.26c-.44.26-1 .11-1.26-.33a1.5 1.5 0 0 0-1.28-.74c-.8 0-1.47.66-1.47 1.47v2.38c0 .81.67 1.47 1.47 1.47c.53 0 1.01-.28 1.28-.74c.26-.43.81-.59 1.26-.33c.42.26.58.82.32 1.26m4.63-3.48l2.6 3.68c.29.42.2.99-.23 1.29c-.42.29-.99.19-1.28-.22l-2.41-3.42l-.69.69v2.2c0 .51-.41.92-.92.92s-.92-.41-.92-.92v-7.17c0-.51.41-.92.92-.92s.92.41.92.92v2.37l3.01-3.02c.36-.36.94-.36 1.3 0c.36.36.36.94 0 1.3z"></path></svg>
        <p class="title-form">Aggiungi il tuo indirizzo di spedizione</p>
        <div class="input-container">
            <div>
                <label for="address">Inserisci l'indirizzo</label>
                <input type="text" placeholder="Indirizzo (Via, Piazza, Scala, Interno, Numero Civico)" id="address" name="address"/>
            </div>
            <div>
                <label for="cap">Inserisci il tuo codice di avviamento postale (CAP) </label>
                <input type="text" placeholder="XXXXX" id="cap" name="cap"/>
            </div>
            <div>
                <label for="location">Seleziona la tua provincia</label>
                <select name="location" id="location">
                    <optgroup label="Piemonte">
                        <option value="TO">Torino</option>
                        <option value="VC">Vercelli</option>
                        <option value="NO">Novara</option>
                        <option value="CN">Cuneo</option>
                        <option value="AT">Asti</option>
                        <option value="AL">Alessandria</option>
                        <option value="BI">Biella</option>
                        <option value="VB">Verbano-Cusio-Ossola</option>
                    </optgroup>
                    <optgroup label="Valle d'Aosta">
                        <option value="AO">Valle d'Aosta/Vallée d'Aoste</option>
                    </optgroup>
                    <optgroup label="Lombardia">
                        <option value="VA">Varese</option>
                        <option value="CO">Como</option>
                        <option value="SO">Sondrio</option>
                        <option value="MI">Milano</option>
                        <option value="BG">Bergamo</option>
                        <option value="BS">Brescia</option>
                        <option value="PV">Pavia</option>
                        <option value="CR">Cremona</option>
                        <option value="MN">Mantova</option>
                        <option value="LC">Lecco</option>
                        <option value="LO">Lodi</option>
                        <option value="MB">Monza e della Brianza</option>
                    </optgroup>
                    <optgroup label="Trentino-Alto Adige">
                        <option value="BZ">Bolzano/Bozen</option>
                        <option value="TN">Trento</option>
                    </optgroup>
                    <optgroup label="Veneto">
                        <option value="VR">Verona</option>
                        <option value="VI">Vicenza</option>
                        <option value="BL">Belluno</option>
                        <option value="TV">Treviso</option>
                        <option value="VE">Venezia</option>
                        <option value="PD">Padova</option>
                        <option value="RO">Rovigo</option>
                    </optgroup>
                    <optgroup label="Friuli-Venezia Giulia">
                        <option value="UD">Udine</option>
                        <option value="GO">Gorizia</option>
                        <option value="TS">Trieste</option>
                        <option value="PN">Pordenone</option>
                    </optgroup>
                    <optgroup label="Liguria">
                        <option value="IM">Imperia</option>
                        <option value="SV">Savona</option>
                        <option value="GE">Genova</option>
                        <option value="SP">La Spezia</option>
                    </optgroup>
                    <optgroup label="Emilia-Romagna">
                        <option value="PC">Piacenza</option>
                        <option value="PR">Parma</option>
                        <option value="RE">Reggio nell'Emilia</option>
                        <option value="MO">Modena</option>
                        <option value="BO">Bologna</option>
                        <option value="FE">Ferrara</option>
                        <option value="RA">Ravenna</option>
                        <option value="FC">Forlì-Cesena</option>
                        <option value="RN">Rimini</option>
                    </optgroup>
                    <optgroup label="Toscana">
                        <option value="MS">Massa-Carrara</option>
                        <option value="LU">Lucca</option>
                        <option value="PT">Pistoia</option>
                        <option value="FI">Firenze</option>
                        <option value="LI">Livorno</option>
                        <option value="PI">Pisa</option>
                        <option value="AR">Arezzo</option>
                        <option value="SI">Siena</option>
                        <option value="GR">Grosseto</option>
                        <option value="PO">Prato</option>
                    </optgroup>
                    <optgroup label="Umbria">
                        <option value="PG">Perugia</option>
                        <option value="TR">Terni</option>
                    </optgroup>
                    <optgroup label="Marche">
                        <option value="PU">Pesaro e Urbino</option>
                        <option value="AN">Ancona</option>
                        <option value="MC">Macerata</option>
                        <option value="AP">Ascoli Piceno</option>
                        <option value="FM">Fermo</option>
                    </optgroup>
                    <optgroup label="Lazio">
                        <option value="VT">Viterbo</option>
                        <option value="RI">Rieti</option>
                        <option value="RM">Roma</option>
                        <option value="LT">Latina</option>
                        <option value="FR">Frosinone</option>
                    </optgroup>
                    <optgroup label="Abruzzo">
                        <option value="AQ">L'Aquila</option>
                        <option value="TE">Teramo</option>
                        <option value="PE">Pescara</option>
                        <option value="CH">Chieti</option>
                    </optgroup>
                    <optgroup label="Molise">
                        <option value="CB">Campobasso</option>
                        <option value="IS">Isernia</option>
                    </optgroup>
                    <optgroup label="Campania">
                        <option value="CE">Caserta</option>
                        <option value="BN">Benevento</option>
                        <option value="NA">Napoli</option>
                        <option value="AV">Avellino</option>
                        <option value="SA">Salerno</option>
                    </optgroup>
                    <optgroup label="Puglia">
                        <option value="FG">Foggia</option>
                        <option value="BA">Bari</option>
                        <option value="TA">Taranto</option>
                        <option value="BR">Brindisi</option>
                        <option value="LE">Lecce</option>
                        <option value="BT">Barletta-Andria-Trani</option>
                    </optgroup>
                    <optgroup label="Basilicata">
                        <option value="PZ">Potenza</option>
                        <option value="MT">Matera</option>
                    </optgroup>
                    <optgroup label="Calabria">
                        <option value="CS">Cosenza</option>
                        <option value="CZ">Catanzaro</option>
                        <option value="RC">Reggio di Calabria</option>
                        <option value="KR">Crotone</option>
                        <option value="VV">Vibo Valentia</option>
                    </optgroup>
                    <optgroup label="Sicilia">
                        <option value="TP">Trapani</option>
                        <option value="PA">Palermo</option>
                        <option value="ME">Messina</option>
                        <option value="AG">Agrigento</option>
                        <option value="CL">Caltanissetta</option>
                        <option value="EN">Enna</option>
                        <option value="CT">Catania</option>
                        <option value="RG">Ragusa</option>
                        <option value="SR">Siracusa</option>
                    </optgroup>
                    <optgroup label="Sardegna">
                        <option value="SS">Sassari</option>
                        <option value="NU">Nuoro</option>
                        <option value="CA">Cagliari</option>
                        <option value="OR">Oristano</option>
                        <option value="OT">Olbia-Tempio</option>
                        <option value="OG">Ogliastra</option>
                        <option value="VS">Medio Campidano</option>
                        <option value="CI">Carbonia-Iglesias</option>
                    </optgroup>
                </select>
            </div>
            <p id="error-text" class="error-text <%=request.getAttribute("error-address") == null ? "remove-item" : ""%>"><%=request.getAttribute("error-address") != null ? request.getAttribute("error-address") : ""%></p>
            <button type="submit"> Aggiungi indirizzo </button>
        </div>
    </form>
    <script src="js/addressForm.js"></script>
</body>
</html>