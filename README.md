# PingPongMusic

PingPongMusic is a random music generator and harmonizer that accompanies a ping-pong game.
The project utilizes the idea of “home”->”away”->”home” as the basis for any composition. Any piece starts at a certain chord, gradually moves to a different chord then moves back to the original chord.
The rules of consonance and dissonance govern the movement from “home” to “away” and back. If it is possible to move from one chord to another without changing more than two notes in the chord at a time, then the move is acceptable.
The program first develops a random tune using this idea.
It then revisits this tune and begins to add a second note to each note in an attempt to harmonize the original tune.
This is again done by making sure that two notes will only be added together if the resulting chord does not cause too much of a change in chords.
The program keeps running as many of these cycles as possible until there is a fully harmonized tune.
The idea is to attach sensors to ping-pong paddles that trigger a note whenever they strike a ball. Every strike of the ball moves the process a step further.
To hear a fully harmonized, the players will have to play out a long rally.
This adds another dimension to a ping-pong game.
