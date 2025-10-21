# Metody Programowania - Przykłady Maven

Ten projekt zawiera przykłady użycia Apache Maven zgodnie z przewodnikiem po podstawach Maven.

## Struktura projektu

Projekt demonstruje standardową strukturę katalogów Maven:

```
.
├── pom.xml                                    # Project Object Model - konfiguracja Maven
├── src/
│   ├── main/
│   │   ├── java/com/example/                 # Kod źródłowy aplikacji
│   │   │   └── FileFormatExample.java
│   │   └── resources/                         # Zasoby aplikacji (pliki konfiguracyjne, dane)
│   │       ├── books.csv
│   │       └── books.json
│   └── test/
│       ├── java/com/example/                 # Testy jednostkowe
│       │   └── FileFormatExampleTest.java
│       └── resources/                         # Zasoby testowe
└── target/                                    # Katalog generowany przez Maven (ignorowany w Git)
```

## Technologie

- **Java 11** - wersja JDK
- **Maven 3.x** - narzędzie do budowania projektu
- **Gson 2.10.1** - biblioteka do obsługi JSON
- **OpenCSV 5.8** - biblioteka do parsowania CSV
- **JUnit Jupiter 5.9.3** - framework do testów jednostkowych (zakres `test`)

## Podstawowe komendy Maven

### Kompilacja projektu
```bash
mvn compile
```
Kompiluje kod źródłowy z `src/main/java` do `target/classes`.

### Uruchomienie testów
```bash
mvn test
```
Kompiluje kod główny i testy, następnie uruchamia wszystkie testy jednostkowe.

### Pakowanie aplikacji
```bash
mvn package
```
Wykonuje kompilację, testy i tworzy plik JAR w katalogu `target/`.

### Czyszczenie projektu
```bash
mvn clean
```
Usuwa katalog `target/` z wszystkimi wygenerowanymi plikami.

### Pełny cykl budowania
```bash
mvn clean install
```
Czyści projekt, kompiluje, testuje, pakuje i instaluje artefakt do lokalnego repozytorium Maven (`~/.m2/repository`).

## Uruchomienie aplikacji

Po zbudowaniu projektu (`mvn package`), możesz uruchomić aplikację:

```bash
java -cp target/file-format-example-1.0.0.jar com.example.FileFormatExample
```

Aplikacja wczyta książki z plików CSV i JSON znajdujących się w resources i wyświetli je na konsoli.

## Kluczowe koncepcje Maven

### Współrzędne Maven (GAV)
- **groupId**: `com.example` - identyfikator organizacji/grupy
- **artifactId**: `file-format-example` - nazwa projektu
- **version**: `1.0.0` - wersja projektu

### Zakresy zależności
- **compile** (domyślny) - potrzebne do kompilacji i uruchomienia (Gson, OpenCSV)
- **test** - potrzebne tylko do testów (JUnit Jupiter)
- **provided** - dostarczone przez środowisko uruchomieniowe
- **runtime** - potrzebne tylko w czasie uruchomienia

### Cykl życia Maven
Maven wykonuje fazy w określonej kolejności:
1. `validate` - walidacja struktury projektu
2. `compile` - kompilacja kodu źródłowego
3. `test` - uruchomienie testów jednostkowych
4. `package` - pakowanie do JAR/WAR
5. `verify` - weryfikacja poprawności pakietu
6. `install` - instalacja do lokalnego repozytorium
7. `deploy` - publikacja do zdalnego repozytorium

## Zarządzanie zależnościami

Maven automatycznie:
- Pobiera biblioteki z Maven Central (https://repo.maven.apache.org/maven2/)
- Rozwiązuje zależności przechodnie (biblioteki używane przez nasze biblioteki)
- Przechowuje biblioteki w lokalnym repozytorium (`~/.m2/repository`)
- Zarządza wersjami i konfliktami zależności

## Więcej informacji

Szczegółowy przewodnik znajduje się w komentarzach w pliku `pom.xml`.

