# Fangeregister for Mesans arkitekturgruppes fengselscase

Dette fangeregisteret er består av en REST-tjeneste for lagring av fanger.

## Hvordan sette opp tjenesten med Docker

Start en MongoDB databasecontainer:
```
docker run -d --name mongo-fangereg mongo:3.0.1
```

Link deretter fangeregister-servicen til databasen:
```
docker run --rm --name fangeregister -p 49000:8080 --link mongo-fangereg:mongo mesanfagark/fag-ark-persistering-fangereg
```
Navnet *mongo-fangereg* er tilfeldig valgt, og kan byttes ut med et hvilket som helst navn, mens aliaset *mongo* i `... --link <tilfeldig-valgt-navn>:mongo ...` må hete akkurat dette. 

Tjenesten kan nå aksesseres via **http://\<din-IP\>:49000**.

Dersom du føler for å inspisere databasen kan dette for eksempel gjøres slik:
```
docker run --rm -it --link mongo-fangereg:mongo mongo bash -c 'mongo --host=$MONGO_PORT_27017_TCP_ADDR'
```

---

Teknologistikkord: [Docker](https://www.docker.com/), [Dropwizard](http://dropwizard.io/), [MongoDB](https://www.mongodb.org/), [Morphia](https://github.com/mongodb/morphia).
