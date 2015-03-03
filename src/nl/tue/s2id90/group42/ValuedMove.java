/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group42;

import org10x10.dam.game.Move;

/**
 *
 * @author s132303
 */
public class ValuedMove extends Move{
    public int value;
    public ValuedMove(Move move){
        super(move);
    }
}
