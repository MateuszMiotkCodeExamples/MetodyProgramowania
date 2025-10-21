# Metody Programowania - Przykłady Maven i Gradle

Ten projekt zawiera przykłady użycia **Apache Maven** i **Gradle** - dwóch najpopularniejszych narzędzi do automatyzacji budowania projektów Java. Projekt można budować obiema narzędziami, co pozwala na porównanie ich składni i możliwości.

## Struktura projektu

Projekt demonstruje standardową strukturę katalogów używaną zarówno przez Maven jak i Gradle:

```
.
├── pom.xml                                    # Project Object Model - konfiguracja Maven
├── build.gradle                               # Build Script - konfiguracja Gradle
├── settings.gradle                            # Konfiguracja projektu Gradle
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
├── target/                                    # Katalog generowany przez Maven (ignorowany w Git)
└── build/                                     # Katalog generowany przez Gradle (ignorowany w Git)
```

## Technologie

- **Java 11** - wersja JDK
- **Maven 3.x** lub **Gradle 8.5+** - narzędzia do budowania projektu
- **Gson 2.10.1** - biblioteka do obsługi JSON
- **OpenCSV 5.8** - biblioteka do parsowania CSV
- **JUnit Jupiter 5.9.3** - framework do testów jednostkowych

## Budowanie projektu

Ten sam projekt można zbudować zarówno używając **Maven** jak i **Gradle**. Oba narzędzia używają tej samej struktury katalogów i tych samych zależności.

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

## Podstawowe komendy Gradle

### Kompilacja projektu
```bash
gradle compileJava
# lub z Gradle Wrapper (jeśli został wygenerowany):
./gradlew compileJava
```
Kompiluje kod źródłowy z `src/main/java` do `build/classes/java/main`.

### Uruchomienie testów
```bash
gradle test
# lub:
./gradlew test
```
Kompiluje kod główny i testy, następnie uruchamia wszystkie testy jednostkowe. Raporty HTML dostępne w `build/reports/tests/test/index.html`.

### Pakowanie aplikacji
```bash
gradle build
# lub:
./gradlew build
```
Wykonuje kompilację, testy i tworzy plik JAR w katalogu `build/libs/`.

### Czyszczenie projektu
```bash
gradle clean
# lub:
./gradlew clean
```
Usuwa katalog `build/` z wszystkimi wygenerowanymi plikami.

### Pełny cykl budowania
```bash
gradle clean build
# lub:
./gradlew clean build
```
Czyści projekt, kompiluje, testuje i pakuje.

### Uruchomienie aplikacji (Gradle)
```bash
gradle run
# lub:
./gradlew run
```
Uruchamia aplikację bezpośrednio bez potrzeby wcześniejszego pakowania.

### Generowanie Gradle Wrapper
```bash
gradle wrapper --gradle-version 8.5
```
Tworzy skrypty `gradlew` i `gradlew.bat`, które pozwalają uruchamiać Gradle bez jego instalacji.

## Uruchomienie aplikacji

### Używając Maven

**Opcja 1: Bezpośrednie uruchomienie wykonywalnego JAR (z zależnościami)**
```bash
mvn clean package
java -jar target/file-format-example-1.0.0-with-dependencies.jar
```

Maven tworzy dwa pliki JAR:
- `file-format-example-1.0.0.jar` - standardowy JAR (bez zależności)
- `file-format-example-1.0.0-with-dependencies.jar` - fat JAR ze wszystkimi bibliotekami

**Opcja 2: Uruchomienie ze standardowym JAR (wymaga classpath)**
```bash
java -cp target/file-format-example-1.0.0.jar com.example.FileFormatExample
```
⚠️ To nie zadziała, ponieważ brakuje zależności (Gson, OpenCSV) w classpath.

### Używając Gradle

**Opcja 1: Bezpośrednie uruchomienie (najłatwiejsze)**
```bash
gradle run
# lub:
./gradlew run
```

**Opcja 2: Wykonywalny fat JAR ze wszystkimi zależnościami**
```bash
gradle fatJar
java -jar build/libs/file-format-example-1.0.0-with-dependencies.jar
```

**Opcja 3: Standardowy JAR (bez zależności, tylko dla demonstracji manifestu)**
```bash
gradle build
java -jar build/libs/file-format-example-1.0.0.jar
```
⚠️ To nie zadziała, ponieważ standardowy JAR nie zawiera bibliotek zależnych.

### Wyjaśnienie

**Standardowy JAR** zawiera tylko kod aplikacji, bez bibliotek zależnych (Gson, OpenCSV). 
Można go uruchomić tylko z `-cp` i dodając wszystkie zależności do classpath.

**Fat JAR (uber JAR)** zawiera kod aplikacji ORAZ wszystkie biblioteki zależne, 
co pozwala na proste uruchomienie: `java -jar nazwa.jar`

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

## Porównanie Maven vs Gradle

| Aspekt | Maven | Gradle |
|--------|-------|--------|
| **Format konfiguracji** | XML (`pom.xml`) | Groovy DSL (`build.gradle`) |
| **Składnia** | Verbose, deklaratywna | Zwięzła, programowalna |
| **Wydajność** | Kompiluje wszystko za każdym razem | Incremental builds, caching |
| **Kompilacja** | `mvn compile` | `gradle compileJava` |
| **Testowanie** | `mvn test` | `gradle test` |
| **Pakowanie** | `mvn package` | `gradle build` |
| **Uruchomienie** | Wymaga `exec:java` plugin | Wbudowane `gradle run` |
| **Katalog wyjściowy** | `target/` | `build/` |
| **Raporty testów** | `target/surefire-reports/` | `build/reports/tests/` (HTML) |
| **Elastyczność** | Ograniczona, sztywna struktura | Wysoka, łatwa customizacja |
| **Krzywa uczenia** | Łatwiejsza dla początkujących | Wymaga znajomości Groovy/Kotlin |

### Kluczowe różnice

**Maven** jest lepszy gdy:
- Preferujesz standardową, przewidywalną konfigurację
- Projekty są proste i nie wymagają dużej customizacji
- Zespół jest przyzwyczajony do XML

**Gradle** jest lepszy gdy:
- Potrzebujesz lepszej wydajności (duże projekty)
- Wymagana jest elastyczność i customizacja
- Projekty Android (Gradle jest standardem)
- Preferujesz zwięzłą składnię

## Więcej informacji

- **Maven**: Szczegółowy przewodnik znajduje się w komentarzach w pliku `pom.xml`
- **Gradle**: Szczegółowy przewodnik dostępny w pliku `GRADLE_GUIDE.md`
- **Build.gradle**: Komentarze wyjaśniające konfigurację w pliku `build.gradle`

