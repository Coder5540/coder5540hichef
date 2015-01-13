package com.aia.hichef.attribute;

public class CommonProcess {

	public static int getNext(int curentValue, int min, int max) {
		curentValue = clamp(curentValue, min, max);
		if (curentValue == max)
			return min;
		
		return (curentValue + 1);
	}

	public static int getPrevious(int curentValue, int min, int max) {
		curentValue = clamp(curentValue, min, max);
		if (curentValue == min)
			return max;

		return (curentValue -1);
	}

	public static int clamp(int curentValue, int min, int max) {
		if (curentValue <= min)
			curentValue = min;
		if (curentValue >= max)
			curentValue = max;
		return curentValue;
	}
}
