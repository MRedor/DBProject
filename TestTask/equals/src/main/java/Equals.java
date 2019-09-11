import java.util.ArrayList;
import java.util.List;

public class Equals {
    private Literal[] first;
    private Literal[] second;


    public Equals(String first, String second) {
        this.first = parse(first);
        this.second = parse(second);
    }

    private Literal[] parse(String s) {
        List<Literal> list = new ArrayList<>();

        char op = '-';
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            var current = s.charAt(i);
            if (current == '+' || current == '*') {
                if (op != '-') {
                    list.add(new Literal(op, num));
                }
                op = s.charAt(i);
                num = 0;
            } else {
                num = num * 10 + current;
            }
        }
        if (op != '-') {
            list.add(new Literal(op, num));
        }

        Literal[] result = new Literal[list.size()];
        list.toArray(result);
        return result;
    }

    public boolean equals() {
        int mults = 0;
        int balance = 0;

        var maxlen = Math.min(first.length, second.length);
        for (int i = 0; i < maxlen; i++) {
            if (i < first.length && first[i].operator == '*') {
                mults += first[i].number;
                for (int j = 0; j < i; j++) {
                    first[j].number *= first[i].number;
                }
            }
            if (i < second.length && second[i].operator == '*') {
                mults -= second[i].number;
                for (int j = 0; j < i; j++) {
                    second[j].number *= second[i].number;
                }
            }
        }

        if (mults != 0) {
            return false;
        }

        for (int i = 0; i < maxlen; i++) {
            if (i < first.length && first[i].operator == '+') {
                balance += first[i].number;
            }
            if (i < second.length && second[i].operator == '+') {
                balance -= second[i].number;
            }
        }

        return (balance == 0);
    }

    private class Literal {
        private char operator;
        private int number;

        private Literal(char operator, int number) {
            this.operator = operator;
            this.number = number;
        }
    }
}
