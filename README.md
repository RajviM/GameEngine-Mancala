# GameEngine-Mancala
 Developed a game engine for the ‘mancala’ that would generate the next best possible move.Greedy Algorithm, Minimax Algorithm and Alpha Beta Pruning were used 
 
 
##TASK
To write a program to determine the next move by implementing the following algorithms: 
1.Greedy 
2.Minimax
3.Alpha-Beta 

# Mancala Game
https://en.wikipedia.org/wiki/Mancala


###Evaluation function: 
The goal of the game is to collect maximum number of stones by the end of the game and to win the game.  
The evaluation function chosen :   
For any legal move is computed as the difference between the numbers of stones in both players’ Mancala if that move is chosen.   
E(p) = #Stones_player - #Stones_opponent 

#Rules and Conditions:
Tie breaking and Expand order:  
Ties between pits are broken by selecting the node that is first in the position order on the figure above.
 For example, if all legal moves for Player-1 (B2, B3, B4, B5, B5, B6, and B7) have the same evaluated values,   the program must pick B2 according to tie breaker rule. Same rule applies for Player-2. 

Board size: 
The board size will be 2xN along with a mancala for each player, where N represents the number of pits for a player and 3≤N≤10.   
The board size for the mancala board shown above would be 2x6.    
The initial number of stones in each pit can be maximum 1000. 



#Input Format
Task# Greedy=1, MiniMax=2, Alpha-Beta=3   
Your player: 1 or 2   
Cutting off depth    
Board state for player-2   
Board state for player-1    
No of stones in player-2’s mancala   
No of stones in player-1’s mancala 


#Output Format
Greedy: 
Line-1 represents the board state for player-2, i.e. the upper side of the board.    
Line-2 represents the board state for player-1, i.e. the upper side of the board.    
Line-3 gives you the number of stones in player-2’s mancala.   
Line-4 gives you the number of stones in player-1’s mancala.    



Additionally there is Traverse Log for Minimax and Alpha Beta Pruning:    
Node,Depth,Value,Alpha,Beta 
