public class RuleEngineTest {

    public static void main(String[] args) {
        RuleEngine engine = new RuleEngine();

        // Test 1: Create individual rules
        Node ast1 = engine.createRule("age > 30");
        assert ast1 != null : "AST1 should not be null";

        // Test 2: Combine rules
        Node combined = engine.combineRules(Arrays.asList(ast1));
        assert combined != null : "Combined rule should not be null";

        // Test 3: Evaluate rule
        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        boolean evaluationResult = engine.evaluateRule(ast1, data);
        assert evaluationResult : "Evaluation should return true for age > 30";

        // Test 4: Evaluate combined rules
        String rule2 = "age < 40";
        Node ast2 = engine.createRule(rule2);
        Node combinedRules = engine.combineRules(Arrays.asList(ast1, ast2));
        boolean combinedEvaluation = engine.evaluateRule(combinedRules, data);
        assert combinedEvaluation : "Combined evaluation should return true";
        
        System.out.println("All tests passed!");
    }
}
