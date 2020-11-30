public class CalculatorController {
    private CalculatorModel calcModel;

    public CalculatorController() {
        calcModel = new CalculatorModel();
    }

    public String[] update(String action) {
        if (action.equals("Graph")) {
            return calcModel.evaluateGraph();
        }
        return calcModel.performAction(action);

    }

}

