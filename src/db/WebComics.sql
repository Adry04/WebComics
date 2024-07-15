#TESTATO E FUNZIONANTE

DROP DATABASE IF EXISTS webcomics;
CREATE DATABASE webcomics;
USE webcomics;

CREATE TABLE Utente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255),
    isAdmin BOOLEAN NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    pass VARCHAR(255) NOT NULL
);

CREATE TABLE CC(
   id INT AUTO_INCREMENT PRIMARY KEY,
   intestatario VARCHAR(255) NOT NULL,
   IBAN CHAR(27) NOT NULL,
   bic CHAR(8) NOT NULL
);

CREATE TABLE CDC (
     id INT AUTO_INCREMENT PRIMARY KEY,
     numero VARCHAR(19) NOT NULL,
     intestatario VARCHAR(255) NOT NULL,
     cvc CHAR(3) NOT NULL,
     scadenza DATE NOT NULL
);

CREATE TABLE Fumetto (
     ISBN CHAR(13) PRIMARY KEY,
     autore VARCHAR(255) NOT NULL,
     prezzo DECIMAL(10, 2) NOT NULL,
     titolo VARCHAR(255) NOT NULL,
     descrizione LONGTEXT,
     categoria VARCHAR(255) NOT NULL,
     sconto INT,
     immagine VARCHAR(255) NOT NULL,
     ddi DATE NOT NULL
);

CREATE TABLE Indirizzo (
   id INT AUTO_INCREMENT PRIMARY KEY,
   indirizzo VARCHAR(255) NOT NULL,
   CAP CHAR(5) NOT NULL,
   provincia CHAR(2) NOT NULL,
   idUtente INT NOT NULL,
   FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE
);

CREATE TABLE Carrello (
  idUtente INT,
  isbn CHAR(13),
  quantita INT NOT NULL,
  FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE,
  FOREIGN KEY (isbn) REFERENCES Fumetto(isbn) ON DELETE CASCADE,
  PRIMARY KEY (idUtente, isbn)
);

CREATE TABLE Wishlist (
  idUtente INT,
  isbn CHAR(13),
  FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE,
  FOREIGN KEY (isbn) REFERENCES Fumetto(isbn) ON DELETE CASCADE,
  PRIMARY KEY (idUtente, isbn)
);

CREATE TABLE ContoUtente(
    ccid INT,
    idUtente INT,
    FOREIGN KEY (ccid) REFERENCES CC(id) ON DELETE CASCADE,
    FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE,
    PRIMARY KEY (ccid, idUtente)
);

CREATE TABLE CartaUtente(
    cdcid INT,
    idUtente INT,
    FOREIGN KEY (cdcid) REFERENCES CDC(id) ON DELETE CASCADE,
    FOREIGN KEY (idUtente) REFERENCES Utente(id) ON DELETE CASCADE,
PRIMARY KEY (cdcid, idUtente)
);

CREATE TABLE Ordine(
   idUtente INT,
   id INT AUTO_INCREMENT,
   dataordine DATE NOT NULL,
   prezzoacquisto DECIMAL(10, 2) NOT NULL,
   quantita INT NOT NULL,
   indirizzo VARCHAR(255) NOT NULL,
   CAP CHAR(5) NOT NULL,
   idcdc INT,
   idcc INT,
   FOREIGN KEY (idUtente) REFERENCES Utente(id),
   FOREIGN KEY (idcdc) REFERENCES CDC(id) ON DELETE SET NULL,
   FOREIGN KEY (idcc) REFERENCES CC(id) ON DELETE SET NULL,
   PRIMARY KEY (id)
);

CREATE TABLE FumettoOrdinato (
     ordineid INT,
     idOrdineFumetto INT AUTO_INCREMENT,
     ISBN CHAR(13),
     quantita INT NOT NULL,
     prezzo_fumetto DECIMAL(10, 2) NOT NULL,
     titolo_fumetto VARCHAR(255) NOT NULL,
     immagine_fumetto VARCHAR(255) NOT NULL,
     FOREIGN KEY (ordineid) REFERENCES Ordine(id) ON DELETE CASCADE,
     FOREIGN KEY (ISBN) REFERENCES Fumetto(ISBN) ON DELETE SET NULL,
     PRIMARY KEY (idOrdineFumetto),
     KEY (ordineid)
);
