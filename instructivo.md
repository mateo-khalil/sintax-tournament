# Obligatorio - Introducción Teórica

# JFlex y CUP: Herramientas para Análisis Léxico y Sintáctico

## Introducción

En el procesamiento de lenguajes formales, el análisis de cadenas de texto se divide tradicionalmente en dos fases principales:

1. **Análisis Léxico**: Convierte la secuencia de caracteres en tokens (unidades léxicas)
2. **Análisis Sintáctico**: Organiza los tokens según las reglas gramaticales del lenguaje

**JFlex** y **CUP** son herramientas que automatizan estos procesos, permitiendo implementar reconocedores de lenguajes de manera eficiente.
## Diferencias Fundamentales: Análisis Léxico vs Análisis Sintáctico

### Visión General del Proceso

```
Texto de entrada → [Análisis Léxico] → Tokens → [Análisis Sintáctico] → Estructura
```

### Análisis Léxico (Lexical Analysis)

#### ¿Qué hace?
El análisis léxico divide el texto de entrada en unidades más pequeñas llamadas **tokens** o **lexemas**.

#### Nivel de Operación
- Opera a nivel de caracteres individuales
- Agrupa caracteres según patrones específicos
- No considera relaciones entre tokens

#### Ejemplo Práctico

**Entrada**: `"2 pan_integral lechuga;"`

**Proceso léxico**:
- `2` → Token: `QUANTITY ("2")`
- `pan_integral` → Token: `PAN ("pan_integral")`
- `lechuga` → Token: `ING ("lechuga")`
- `;` → Token: `SEMI (";")`

#### Características Clave
- **Secuencial**: Procesa de izquierda a derecha
- **Local**: Cada token se reconoce independientemente
- **Sin memoria**: No recuerda tokens anteriores
- **Basado en patrones**: Usa expresiones regulares
### Análisis Sintáctico (Syntax Analysis)

#### ¿Qué hace?
El análisis sintáctico organiza los tokens en estructuras que reflejan la gramática del lenguaje.

#### Nivel de Operación
- Opera a nivel de tokens (no caracteres)
- Construye relaciones jerárquicas entre tokens
- Considera el contexto y la estructura global

#### Ejemplo Práctico

**Tokens**: `[QUANTITY, PAN, ING, SEMI]`

**Proceso sintáctico**:
```
┌─ SANDWICH ─────────────────────┐
│ ┌─ QUANTITY ("2")              │
│ ┌─ PAN ("pan_integral")        │
│ ┌─ ING ("lechuga")             │
│ └─ SEMI (";")                  │
└─────────────────────────────────┘
```

#### Características Clave
- **Jerárquico**: Construye árboles de derivación
- **Contextual**: Considera relaciones entre elementos
- **Con memoria**: Mantiene estado de la pila
- **Basado en reglas**: Usa gramáticas libres de contexto
### Comparación Detallada

| Aspecto | Análisis Léxico | Análisis Sintáctico |
|---------|-----------------|---------------------|
| **Entrada** | Secuencia de caracteres | Secuencia de tokens |
| **Salida** | Secuencia de tokens | Árbol sintáctico/Estructura |
| **Fundamento Teórico** | Expresiones regulares | Gramáticas libres de contexto |
| **Autómata** | Finito Determinista (AFD) | De pila (Push-down) |
| **Complejidad** | O(n) - Lineal | O(n³) - Cúbica (en general) |
| **Memoria** | Sin memoria (estado actual) | Con memoria (pila) |
| **Errores** | Caracteres ilegales | Secuencias inválidas de tokens |
### Tipos de Errores

#### Errores Léxicos

**Entrada**: `"2 pan_integral@ lechuga;"` // @ es ilegal en tipo de pan

**Error**: `"Carácter ilegal '@' en token de pan"`

- **Qué detecta**: Caracteres que no forman ningún token válido
- **Cuándo ocurre**: Durante la formación de tokens

#### Errores Sintácticos

**Tokens**: `[QUANTITY, ING, PAN, SEMI]` // Orden incorrecto: ingrediente antes que pan

**Error**: `"Se esperaba PAN después de QUANTITY"`

- **Qué detecta**: Secuencias de tokens que no siguen la gramática
- **Cuándo ocurre**: Durante la construcción de la estructura
### Ejemplos Prácticos con el Problema

