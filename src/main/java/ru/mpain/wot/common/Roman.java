package ru.mpain.wot.common;

/**
 * User: sergey
 * Date: 4/18/12
 * Time: 7:48 PM
 */
public final class Roman {
	final static RomanValue[] ROMAN_VALUE_TABLE = {
			new RomanValue(1000, "M"),
			new RomanValue(900, "CM"),
			new RomanValue(500, "D"),
			new RomanValue(400, "CD"),
			new RomanValue(100, "C"),
			new RomanValue(90, "XC"),
			new RomanValue(50, "L"),
			new RomanValue(40, "XL"),
			new RomanValue(10, "X"),
			new RomanValue(9, "IX"),
			new RomanValue(5, "V"),
			new RomanValue(4, "IV"),
			new RomanValue(1, "I")
	};

	public static String int2roman(int number) {
		if (number >= 4000 || number < 1) {
			throw new NumberFormatException("Numbers must be in range 1-3999");
		}

		StringBuffer result = new StringBuffer(10);

		for (RomanValue equiv : ROMAN_VALUE_TABLE) {
			while (number >= equiv.intValue) {
				number -= equiv.intValue;
				result.append(equiv.romanValue);
			}
		}
		return result.toString();
	}

	public static int roman2Int(java.lang.String romanNumber) {
	    int result = 0;

	    String romanNumeral = romanNumber.toUpperCase();
	    for (int index = romanNumeral.length() - 1, lastNumber = 0; index >= 0 ; index--) {
	        char convertToDecimal = romanNumber.charAt(index);

	        switch (convertToDecimal) {
	            case 'M':
	                result = processDecimal(1000, lastNumber, result);
	                lastNumber = 1000;
	                break;

	            case 'D':
	                result = processDecimal(500, lastNumber, result);
	                lastNumber = 500;
	                break;

	            case 'C':
	                result = processDecimal(100, lastNumber, result);
	                lastNumber = 100;
	                break;

	            case 'L':
	                result = processDecimal(50, lastNumber, result);
	                lastNumber = 50;
	                break;

	            case 'X':
	                result = processDecimal(10, lastNumber, result);
	                lastNumber = 10;
	                break;

	            case 'V':
	                result = processDecimal(5, lastNumber, result);
	                lastNumber = 5;
	                break;

	            case 'I':
	                result = processDecimal(1, lastNumber, result);
	                lastNumber = 1;
	                break;
	        }
	    }

	    return result;
	}

	private static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
	    if (lastNumber > decimal) {
	        return lastDecimal - decimal;
	    } else {
	        return lastDecimal + decimal;
	    }
	}

	private static class RomanValue {
		int intValue;
		String romanValue;

		RomanValue(int dec, String rom) {
			this.intValue = dec;
			this.romanValue = rom;
		}
	}
}
