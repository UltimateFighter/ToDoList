# ToDoList

Ein kleines, aber durchdachtes Beispielprojekt zur Demonstration moderner Softwarearchitektur-Prinzipien wie 

* Clean Code
* Hexagonale Architektur
* Gute Testbarkeit

---

## 🏁 Ziel

Dieses Projekt wurde von **Michael Nahberger** entwickelt, um im Rahmen meiner Bewerbung
folgendes zu demonstrieren:

- Saubere Architektur
- REST-APIs mit Quarkus effizient strukturiert,
- Solides Test-, Tracing und Logging-Setup aufbaut.
- Daten werden über Mappings zwischen Schichten weitergereicht,

---

## 📐 Architektur

Die Anwendung folgt der  **Hexagonalen Architektur** mit klarer Trennung zwischen:

- `domain`: Reine Geschäftslogik (Service-Schicht)
- `web.rest` Adapter für REST
- `persistence`: Adapter für die Persistence
- `infrastructure`: Logging, Exception Handling etc.

---

## 🔧 Technologien

- Java 17
- Quarkus
- Maven
- JUnit 5
- AssertJ
- Mockito
- Postman (für Integrationstest-Szenarien)

---

## ▶️ Starten der Anwendung

Nach dem Bauen per Maven:

```bash
mvn clean install
```

...kann die Anwendung mit folgendem Skript gestartet werden:

```bash
cd project-resources/bin | project-resources/bin/start-todolist.bat
```


Alternativ kannst man die JAR direkt starten:

```bash
java -jar target\todolist-runner.jar
```

---

## 📮 REST API

Alle REST-Endpunkte sind unter dem Pfad:

```
/api/v1/todos
```

verfügbar.

Ein Request-Header `requestId` (optional) kann übergeben werden. Falls leer, wird eine UUID generiert und ins Logging 
und als Response Header übernommen.

### Health Check

Ein zusätzlicher Health-Endpoint für Quarkus ist erreichbar unter:

```
GET /q/health
```

---

## 🧪 Tests

Das Projekt enthält:

- **Unit-Tests** für alle Komponenten mit Mockito
- **Integrationstests** mit Quarkus-Test-Framework
- **Szenario Tests** in Postman

---

## 🧪 Postman

Die vollständige Postman-Collection findet man unter:

```bash
project-resources/postman/ToDoList API.postman_collection.json
```

### ✅ Szenario-Test:

Ein Beispiel-Szenario prüft:

1. Anlegen eines Elements (`Create`)
2. Prüfen von `GET ALL`, dass 1 Element enthalten ist
3. Prüfen auf 409 `Conflict`, wenn Datum doppelt
4. Anlegen eines zweiten Elements
5. Sortieren per `sortOrder=desc`
5. Sortieren per `sortOrder=asc`
6. Löschen des ersten Elements
7. Nochmaliges Löschen – Erwartung: 404
8. Finaler `GET` mit Prüfung, dass nur noch 1 Element da ist
6. Löschen des Elements, was noch über ist

---

## 🗂️ Struktur

```
.
├── src
│   ├── main
│   │   ├── java/com/nahberger/todolist/...
│   │   └── resources/application.yaml
│   └── test
│       └── java/com/nahberger/todolist/...
├── project-resources
│   ├── bin/start-todolist.bat
│   └── postman/ToDoList.postman_collection.json
├── pom.xml
└── README.md
```

---

## ℹ️ Info

- Anwendung läuft auf Port **8080**
- `application.yaml` enthält zentrale Konfiguration
- Sortierung erfolgt standardmäßig `asc` (aufsteigend)

---

## ✍🏼 Autor

Michael Nahberger