#### Nivel Léxico - Reconocimiento de Patrones

```java
// JFlex - Definición de tokens
[1-9][0-9]*                    → QUANTITY
"pan_blanco"|"pan_integral"    → PAN
"tomate"|"lechuga"|"jamon"     → ING
";"                            → SEMI
```

**Responsabilidad**: "¿Es esto un ingrediente válido?"

#### Nivel Sintáctico - Estructura Gramatical

```java
// CUP - Reglas de estructura
expr_list ::= expr_list sandwich | sandwich
sandwich  ::= QUANTITY PAN ING SEMI
```

**Responsabilidad**: "¿Estos tokens forman un sandwich válido?"
### Interdependencia

El análisis sintáctico **DEPENDE** del léxico:
- No puede funcionar sin tokens bien formados
- Asume que los tokens son correctos léxicamente

#### Ejemplo de interacción:

1. Léxico ve `"pan_integral"` → produce `PAN`
2. Sintáctico ve `PAN` después de `QUANTITY` → válido según gramática
3. Sintáctico ve `ING` después de `PAN` → válido según gramática
4. Sintáctico ve `QUANTITY` después de `ING` → **ERROR** (esperaba `SEMI`)
### ¿Por qué esta separación?

#### Ventajas de la Separación

1. **Modularidad**: Cada fase tiene responsabilidades claras
2. **Reutilización**: Los tokens pueden usarse en múltiples contextos gramáticos
3. **Eficiencia**: Algoritmos especializados para cada tarea (AFD vs Autómata de Pila)
4. **Mantenimiento**: Cambios en patrones léxicos no requieren modificar la gramática
5. **Debugging**: Errores más específicos y localizados en cada fase

> Ver ejemplos detallados en el **Anexo A** al final del documento.
### Complejidad Teórica

#### Análisis Léxico (Regulares):
- Memoria finita (estados del AFD)
- No puede contar o balancear
- Perfecto para patrones simples

#### Análisis Sintáctico (Libres de Contexto):
- Memoria de pila (infinita en teoría)
- Puede manejar estructuras anidadas y recursivas
- Perfecto para estructura jerárquica
### Ejemplo Completo: `"2 pan_integral lechuga;"`

#### Fase 1: Análisis Léxico

```
Entrada: 2 p a n _ i n t e g r a l   l e c h u g a ;
         ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
Tokens:  [QUANTITY:2] [PAN:pan_integral] [ING:lechuga] [SEMI:;]
```

#### Fase 2: Análisis Sintáctico

```
Tokens → Estructura:
    SANDWICH
    ├── QUANTITY ("2")
    ├── PAN ("pan_integral")
    ├── ING ("lechuga")
    └── SEMI (";")
```
### Conclusión

El análisis léxico y sintáctico son fases complementarias que operan en diferentes niveles de abstracción:

- **Léxico**: "¿Qué son estos caracteres?" → Convierte caracteres en tokens
- **Sintáctico**: "¿Cómo se relacionan estos tokens?" → Organiza tokens en estructura

Esta división permite aplicar las herramientas teóricas más apropiadas para cada tarea y construir analizadores eficientes y mantenibles.

---

## JFlex - Generador de Analizadores Léxicos
### ¿Qué es JFlex?

JFlex es un generador de analizadores léxicos (lexers) para Java. Toma como entrada una especificación de tokens usando expresiones regulares y genera código Java que puede reconocer estos patrones en texto de entrada.

### Fundamentos Teóricos

#### Relación con Autómatas Finitos
- JFlex convierte expresiones regulares en **Autómatas Finitos Deterministas (AFD)**
- Cada expresión regular define un **lenguaje regular**
- El AFD resultante reconoce exactamente las cadenas que pertenecen a ese lenguaje

#### Proceso de Compilación

```
Expresiones Regulares → AFN → AFD → Código Java
```

1. **AFN** (Autómata Finito No Determinista): Construcción directa desde ER
2. **AFD**: Determinización del AFN usando el algoritmo de construcción de subconjuntos
3. **Minimización**: Optimización del AFD para reducir estados
4. **Generación de código**: Traducción a código Java ejecutable
### Estructura de un archivo JFlex

Un archivo `.flex` tiene tres secciones:

