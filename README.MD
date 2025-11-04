# Football Tournament Forecasting System

Analizador de pronósticos de campeonatos de fútbol usando **JFlex** (análisis léxico) y **CUP** (análisis sintáctico).

## Quick Start (Maven - Recommended)

### Prerequisites

- Java JDK 8+
- Maven 3.6+

### Build and Run

```bash
mvn clean generate-sources
mvn compile
mvn exec:java -Dexec.args="test/fixtures/valid_tournament.txt"
```

### Or package as JAR

```bash
mvn package
java -jar target/sintax-tournament-1.0.0.jar test/fixtures/valid_tournament.txt
```

## Features

- Lexical analysis with JFlex
- Syntactic analysis with CUP
- Complete validation (4/8/12 series, 4 teams per series, max 4 (X) marks)
- Automatic ranking calculation
- Scoring system: 2pts result + 1pt per goal + 2pts exact bonus (×2 with X mark)
- Cumulative error reporting
- Console output

## Maven Commands

```bash
mvn clean                    # Clean generated files
mvn generate-sources         # Generate Scanner.java and parser.java
mvn compile                  # Compile all classes
mvn exec:java                # Run application
mvn package                  # Create JAR file
```

## Input File Format

```
Campeonato "Tournament Name"

SERIE "Group Name"
Equipos: [Team1, Team2, Team3, Team4]

Partido Nro: 1
YYYY/MM/DD - "Stadium"
TeamLocal Goals - TeamVisitor Goals

---

Participante: Full Name - email@example.com
Pronósticos Partidos:
1: Team1 2 - Team2 1
(X) 2: Team3 0 - Team4 0

---
```

## Validations

- **Lexical**: Date format (YYYY/MM/DD), valid email, non-negative integers
- **Syntactic**: Correct file structure and section ordering
- **Semantic**: 4/8/12 series, exactly 4 teams per series, max 4 (X) marks, team existence, match consistency

## Scoring System

| Achievement            | Base Points | With (X) |
| ---------------------- | ----------- | -------- |
| Result (win/draw/loss) | 2           | 4        |
| Local team goals       | 1           | 2        |
| Visitor team goals     | 1           | 2        |
| Exact score bonus      | +2          | +4       |
| **Max per match**      | 6           | 12       |

## Test Files

```bash
mvn exec:java -Dexec.args="test/fixtures/valid_tournament.txt"      # Valid
mvn exec:java -Dexec.args="test/fixtures/invalid_series.txt"        # Invalid series count
mvn exec:java -Dexec.args="test/fixtures/invalid_marks.txt"         # Too many (X)
mvn exec:java -Dexec.args="test/fixtures/invalid_email.txt"         # Bad email
mvn exec:java -Dexec.args="test/fixtures/invalid_team.txt"          # Team not in fixture
```

## Troubleshooting

### "mvn command not found"

Install Maven:

```bash
# Ubuntu/Debian
sudo apt install maven

# macOS
brew install maven
```

### Build fails

```bash
mvn clean
mvn generate-sources
mvn compile
```
