# Kata Event Sourcing et CQRS

## Event Sourcing

1. Create an account with a simple operation 'deposit' without event

2. Transform this operation deposit to an event
    Note : an event describe a fact that already happened !
    
3. Persist account
 
5. Reload the state of an account

6. Create another operation like 'withdraw'

## Command Query Responsibility Segregation

1. Make commands as input of your system for 'deposit' and 'withdraw' 

2. Dispatch events synchronously, which are listened by a a reader

3. Make a projection of an account

4. how to solve concurrency ?
    * How to deal with a slow state rebuild ?
    * How to deal with two commands fired on a same account ?
    * How to deal with the reading order when two events come to the same account ?

5. Scalability : split them all ! 