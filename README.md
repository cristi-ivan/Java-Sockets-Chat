# SocketsChat

## Detalii
  Aceasta este o aplicaţie cu arhitectura client-server care sa ofere utilizatorilor posibilitatea conversaţiilor în timp real, de tip text.Fiecare utilizator va fi identificat, în mod unic, de un nume de utilizator (sau porecla). Acesta va fi ales de utilizator înainte de conectarea la server. Serverul se va asigura ca nu pot fi conectati, în acelasi timp, doi utilizatori cu acelasi nume. Utilizatorii au la dispozitie urmatoarele comenzi: list, msg, bcast, nick, quit.
  
## Comenzi:

* LIST Comanda list va întoarce utilizatorului o lista care va conţine, pe fiecare linie, numele unui utilizator conectat la server la momentul primirii comenzii. Lista va cuprinde toţi utilizatorii conectaţi în acel moment, inclusiv utilizatorul care iniţiaza comanda.
* MSG Aceasta comanda primeşte ca parametru numele unui utilizator şi un mesaj text terminat prin sfîrşit de linie şi instruieşte serverul sa trimita mesajul respectiv utilizatorului selectat.
* BCAST Comanda primeşte care parametru un mesaj text terminat prin sfârşit de linie şi instruieşte serverul sa trimita mesajul tuturor utilizatorilor conectaţi la momentul respectiv.
* NICK Aceasta comanda primeşte ca parametru un identificator / nume de utilizator şi schimba numele de utilizator curent.
* QUIT Aceasta comanda deconecteaza clientul şi încheie execuţia programului client.
  


