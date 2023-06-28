package jp.wapio.calc.parser;

class Characters {

    private String string;

    private int index = -1;

    public Characters(String string) {

        if (string == null) {
            throw new IllegalArgumentException("string is null.");
        }

        this.string = string;
    }

    boolean hasNext() {
        return index + 1 < string.length();
    }
    
    char next() {
        return string.charAt(++index);
    }

    char getNext() {
        return hasNext() ? string.charAt(index + 1) : null;
    }

    char previous() {
        return string.charAt(--index);
    }

    int getIndex() {
        return index;
    }

    protected Characters clone() {
        var chars = new Characters(string);
        chars.index = index;
        return chars;
    }
}
