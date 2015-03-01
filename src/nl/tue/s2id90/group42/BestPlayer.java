package nl.tue.s2id90.group42;

import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 *
 * @author s121924
 */
public class BestPlayer extends DraughtsPlayer {
    
    public BestPlayer() {
        super(BestPlayer.class.getResource("resources/spongebob.png"));
    }
    
    @Override
    /** @return a random move **/
    public Move getMove(DraughtsState s) {
        List<Move> moves = s.getMoves();
        return moves.get(0);
    }

    @Override
    public Integer getValue() {
        return 0;
    }
    
    public Integer alphaBeta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || node.childs.length == 1) {
            return node.childs[0].value;
        }
        if (maximizingPlayer) {
            int v = Integer.MIN_VALUE; //Effectively equal to -infinite
            for (Node child : node.childs) {
                v = Math.max(v, alphaBeta(child, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }
        else {
            int v = Integer.MAX_VALUE; //Effectively equal to -infinite
            for (Node child : node.childs) {
                v = Math.min(v, alphaBeta(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta, v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }
    }
    
}
