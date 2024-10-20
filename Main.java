import java.util.*;

public class Main {
    public static void main(String[] args) {
        RuleEngine engine = new RuleEngine();

        // Sample rules
        String rule1 = "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)";
        String rule2 = "((age > 30 AND department = 'Marketing')) AND (salary > 20000 OR experience > 5)";

        Node ast1 = engine.createRule(rule1);
        Node ast2 = engine.createRule(rule2);

        List<Node> rules = Arrays.asList(ast1, ast2);
        Node combinedAST = engine.combineRules(rules);

        // Sample user data
        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        data.put("department", "Sales");
        data.put("salary", 60000);
        data.put("experience", 3);

        boolean result = engine.evaluateRule(combinedAST, data);
        System.out.println("User eligibility: " + result); // Output: User eligibility: true
    }
}