```java
%%
// Sección 1: Declaraciones de usuario
package ejemplo;
import java.io.*;

%%
// Sección 2: Opciones y declaraciones de JFlex
%class Scanner
%line
%column
%unicode

LETRA = [a-zA-Z]
DIGITO = [0-9]
NUMERO = {DIGITO}+

%%
// Sección 3: Reglas léxicas
{NUMERO}      { return new Token(NUM, yytext()); }
{LETRA}+      { return new Token(ID, yytext()); }
[ \t\n\r]+    { /* ignorar espacios */ }
.             { throw new Error("Carácter ilegal: " + yytext()); }
```
#### Sección 1: Código de Usuario
- Imports de Java necesarios
- Declaración de paquetes
- Código que se copiará directamente al archivo generado

#### Sección 2: Opciones y Macros
- `%class`: Nombre de la clase generada
- `%line`, `%column`: Seguimiento de posición en el texto
- `%unicode`: Soporte para caracteres Unicode
- **Macros**: Definiciones de patrones reutilizables

#### Sección 3: Reglas de Reconocimiento
- **Patrón → Acción**: Cada línea define qué hacer cuando se encuentra un patrón
- **Prioridad por orden**: La primera regla que coincida se ejecuta
- `yytext()`: Función que retorna el texto reconocido
### Tokens y Reconocimiento

#### ¿Qué es un Token?

Un token es una unidad léxica que representa:
- **Tipo**: Categoría del token (número, identificador, operador, etc.)
- **Valor**: El texto específico reconocido
- **Posición**: Línea y columna donde aparece (opcional)

#### Ejemplo de Tokenización

**Entrada**: `"2 pan_integral lechuga;"`

**Tokens**:
- `QUANTITY`: "2"
- `PAN`: "pan_integral"
- `ING`: "lechuga"
- `SEMI`: ";"

---

## CUP - Constructor de Analizadores Sintácticos
### ¿Qué es CUP?

CUP (Constructor of Useful Parsers) es un generador de analizadores sintácticos para Java. Utiliza el algoritmo **LALR(1)** para construir parsers que pueden reconocer gramáticas libres de contexto.

### Fundamentos Teóricos

#### Gramáticas Libres de Contexto
- CUP trabaja con **Gramáticas Libres de Contexto (GLC)**
- Estas gramáticas pueden expresar estructuras recursivas y anidadas
- Son más poderosas que las expresiones regulares

#### Algoritmo LALR(1)
- **LALR**: Look-Ahead LR
- **LR**: Left-to-right scan, Rightmost derivation
- **1**: Un símbolo de lookahead
- Construye un Autómata de Pila para el reconocimiento

#### Relación con Autómatas de Pila

```
Gramática Libre de Contexto → Autómata de Pila → Parser LALR(1)
```
### Estructura de un archivo CUP

Un archivo `.cup` tiene las siguientes secciones:

```java
// Importaciones y código de usuario
import java.io.*;

// Declaración de símbolos
terminal QUANTITY, PAN, ING, SEMI;
non terminal archivo, sandwich, ingredientes;

// Precedencia y asociatividad (si es necesario)
precedence left SEMI;

// Símbolo inicial
start with archivo;

// Reglas de producción
archivo ::= sandwich
          | archivo SEMI sandwich
          ;

sandwich ::= QUANTITY PAN ingredientes SEMI
           ;

ingredientes ::= ING
               | ingredientes ING
               ;
```
### Elementos Principales

#### Símbolos Terminales y No Terminales
- **Terminales**: Tokens que provienen del analizador léxico
- **No terminales**: Símbolos que se derivan en la gramática

#### Reglas de Producción
- **Formato**: `no_terminal ::= secuencia_de_simbolos`
- **Alternativas**: Usando `|` para múltiples producciones
- **Acciones**: Código Java que se ejecuta cuando se aplica la regla

#### Símbolo Inicial
- Define desde dónde comenzar el análisis sintáctico
- Corresponde al axioma de la gramática
### Proceso de Análisis Sintáctico

#### Algoritmo Shift-Reduce

1. **Shift**: Mover el próximo token a la pila
2. **Reduce**: Aplicar una regla de producción (reemplazar símbolos por no terminal)
3. **Accept**: Entrada válida reconocida
4. **Error**: Entrada no válida

