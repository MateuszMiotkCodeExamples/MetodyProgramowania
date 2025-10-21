# Podstawy Gradle

Gradle to nowoczesne narzędzie do automatyzacji budowania projektów, które stanowi alternatywę dla Apache Maven. W przeciwieństwie do Maven, który używa XML, Gradle wykorzystuje języki DSL (Domain Specific Language) oparte na Groovy lub Kotlin, co czyni konfigurację bardziej zwięzłą, czytelną i programowalną.

## Różnice między Maven a Gradle

### Filozofia i podejście

Maven opiera się na zasadzie **convention over configuration** (konwencja nad konfiguracją) i używa deklaratywnego podejścia, gdzie programista opisuje **co** chce osiągnąć, a Maven określa **jak** to zrobić według z góry zdefiniowanych konwencji. Maven ma sztywną strukturę i cykl życia, co zapewnia spójność, ale ogranicza elastyczność.

Gradle łączy podejście deklaratywne z imperatywnym, oferując **configuration over convention** - respektuje konwencje Maven (można użyć tej samej struktury katalogów), ale pozwala na pełną customizację gdy jest to potrzebne. Gradle jest znacznie bardziej elastyczny i pozwala na programowanie procesu budowania jak normalnego kodu.

### Format konfiguracji

**Maven** używa XML w pliku `pom.xml`, co jest verbose i czasem trudne w utrzymaniu:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

**Gradle** używa Groovy DSL w pliku `build.gradle`, co jest znacznie bardziej zwięzłe:

```groovy
implementation 'com.google.code.gson:gson:2.10.1'
```

Gradle oferuje też Kotlin DSL (`build.gradle.kts`), który zapewnia lepsze wsparcie IDE i type safety.

### Wydajność

Gradle jest zazwyczaj **szybszy** niż Maven dzięki:
- **Incremental builds** - kompiluje tylko zmienione pliki
- **Build cache** - współdzieli wyniki między różnymi budowaniami
- **Daemon process** - działa w tle i unika kosztownego uruchamiania JVM przy każdym budowaniu
- **Parallel execution** - wykonuje niezależne zadania równolegle

Maven kompiluje zawsze wszystko od nowa (chyba że użyje się specjalnych pluginów) i uruchamia nowy proces JVM przy każdym wywołaniu.

## Struktura plików Gradle

### build.gradle

Główny plik konfiguracyjny projektu - odpowiednik `pom.xml` w Maven. Zawiera:

```groovy
plugins {
    id 'java'           // Plugin dodający wsparcie dla Java
    id 'application'    // Plugin do uruchamiania aplikacji
}

group = 'com.example'       // groupId w Maven
version = '1.0.0'           // version w Maven

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()     // Używa Maven Central jako źródła bibliotek
}

dependencies {
    implementation 'com.google.code.gson:gson:2.10.1'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.3'
}

test {
    useJUnitPlatform()  // Używa JUnit 5
}
```

#### Sekcja plugins

Wtyczki (plugins) w Gradle dodają funkcjonalność do procesu budowania. Plugin `java` dodaje standardowe zadania jak `compileJava`, `test`, `jar`. Plugin `application` dodaje możliwość uruchomienia aplikacji przez `gradle run`.

Plugins można dodawać na dwa sposoby:

```groovy
// Nowa składnia (preferowana) - dla pluginów z Gradle Plugin Portal
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.0'
}

// Stara składnia - dla starszych pluginów lub własnych
apply plugin: 'java'
```

#### Sekcja repositories

Repozytoria określają skąd Gradle pobiera zależności:

```groovy
repositories {
    mavenCentral()              // Maven Central Repository
    google()                    // Google's Maven repository (Android)
    mavenLocal()                // Lokalne repozytorium Maven (~/.m2/repository)
    
    // Własne repozytorium
    maven {
        url 'https://repo.example.com/maven2'
        credentials {
            username = 'user'
            password = 'password'
        }
    }
}
```

Gradle przeszukuje repozytoria w kolejności zdefiniowania i używa pierwszego znalezionego artefaktu.

#### Sekcja dependencies

Zależności definiują biblioteki potrzebne projektowi. Gradle używa różnych **konfiguracji** (odpowiednik scopes w Maven):

