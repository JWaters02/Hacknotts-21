package dev.jwaters.hacknotts21.graph;

import com.google.gson.annotations.Expose;
import dev.jwaters.hacknotts21.swing.DragTransferHandler;
import dev.jwaters.hacknotts21.type.Type;
import dev.jwaters.hacknotts21.type.VoidType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public final class BlockNode extends GraphNode<JPanel> {
    @Expose
    private final List<GraphNode<?>> children = new ArrayList<>();

    public BlockNode(@Nullable GraphNode<?> parent) {
        super(parent);
    }

    @Override
    public Type getExpectedChildType(GraphNode<?> child, FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    public Type getReturnType(FunctionRepr containingFunc) {
        return VoidType.INSTANCE;
    }

    @Override
    protected JPanel makeComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (GraphNode<?> child : children) {
            panel.add(child.createComponent());
        }
        return panel;
    }

    @Override
    public void readFromComponent(JPanel component) throws UserInputException {
        for (int i = 0; i < children.size(); i++) {
            GraphNode<?> child = children.get(i);
            JComponent childComponent = (JComponent) component.getComponent(i);
            doReadFromComponent(child, childComponent);
        }
    }

    @Override
    public void writeToComponent(JPanel component) {
        for (int i = 0; i < children.size(); i++) {
            GraphNode<?> child = children.get(i);
            JComponent childComponent = (JComponent) component.getComponent(i);
            doWriteToComponent(child, childComponent);
        }
    }

    @Override
    public List<GraphNode<?>> getChildren() {
        return children;
    }

    @Override
    public void replaceChild(int index, GraphNode<?> newChild) {
        children.set(index, newChild);
    }

    @Override
    public void replace(GraphNode<?> newNode) {
        BlockNode blockNode = (BlockNode) newNode;
        children.clear();
        children.addAll(blockNode.children);
    }

    @Override
    protected void addDragHandler(JPanel component) {
        component.setTransferHandler(new DragTransferHandler() {
            @Override
            protected boolean isSupportedForImport(Component component) {
                return true;
            }

            @Override
            protected boolean handleDrop(TransferSupport support, GraphNode<?> replacedNode, JComponent replacedComponent, Constructor<? extends GraphNode<?>> constructor) throws Exception {
                GraphNode<?> newNode = constructor.newInstance(replacedNode);
                JComponent newComponent = newNode.createComponent();
                if (newNode instanceof BlockNode) {
                    return super.handleDrop(support, replacedNode, replacedComponent, constructor);
                }
                int dropY = support.getDropLocation().getDropPoint().y;
                int insertionIndex = replacedComponent.getComponentCount();
                for (int index = 0; index < replacedComponent.getComponentCount(); index++) {
                    int y = replacedComponent.getComponent(index).getLocation().y;
                    int nextY = index + 1 < replacedComponent.getComponentCount() ? replacedComponent.getComponent(index + 1).getLocation().y : replacedComponent.getY() + replacedComponent.getHeight();
                    if (dropY < nextY) {
                        if (dropY - y < nextY - dropY) {
                            insertionIndex = index;
                        } else {
                            insertionIndex = index + 1;
                        }
                        break;
                    }
                }

                replacedNode.getChildren().add(insertionIndex, newNode);
                replacedComponent.add(newComponent, insertionIndex);

                return true;
            }
        });
    }
}
