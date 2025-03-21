package Main;

/**
 * Interface for validating field values against a given threshold.
 * @param <T> Type of value being validated.
 * @param <E> Type of threshold used for validation.
 */
public interface FieldValidator<T, E> {

    /**
     * Validates a value against a threshold.
     * @param value The input value to validate.
     * @param threshold The validation threshold.
     * @return A message string if invalid, or null if valid.
     */
    String validate(T value, E threshold);

}
