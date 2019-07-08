matrix(a, b, c, d, e, f) 矩阵化之后如下

<img src="https://github.com/HanLess/experience/blob/master/css/img/%E7%9F%A9%E9%98%B5%E8%AE%A1%E7%AE%97.png" />

其中 x， y 是原点坐标 （0， 0），原点 x ，y 固定为 0

新坐标（x' , y'）如下：

x' = ax+cy+e

y' = bx+dy+f

### translate

matrix(1, 0, 0, 1, e, f)  ===  translate(e + "px", f + "px")

x' = x + e 

y' = y + f

即 translate 只跟 e，f 有关

### rotate

matrix(cosθ, sinθ, -sinθ, cosθ, 0, 0)  ===  rotate(θ + "deg")

x' = x*cosθ-y*sinθ+0 = x*cosθ-y*sinθ
y' = x*sinθ+y*cosθ+0 = x*sinθ+y*cosθ

即 rotate 跟 a, b, c, d 有关

### scale

matrix(sx, 0, 0, sy, 0, 0)  ===  scale(sx, sy)

x' = sx * x 

y' = sy * y 

### skew

matrix(1, tan(θy), tan(θx), 1, 0, 0)  === skew(θx + "deg", θy + "deg")

x' = x * tan(θx)

y' = y * tan(θy)


### 可以发现只有 translate 可以改变坐标


