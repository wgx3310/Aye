package aye.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by reid on 2016/11/26.
 */

public class Block<T> extends DisplayItem implements Serializable {
    private static final long serialVersionUID = 1L;
    public ArrayList<T> items;
    public ArrayList<Block<T>> blocks;

    public String toString(){
        return "\n\nBlock: " + super.toString() + "\n\tblocks: " +blocks + " \n\titems:"+items + "\n\tend\n\n\n";
    }
}