| Gradle | Maven | Opis |
|--------|-------|------|
| `implementation` | `compile` | Potrzebne do kompilacji i runtime, NIE są eksponowane jako zależności przechodnie |
| `api` | `compile` | Potrzebne do kompilacji i runtime, SĄ eksponowane jako zależności przechodnie (wymaga `java-library`) |
| `compileOnly` | `provided` | Tylko do kompilacji, dostarczone przez środowisko runtime |
| `runtimeOnly` | `runtime` | Tylko w runtime, nie do kompilacji |
| `testImplementation` | `test` | Tylko dla testów - kompilacja i runtime |
| `testCompileOnly` | `test` + `provided` | Tylko do kompilacji testów |
| `testRuntimeOnly` | `test` + `runtime` | Tylko do runtime testów |

**Kluczowa różnica:** Gradle wprowadza rozróżnienie między `implementation` a `api`:
- `implementation` - zależność jest widoczna tylko wewnątrz modułu (szybsze rebuildy)
- `api` - zależność jest eksponowana na zewnątrz (jak `compile` w Maven)

Przykłady:

```groovy
dependencies {
    // Biblioteka do użytku wewnętrznego
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // API publiczne (wymaga pluginu 'java-library')
    api 'org.apache.commons:commons-lang3:3.12.0'
    
    // Dostarczone przez serwer aplikacji
    compileOnly 'javax.servlet:servlet-api:2.5'
    
    // Sterownik bazy danych potrzebny tylko w runtime
    runtimeOnly 'org.postgresql:postgresql:42.6.0'
    
    // Testy
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.3'
    testImplementation 'org.mockito:mockito-core:5.3.1'
}
```

Można też użyć bardziej szczegółowej składni:

```groovy
implementation group: 'com.google.code.gson', name: 'gson', version: '2.10.1'
```

### settings.gradle

Plik konfiguracyjny definiujący strukturę projektu - odpowiednik sekcji `<modules>` w Maven dla projektów wielomodułowych:

```groovy
rootProject.name = 'file-format-example'  // artifactId w Maven

// Dla projektów wielomodułowych:
// include 'modul1', 'modul2', 'modul3'
```

Ten plik jest odczytywany **przed** `build.gradle` podczas inicjalizacji buildu.

## Podstawowe komendy Gradle

Gradle wykonuje **tasks** (zadania) zamiast goals/phases jak w Maven. Każdy task ma określone zależności i jest wykonywany tylko raz, nawet jeśli wiele innych tasków od niego zależy.

### Kompilacja projektu

```bash
gradle compileJava
```

Kompiluje kod źródłowy z `src/main/java` do `build/classes/java/main`. Odpowiednik `mvn compile`.

### Kompilacja z Gradle Daemon

```bash
gradle compileJava
```

Przy pierwszym uruchomieniu Gradle startuje proces daemon w tle, który pozostaje aktywny i przyspiesza kolejne wywołania. Maven zawsze uruchamia nowy proces JVM.

### Uruchomienie testów

```bash
gradle test
```

Kompiluje kod i testy, następnie uruchamia wszystkie testy jednostkowe. Wyniki są zapisywane w `build/reports/tests/test/index.html` jako ładny raport HTML. Odpowiednik `mvn test`.

```bash
gradle test --tests com.example.FileFormatExampleTest
gradle test --tests *ExampleTest
gradle test --tests *.testLoadBooksFromCSV
```

Gradle pozwala uruchamiać wybrane testy bez dodatkowej konfiguracji.

### Pakowanie aplikacji

```bash
gradle build
```

Wykonuje pełny cykl budowania: kompilacja, testy, pakowanie do JAR. Tworzy plik `build/libs/file-format-example-1.0.0.jar`. Jest to odpowiednik `mvn package`, ale `build` wykonuje też dodatkowe zadania sprawdzające jakość.

```bash
gradle assemble
```

Tworzy JAR bez uruchamiania testów - przydatne gdy chcemy szybko zbudować artefakt.

### Czyszczenie projektu

```bash
gradle clean
```

Usuwa katalog `build/` ze wszystkimi wygenerowanymi plikami. Odpowiednik `mvn clean`.

```bash
gradle clean build
```

Najpierw czyści, potem buduje od zera - odpowiednik `mvn clean package`.

### Uruchomienie aplikacji

```bash
gradle run
```

Uruchamia aplikację bezpośrednio (wymaga pluginu `application` i zdefiniowania `mainClass`). Maven nie ma bezpośredniego odpowiednika - trzeba użyć `exec:java`.

