package nl.tue.s2id90.group42;

import nl.tue.s2id90.group42.BitBoard.BitBoardMove;
import nl.tue.s2id90.group42.BitBoard.BitBoardDirection;
import nl.tue.s2id90.group42.BitBoard.BitBoardMoveList;
import nl.tue.s2id90.group42.BitBoard.BitBoardPlayer;
import nl.tue.s2id90.group42.BitBoard.BitBoard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 *
 * @author s121924
 */
public class BitBot extends DraughtsPlayer {
    
    private boolean stop = false;
    private boolean isWhite;
    
    private static final long ODDSQUARE = 0b11111000001111100000111110000011111000001111100000L;
    private static final long EVENSQUARE = 0b00000111110000011111000001111100000111110000011111L;
    
    @Override
    public void stop() { stop = true; }
    
    public BitBot() {
        super(BitBot.class.getResource("resources/spongebob.png"));
    }
    
    @Override
    public Integer getValue() {
        return 0;
    }

    @Override
    public Move getMove(DraughtsState s) {
        BitBoardPlayer player;
        if (s.isWhiteToMove()){
            player = BitBoardPlayer.PLAYER2;
        } else {
            player = BitBoardPlayer.PLAYER1;
        }
        
	BitBoard board = new BitBoard(s);
        //System.out.println(s.toString());
        //System.out.println(board.toString());
        List<Move> moves = s.getMoves();
        if (moves.size() == 1)
            return moves.get(0);
        Map<String, Move> nextStates = new HashMap<>();
        for (Move move : moves) {
            s.doMove(move);
            BitBoard next = new BitBoard(s);
            nextStates.put(next.toString(), move);
            //System.out.println(next.toString());
            s.undoMove(move);
        }
        
        //Generate best move from alpha beta pruning
        BitBoardMove bestmove = search(player, player.mine(board), player.his(board), board.kings);
        //Failsafe
        try {
            player.applyMove(board, bestmove);
        } catch (NullPointerException ex){
            System.out.println("Something went wrong");
            Collections.shuffle(moves);
            return moves.get(0);
        }
        System.out.println("__________________");
        System.out.println(board.toString());
        //Find correct move
        //Null-move failsafe
        if (nextStates.get(board.toString()) != null){
            return nextStates.get(board.toString());
        } else {
            System.out.println("Something went wrong");
            Collections.shuffle(moves);
            return moves.get(0);
        }
    }
        
    private BitBoardMove search(BitBoardPlayer player, long mine, long his, long kings){
        long depth = 0;
        long max = -1000;
        BitBoardMove best = null;

        // find possible moves
        BitBoardMoveList moves = new BitBoardMoveList(player, mine, his, kings);

        if (moves.size() == 1){
            best = moves.firstElement();
        } else
                // try to look a little further ahead as long as there is time
                while (! stop)
                {
                    depth++;

                    // try each possible move - there's going to be some reordering as well
                    for (int i = 0; i < moves.size(); i++)
                    {
                        BitBoardMove move = moves.get(i);
                        
                        try {
                            // find value of move
                            move.value = -alphaBeta(depth, -1000, -max, player.other, move.applyHis(his), move.applyMine(mine), move.applyKings(kings, player.crownrow)
                            );
                        } catch (AIStoppedException ex) {
                            return best;
                        }

                        // remember best move so far
                        if (move.value > max)
                        {
                            moves.remove(i);
                            moves.insertElementAt(move, 0);

                            max = move.value;
                            best = move;
                        }

                    }

                        //System.out.printf("search moves %2d %s %d\n", depth, moves, max);
                }
        System.out.printf("search depth=%d evaluation=%d\n", depth, max);
        return best;
    }
    
    private long alphaBeta(long depth, long alpha, long beta, BitBoardPlayer player, long mine, long his, long kings) throws AIStoppedException
	{
            if (stop){
                stop = false;
                throw new AIStoppedException();

            }
            if (depth == 0)
                // search more if jumps are possible
                if (canJump(player, mine, his, kings))
                    return alphaBeta(depth, -2, alpha, beta, player, mine, his, kings);

                // return static evaluation of boardstate
                else
                    return evaluate(mine, his, kings);

            // try all possible moves
            BitBoardMoveList moves = new BitBoardMoveList(player, mine, his, kings);

            if (moves.size() == 0)
                return evaluate(mine, his, kings);

            for (BitBoardMove move : moves)
            {
                // find value of move
                long value = -alphaBeta(depth - 1,-beta,-alpha,player.other,move.applyHis(his),move.applyMine(mine),move.applyKings(kings, player.crownrow)
                );

                // save value of best move so far
                if (value > alpha)
                {
                    alpha = value;

                    // a better alternative was possible in previous move for the other player
                    if (alpha >= beta) 
                        return alpha;
                }

                // stop if time is up
                if (stop)
                    return alpha;
            }

            return alpha;
	}
	
	private long alphaBeta(long depth, long maxdepth, long alpha, long beta, BitBoardPlayer player, long mine, long his, long kings) throws AIStoppedException
	{
            if (stop){
                stop = false;
                throw new AIStoppedException();

            }
            if (depth <= maxdepth)
                    return evaluate(mine, his, kings);

            // try all possible moves
            BitBoardMoveList moves = new BitBoardMoveList(player, mine, his, kings);

            if (moves.size() == 0)
                return evaluate(mine, his, kings);

            for (BitBoardMove move : moves)
            {
                // find value of move
                long value = -alphaBeta(
                                depth - 1,
                                maxdepth,
                                -beta,
                                -alpha,
                                player.other,
                                move.applyHis(his),
                                move.applyMine(mine),
                                move.applyKings(kings, player.crownrow)
                );

                // save value of best move so far
                if (value > alpha)
                {
                    alpha = value;

                    // a better alternative was possible in previous move for the other player
                    if (alpha >= beta) 
                        return alpha;
                }

                // stop if time is up
                if (stop)
                    return alpha;
            }

            return alpha;
	}

	private long evaluate(long mine, long his, long kings)
	{
            return Long.bitCount(mine & ~kings) + 5 * Long.bitCount(mine & kings)
                     - Long.bitCount(his  & ~kings) - 5 * Long.bitCount(his  & kings);

	}
	
	private boolean canJump(BitBoardPlayer player, long mine, long his, long kings)
	{
            for (BitBoardDirection dir : player.directions)
            {
                // only use kings if direction requires it
                long source = mine;

                // find origins of empty destination squares of jumps
                long jumpdest = ((source & dir.jumps) << dir.jump_shl) >>> dir.jump_shr;
                long jumporig = ((jumpdest & ~(mine | his)) >>> dir.jump_shl) << dir.jump_shr;

                // find origins of squares on which a capture is possible
                long captdest = (((source & dir.moves & EVENSQUARE) << dir.move_shl_e) >>> dir.move_shr_e)
                                         | (((source & dir.moves & ODDSQUARE) << dir.move_shl_o) >>> dir.move_shr_o);

                long captorig = (((captdest & his & ODDSQUARE) >>> dir.move_shl_e) << dir.move_shr_e)
                                         | (((captdest & his & EVENSQUARE) >>> dir.move_shl_o) << dir.move_shr_o);

                // can jump and capture from at least one square, position not stable
                if ((jumporig & captorig) != 0)
                    return true;
            }
		
            // position stable
            return false;			
	}
}
