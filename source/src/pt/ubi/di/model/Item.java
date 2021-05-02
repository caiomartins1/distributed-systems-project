package pt.ubi.di.model;

import java.io.Serializable;

/**
 * Class for an item
 * it could be improved by adding more information/variables
 * It serves the purpose of representing a single piece(item) of a part
 * A part keeps a stock of these
 */
public class Item implements Serializable {
    String id;

    public Item(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                '}';
    }
}