### Instalacja do lokalnego repozytorium

```bash
gradle publishToMavenLocal
```

Instaluje artefakt do lokalnego repozytorium Maven (`~/.m2/repository`), dzięki czemu inne projekty Maven lub Gradle mogą go używać. Odpowiednik `mvn install`.

Wymaga dodania w `build.gradle`:

```groovy
plugins {
    id 'maven-publish'
}

publishing {
    publications {
        maven(MavenPublication) {
            from components.java
        }
    }
}
```

### Wyświetlanie zależności

```bash
gradle dependencies
```

Wyświetla kompletne drzewo zależności dla wszystkich konfiguracji, pokazując również zależności przechodnie i konflikty wersji. Odpowiednik `mvn dependency:tree`.

```bash
gradle dependencies --configuration runtimeClasspath
```

Wyświetla zależności tylko dla runtime classpath.

### Wyświetlanie wszystkich tasków

```bash
gradle tasks
```

Wyświetla wszystkie dostępne zadania pogrupowane według kategorii. Dodanie `--all` pokazuje również ukryte taski.

### Informacje o projekcie

```bash
gradle projects
```

Wyświetla strukturę projektu i wszystkie podmoduły (dla projektów wielomodułowych).

```bash
gradle properties
```

Wyświetla wszystkie właściwości projektu (wersja, nazwa, zależności, itp.).

## Gradle Wrapper

Gradle Wrapper to mechanizm pozwalający uruchamiać Gradle bez jego instalacji. Jest to **best practice** dla projektów Gradle, ponieważ gwarantuje że wszyscy deweloperzy i systemy CI/CD używają tej samej wersji Gradle.

### Generowanie Wrapper

Jeśli masz zainstalowanego Gradle:

```bash
gradle wrapper --gradle-version 8.5
```

To tworzy:
- `gradlew` - skrypt dla Unix/Mac
- `gradlew.bat` - skrypt dla Windows
- `gradle/wrapper/gradle-wrapper.jar` - kod wrappera
- `gradle/wrapper/gradle-wrapper.properties` - konfiguracja wersji

### Używanie Wrapper

Po wygenerowaniu wrapper, używaj `./gradlew` zamiast `gradle`:

```bash
./gradlew build         # Unix/Mac/Linux
gradlew.bat build       # Windows
```

Przy pierwszym uruchomieniu wrapper automatycznie pobierze określoną wersję Gradle i użyje jej do budowania. Pliki wrapper powinny być commitowane do repozytorium Git.

### Aktualizacja wersji Gradle

```bash
./gradlew wrapper --gradle-version 8.5
```

Aktualizuje wrapper do nowej wersji Gradle.

## Struktura katalogów

Gradle domyślnie używa **tej samej struktury** co Maven (convention over configuration):

```
projekt/
├── build.gradle                   # Konfiguracja Gradle (odpowiednik pom.xml)
├── settings.gradle                # Konfiguracja projektu
├── gradlew                        # Wrapper Unix/Mac
├── gradlew.bat                    # Wrapper Windows
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/                  # Kod źródłowy
│   │   └── resources/             # Zasoby aplikacji
│   └── test/
│       ├── java/                  # Testy
│       └── resources/             # Zasoby testowe
└── build/                         # Katalog wyjściowy (odpowiednik target/)
    ├── classes/                   # Skompilowane klasy
    ├── libs/                      # Wygenerowane JARy
    ├── reports/                   # Raporty (testy, pokrycie, itp.)
    └── test-results/              # Wyniki testów w XML
```

Możesz zmienić te ścieżki jeśli potrzebujesz:

```groovy
sourceSets {
    main {
        java {
            srcDirs = ['src/java']
        }
        resources {
            srcDirs = ['src/resources']
        }
    }
}
```

## Własne taski (Custom Tasks)

Gradle pozwala łatwo definiować własne zadania bezpośrednio w `build.gradle`:

```groovy
// Prosty task
task hello {
    doLast {
        println 'Hello, Gradle!'
    }
}

// Task z parametrami
task printVersion {
    group = 'custom'
    description = 'Wyświetla wersję projektu'
    
    doLast {
        println "Wersja: ${project.version}"
    }
}

// Task zależny od innych tasków
task buildAndRun {
    dependsOn 'build'
    doLast {
        println 'Uruchamiam aplikację...'
    }
}
```

