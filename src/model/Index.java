package model;

import java.io.Serializable;
import java.util.Objects;

public class Index implements Serializable {
    int row, column;

    // Constructor
    public Index(int oRow, int oColumn) {
        this.row = oRow;
        this.column = oColumn;
    }
    // Constructor
    public Index(Index idx) {
        this.row = idx.getRow();
        this.column = idx.getColumn();
    }
    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return row == index.row &&
                column == index.column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
