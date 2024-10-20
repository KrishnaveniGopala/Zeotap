import java.util.*;

public class RuleEngine {

    public Node createRule(String ruleString) {
        return parseRule(ruleString);
    }

    private Node parseRule(String ruleString) {
        // A simple parser to convert the rule string into an AST.
        Stack<Node> stack = new Stack<>();
        String[] tokens = ruleString.split(" ");

        for (String token : tokens) {
            if (token.equals("AND") || token.equals("OR")) {
                Node right = stack.pop();
                Node left = stack.pop();
                stack.push(new Node("operator", left, right, token));
            } else if (token.contains(">") || token.contains("<") || token.contains("=")) {
                String[] parts = token.split("(?<=[><=])");
                String attribute = parts[0].trim();
                String operator = parts[1].trim();
                String value = parts[2].trim();
                stack.push(new Node("operand", null, null, attribute + " " + operator + " " + value));
            }
        }

        return stack.isEmpty() ? null : stack.pop();
    }

    public Node combineRules(List<Node> rules) {
        Node root = null;
        for (Node rule : rules) {
            if (root == null) {
                root = rule;
            } else {
                root = new Node("operator", root, rule, "OR"); // Combine with OR for demonstration
            }
        }
        return root;
    }

    public boolean evaluateRule(Node ast, Map<String, Object> data) {
        if (ast.type.equals("operand")) {
            return evaluateOperand(ast, data);
        } else if (ast.type.equals("operator")) {
            return evaluateOperator(ast, data);
        }
        return false;
    }

    private boolean evaluateOperand(Node operandNode, Map<String, Object> data) {
        String[] parts = operandNode.value.split(" ");
        String attribute = parts[0];
        String operator = parts[1];
        Object compareValue = parseValue(parts[2].replace("'", "").trim());

        Object dataValue = data.get(attribute);

        // Comparison logic based on the operator
        switch (operator) {
            case ">":
                return ((Comparable<Object>) dataValue).compareTo(compareValue) > 0;
            case "<":
                return ((Comparable<Object>) dataValue).compareTo(compareValue) < 0;
            case "=":
                return dataValue.equals(compareValue);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private boolean evaluateOperator(Node operatorNode, Map<String, Object> data) {
        boolean leftResult = evaluateRule(operatorNode.left, data);
        boolean rightResult = evaluateRule(operatorNode.right, data);
        
        return operatorNode.value.equals("AND") ? (leftResult && rightResult) : (leftResult || rightResult);
    }

    private Object parseValue(String value) {
        // Simple parsing logic for basic types
        if (value.matches("\\d+")) {
            return Integer.parseInt(value);
        } else if (value.matches("\\d+\\.\\d+")) {
            return Double.parseDouble(value);
        } else {
            return value; // Treat as string
        }
    }
}
