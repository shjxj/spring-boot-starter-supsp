package com.supsp.springboot.core.utils;

import cn.hutool.core.util.NumberUtil;
import com.supsp.springboot.core.consts.Constants;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class NumUtils {

    // ============= Long

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(Long num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.LONG_ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(Long num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.LONG_ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(Long num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.LONG_ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(Long num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.LONG_ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(Long num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.LONG_ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(Long num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.LONG_ZERO) <= 0;
    }

    public static boolean isTrue(Long value) {
        return gtZero(value);
    }

    public static boolean isFalse(Long value) {
        return leZero(value);
    }

    public static long value(Long value) {
        if (value == null) {
            return 0;
        }
        return value.longValue();
    }

    // ============= Integer

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(Integer num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.INT_ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(Integer num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.INT_ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(Integer num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.INT_ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(Integer num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.INT_ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(Integer num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.INT_ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(Integer num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.INT_ZERO) <= 0;
    }

    public static boolean isTrue(Integer value) {
        return gtZero(value);
    }

    public static boolean isFalse(Integer value) {
        return leZero(value);
    }

    public static int value(Integer value) {
        if (value == null) {
            return 0;
        }
        return value.intValue();
    }

    // ============= Short

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(Short num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.SHORT_ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(Short num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.SHORT_ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(Short num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.SHORT_ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(Short num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.SHORT_ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(Short num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.SHORT_ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(Short num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.SHORT_ZERO) <= 0;
    }

    public static boolean isTrue(Short value) {
        return gtZero(value);
    }

    public static boolean isFalse(Short value) {
        return leZero(value);
    }

    public static short value(Short value) {
        if (value == null) {
            return 0;
        }
        return value.shortValue();
    }

    // ============= Byte

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(Byte num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.BYTE_ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(Byte num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.BYTE_ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(Byte num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.BYTE_ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(Byte num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.BYTE_ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(Byte num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.BYTE_ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(Byte num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.BYTE_ZERO) <= 0;
    }

    public static boolean isTrue(Byte value) {
        return gtZero(value);
    }

    public static boolean isFalse(Byte value) {
        return leZero(value);
    }

    public static byte value(Byte value) {
        if (value == null) {
            return 0;
        }
        return value.byteValue();
    }

    // ============= BigDecimal

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(BigDecimal num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(BigDecimal num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(BigDecimal num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(BigDecimal num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(BigDecimal num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(BigDecimal num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(BigDecimal.ZERO) <= 0;
    }

    public static boolean isTrue(BigDecimal value) {
        return gtZero(value);
    }

    public static boolean isFalse(BigDecimal value) {
        return leZero(value);
    }

    public static BigDecimal value(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value;
    }

    public static BigDecimal discountValue(Long discount) {
        if (
                discount == null
                        || leZero(discount)
                        || discount.compareTo(Constants.DISCOUNT_HUNDRED) >= 0
        ) {
            return Constants.DISCOUNT_TEN;
        }
        return NumberUtil.toBigDecimal(
                NumberUtil.round(
                        NumberUtil.div(discount, Constants.DISCOUNT_TEN),
                        1,
                        RoundingMode.FLOOR
                )
        );
    }

    public static Long discount(BigDecimal discountValue) {
        if (
                discountValue == null
                        || leZero(discountValue)
                        || discountValue.compareTo(Constants.DISCOUNT_TEN) >= 0
        ) {
            return Constants.DISCOUNT_HUNDRED;
        }
        return NumberUtil.round(
                NumberUtil.mul(discountValue, Constants.DISCOUNT_TEN),
                0,
                RoundingMode.FLOOR
        ).longValue();
    }

    // ============= Double

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(Double num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.DOUBLE_ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(Double num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.DOUBLE_ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(Double num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.DOUBLE_ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(Double num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.DOUBLE_ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(Double num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.DOUBLE_ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(Double num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.DOUBLE_ZERO) <= 0;
    }

    public static boolean isTrue(Double value) {
        return gtZero(value);
    }

    public static boolean isFalse(Double value) {
        return leZero(value);
    }


    public static double value(Double value) {
        if (value == null) {
            return 0;
        }
        return value.doubleValue();
    }

    // ============= Float

    /**
     * == 0
     *
     * @param num
     * @return
     */
    public static boolean eqZero(Float num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.FLOAT_ZERO) == 0;
    }

    /**
     * != 0
     *
     * @param num
     * @return
     */
    public static boolean neZero(Float num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.FLOAT_ZERO) != 0;
    }

    /**
     * > 0
     *
     * @param num
     * @return
     */
    public static boolean gtZero(Float num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.FLOAT_ZERO) > 0;
    }

    /**
     * >= 0
     *
     * @param num
     * @return
     */
    public static boolean geZero(Float num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.FLOAT_ZERO) >= 0;
    }

    /**
     * < 0
     *
     * @param num
     * @return
     */
    public static boolean ltZero(Float num) {
        if (num == null) {
            return false;
        }
        return num.compareTo(Constants.FLOAT_ZERO) < 0;
    }

    /**
     * <= 0
     *
     * @param num
     * @return
     */
    public static boolean leZero(Float num) {
        if (num == null) {
            return true;
        }
        return num.compareTo(Constants.FLOAT_ZERO) <= 0;
    }

    public static boolean isTrue(Float value) {
        return gtZero(value);
    }

    public static boolean isFalse(Float value) {
        return leZero(value);
    }


    public static float value(Float value) {
        if (value == null) {
            return 0;
        }
        return value.floatValue();
    }
}