#### Tabla de Análisis LALR(1)

CUP genera una tabla que determina la acción a tomar basándose en:
- Estado actual de la pila
- Símbolo de entrada (lookahead)

---

## Integración JFlex + CUP
### Flujo de Trabajo

```
Texto de entrada → JFlex (Lexer) → Tokens → CUP (Parser) → Árbol Sintáctico
```

#### 1. El Lexer procesa caracteres

```java
// JFlex genera esta clase
Scanner lexer = new Scanner(input);
Token token = lexer.next_token();
```

#### 2. El Parser consume tokens

```java
// CUP genera esta clase
Parser parser = new Parser(lexer);
parser.parse(); // Retorna el resultado del análisis
```
### Comunicación entre Componentes

#### Definición de Tokens Compartida

```java
// Archivo Symbol.java (generado por CUP)
public class Symbol {
    public static final int QUANTITY = 1;
    public static final int PAN = 2;
    public static final int ING = 3;
    // ...
}
```

#### En JFlex

```java
// Uso de constantes de CUP
\d+                         { return new Symbol(Symbol.QUANTITY, yytext()); }
pan_(integral|blanco)       { return new Symbol(Symbol.PAN, yytext()); }
(lechuga|tomate|pollo)      { return new Symbol(Symbol.ING, yytext()); }
```

#### En CUP

```java
// Declaración correspondiente
terminal String QUANTITY, PAN, ING;
```

---

## Aplicación al Ejercicio
### Análisis del Problema

Para el sistema de procesamiento de sandwiches:

#### Tokens a Reconocer (JFlex)

```java
QUANTITY:  \d+
PAN:       pan_(integral|blanco)
ING:       (lechuga|tomate|pollo|palta|queso)
SEMI:      ;
```

#### Estructura Sintáctica (CUP)

```java
archivo ::= sandwich
          | archivo SEMI sandwich
          ;

sandwich ::= QUANTITY PAN ingredientes SEMI
           ;

ingredientes ::= ING
               | ingredientes ING
               ;
```
### Ventajas de esta Aproximación

1. **Separación de responsabilidades**: Léxico vs Sintáctico
2. **Reutilización**: Los tokens pueden usarse en múltiples contextos
3. **Mantenibilidad**: Cambios en patrones no afectan la gramática
4. **Eficiencia**: AFD optimizado + tabla LALR(1)
5. **Manejo de errores**: Localización precisa de errores léxicos y sintácticos

---

## Conceptos Teóricos Aplicados
### De la Materia a la Práctica

#### Expresiones Regulares → JFlex
- Cada patrón en JFlex es una expresión regular
- JFlex construye el AFD correspondiente
- Relación directa con lenguajes regulares

#### Gramáticas Libres de Contexto → CUP
- Las reglas de CUP forman una GLC
- El parser implementa un autómata de pila
- Reconoce lenguajes libres de contexto

### Jerarquía de Chomsky

```
Tipo 3 (Regulares) ⊂ Tipo 2 (Libres de Contexto) ⊂ Tipo 1 ⊂ Tipo 0
        ↑                       ↑
      JFlex                    CUP
```
### Limitaciones y Capacidades

#### JFlex (Regulares)
✅ Patrones simples, tokens independientes  
❌ Estructuras anidadas o balanceadas  
❌ Conteo o memoria de contexto

#### CUP (Libres de Contexto)
✅ Estructuras recursivas y anidadas  
✅ Balanceo de símbolos  
❌ Dependencias de contexto complejo  
❌ Verificación semántica

---

## Conclusión
La combinación de JFlex y CUP proporciona un marco robusto para implementar reconocedores de lenguajes, aplicando directamente los conceptos teóricos de la materia:

- **Autómatas finitos** para el análisis léxico
- **Autómatas de pila** para el análisis sintáctico
- **Expresiones regulares** para definir tokens
- **Gramáticas libres de contexto** para definir la estructura

Este enfoque permite construir analizadores eficientes y mantenibles para lenguajes de complejidad práctica, estableciendo una base sólida para el desarrollo de intérpretes y compiladores.

---

# Anexo A: Ejemplos Detallados de las Ventajas de la Separación

## A.1. Reutilización de Tokens en Múltiples Contextos

