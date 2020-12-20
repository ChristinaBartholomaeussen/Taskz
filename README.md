TASKZ - README

Det er nødvendigt at have Java 13.0 installeret på sin maskine, for at kunne kompilere vores applikation. Det vil være nødvendigt at have installeret en IDE, som f.eks. IntelliJ IDEA, Ecplise, NetBeans, JGRASP osv. der alle supportere Java, applikationen skal kompileres. 

Derfra kan man kunne tilgå vores applikation via: http://localhost:8080/ i ens webbrowser.

Der kan benyttes følgende brugerinformationer til logge ind på applikationen:

-	email: nicklas@taskz.com password: 1234
-	email: marianne@taskz.com password: 1234
-	email: kristoffer@taskz.com password 1234
-	email: oskar@taskz.com password: 1234

For at kunne visualisere databasen og se vores tabeller, kan man downloade MySQL Workbench fra MySQL.

MySQL Workbench download: https://dev.mysql.com/downloads/windows/installer/8.0.html

Vi har benyttes os af cloudbaseret database hosting via GearHost, hvilket betyder, at man blot skal lave en connection til vores hostede database, hvorefter man har adgang til databasen.
Applikationen er som default connected med GearHost, derfor anbefaler vi denne løsning.

Når Workbench er blevet installeret, skal man åbne en ny connection:

Hostname: /den1.mysql2.gear.host

Username: taskz

Password: taskz!

Porten man skal bruge er 3306.

Man kan som alternativt benytte vores vedlagte SQL-Script, som skaber en kopi af selvsamme database. I skal dog ændre ConnectionService.java, for at den interagerer med jeres lokale database.

Grunden til, at vi har valgt at vedlægge et SQL-Script er, at GearHost kan være nede, og du derfor ikke kan få adgang via dem, ellers det kan være, at I ønsker et testmiljø, hvor I ikke kan ændre den primære data hos GearHost.
