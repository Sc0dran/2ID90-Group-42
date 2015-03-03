/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group42;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 *
 * @author s132303
 */
public class GameNode {
    
    private final DraughtsState state;
    private ValuedMove bestMove;
    
    public List<ValuedMove> moves;
    
    public GameNode(DraughtsState state){
        this.state = state;
        moves = new ArrayList<ValuedMove>();
        for(Move move : state.getMoves()){
            this.moves.add(new ValuedMove(move));
        }
    }
    
    public DraughtsState getState() {
        return state;
    }
    
    public void setBestMove(Move move) {
        bestMove = (ValuedMove)move;
    }
            
    public Move getBestMove() {
        return bestMove;
    }
}
