package dev.jwaters.hacknotts21.type;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public sealed class Type permits BooleanType, IntType, StringType, VoidType {
    public static class TypeChooser extends JPanel {
        private final boolean includeVoid;
        private final JComboBox<TypeType> comboBox;

        public TypeChooser(boolean includeVoid) {
            this.includeVoid = includeVoid;

            TypeType[] values = TypeType.values();
            if (!includeVoid) {
                int voidIndex;
                for (voidIndex = 0; voidIndex < values.length; voidIndex++) {
                    if (values[voidIndex] == TypeType.VOID) {
                        break;
                    }
                }
                TypeType[] newValues = new TypeType[values.length - 1];
                System.arraycopy(values, 0, newValues, 0, voidIndex);
                System.arraycopy(values, voidIndex + 1, newValues, voidIndex, newValues.length - voidIndex);
                values = newValues;
            }
            this.comboBox = new JComboBox<>(values);
        }

        @Nullable
        public Type getType() {
            TypeType typeType = (TypeType) comboBox.getSelectedItem();
            if (typeType == null) {
                return null;
            }
            return switch (typeType) {
                case BOOLEAN -> BooleanType.INSTANCE;
                case INT -> IntType.INSTANCE;
                case STRING -> StringType.INSTANCE;
                case VOID -> VoidType.INSTANCE;
            };
        }

        public void setType(@Nullable Type type) {
            TypeType typeType;
            if (type instanceof BooleanType) {
                typeType = TypeType.BOOLEAN;
            } else if (type instanceof IntType) {
                typeType = TypeType.INT;
            } else if (type instanceof StringType) {
                typeType = TypeType.STRING;
            } else if (type instanceof VoidType && includeVoid) {
                typeType = TypeType.VOID;
            } else {
                throw new IllegalArgumentException("Unsupported type: " + type);
            }

            this.comboBox.setSelectedItem(typeType);
        }

        private enum TypeType {
            BOOLEAN("Boolean"),
            INT("Int"),
            STRING("String"),
            VOID("Void"),
            ;

            private final String name;

            TypeType(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return name;
            }
        }
    }
}
