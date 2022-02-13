package dev.jwaters.hacknotts21.swing;

import dev.jwaters.hacknotts21.graph.GraphNode;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.lang.reflect.Constructor;

public class DragTransferHandler extends TransferHandler {
    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        return isStringDataSupported(support);
    }

    protected boolean isStringDataSupported(TransferSupport support) {
        if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) return true;
        DataFlavor[] flavors = support.getDataFlavors();
        for (DataFlavor dataFlavor : flavors) {
            if (dataFlavor.getRepresentationClass() == String.class) return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        if (!(support.getComponent() instanceof JComponent replacedComponent)) {
            return false;
        }

        String line;
        try {
            line = getStringData(support);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            Class<?> blockClass = Class.forName(line);
            if (GraphNode.class.isAssignableFrom(blockClass)) {
                Constructor<? extends GraphNode<?>> constructor = (Constructor<? extends GraphNode<?>>) blockClass.asSubclass(GraphNode.class).getConstructor(GraphNode.class);
                GraphNode<?> newNode = constructor.newInstance((Object) null);
                JComponent newComponent = newNode.createComponent();
                GraphNode<?> replacedNode = NodeUIUtils.getAssociatedNode(replacedComponent);
                if (replacedNode == null) {
                    return false;
                }
                replacedNode.replace(newNode);
                for (; replacedComponent != null && replacedComponent.getParent() instanceof JComponent; replacedComponent = (JComponent) replacedComponent.getParent()) {
                    if (NodeUIUtils.getDirectAssociatedNode(replacedComponent) == replacedNode) {
                        break;
                    }
                }
                if (replacedComponent == null) {
                    return false;
                }
                Container replacedParent = replacedComponent.getParent();
                Component[] children = replacedParent.getComponents().clone();
                int index;
                for (index = 0; index < children.length; index++) {
                    if (children[index] == replacedComponent) {
                        break;
                    }
                }
                children[index] = newComponent;
                replacedParent.removeAll();
                for (Component child : children) {
                    replacedParent.add(child);
                }
                replacedParent.revalidate();
                NodeUIUtils.onPropertyChanged(newComponent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(line);
        return true;
    }

    protected String getStringData(TransferSupport support)
            throws UnsupportedFlavorException, IOException {
        if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
        }
        DataFlavor[] flavors = support.getDataFlavors();
        for (DataFlavor dataFlavor : flavors) {
            if (dataFlavor.getRepresentationClass() == String.class) {
                return (String) support.getTransferable().getTransferData(dataFlavor);
            }
        }
        return "";
    }
}
