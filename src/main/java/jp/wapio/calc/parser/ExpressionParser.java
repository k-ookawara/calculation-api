package jp.wapio.calc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 計算式パーサー
 */
public class ExpressionParser {

    private Tokenizer tokenizer;
    private String token;

    public ExpressionParser(String arg) {
        tokenizer = new Tokenizer(arg);
    }

    private final Map<String, String> OPERATOR_TO_FUNCTION = Map.of(
            "+",  "&ADD",
            "-",  "&SUB",
            "*",  "&MUL",
            "/",  "&DIV",
            "=",  "&EQ",
            "!=", "&NE",
            ">",  "&GT",
            ">=", "&GE",
            "<",  "&LS",
            "<=", "&LE"
    );

    public record Node(List<String> strings, Node left, Node right) {

        Node(List<String> string) {
            this(string, null, null);
        }

        public List<String> expression() {
            List<String> expr = new ArrayList<>();
            if (left != null) {
                expr.addAll(left.expression());
            }
            if (right != null) {
                expr.addAll(right.expression());
            }
            expr.addAll(strings);
            return expr;
        }
    }

    public List<String> parse() {
        return low().expression();
    }

    private Node low() {
        Node node = mid();
        while ("=".equals(token)
                  || "!=".equals(token)
                  || "<=".equals(token)
                  || ">=".equals(token)
                  || "<".equals(token)
                  || ">".equals(token)) {
            String s = token;
            Node right = low();
            node = new Node(List.of(OPERATOR_TO_FUNCTION.get(s)), node, right);
        }
        return node;
    }

    private Node mid() {
        Node node = high();
        while ("+".equals(token) || "-".equals(token)) {
            String s = token;
            Node right = high();
            node = new Node(List.of(OPERATOR_TO_FUNCTION.get(s)), node, right);
        }
        return node;
    }

    private Node high() {
        Node node = parseToken();
        while ("*".equals(token) || "/".equals(token)) {
            String s = token;
            Node right = parseToken();
            node = new Node(List.of(OPERATOR_TO_FUNCTION.get(s)), node, right);
        }
        return node;
    }

    private Node parseToken() {
        Node node = null;
        String token = next();
        while (",".equals(token)) {
            token = next();
        }
        if ("(".equals(token)) {
            node = low();
            if (")".equals(this.token)) {
                next();
            } else {
                throw new ParseException();
            }
        } else if ("+".equals(token) || "-".equals(token)) {
            String number = next();
            if (number.matches("^[0-9]+$")) {
                node = new Node(List.of(token + number));
                next();
            } else {
                throw new ParseException();
            }
        } else if (token.startsWith("&")) {
            node = new Node(parseFunction());
        } else if (token.equals("true") || token.equals("false") || token.equals("TRUE") || token.equals("FALSE")) {
            node = new Node(List.of(token.toLowerCase()));
            next();
        } else {
            node = new Node(List.of(token));
            next();
        }
        return node;
    }
    

    private List<String> parseFunction() {
        List<String> strings = new ArrayList<>();
        String functionName = token;
        if (!next().equals("(")) {
            throw new ParseException();
        }
        if (functionName.equals("&IF")) {
            strings.addAll(parse());
            List<String> arg = parse();
            List<String> arg2 = parse();
            strings.add("@" + (arg.size() + 1));
            strings.addAll(arg);
            strings.add("%" + arg2.size());
            strings.addAll(arg2);
        } else {
            while (hasNext()) {
                if (token.equals(")")) {
                    break;
                } else {
                    strings.addAll(parse());
                }
            }
            strings.add(functionName);
        }
        return strings;
    }

    private boolean hasNext() {
        return tokenizer.hasNext();
    }

    private String next() {
        return token = tokenizer.next();
    }
}
