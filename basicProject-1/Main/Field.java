package Main;

/**
 * A Field class used for input of values like Bank Name or Bank Passcode.
 * @param <T> The type of field value (e.g., String, Double, Integer).
 * @param <E> The type used for threshold comparison in validation.
 */
public class Field<T, E> {

    private final Class<T> fieldType;
    private T fieldValue;
    private final E threshold;
    private final FieldValidator<T, E> fieldValidator;

    public Field(String fieldName, Class<T> fieldType, E threshold, FieldValidator<T, E> validator) {
        this.fieldType = fieldType;
        this.threshold = threshold;
        this.fieldValidator = validator;
        // fieldName removed as it's unused
    }

    public T getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String promptText) {
        setFieldValue(promptText, true);
    }

    @SuppressWarnings("unchecked")
    public void setFieldValue(String promptText, boolean inlineInput)
            throws ClassCastException, NumberFormatException {
            
        String inputValue = null;
            
        while (true) {
            try {
                inputValue = Main.prompt(promptText, inlineInput);
                fieldValue = fieldType.cast(inputValue);
            } catch (ClassCastException error) {
                try {
                    if (fieldType == Double.class) {
                        fieldValue = (T) stringToDouble(inputValue);
                    } else if (fieldType == Integer.class) {
                        fieldValue = (T) stringToInteger(inputValue);
                    }
                } catch (NumberFormatException error2) {
                    // silent catch, fieldValue stays null
                }
            } finally {
                if (fieldValue != null) {
                    String validation = fieldValidator.validate(fieldValue, threshold);
                    if (validation == null) {
                        break;
                    } else {
                        System.out.println(validation);
                    }
                } else {
                    System.out.println("Invalid input given!");
                }
            }
        }
    }


    private Double stringToDouble(String val) {
        return Double.parseDouble(val);
    }

    private Integer stringToInteger(String val) {
        return Integer.parseInt(val);
    }

    public static class DoubleFieldValidator implements FieldValidator<Double, Double> {
        public String validate(Double value, Double threshold) {
            if (value < threshold) {
                return "Field input must be greater or equal to: " + threshold;
            }
            return null;
        }
    }

    public static class IntegerFieldValidator implements FieldValidator<Integer, Integer> {
        public String validate(Integer value, Integer threshold) {
            if (value < threshold) {
                return "Field input must be greater than or equal to: " + threshold;
            }
            return null;
        }
    }

    public static class StringFieldValidator implements FieldValidator<String, String> {
        public String validate(String value, String threshold) {
            if (value.isEmpty()) {
                return "Field cannot be empty!";
            }
            return null;
        }
    }

    public static class StringFieldLengthValidator implements FieldValidator<String, Integer> {
        public String validate(String value, Integer threshold) {
            if (value.length() < threshold) {
                return "Field must have at least " + threshold + " characters";
            }
            return null;
        }
    }
}
