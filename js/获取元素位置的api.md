#### clientTop（无用）

元素顶部边框的宽度（以像素表示）。不包括顶部外边距或内边距，就是 border 宽度

#### offsetTop

它返回当前元素相对于其 offsetParent 元素的顶部内边距的距离。

 offsetParent 是一个只读属性，返回一个指向最近的（closest，指包含层级上的最近）包含该元素的定位元素。如果没有定位的元素，则 offsetParent 为最近的 table, table cell 或body元素
 
 当元素的 style.display 设置为 "none" 时，offsetParent 返回 null
 
 在 Webkit 中，如果元素为隐藏的（该元素或其祖先元素的 style.display 为 "none"），或者该元素的 style.position 被设为 "fixed"，则该属性返回 null。
 
 #### scrollTop
 
 滚动过的距离
 
 
 #### getBoundingClientRect() 方法（最实用）
 
 返回一个对象 rectObject，rectObject.top 返回当前元素距离屏幕视图顶部的距离
