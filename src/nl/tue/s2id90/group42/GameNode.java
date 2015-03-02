/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group42;

/**
 *
 * @author s132303
 */
public class GameNode {
    
    
    
    public Integer alphabeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){
        if  (depth == 0 || node.childs.length == 1)
            return node.childs[0].value;
        if (maximizingPlayer) {
            for (Node child : node.childs) {
                alpha = Math.max(alpha, alphabeta(child, depth-1, alpha, beta, false));
                if (beta <= alpha)
                    break;
            }
            return alpha;
        }else{
            for (Node child : node.childs) {
                beta = Math.min(beta, alphabeta(child, depth-1, alpha, beta, true));    
                if (beta <= alpha)
                    break;
            }
            return beta;
        }
    }
    
}
