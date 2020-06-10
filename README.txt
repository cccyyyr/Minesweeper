CIS 120 Game Project README
PennKey: _CathyCh1__
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D-Array. I have a 2D-array of integers that represent the state of my board. It's a neat way to
  represent the board as I can easily store the state and also modify it quite efficiently.

  2. Collection. My board has two states - a set of ordered pairs that keep track of which cells 
  	have been revealed and a set of ordered pairs that keep track of which zeros have been checked.
  	The first state is so that I can keep track when the users have won the game and the second state
  	is to keep track which zero cell i have recursed on so "zeroProtocol" function won't go into
  	infinite looping. I used Sets so I wouldn't have duplicated cells and since the order of elements
  	don't matter, set is an ideal data structure in this case. I can also rely on the .size() function
	to check if all cells have been uncovered.
  3. Recursion. I used recursion in zeroProtocol, a function i call when the user click on a cell that
  	has 0 mines around it. It's necessary to use recursion b/c the game should recursively check the cells
  	around it until some none-zero number show up.
  4. File I/O. I used file I/O that allow users, during a game, to store a game state and try flagging 
  	a few mines to see if it makes sense, and if the number of mines don't add up, they can always click 
  	retrieve and go back to the state they saved. 


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
	An ordered pair class is class that stores the x, y value as Java doesn't have a tuple as
 a data type. I used it to basically identify the cells and give them a unique identity. 
 	A new game class which basically starts a new game. Buttons like new game, retrieve, save and
 	a label that show the game status is added.
 	An internal board class that extends a JPanel and keep track of the state of the board.
 	A file line iterator class from pennpal that helps with reading files.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
	Recursion was challenging. I couldn't do recursion in a for-loop at first and then after a lot
	of debugging i realize the problem lied with this other part of my code.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
	I think the states are pretty well encapsulated. I could try refactor my internal board better so
	it will be easier for me to write tests but other than that, i think i have a pretty succinct format. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I used the images from https://github.com/janbodnar/Java-Minesweeper-Game/tree/master/src/resources