Uruchomienie:

```bash
gradle hello
gradle printVersion
gradle buildAndRun
```

## Gradle vs Maven - Podsumowanie

### Kiedy używać Maven:

- Prosty, standardowy projekt Java bez specjalnych wymagań
- Zespół preferuje jawną, deklaratywną konfigurację w XML
- Potrzeba maksymalnej stabilności i przewidywalności
- Organizacja ma już ustalone standardy Maven
- Projekt nie wymaga dużej customizacji procesu budowania

### Kiedy używać Gradle:

- Potrzebujesz lepszej wydajności buildów (duże projekty)
- Wymagana jest elastyczność i programowalna konfiguracja
- Projekty Android (Gradle jest standardem)
- Projekty wielomodułowe z złożonymi zależnościami
- Potrzebujesz zaawansowanych optymalizacji (incremental builds, caching)
- Preferujesz zwięzłą składnię nad verbose XML

### Tabela porównawcza komend

| Operacja | Maven | Gradle |
|----------|-------|--------|
| Kompilacja | `mvn compile` | `gradle compileJava` |
| Testy | `mvn test` | `gradle test` |
| Pakowanie | `mvn package` | `gradle build` |
| Pakowanie bez testów | `mvn package -DskipTests` | `gradle assemble` |
| Czyszczenie | `mvn clean` | `gradle clean` |
| Instalacja lokalna | `mvn install` | `gradle publishToMavenLocal` |
| Uruchomienie | `mvn exec:java` | `gradle run` |
| Drzewo zależności | `mvn dependency:tree` | `gradle dependencies` |
| Lista zadań | `mvn help:describe` | `gradle tasks` |

## Wydajność - Build Cache

Gradle oferuje zaawansowany mechanizm **build cache**, który pozwala współdzielić wyniki budowania między różnymi projektami i maszynami:

```groovy
// W build.gradle lub gradle.properties
org.gradle.caching=true
```

Gdy cache jest włączony, Gradle:
1. Oblicza hash inputów dla każdego taska (kod, zależności, konfiguracja)
2. Sprawdza czy wynik dla tego hasha już istnieje w cache
3. Jeśli tak, używa cached output zamiast wykonywać task ponownie
4. Jeśli nie, wykonuje task i cachuje wynik

To może dramatycznie przyspieszyć buildy w systemach CI/CD gdzie różne gałęzie często kompilują ten sam kod.

## Incremental Compilation

Gradle automatycznie śledzi które pliki się zmieniły i kompiluje tylko je, zamiast całego projektu:

```bash
gradle compileJava  # Kompiluje wszystko przy pierwszym uruchomieniu
# ... edytujesz jeden plik ...
gradle compileJava  # Kompiluje TYLKO zmieniony plik
```

Maven zawsze kompiluje wszystkie pliki (chyba że użyje się specjalnych pluginów jak `maven-compiler-plugin` z `useIncrementalCompilation`).

## Gradle Daemon

Gradle domyślnie uruchamia proces daemon w tle, który:
- Pozostaje aktywny między buildami
- Utrzymuje JVM w pamięci (unikając kosztownego startupu)
- Cachuje strukturę projektu i pliki
- Automatycznie się wyłącza po okresie bezczynności (domyślnie 3 godziny)

Status daemon:

```bash
gradle --status        # Sprawdź działające daemony
gradle --stop          # Zatrzymaj wszystkie daemony
gradle --no-daemon     # Wyłącz daemon dla tego buildu
```

## Migracja z Maven do Gradle

Gradle oferuje automatyczny mechanizm konwersji:

```bash
gradle init
```

To narzędzie:
1. Wykrywa projekt Maven (pom.xml)
2. Analizuje konfigurację
3. Generuje równoważne pliki Gradle (build.gradle, settings.gradle)
4. Tworzy Gradle Wrapper

Można też użyć pluginu:

```bash
gradle init --type pom
```

## Dodatkowe zasoby

- **Dokumentacja oficjalna:** https://docs.gradle.org
- **Gradle User Guide:** https://docs.gradle.org/current/userguide/userguide.html
- **Gradle Plugin Portal:** https://plugins.gradle.org
- **Porównanie Maven vs Gradle:** https://gradle.org/maven-vs-gradle/

