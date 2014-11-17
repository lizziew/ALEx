ALEx
====
Group: Grace, Lizzie, Ruby, and the smelly and dangerous and quite lovable wumpus

Goals: 
To make an agent in a top-down environment that can understand and execute commands, clarifying them with requests for information, and also answer requests for information. 

Aspects of Project: 
* GUI: Representation of 2-D world containing ALEx the agent & objects with various qualities such as those listed below:
  * color
  * shape
  * (heaviness/edibility - to add if we have time, they prevent certain actions)
* Text input from users
* Language processing to understand commands - specifically sentence parsing and extracting meaning from commands like:
  * Move the blue triangle
  * Move the blue thing to (1,1)
  * Eat the banana
  * Go to the star
  * Tell me where it is
* A way to retain memory of what is being discussed from command to command so it can ask for and receive clarification
* Some problem-solving skills so it knows which information to request
* How to respond:
  * I have no idea what you just said...
  * Which “blue thing?”
  * (Gross! It’s not edible.)
  * (It’s too heavy.)
  
Timeline for project:
Goals, ranked from theoretically easiest to theoretically hardest.

	•	GUI/world mechanics 
	•	Executing commands 
	•	Understanding and answering questions 
	•	Understanding commands 
	•	Figuring out what information to request to clarify commands 
	•	If we have time, additional properties of objects and how to interact with them (edibility/heaviness/etc)

Things to consider when understanding commands:

	*	Identify VERB, COORDINATES, COLOR and SHAPE (if an item is involved in the command), NEGATIVES

	*	"Move" ambiguity -> "move to [item]" vs. "move [item] to"

	*	Synonyms of "move," "pick up," "put down"

	*	Translating VERB, COORDINATES, etc. into simple command (or sequence of commands) that ALExGUI can understand

	*	Translate human spellings ("light blue") as ALEx spellings ("lightblue")
	*	Special cases: pick up ALL stars

Done: 

*  "pick up"/"put down"/"light blue" --> go to blue star --> item exists/multiple items exist/item doesn't exist 
*  Nov 11 class: respond to other verbs (pick up and put down) 
* negative commands (do not go to the blue star) Ignore everything in that clause  (used to ignore the whole command rather than just clause, but i fixed it -ruby) 
* deal with multiple verbs like "I think you should go PICK UP the ..." (interpreting it as "pick up" rather than "move") —> DONE (just changed the verb finder to if rather than else if, and put pick up after move - so it might find the go, but then it will find the pickup and change verb to that)
* hitting "Enter" makes it work, so we don't have to click the "Go" button, and showing user's previous cmnds in GUI 
* compound commands (go to blue star and pick it up) If not complete command (move + coord, pick up + item, put down + item), then iterate back until we find clause with the missing info -> DONE for everything except examples like “pick up the blue circle and star,” which is understandable given that alex still can’t deal with the ambiguity of having just a shape or a color
* store information between commands and answer back (pick up blue star...which blue star...the one at 3 3) Keep a list of previous commands, split by clause. 
* cmds like "move 2 squares to the east" etc 
* saying "i don't understand" to confusing commands rather than ignoring

Todo (some optional due to time constraints): 

*”nearest” and so on as descriptors for objects
*dealing with only having descriptors (“nearest blue thing”) rather than nice color + shape combo
*move object from [wherever] to [wherever]
*the 'all' descriptor
*descriptors like 'nearest,' 'leftmost,' 'above you'...
*answering questions like 'where is [whatever]'


