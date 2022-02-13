package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.MainForm;
import dev.jwaters.hacknotts21.type.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CallFunctionNode extends GraphNode<CallFunctionNode.Panel> {
    @Nullable
    @Expose
    private String functionName;

    @Expose
    private List<GraphNode<?>> parameters = new ArrayList<>();

    private Panel currentPanel;

    public CallFunctionNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public @Nullable Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        if (functionName != null) {
            var optFunctionRepr = getFunction();

            if (optFunctionRepr.isPresent() && parameters.contains(child)) {
                var functionRepr = optFunctionRepr.get();
                int index = parameters.indexOf(child);
                return functionRepr.getParams().get(index).getType();
            } else return null;
        } else return null;
    }

    @NotNull
    private Optional<FunctionRepr> getFunction() {
        return MainForm.getInstance().getFunctions().stream().filter(f -> f.getName().equals(functionName)).findFirst();
    }

    @Override
    public @Nullable Type getReturnType(FunctionRepr containingFunc) {
        if (functionName != null) {
            return getFunction().map(FunctionRepr::getReturnType).orElse(null);
        } else return null;
    }

    @Override
    protected Panel makeComponent() {
        return new Panel(
                parameters.stream().map(GraphNode::createComponent).collect(Collectors.toList())
        );
    }

    @Override
    public void readFromComponent(Panel component) throws UserInputException {
        functionName = ((String) component.functionName.getSelectedItem());

        for (int i = 0; i < parameters.size(); i++) {
            doReadFromComponent(parameters.get(i), component.parameters.get(i));
        }
    }

    @Override
    public void writeToComponent(Panel component) {
        if (functionName != null) {
            component.functionName.setName(functionName);
        }

        for (int i = 0; i < parameters.size(); i++) {
            doWriteToComponent(parameters.get(i), component.parameters.get(i));
        }
    }

    @Override
    public List<@Nullable GraphNode<?>> getChildren() {
        return parameters;
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        parameters.set(index, newChild);
    }

    public static class Panel extends JPanel {
        private final JComboBox<String> functionName;
        private final List<JComponent> parameters;

        public Panel(List<JComponent> parameters) {
            functionName = new JComboBox<>();

            functionName.addActionListener(e -> {
                for (var param : parameters) {
                    remove(param);
                }

                parameters.clear();


            });

            this.parameters = parameters;

            setLayout(new FlowLayout());

            add(new JLabel("Call "));
            for (JComponent param : parameters) {
                add(param);
            }
            add(functionName);
            updateGlobalFunctionNames();
        }

        private void updateGlobalFunctionNames() {
            functionName.setModel(new DefaultComboBoxModel<>(
                    MainForm.getInstance().getFunctions().stream().map(FunctionRepr::getName).toArray(String[]::new)
            ));
        }
    }
}
