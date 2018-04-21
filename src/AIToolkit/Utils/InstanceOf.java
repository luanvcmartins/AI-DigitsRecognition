package AIToolkit.Utils;

import java.util.regex.Pattern;

/**
 * Set of utilities functions to work with datasets.
 *
 * @author Luan
 */
public class InstanceOf {

    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)"
            + "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|"
            + "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))"
            + "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    /**
     * Returns true if the item (usually a property) is can be cast to double.
     *
     * @param s The property to check if can be casted to double.
     * @return
     */
    public static boolean Double(String s) {
        return DOUBLE_PATTERN.matcher(s).matches();
    }
}