**Problema**: Supongamos que nuestro sistema crece y ahora debe procesar dos tipos de archivos:

- **Archivo Tipo A (Original)**: `2 pan_integral lechuga;`
- **Archivo Tipo B (Nuevo)**: `ORDEN:COMIDA_RAPIDA|2,pan_blanco,tomate,pollo|12:30`
```java
// Los MISMOS tokens léxicos sirven para ambos formatos:
\d+                         { return new Token(QUANTITY, yytext()); }      // "2"
pan_(integral|blanco)       { return new Token(PAN, yytext()); }           // "pan_integral"
(lechuga|tomate|pollo)      { return new Token(ING, yytext()); }           // "lechuga", "tomate"
[A-Z_]{5,20}                { return new Token(TIPO, yytext()); }          // "COMIDA_RAPIDA"
\d{2}:\d{2}                 { return new Token(HORA, yytext()); }          // "12:30"
```

**Gramáticas DIFERENTES para cada formato:**

```java
// Gramática Tipo A (original)
archivo_A ::= sandwich_A | archivo_A SEMI sandwich_A ;
sandwich_A ::= QUANTITY PAN ingredientes ;

// Gramática Tipo B (nueva)
archivo_B ::= TIPO DOSPUNTOS lista_sandwiches ;
lista_sandwiches ::= sandwich | lista_sandwiches PIPE sandwich ;
sandwich ::= QUANTITY COMA PAN COMA ING COMA ING PIPE HORA ;
```

**Parser A (formato original):**
```java
archivo_A ::= lista_sandwiches ;
lista_sandwiches ::= sandwich | lista_sandwiches sandwich ;
sandwich ::= QUANTITY PAN ingredientes SEMI ;
ingredientes ::= ING | ingredientes ING ;
```

**Parser B (formato nuevo):**
```java
archivo_B ::= TIPO DOSPUNTOS lista_entidades ;
lista_entidades ::= entidad | lista_entidades PIPE entidad ;
entidad ::= QUANTITY COMA PAN COMA ING COMA ING PIPE HORA ;
```

**Ventaja**: Los tokens `QUANTITY`, `PAN`, `ING`, `TIPO` se reutilizan sin cambios, solo cambia la gramática.
## A.2. Cambios en Patrones NO Afectan la Gramática

**Escenario**: El cliente decide cambiar el formato de los tipos de pan.

**Cambio Requerido**:
- **Antes**: `pan_(integral|blanco)` (ej: "pan_integral", "pan_blanco")
- **Después**: `[A-Z][a-z]{5,12}` (ej: "Integral", "Blanco")

**Solución**:

```java
// SOLO cambia el patrón léxico:
// pan_(integral|blanco)     // VIEJO
[A-Z][a-z]{5,12}              // NUEVO
{ return new Token(PAN, yytext()); }
```

**La gramática NO CAMBIA**:

```java
// Esta regla sigue siendo exactamente igual:
ingredientes ::= ING | ingredientes ING ;
sandwich ::= QUANTITY PAN ingredientes SEMI ;
```

**Ventaja**:
- ✅ Cambio aislado en una sola línea de JFlex
- ✅ Cero cambios en CUP
- ✅ Toda la lógica sintáctica se mantiene
- ✅ Menos riesgo de introducir errores
## A.3. Ejemplo de Cambio Complejo - Nuevos Tipos de Porciones

**Escenario**: Agregar porciones especiales que incluyen subcódigos.

**Nuevo Requerimiento**:
- **Antes**: Solo cantidad: `"2 pan_integral lechuga"`
- **Después**: Agregar porciones especiales: `"2(XL) pan_integral lechuga"` (tamaño extra grande)

**Cambio Léxico (JFlex)**:

```java
// Modificar regla existente:
\d+              { return new Token(QUANTITY, yytext()); }
\d+\([A-Z]+\)    { return new Token(QUANTITY_ESPECIAL, yytext()); }  // NUEVO
```

**Cambio Sintáctico (CUP)**:

```java
// Regla original:
ingredientes ::= ING | ingredientes ING ;

// Regla extendida:
ingredientes ::= porcion PAN lista_ing ;

porcion ::= QUANTITY
          | QUANTITY_ESPECIAL ;  // NUEVO

lista_ing ::= ING
            | lista_ing ING ;
```

