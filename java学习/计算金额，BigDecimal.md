在计算金额的时候，可以使用 BigDecimal 类，以避免出现精度问题，且在使用时，要传入 String 类型
```
BigDecimal price1 = new BigDecimal("0.05")
BigDecimal price2 = new BigDecimal("0.01")
System.out.println(price1 + price2)
```
