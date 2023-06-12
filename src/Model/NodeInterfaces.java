package Model;

import java.util.ArrayList;

class NodeInterfaces {
    @FunctionalInterface
    static interface EgdeFunctionContainer {
        void activate(Core core);
    }

    @FunctionalInterface
    static interface NodeNameFunctionContainer {
        String activate(Core core);
    }

    @FunctionalInterface
    static interface EdgeNameFunctionContainer {
        String activate(ArrayList<Integer> data, Core core);
    }

    @FunctionalInterface
    static interface DataGeneratorFunctionContainer {
        ArrayList<ArrayList<Integer>> activate(Core core);
    }

    @FunctionalInterface
    static interface AccumulatorFunctionContainer {
        void activate(ArrayList<Integer> data, Core core);
    }

    @FunctionalInterface
    static interface EdgeCreatorCheckerContainer {
        boolean activate(Core core);
    }

}
