# Problema: Analizador de Pronósticos de Fútbol

## Descripción del Dominio

Un sistema de pronósticos de fútbol procesa información sobre campeonatos que contienen series de equipos y partidos jugados entre ellos. Los usuarios del sistema pueden hacer pronósticos sobre los resultados de partidos individuales y sobre las posiciones finales de los equipos en cada serie.

## ¿Qué es un Pronosticador de Campeonato de Fútbol?

Un pronóstico de un campeonato consta del **Fixture del Campeonato** y de los **Pronósticos** enviados por los participantes.

Un campeonato está organizado en series, donde cada serie agrupa siempre a 4 equipos que compiten entre sí. Cada participante envía sus datos junto con el resultado de cada partido. El sistema debe manejar:

- **Series de equipos**: Grupos de equipos que compiten entre sí durante el campeonato
- **Partidos individuales**: Con resultados específicos y información del encuentro
- **Pronósticos de partidos**: Predicciones sobre resultados específicos
## Estructura del Campeonato

Un campeonato contiene:

- Series de equipos, que pueden ser **12, 8 o 4 series**
- Cada serie tiene **4 equipos**
- El fixture se arma con los partidos que los equipos de cada serie juegan entre sí
- Cada partido se juega en una fecha, a una hora y en un estadio determinado
- Cada participante realiza un único pronóstico, con el resultado de cada partido del fixture
- Los participantes se identifican por un **nombre** y un **email**
- Cada participante puede marcar, con una **(X)**, hasta **cuatro partidos** a los cuales elige puntear doble
## Ejemplo de Estructura

### Campeonato "Mundial 2026"

#### Fixture

**SERIE "Grupo A"**
- Equipos: [Japón, Qatar, Alemania, Uruguay]

```
Partido Nro: 1
2026/06/15 - "Estadio Monumental"
Argentina 3 - Qatar 2
...
Partido Nro: 8
2026/06/16 - "Estadio Porahi"
Alemania 3 - Uruguay 2
```

**SERIE "Grupo F"**
- Equipos: [México, Argentina, Portugal, Senegal]

```
Partido Nro: 30
2026/07/05 - "Antelcito Arena"
México 1 - Portugal 2
...
Partido Nro: 32
2026/06/26 - "Azteca Stadium"
Argentina 3 - Senegal 0
```

---

#### Pronósticos de Participantes

**Participante: Pepe Repepe** - pepe@mail.com
```
Pronósticos Partidos:
1: Japón 1 - Qatar 2
2: Uruguay 1 - Alemania 2
...
30: México 1 - Portugal 2
32: Argentina 3 - Senegal 0
```

---

**Participante: Yosepo Coinada** - y.coinada@yahoo.com
```
Pronósticos Partidos:
1: Japón 1 - Qatar 2
2:(X) Uruguay 1 - Alemania 2
...
30:(X) México 1 - Portugal 2
32: Argentina 0 - Senegal 1
```

---

**Juego: Juanchi Quasigol** - quasi@gol.com
```
...
72: Cabo Frio 0 - Francia 0
```
---

## Problema a Resolver

**Objetivo**: Diseñar e implementar un analizador léxico (JFlex) y un analizador sintáctico (CUP) que procese archivos de pronósticos de fútbol que incluyan tanto el fixture del campeonato como los pronósticos individuales de cada participante.

## Requisitos Mínimos

1. Reconocer la estructura completa: campeonato, fixture, y pronósticos de participantes
2. Identificar y validar la cantidad de series correcta
3. Identificar las series con sus equipos (exactamente 4 por serie)
4. Procesar partidos del fixture con todos sus datos (número, fecha, estadio, equipos, resultado)
5. Manejar pronósticos individuales de cada participante con su información personal
6. Procesar pronósticos de partidos con la marca (X) para puntos dobles
7. Validar que cada participante tenga como máximo 4 pronósticos marcados con (X)
8. Reportar errores de formato cuando sea necesario
## Consideraciones de Diseño

- ¿Cómo validar que cada serie tenga exactamente 4 equipos?
- ¿Cuál debería ser el límite máximo de pronósticos con puntos dobles (X) por participante?
- ¿Cómo manejar diferentes formatos de fecha, nombres de estadios y datos de participantes?
- ¿Deben validarse que los equipos en los pronósticos coincidan con los del fixture?
- ¿Cómo estructurar la gramática para manejar tanto resultados reales como pronósticos?
- ¿Cómo generar información adicional a partir del archivo parseado?
## Desafíos Adicionales (Opcionales)

Los estudiantes pueden considerar implementar:

### A. Validación de pronósticos
Validación cruzada de partidos (que los equipos en pronósticos coincidan con los del fixture)

### B. Generar Ranking de Participantes
Calcular y generar automáticamente el ranking de participantes basado en sus aciertos/puntaje y en el puntaje definido.

**Puntaje:**
- **Acierto ganador/perdedor/empate**: 2 puntos
- **Acierto goles de un equipo**: 1 punto
- **Bonificación resultado exacto**: 2 puntos extras

### C. Aceptar diferentes formatos de fecha
Aceptar diferentes formatos de fecha:
- `dd/mm/aaaa`
- `dd|mm|aa`
- `aaaa-mm-dd`
- `20-Ago-2026`
- etc.

### D. Extensión "Estadísticas del Campeonato"
Generar estadísticas como:
- Equipos más goleados
- Promedio de goles por partido
- Equipo mejor pronosticado
- etc.

### E. Cantidad de puntos dobles
Validación de límites personalizables para marcas (X) de puntos dobles

---

## Conclusión

El objetivo es crear un sistema flexible que maneje tanto la información del campeonato como los pronósticos de múltiples participantes, permitiendo diferentes enfoques y niveles de complejidad según los criterios de cada implementación.