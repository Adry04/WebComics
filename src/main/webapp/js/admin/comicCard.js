function deleteComic (isbn) {
    if(confirm("Vuoi davvero eliminare questo prodotto?")) {
        const urlPageDelete = window.location.origin + "/tswProject_war_exploded/admin"
        const comicCard = document.getElementById("comic-card-" + isbn);
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                comicCard.style.display = 'none';
            }
        }
        xhttp.open("POST", urlPageDelete, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("ISBN=" + isbn);
    }
}