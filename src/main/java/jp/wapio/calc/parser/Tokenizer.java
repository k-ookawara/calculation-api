package jp.wapio.calc.parser;

class Tokenizer {

    private final Characters chars;

    Tokenizer(String arg) {
        this.chars = new Characters(arg);
    }

    Tokenizer(Characters chars) {
        this.chars = chars;
    }

    String next() {

        StringBuilder sb = new StringBuilder();

        while (chars.hasNext()) {

            char ch = chars.next();

            // 文字列
            if (ch == '"') {
                sb.append(ch);
                while (chars.hasNext()) {
                    char nextCh = chars.next();
                    sb.append(nextCh);
                    if (nextCh == '"') {
                        return sb.toString();
                    }
                }
                throw new ParseException("string must be enclosed in double quotes.");
            }

            // 数値
            if (isDigit(ch)) {
                sb.append(ch);
                boolean isDecimal = false;
                while (chars.hasNext()) {
                    char nextCh = chars.next();
                    if (isDigit(nextCh)) {
                        sb.append(nextCh);
                    } else if (nextCh == '.' && !isDecimal) {
                        isDecimal = true;
                        sb.append(nextCh);
                    } else {
                        chars.previous();
                        break;
                    }
                }
                return sb.toString();
            }

            // 括弧、カンマ、演算子
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '(' || ch == ')' || ch == ',') {
                return String.valueOf(ch);
            }
            if (ch == '!') {
                if (chars.getNext() == '=') {
                    return sb.append(ch).append(chars.next()).toString();
                } else {
                    throw new ParseException("invalid character.");
                }
            }
            if (ch == '<' || ch == '>') {
                if (chars.getNext() == '=') {
                    return sb.append(ch).append(chars.next()).toString();
                } else {
                    return String.valueOf(ch);
                }
            }

            // 関数、変数
            if (ch == '&' || ch == '$') {
                sb.append(ch);
                while (chars.hasNext()) {
                    ch = chars.next();
                    if (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ('0' <= ch && ch <= '9') || ch == '_') {
                        sb.append(ch);
                    } else {
                        chars.previous();
                        break;
                    }
                }
                return sb.toString();
            }

            if (' ' == ch || ',' == ch) {
                continue;
            }

            throw new IllegalArgumentException(ch + " is invalid value.");
        }

        return null;
    }

    private boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    String getNext() {
        Tokenizer clone = this.clone();
        return clone.next();
    }

    boolean hasNext() {
        return chars.hasNext();
    }

    protected Tokenizer clone() {
        return new Tokenizer(chars.clone());
    }

    int getIndex() {
        return chars.getIndex();
    }
}
