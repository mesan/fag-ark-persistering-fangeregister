# Fangeregister for Mesans arkitekturgruppes fengselscase

Dette er en REST-tjeneste for lagring av fanger.

## Hvordan sette opp tjenesten med Docker

Start en MongoDB databasecontainer:

```
docker run -d --name mongo-fangereg mongo:3.0.1
```

Link deretter fangeregister-servicen til databasen:

```
docker run --rm --name fangeregister -p 49000:8080 --link mongo-fangereg:mongo mesanfagark/fag-ark-persistering-fangereg
```

Navnet *mongo-fangereg* er tilfeldig valgt, og kan byttes ut med et hvilket som helst navn, mens aliaset *mongo* i 

```... --link <tilfeldig-valgt-navn>:mongo ...```

må hete akkurat dette. 

Tjenesten kan nå aksesseres via **http://\<din-IP\>:49000**.

Dersom du føler for å inspisere databasen kan dette for eksempel gjøres slik:

```
docker run --rm -it --link mongo-fangereg:mongo mongo bash -c 'mongo --host=$MONGO_PORT_27017_TCP_ADDR'
```

## API

Alle verdier er på **JSON-format** og all kommunikasjon foregår over **HTTP**.

En **fange** kan for for øyeblikket se slik ut:

```json
{
  "id": "552a30cfe4b0c8d8b3a82f1a",
  "navn": "Ola Nordmann"
}
```

Mens en **liste med fanger** (json-array) kan se slik ut:

```json
[
  {
    "id": "552a30cfe4b0c8d8b3a82f1a",
    "navn": "Ola Nordmann"
  },
  {
    "id": "552a311ae4b0c8d8b3a82f1b",
    "navn": "Kari Nordkvinne"
  },
  ...
  {
    "id": "552a356ce4b0c8d8b3a82f1c",
    "navn": "Sist I Lista"
  }
]
```

### Hente alle fanger
URL | Metode | Status | Returverdi
--- | --- | --- | ---
/fanger | GET | 200 (OK) | Array med fanger.

Eksempel-URL: http://localhost:49000/fanger 

### Hente én fange
URL | Metode | Status | Returverdi
--- | --- | --- | ---
/fanger/{id} | GET | 200 (OK) / 404 (Not Found) | Fange.

Eksempel-URL: http://localhost:49000/fanger/552a30cfe4b0c8d8b3a82f1a

### Lagre en fange
URL | Metode | Headers | Status | Input | Returverdi
--- | --- | --- | --- | --- | ---
/fanger | POST | Content-Type: application/json | 201 (Created) | Fange (uten id/id blir ignorert) | Fange med oppdatert id.

### Oppdatere en fange
URL | Metode | Headers | Status | Input | Returverdi
--- | --- | --- | --- | --- | ---
/fanger | PUT | Content-Type: application/json | 200 (OK) / 404 (Not Found) | Fange. | Oppdatert fange.

### Slette en fange
URL | Metode | Status
--- | --- | ---
/fanger/{id} | DELETE  | 200 (OK) / 404 (Not Found) 

---

Teknologistikkord: [Docker](https://www.docker.com/), [Dropwizard](http://dropwizard.io/), [MongoDB](https://www.mongodb.org/), [Morphia](https://github.com/mongodb/morphia).
