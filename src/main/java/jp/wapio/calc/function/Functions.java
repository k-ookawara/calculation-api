package jp.wapio.calc.function;

import java.util.EnumMap;
import java.util.Map;

import jp.wapio.calc.function.impl.ADD;
import jp.wapio.calc.function.impl.DIV;
import jp.wapio.calc.function.impl.IF;
import jp.wapio.calc.function.impl.MUL;
import jp.wapio.calc.function.impl.RND;
import jp.wapio.calc.function.impl.SUB;

public class Functions {

    private static final Map<Functions.Type, Function> INSTANCES = new EnumMap<Type, Function>(Functions.Type.class);

    static {
        INSTANCES.put(Functions.Type.ADD, ADD.getInstance());
        INSTANCES.put(Functions.Type.SUB, SUB.getInstance());
        INSTANCES.put(Functions.Type.MUL, MUL.getInstance());
        INSTANCES.put(Functions.Type.DIV, DIV.getInstance());
        INSTANCES.put(Functions.Type.EQ, IF.getInstance(Functions.Type.EQ));
        INSTANCES.put(Functions.Type.NE, IF.getInstance(Functions.Type.NE));
        INSTANCES.put(Functions.Type.GT, IF.getInstance(Functions.Type.GT));
        INSTANCES.put(Functions.Type.GE, IF.getInstance(Functions.Type.GE));
        INSTANCES.put(Functions.Type.LS, IF.getInstance(Functions.Type.LS));
        INSTANCES.put(Functions.Type.LE, IF.getInstance(Functions.Type.LE));
        INSTANCES.put(Functions.Type.RND, RND.getInstance(Functions.Type.RND));
        INSTANCES.put(Functions.Type.RNDU, RND.getInstance(Functions.Type.RNDU));
        INSTANCES.put(Functions.Type.RNDD, RND.getInstance(Functions.Type.RNDD));
    }

    public static Function get(String name) {
        return get(Functions.Type.lookupBy(name));
    }

    public static Function get(Functions.Type type) {
        return get(type.name(), type);
    }

    private static Function get(String name, Functions.Type type) {
        return INSTANCES.get(type);
    }

    public static enum Type {
        /** 加算 */
        ADD("ADD"),
        /** 減算 */
        SUB("SUB"),
        /** 乗算 */
        MUL("MUL"),
        /** 除算 */
        DIV("DIV"),
        /** 等価 */
        EQ("EQ"),
        /** 不等価 */
        NE("NE"),
        /** 超過 */
        GT("GT"),
        /** 以上 */
        GE("GE"),
        /** 未満 */
        LS("LS"),
        /** 以下 */
        LE("LE"),
        /** 四捨五入 */
        RND("RND"),
        /** 切り上げ */
        RNDU("RNDU"),
        /** 切り下げ */
        RNDD("RNDD")
        ;

        private String name;

        private Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Type lookupBy(String name) {
            for (Type type : values()) {
                if (type.name.equals(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid argument. arg=%s".formatted(name));
        }
    }
}
