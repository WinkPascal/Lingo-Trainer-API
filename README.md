Game
In onze één-spelervariant spelen we steeds eerst een 5-letterwoord, dan een 6-letterwoord en dan een 7-letterwoord. Vervolgens komt er weer een 5-letterwoord.
Het spel eindigt wanneer de speler het woord niet heeft geraden.
Wanneer het spel eindigt, wordt de score van de speler opgeslagen nadat diens naam is ingevuld.
Vervolgens wordt de score opgeslagen in een highscore.

woord controle
    Het woord bestaat
    Het woord is juist gespeld
    Het woord bestaat uit het gegeven aantal letters
    Het woord schrijf je niet met een hoofdletter, zoals plaats- en eigennamen
    Het woord bevat geen leestekens, zoals apostrofs, koppelstreepjes en punten


Geldige beurt feedback
    De letter komt niet in het woord voor (absent)
    De letter komt in het woord voor, maar staat niet op de juiste plek (present)
    De letter komt in het woord voor en staat op de juiste plek (correct)

tijdslimiet 10 seconde
Als het spel eindigt, kan een speler zijn naam voeren.
Deze highscore kan ook via een Web API opgevraagd worden.
de high score moet ook weer gepersisteerd worden.


Import
Woorden moeten voldoen aan
    Het woord bestaat uit het gegeven aantal letters (5, 6 of 7 letters)
    Het woord schrijf je niet met een hoofdletter, zoals plaats- en eigennamen
    Het woord bevat geen leestekens, zoals apostrofs, koppelstreepjes en punten



Testen
    Unit en integration testing: JUnit, TestNG
    Mocking: EasyMock, Mockito
    Code coverage: JaCoCo
    Mutation testing: PITest
    End-to-end testing: Cucumber, JBehave, FITnesse,
    Assertions: AssertJ, Hamcrest
    Microbenchmarks: Java Microbenchmark Harness (JMH)
    Superlinter: een linter voor allerlei talen (gebruikt specifieke linters, wel even goed instellen)


Beoordeling
Build tools en pipeline
Kunnen we het geautomatiseerd bouwen?
    

Mate van functionaliteit
Doet het genoeg?
    startGame   
    nextMove
    endGame
    getHighscore

Testorganisatie
Is op verschillende lagen getest?
    Unit en integration tests.

Mate van clean tests
Zijn FIRST principles toegepast? Is gebruik gemaakt van lifecycle methods en data providers? Is mocking gebruikt waar verstandig?

Coverage en mutation testing
 Wordt kritisch bijgehouden hoeveel er getest wordt?
 
Mate van structuur 
Dependency injection is ingezet. Design patterns worden gebruikt ten behoeve van onderhoudbaarheid.

Is het netjes geschreven?
    Principes van clean code en SOLID zijn toegepast. Er worden verklarende namen gebruikt. Er wordt input validatie uitgevoerd. Exceptions worden correct afgehandeld.
    
    
Static analysis tools
    Wordt automatisch de structurele kwaliteit geïnspecteerd ?
    Meerdere complexity analysis, code style of bug prevention tools ingezet in de build
    
Deployment
    Draait het? En draait het goed?
    

Dit criterium is aan een leerresultaat gekoppeld Creatieve ruimte 
    
 