**Ventaja**: Cada herramienta maneja lo que mejor sabe hacer:
- **JFlex**: Reconocer patrones complejos con expresiones regulares
- **CUP**: Manejar alternativas sintácticas con reglas de producción
## A.4. Debugging Específico y Localizado

**Ejemplo de Error Léxico**:

```
Entrada: "2@pan_integral lechuga;"  // @ es ilegal en cantidad
Error JFlex: "Carácter ilegal '@' en línea 1, columna 2"
```

**Ejemplo de Error Sintáctico**:

```
Tokens: [SEMI, PAN, ING, QUANTITY, ...]  // Orden incorrecto
Error CUP: "Error sintáctico en línea 1: se esperaba QUANTITY después del inicio, se encontró SEMI"
```

**Ventaja**:
- **Errores específicos**: Sabes exactamente qué herramienta falló
- **Localización precisa**: Línea y columna exactas
- **Mensajes claros**: Qué se esperaba vs qué se encontró
## A.5. Ejemplo Real: Evolución del Sistema

**Iteración 1 - Sistema Básico**:

```java
\d+                         { return new Token(QUANTITY); }
pan_(integral|blanco)       { return new Token(PAN); }
(lechuga|tomate|pollo)      { return new Token(ING); }
```

**Iteración 2 - Distinción de Ingredientes**:

```java
\d+                         { return new Token(QUANTITY); }
pan_(integral|blanco)       { return new Token(PAN); }
(lechuga|tomate|pollo)      { return new Token(ING_VEGETAL); }   // Más específico
(queso|jamon|palta)         { return new Token(ING_PROTEIN); }   // Distinguir categorías
;                           { return new Token(SEMI); }
```

**Iteración 3 - Validación de Cantidad**:

```java
\d+   { if(validarCantidad(yytext())) return new Token(QUANTITY);
        else throw new Error("Cantidad inválida"); }
pan_(integral|blanco)       { return new Token(PAN); }
(lechuga|tomate|pollo)      { return new Token(ING); }
0                           { throw new Error("Cantidad no puede ser cero"); }
```

**Gramática CUP (NO cambia en las iteraciones)**:

```java
sandwich ::= QUANTITY PAN ingredientes SEMI ;
ingredientes ::= ING | ingredientes ING ;
```

Solo en Iteración 2 cambió la gramática:

```java
// ingredientes ::= ING | ingredientes ING ;  // Iteración 1
ingredientes ::= ING_VEGETAL lista_ing | ING_PROTEIN lista_ing ;  // Iteración 2+
lista_ing ::= ING_VEGETAL | ING_PROTEIN | lista_ing ING_VEGETAL | lista_ing ING_PROTEIN ;
```
## A.6. Contraejemplo: ¿Qué pasaría SIN separación?

**Hipotético analizador monolítico**:

```java
// MALO: Todo mezclado
if (siguienteCaracteres.matches("\\d+")) {
    if (siguientesCaracteres.matches("pan_(integral|blanco)")) {
        if (despuesViene("(lechuga|tomate|pollo)")) {
            if (despuesVieneOtroIngrediente()) {
                if (despuesVieneOtraCantidad()) {
                    // ¡Reconocer sandwich completo!
                }
            }
        }
    }
}
```

**Problemas**:
- ❌ Código complejo y difícil de leer
- ❌ Cambios requieren reescribir toda la lógica
- ❌ Errores difíciles de localizar
- ❌ No reutilizable para otros formatos de alimentos
- ❌ Mezcla niveles de abstracción
## A.7. Beneficio de Algoritmos Especializados

**JFlex usa algoritmos optimizados para expresiones regulares**:
- Construcción Thompson (ER → AFN)
- Determinización por subconjuntos (AFN → AFD)
- Minimización de estados (AFD optimizado)
- **Resultado**: Reconocimiento en **O(n)** tiempo lineal

**CUP usa algoritmos optimizados para gramáticas**:
- Construcción de tabla LALR(1)
- Algoritmo shift-reduce optimizado
- Manejo eficiente de la pila
- **Resultado**: Análisis sintáctico eficiente

**Ventaja**: Cada herramienta usa el algoritmo más apropiado para su tarea.