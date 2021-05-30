# ChatApp
Proiect realizat de Vina Andreea Madalina in cadrul laboratorului de Programare Avansata. <br>
Proiectul ChatApp este, asa cum ii spune si denumirea, o aplicatie care se bazeaza pe conceptul de Multithreading pentru comunicarea intre useri. <br>
Un user isi poate crea un cont unde trebuie sa furnizeze informatii precum : fullName, username, email, phone, password, gender. Toate aceste informatii <br>
sunt stocate intr-o baza de date MySql. <br>
Aplicatia arunca warning uri in momentul in care un user incearca sa isi creeze un cont cu un username sau un email deja existent in baza de date. <br>
Dupa ce partea de register s-a realizat cu succes, user ul trebuie sa se autentifice, moment in care username-ul si parola sunt verificate in baza de date. <br>
In cazul in care login ul se realizeaza cu succes, userul este redirectionat catre un chat room in care va putea comunica cu toti ceilalti useri online, fiecare mesaj ce <br>
apare pe ecran este precedat de username ul user ului care a trimis mesajul. <br>
Tot in aceasta parte userul isi poate vizualiza profilul si isi poate schimba de asemenea imaginea de profil daca doreste. <br>
Alte functionalitati : log out, vizualizarea userilor conectati. <br>
Tehnologii folosite : <br>
  1. Multithreading <br>
  2. JavaFx
  3. JDBC cu MySQL
