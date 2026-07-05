# Opis zadatka:
Banka izdaje kreditne kartice fizičkim osobama koje apliciraju za iste.
Potrebno je razviti mini-aplikaciju za evidentiranje klijenata koji traže izradu kreditnih kartica.
Svaki klijent ima sljedeće atribute: Ime, Prezime, OIB i Status kartice (statusi proizvoljni npr.
&quot;Pending&quot;, &quot;Approved&quot;, &quot;Rejected&quot;,…).
Aplikacija treba osigurati trajno spremanje podataka, preferirano u bazu podataka
(npr. H2) ili datoteku.
## Funkcionalnosti aplikacije:
1. Evidentiranje klijenata:
   Unos podataka o klijentu (Ime, Prezime, OIB, Status kartice) i spremanje u bazu podataka.
2. Pretraživanje baze:
   Mogućnost pretraživanja klijenata prema OIB-u. Ako klijent postoji u bazi, aplikacija vraća
   njegove podatke (Ime, Prezime, OIB, Status kartice). U suprotnom, ne vraća ništa.
3. Prosljeđivanje podataka na API:
   Za pronađenu osobu, podatke je potrebno proslijediti na dani RESTful API (endpoint i
   metoda su definirani u priloženoj YAML datoteci).
4. Brisanje klijenta:
   Omogućiti brisanje klijenta prema OIB-u.
5. RESTful Metode:
   Implementirati potrebne metode kao RESTful API.
  
# Bonus značajke:
## Bonus Feature I:
  Implementirati zaprimanje statusa izrade kartice preko KAFKA topica. (Statusi kartice
  proizvoljni)

## Bonus Feature II:
  Izraditi jednostavnu formu za korisničku podršku korištenjem React-a. Ako se ne želi raditi
  frontend, dovoljan je jednostavan interface putem HTTP konzole.

## Tehnički detalji:
- Aplikacija treba biti razvijena u Javi, poželjno korištenjem frameworka poput Spring Boot-a.
- Trajno spremanje podataka u bazu npr. H2 (uz konfiguraciju tablice i primjera).
- Dodati sučelje za slanje podataka API-u prema zadanoj strukturi.
- Sve dodatne značajke kao što su testovi, logging i slično, su dobrodošle.
- Kada je aplikacija gotova, postaviti je na javni GIT i poslati link.
- Na tehničkom razgovoru će se prokomentirati rješenje.
